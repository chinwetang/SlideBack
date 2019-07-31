package com.chinwe.lib_slide_back

import android.app.Activity

/**
 * 设置侧滑监听器
 */
interface onBackListener{
    /**
     * 在设置侧滑配前调用
     *
     * @param activity 当前 [Activity]
     */
    fun onSetupBefore( activity: Activity)

    /**
     * 在设置侧滑后调用
     *
     * @param activity 当前 [Activity]
     */
    fun onSetupAfter(activity: Activity)
}