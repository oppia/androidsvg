package com.caverock.androidsvg.util;

import android.util.Log;

public class AndroidLogger implements Logger {
    @Override
    public void logWarning(String tag, String message) {
        Log.w(tag, message);
    }

    @Override
    public void logError(String tag, String message) {
        Log.e(tag, message);
    }

    @Override
    public void logError(String tag, String message, Exception exception) {
        Log.e(tag, message, exception);
    }
}
