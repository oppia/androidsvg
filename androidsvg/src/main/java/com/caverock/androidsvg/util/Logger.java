package com.caverock.androidsvg.util;

public interface Logger {
    void logWarning(String tag, String message);

    void logError(String tag, String message);

    void logError(String tag, String message, Exception exception);
}
