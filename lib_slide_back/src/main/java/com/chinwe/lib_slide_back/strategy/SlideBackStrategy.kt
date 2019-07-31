package com.chinwe.lib_slide_back.strategy

import android.app.Activity

interface SlideBackStrategy{
    /**
     * 开始设置侧滑返回逻辑
     */
    fun apply(activity: Activity)
}