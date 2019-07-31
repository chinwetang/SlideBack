package com.chinwe.lib_slide_back.strategy

import android.app.Activity
import com.chinwe.lib_slide_back.SlideBack
import com.chinwe.lib_slide_back.SlideBackConfig
import com.chinwe.lib_slide_back.external.ExternalSlideBackInfo
import com.chinwe.lib_slide_back.internal.CancelSlideBack
import com.chinwe.lib_slide_back.internal.CustomSlideBack
import com.chinwe.lib_slide_back.utils.LogUtils
import java.util.*

/**
 * 默认实现
 */
class DefaultSlideBackStrategy : SlideBackStrategy {

    override fun apply(activity: Activity) {

        if (SlideBackConfig.mExternalAdaptManager.isRun) {
            //优先级最高的是例外配置
            if (SlideBackConfig.mExternalAdaptManager.isCancelSideslip(activity.javaClass)) {
                //被标记为不需要该功能的类
                LogUtils.w(String.format(Locale.ENGLISH, "%s canceled the adaptation!", activity.javaClass.name))
                return
            } else {
                var externalSideslipInfo =
                    SlideBackConfig.mExternalAdaptManager.getExternalAdaptInfoOfActivity(activity.javaClass)
                if (externalSideslipInfo != null) {
                    //被标记为单独配置的类
                    LogUtils.d(
                        String.format(
                            Locale.ENGLISH,
                            "%s used %s for adaptation!",
                            activity.javaClass.name,
                            ExternalSlideBackInfo::class.java!!.name
                        )
                    )
                    SlideBack.initSlideBackOfExternalSlideBackInfo(activity, externalSideslipInfo)
                    return
                }
            }
        }


        if(activity is CancelSlideBack){
            //被标记为不需要该功能的类
            LogUtils.w(String.format(Locale.ENGLISH, "%s canceled the adaptation!", activity.javaClass.name))
            return
        }

        if(activity is CustomSlideBack){
            //被标记为单独配置的类
            LogUtils.d(
                String.format(
                    Locale.ENGLISH,
                    "%s implemented by %s!",
                    activity.javaClass.name,
                    CustomSlideBack::class.java!!.name
                )
            )
            SlideBack.initSlideBackOfCustomSlideBack(activity,activity)
        }else{
            //普通配置
            LogUtils.d(String.format(Locale.ENGLISH, "%s used the global configuration.", activity.javaClass.name))
            SlideBack.initSlideBackOfDefault(activity)
        }

    }

}