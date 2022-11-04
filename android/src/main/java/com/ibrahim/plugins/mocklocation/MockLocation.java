package com.ibrahim.plugins.mocklocation;

import android.util.Log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings.Secure;

import com.getcapacitor.JSArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MockLocation {

    private Context mContext;

    MockLocation(Context context) {
        this.mContext = context;
    }

    public JSONObject check(JSArray whiteList) throws JSONException {
        JSONObject checkResult = new JSONObject();
        if (android.os.Build.VERSION.SDK_INT < 18) {
            if (Secure.getString(mContext.getContentResolver(), Secure.ALLOW_MOCK_LOCATION).equals("0")) {
                checkResult.put("isMockDetected", false);
            } else {
                checkResult.put("isMockDetected", true);
            }
            return checkResult;
        } else {
            JSONArray mocks = areThereMockPermissionApps(mContext, whiteList);
            checkResult.put("isMockDetected", mocks.length() > 0);
            if (mocks.length() > 0) {
                checkResult.put("mocks", mocks);
            }
            return checkResult;
        }
    }

    public static JSONArray areThereMockPermissionApps(Context context, JSArray whiteList) throws JSONException {
        JSONArray mocks = new JSONArray();

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

                // Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;

                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        // Check for System App
                        if (!((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)) {
                            if (requestedPermissions[i]
                                    .equals("android.permission.ACCESS_MOCK_LOCATION")
                                    && !applicationInfo.packageName.equals(context.getPackageName())
                                    && !isWhiteListed(whiteList, applicationInfo.packageName)) {

                                JSONObject mock = new JSONObject();
                                mock.put("name", pm.getApplicationLabel(applicationInfo).toString());
                                mock.put("package", applicationInfo.packageName);
                                mocks.put(mock);
                            }
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("Got exception ", e.getMessage());
            }
        }
        return mocks;
    }

    private static boolean isWhiteListed(JSArray whiteList, String item) throws JSONException {
        for (int i = 0; i < whiteList.length(); i++) {
            if (whiteList.get(i).toString().equals(item)) {
                return true;
            }
        }
        return false;
    }
}
