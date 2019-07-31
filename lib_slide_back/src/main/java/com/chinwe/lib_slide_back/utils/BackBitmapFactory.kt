package com.chinwe.lib_slide_back.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object BackBitmapFactory{

    val mBitmapCache= mutableMapOf<String,Bitmap>()
    val mBitmapWidthCache= mutableMapOf<String,Int>()


    internal fun getBitmap(res: Resources, drawable: Int, width: Int = 0): Bitmap {
        val key="$drawable$width"
        if(mBitmapCache[key]!=null)
            return mBitmapCache[key]!!
        val options = BitmapFactory.Options()
        val outWidth=getBitmapWidth(res,drawable)
        options.inJustDecodeBounds = false
        options.inDensity = outWidth
        options.inTargetDensity = when {
            width > 0 -> width
            else -> outWidth
        }
        mBitmapCache[key]=BitmapFactory.decodeResource(res, drawable, options)
        return mBitmapCache[key]!!
    }

    private fun getBitmapWidth(res: Resources, drawable: Int): Int {
        val key="$drawable"
        if(mBitmapWidthCache[key]!=null)
            return mBitmapWidthCache[key]!!
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, drawable, options)
        mBitmapWidthCache[key]=options.outWidth
        return mBitmapWidthCache[key]!!
    }

}