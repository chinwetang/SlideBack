package com.chinwe.lib_slide_back

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.chinwe.lib_slide_back.basics.IconLocationInfo
import com.chinwe.lib_slide_back.basics.TouchRegionInfo
import com.chinwe.lib_slide_back.external.ExternalSlideBackInfo
import com.chinwe.lib_slide_back.internal.CustomSlideBack
import com.chinwe.lib_slide_back.view.LootView
import com.chinwe.lib_slide_back.view.ProspectView
import com.chinwe.lib_slide_back.utils.Preconditions
import com.chinwe.lib_slide_back.view.TouchEventImpl
import com.chinwe.lib_slide_back.view.SlideBackLayout as SlideBackLayout

/**
 * 核心逻辑实现类
 */
object SlideBack {
    /**
     * 来自单个页面例外的配置
     */
    fun initSlideBackOfCustomSlideBack(activity: Activity, customSlideBack: CustomSlideBack) {
        Preconditions.checkNotNull(customSlideBack, "customAdapt == null")
        initSlideBack(
            activity,
            customSlideBack.damp(),
            customSlideBack.backDrawable(),
            customSlideBack.backDrawableWidth(),
            customSlideBack.backLocationInfo(),
            customSlideBack.touchRegionInfo(),
            customSlideBack.loot()
        )
    }

    /**
     * 来自全局的例外的配置
     */
    fun initSlideBackOfExternalSlideBackInfo(activity: Activity, externalSlideBackInfo: ExternalSlideBackInfo) {
        Preconditions.checkNotNull(externalSlideBackInfo, "externalSlideBackInfo == null")
        initSlideBack(
            activity,
            externalSlideBackInfo.damp,
            externalSlideBackInfo.drawable,
            externalSlideBackInfo.width,
            externalSlideBackInfo.iconLocationInfo,
            externalSlideBackInfo.touchRegionInfo,
            externalSlideBackInfo.loot
        )
    }

    /**
     * 来自全局的例外的配置
     */
    fun initSlideBackOfDefault(activity: Activity) {
        initSlideBack(
            activity,
            SlideBackConfig.damp,
            SlideBackConfig.drawable,
            SlideBackConfig.width,
            SlideBackConfig.iconLocationInfo,
            SlideBackConfig.touchRegionInfo,
            SlideBackConfig.loot
        )
    }

    /**
     * 递归寻找第一个SlideBackLayout
     */
    private fun findSlideBackLayout(viewGroup: View): SlideBackLayout? {
        if (viewGroup !is ViewGroup)
            return null
        if (viewGroup.childCount == 0)
            return null
        if(viewGroup is SlideBackLayout)
            return viewGroup
        var slideBackLayout: SlideBackLayout? = null
        for (i in 0 until viewGroup.childCount) {
            slideBackLayout = findSlideBackLayout(viewGroup.getChildAt(i))
            if (slideBackLayout != null)
                break
        }
        return slideBackLayout
    }

    /**
     * 分发配置
     */
    private fun initSlideBack(
        activity: Activity,
        damp: Float,
        drawable: Int,
        width: Int,
        iconLocationInfo: IconLocationInfo,
        touchRegionInfo: TouchRegionInfo,
        loot: Boolean
    ) {
        val decorView = activity.window.decorView as FrameLayout
        for (i in decorView.childCount - 1 downTo 0) {
            //较重
            if (decorView.getChildAt(i) is ProspectView)
                return
        }
        decorView.post {
            //等一等，等其他view都创建完再盖到上面
            var slideBackLayout: SlideBackLayout? = findSlideBackLayout(decorView)
            if (slideBackLayout != null) {
                //发现第一个对象，直接上
                initSlideBackLayout(
                    activity,
                    damp,
                    drawable,
                    width,
                    iconLocationInfo,
                    touchRegionInfo,
                    decorView,
                    slideBackLayout
                )
                return@post
            }

            if (loot) {
                addLootView(
                    activity,
                    damp,
                    drawable,
                    width,
                    iconLocationInfo,
                    touchRegionInfo,
                    decorView
                )
            } else {
                addProspectView(
                    activity,
                    damp,
                    drawable,
                    width,
                    iconLocationInfo,
                    touchRegionInfo,
                    decorView
                )
            }
        }

    }


    /**
     * 直接对底层decorView做事件监听，属于优先级较低的
     */
    @SuppressLint("ClickableViewAccessibility")
    fun addProspectView(
        activity: Activity,
        damp: Float,
        drawable: Int,
        width: Int,
        iconLocationInfo: IconLocationInfo,
        touchRegionInfo: TouchRegionInfo,
        decorView: FrameLayout
    ) {
        val prospectView = ProspectView(
            activity,
            damp,
            drawable,
            width,
            iconLocationInfo,
            touchRegionInfo
        )
        decorView.addView(
            prospectView,
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        )

        val touchEventImpl = TouchEventImpl(
            activity,
            damp,
            drawable,
            width,
            touchRegionInfo
        )

        decorView.setOnTouchListener { v, event ->
            return@setOnTouchListener touchEventImpl.onTouchEvent(event) { offsetX ->
                prospectView.pointOffsetX = offsetX
            }
        }
    }

    /**
     * 添加一个覆盖在顶层的子View，属于强制夺取事件
     */
    fun addLootView(
        activity: Activity,
        damp: Float,
        drawable: Int,
        width: Int,
        iconLocationInfo: IconLocationInfo,
        touchRegionInfo: TouchRegionInfo,
        decorView: FrameLayout
    ) {
        decorView.addView(
            LootView(
                activity,
                damp,
                drawable,
                width,
                iconLocationInfo,
                touchRegionInfo
            ),
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        )
    }

    /**
     * 初始化根布局
     */
    fun initSlideBackLayout(
        activity: Activity,
        damp: Float,
        drawable: Int,
        width: Int,
        iconLocationInfo: IconLocationInfo,
        touchRegionInfo: TouchRegionInfo,
        decorView: FrameLayout,
        slideBackLayout: SlideBackLayout
    ) {
        for (i in decorView.childCount - 1 downTo 0) {
            if (decorView.getChildAt(i) is ProspectView)
                return
        }
        val prospectView = ProspectView(
            activity,
            damp,
            drawable,
            width,
            iconLocationInfo,
            touchRegionInfo
        )
        decorView.addView(
            prospectView,
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        )
        slideBackLayout.initData(
            damp,
            drawable,
            width,
            iconLocationInfo,
            touchRegionInfo, prospectView
        )
    }
}