package com.lanji.mylibrary.utils;

import android.util.Log;


public class LogUtils {
    private static String TAG = "log";
    public static boolean ISDEBUG = true;

    public static void i(String msg) {
        if (ISDEBUG)
            Log.i(TAG, msg);
    }
    public static void i(String tag,String msg) {
        if (ISDEBUG)
            Log.i(tag, msg);
    }
    public static void v(String msg) {
        if (ISDEBUG)
            Log.v(TAG, msg);
    }
    public static void v(String tag,String msg) {
        if (ISDEBUG)
            Log.v(tag, msg);
    }
    public static void w(String msg) {
        if (ISDEBUG)
            Log.w(TAG, msg);
    }
    public static void w(String tag,String msg) {
        if (ISDEBUG)
            Log.w(tag, msg);
    }
    public static void e(String msg) {
        if (ISDEBUG)
            Log.e(TAG, msg);
    }
    public static void e(String tag,String msg) {
        if (ISDEBUG)
            Log.e(tag, msg);
    }
}
