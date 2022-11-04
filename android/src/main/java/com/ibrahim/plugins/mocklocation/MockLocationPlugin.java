package com.ibrahim.plugins.mocklocation;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONException;
import org.json.JSONObject;

@CapacitorPlugin(name = "MockLocation")
public class MockLocationPlugin extends Plugin {
    private MockLocation implementation;

    @Override
    public void load() {
        implementation = new MockLocation(getContext());
    }

    @PluginMethod
    public void check(PluginCall call) {
        try {
            JSArray whiteList = call.getArray("whiteList", new JSArray());
            JSObject ret = new JSObject();
            JSONObject result = implementation.check(whiteList);
            ret.put("mockDetected", result.getBoolean("isMockDetected"));
            if (result.getBoolean("isMockDetected")) {
                ret.put("mocks", result.getJSONArray("mocks"));
            }
            call.resolve(ret);
        } catch (JSONException e) {
            call.reject(e.getMessage());
        }
    }
}
