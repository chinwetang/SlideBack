package com.chinwe.lib_slide_back.view

import android.graphics.*
import android.graphics.drawable.Drawable
import com.chinwe.lib_slide_back.basics.IconLocationInfo

class BackDrawable(
    val backBitmap: Bitmap,
    val backBitmapWidth: Int,
    val iconLocationInfo: IconLocationInfo,
    val damp: Float
) : Drawable() {

    /**
     * 手指滑动的距离
     */
    var pointOffsetX: Float = 0f

    companion object {
        const val mBackWidth = 200f
        const val mBackHeight = 50f

    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas) {
        val offsetX = when {
            pointOffsetX < 0 -> 0f
            pointOffsetX * damp < backBitmapWidth.toFloat() -> pointOffsetX * damp
            else -> backBitmapWidth.toFloat()
        }
        canvas?.drawBitmap(backBitmap, offsetX - backBitmapWidth, iconLocationInfo.lacation.toFloat(), paint)
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

}