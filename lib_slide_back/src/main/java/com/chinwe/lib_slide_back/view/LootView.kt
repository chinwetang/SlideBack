package com.chinwe.lib_slide_back.view

import android.content.Context
import android.view.MotionEvent
import com.chinwe.lib_slide_back.basics.IconLocationInfo
import com.chinwe.lib_slide_back.basics.TouchRegionInfo

/**
 * 强势拦截
 */
internal class LootView(
    context: Context,
    damp: Float,
    drawable: Int,
    backWidth: Int,
    iconLocationInfo: IconLocationInfo,
    touchRegionInfo: TouchRegionInfo
) : ProspectView(
    context,
    damp,
    drawable,
    backWidth,
    iconLocationInfo,
    touchRegionInfo
){
    val touchEventImpl = TouchEventImpl(
        context,
        damp,
        drawable,
        backWidth,
        touchRegionInfo
    )

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return touchEventImpl.onTouchEvent(event!!) { offsetX ->
            pointOffsetX = offsetX
        }
    }
}