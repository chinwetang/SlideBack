package com.chinwe.lib_slide_back.utils

import android.util.Log

internal object LogUtils{

    private val TAG = "AndroidAutoSize"
    private var debug: Boolean = false

    fun isDebug(): Boolean {
        return debug
    }

    fun setDebug(debug: Boolean) {
        LogUtils.debug = debug
    }

    fun d(message: String) {
        if (debug) {
            Log.d(TAG, message)
        }
    }

    fun w(message: String) {
        if (debug) {
            Log.w(TAG, message)
        }
    }

    fun e(message: String) {
        if (debug) {
            Log.e(TAG, message)
        }
    }
}