package com.chinwe.lib_slide_back.external

import android.os.Parcel
import android.os.Parcelable
import com.chinwe.lib_slide_back.SlideBackConfig
import com.chinwe.lib_slide_back.basics.IconLocationInfo
import com.chinwe.lib_slide_back.basics.TouchRegionInfo

/**
 * ============================
 * 一些例外配置
 * ============================
 */
data class ExternalSlideBackInfo(
    val damp: Float = SlideBackConfig.DEFAULT_DAMP,
    val loot: Boolean = SlideBackConfig.DEFAULT_LOOT,
    val drawable: Int = SlideBackConfig.DEFAULT_BACK_ICON,
    val width: Int = SlideBackConfig.DEFAULT_BACK_ICON_WIDTH,
    val touchRegionInfo: TouchRegionInfo,
    val iconLocationInfo: IconLocationInfo
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        TODO("touchRegionInfo"),
        TODO("iconLocationInfo")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(damp)
        parcel.writeByte(if (loot) 1 else 0)
        parcel.writeInt(drawable)
        parcel.writeInt(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExternalSlideBackInfo> {
        override fun createFromParcel(parcel: Parcel): ExternalSlideBackInfo {
            return ExternalSlideBackInfo(parcel)
        }

        override fun newArray(size: Int): Array<ExternalSlideBackInfo?> {
            return arrayOfNulls(size)
        }
    }
}
