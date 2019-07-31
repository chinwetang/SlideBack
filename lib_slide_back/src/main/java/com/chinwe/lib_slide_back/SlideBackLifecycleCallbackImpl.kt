package com.chinwe.lib_slide_back

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.chinwe.lib_slide_back.strategy.SlideBackStrategy


class SlideBackLifecycleCallbackImpl(var mSlideBackStrategy: SlideBackStrategy?) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if(activity==null)
            return
        mSlideBackStrategy?.apply(activity)
    }


    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }
    /**
     * 设置逻辑策略类
     *
     * @param slideBackStrategy [SlideBackStrategy]
     */
    fun setAutoAdaptStrategy(slideBackStrategy: SlideBackStrategy) {
        mSlideBackStrategy = slideBackStrategy
    }

}