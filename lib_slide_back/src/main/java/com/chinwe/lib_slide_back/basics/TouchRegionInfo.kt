package com.chinwe.lib_slide_back.basics

import com.chinwe.lib_slide_back.SlideBackConfig

/**
 * 触摸事件触发的区域
 */
data class TouchRegionInfo(
    val left: Int = 0,
    val top: Int = 0,
    val right: Int = SlideBackConfig.mScreenWidth,
    val bottom: Int = SlideBackConfig.mScreenHeight
) {

    constructor(leftRatio: Float = 0f, topRatio: Float = 0f, rightRatio: Float = 1f, bottomRatio: Float = 1f) : this(
        (SlideBackConfig.mScreenWidth * leftRatio).toInt(),
        (SlideBackConfig.mScreenHeight * topRatio).toInt(),
        (SlideBackConfig.mScreenWidth * rightRatio).toInt(),
        (SlideBackConfig.mScreenHeight * bottomRatio).toInt()
    )

    constructor() : this(
        0f, 0f, 1f,
        1f
    )

}