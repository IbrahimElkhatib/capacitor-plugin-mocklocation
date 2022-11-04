package com.ibrahim.plugins.mocklocation;

import android.util.Log;

public class MockLocation {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
