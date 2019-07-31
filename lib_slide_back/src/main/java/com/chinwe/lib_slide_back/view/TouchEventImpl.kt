package com.chinwe.lib_slide_back.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.ViewConfiguration
import com.chinwe.lib_slide_back.basics.TouchRegionInfo
import com.chinwe.lib_slide_back.internal.CustomSlideBack
import com.chinwe.lib_slide_back.utils.BackBitmapFactory

/**
 * 触摸事件的集中处理
 */
class TouchEventImpl(
    val context: Context,
    val damp: Float,
    val drawable: Int,
    val width: Int,
    val touchRegionInfo: TouchRegionInfo

) {

    var dX = 0f
    var dY = 0f

    var offsetX = Float.MIN_VALUE

    /**
     * 可能是一个侧滑返回事件
     */
    var possibleIsSlideslip = false

    /**
     * 确定是一个侧滑返回事件
     */
    var isSlideslip = false


    val backBitmapWidth = BackBitmapFactory.getBitmap(context.resources, drawable, width).width


    fun onTouchEvent(event: MotionEvent, isIntercept: Boolean = false, call: (offsetX: Float) -> Unit): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                return if (event.x < touchRegionInfo.left ||
                    event.x > touchRegionInfo.right ||
                    event.y < touchRegionInfo.top ||
                    event.y > touchRegionInfo.bottom
                ) {
                    //超出触摸区域
                    false
                } else {
                    dX = event.x
                    dY = event.y
                    possibleIsSlideslip = true
                    //如果是拦截监听，那么目前还达不到拦截的要求
                    !isIntercept
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (!possibleIsSlideslip) {
                    return false
                }
                if (offsetX == Float.MIN_VALUE && (event.x < dX || Math.abs(event.x - dX) < 2 * Math.abs(event.y - dY))) {
                    //未触发事件时，垂直方向滑动距离大于水平的1/2，判定不是侧滑返回事件
                    possibleIsSlideslip = false
                }
                if (isSlideslip || (possibleIsSlideslip && event.x - dX > ViewConfiguration.get(context).scaledTouchSlop * 1.5f)) {
                    //触发事件之后
                    isSlideslip=true
                    offsetX = event.x - dX
                    call(offsetX)
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (!possibleIsSlideslip) {
                    return false
                }
                when {
                    offsetX < 0 -> {
                    }
                    offsetX * damp < backBitmapWidth -> {
                        ValueAnimator.ofFloat(offsetX, 0f).run {
                            addUpdateListener {
                                call(it.animatedValue as Float)
                            }
                            start()
                        }
                    }
                    else -> {
                        if (context is Activity) {
                            if (context is CustomSlideBack) {
                                if (!context.backCall()) {
                                    context.onBackPressed()
                                }
                            } else {
                                context.onBackPressed()
                            }
                        }
                    }
                }
                isSlideslip=false
                possibleIsSlideslip = false
                call(0f)
                offsetX = Float.MIN_VALUE
            }
            else -> {
            }
        }
        return false
    }
}