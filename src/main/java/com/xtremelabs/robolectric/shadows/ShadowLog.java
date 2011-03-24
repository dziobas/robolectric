package com.xtremelabs.robolectric.shadows;

import android.util.Log;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;


@Implements(Log.class)
public class ShadowLog {
    @Implementation
    public static void v(String TAG, String message) {
        System.out.println(TAG + ":\t " + message);
    }

    @Implementation
    public static void v(String TAG, String message, Throwable thr) {
        System.out.println(TAG + ":\t " + message + " \n" + thr.toString());
    }

    @Implementation
    public static void d(String TAG, String message) {
        System.out.println(TAG + ":\t " + message);
    }

    @Implementation
    public static void d(String TAG, String message, Throwable thr) {
        System.out.println(TAG + ":\t " + message + " \n" + thr.toString());
    }

    @Implementation
    public static void i(String TAG, String message) {
        System.out.println(TAG + ":\t " + message);
    }

    @Implementation
    public static void i(String TAG, String message, Throwable thr) {
        System.out.println(TAG + ":\t " + message + " \n" + thr.toString());
    }

    @Implementation
    public static void e(String TAG, String message) {
        System.err.println(TAG + ":\t " + message);
    }

    @Implementation
    public static void e(String TAG, String message, Throwable thr) {
        System.err.println(TAG + ":\t " + message + " \n" + thr.toString());
    }

    @Implementation
    public static void wtf(String TAG, String message) {
        System.err.println(TAG + ":\t " + message);
    }

    @Implementation
    public static void wtf(String TAG, String message, Throwable thr) {
        System.err.println(TAG + ":\t " + message + " \n" + thr.toString());
    }
}
