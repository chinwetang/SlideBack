package com.chinwe.lib_slide_back.strategy

import android.app.Activity
import com.chinwe.lib_slide_back.SlideBackConfig

/**
 * 包装类，在具体操作之前做一些工作
 */
class WrapperSlideBackStrategy(val autoAdaptStrategy: SlideBackStrategy?) :
    SlideBackStrategy {

    override fun apply(activity: Activity) {
        var onSetupListener = SlideBackConfig.mOnBackListener
        onSetupListener?.onSetupBefore(activity)
        autoAdaptStrategy?.apply(activity)
        onSetupListener?.onSetupAfter(activity)
    }

}