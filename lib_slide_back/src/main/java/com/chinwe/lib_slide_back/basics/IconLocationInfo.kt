package com.chinwe.lib_slide_back.basics

import com.chinwe.lib_slide_back.SlideBackConfig

/**
 * icon 出现的位置左上角
 */
data class IconLocationInfo(val lacation:Int= SlideBackConfig.mScreenHeight /2){

    constructor(lacationRatio:Float):this(lacationRatio.toInt()* SlideBackConfig.mScreenHeight)

}