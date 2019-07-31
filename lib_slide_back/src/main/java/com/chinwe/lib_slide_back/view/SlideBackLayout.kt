package com.chinwe.lib_slide_back.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent
import com.chinwe.lib_slide_back.basics.IconLocationInfo
import com.chinwe.lib_slide_back.basics.TouchRegionInfo

/**
 * 作为界面的根节点，目前只想到这种办法比较靠谱
 */
open class SlideBackLayout(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    var iconLocationInfo: IconLocationInfo? = null
    var touchEventImpl: TouchEventImpl? = null
    var prospectView: ProspectView? = null

    fun initData(
        damp: Float,
        drawable: Int,
        backWidth: Int,
        iconLocationInfo: IconLocationInfo,
        touchRegionInfo: TouchRegionInfo,
        prospectView: ProspectView
    ) {
        this.prospectView = prospectView
        this.iconLocationInfo = iconLocationInfo
        this.touchEventImpl = TouchEventImpl(context, damp, drawable, backWidth, touchRegionInfo)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return touchEventImpl?.onTouchEvent(ev!!, true) { } ?: super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return touchEventImpl?.onTouchEvent(event!!) { offsetX ->
            prospectView?.pointOffsetX = offsetX
        } ?: super.onTouchEvent(event)
    }
}