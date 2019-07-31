package com.chinwe.lib_slide_back.view

import android.content.Context
import android.graphics.Canvas
import android.view.View
import com.chinwe.lib_slide_back.basics.IconLocationInfo
import com.chinwe.lib_slide_back.basics.TouchRegionInfo
import com.chinwe.lib_slide_back.utils.BackBitmapFactory

/**
 * 用于添加到 decorview 内最上层显示back icon的view
 */
open class ProspectView(context: Context,
                   val damp: Float,
                   val drawable: Int,
                   val backWidth: Int,
                   val iconLocationInfo: IconLocationInfo,
                   val touchRegionInfo: TouchRegionInfo) : View(context) {


    var pointOffsetX:Float=Int.MIN_VALUE.toFloat()
    set(value) {
        field=value
        invalidate()
    }

    val backBitmap=BackBitmapFactory.getBitmap(resources,drawable,backWidth)

    val backDrawable=
        BackDrawable(backBitmap, backBitmap.width, iconLocationInfo, damp)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        backDrawable.setBounds(left,top,right,bottom)
    }
    override fun onDraw(canvas: Canvas?) {
        backDrawable.pointOffsetX=pointOffsetX
        backDrawable.draw(canvas!!)
    }

}