package com.chinwe.lib_slide_back.internal;

import com.chinwe.lib_slide_back.SlideBackConfig;
import com.chinwe.lib_slide_back.basics.IconLocationInfo;
import com.chinwe.lib_slide_back.basics.TouchRegionInfo;

/**
 * 这个类使用java8可以更好的兼容
 */
public interface CustomSlideBack {
    /**
     * 手指滑动距离乘以这个系数等于icon移动距离
     */
    default float damp(){
        return SlideBackConfig.INSTANCE.getDEFAULT_DAMP();
    }

    /**
     * 是否优先抢夺事件，如果存在 [SlideBackLayout]，这个属性就不管用了
     */
    default boolean loot(){
        return SlideBackConfig.INSTANCE.getDEFAULT_LOOT();
    }
    /**
     * 返回icon
     */
    default int backDrawable(){
        return SlideBackConfig.INSTANCE.getDEFAULT_BACK_ICON();
    }
    /**
     * 设置返回键的宽度
     */
    default int backDrawableWidth(){
        return SlideBackConfig.INSTANCE.getDEFAULT_BACK_ICON_WIDTH();
    }
    /**
     * 相应回调事件，返回true表示消费拦截
     */
    default boolean backCall(){
        return false;
    }
    /**
     * 触摸事件触发的区域
     */
    default TouchRegionInfo touchRegionInfo(){
        return new TouchRegionInfo();
    }
    /**
     * icon 出现的位置左上角
     */
    default IconLocationInfo backLocationInfo(){
        return new IconLocationInfo();
    }
}
