package com.chinwe.lib_slide_back.utils

import android.content.res.Resources
import android.util.TypedValue

object Utils {

    internal fun dpToPixel(dp: Float, res: Resources = Resources.getSystem()): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            res.displayMetrics
        )
    }

}