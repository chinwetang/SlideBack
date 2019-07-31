package com.chinwe.lib_slide_back

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import com.chinwe.lib_slide_back.basics.IconLocationInfo
import com.chinwe.lib_slide_back.basics.TouchRegionInfo
import com.chinwe.lib_slide_back.external.ExternalSlideBackManager
import com.chinwe.lib_slide_back.strategy.DefaultSlideBackStrategy
import com.chinwe.lib_slide_back.strategy.SlideBackStrategy
import com.chinwe.lib_slide_back.strategy.WrapperSlideBackStrategy
import com.chinwe.lib_slide_back.utils.LogUtils
import com.chinwe.lib_slide_back.utils.Preconditions
import com.chinwe.lib_slide_back.utils.ScreenUtils

/**
 * ===========================================
 * 参数配置类
 */
object SlideBackConfig {
    /**
     * 默认阻尼
     */
    val DEFAULT_DAMP = 0.3F
    /**
     * 默认返回icon
     */
    val DEFAULT_BACK_ICON=R.mipmap.back

    /**
     * 默认返回icon宽度
     */
    val DEFAULT_BACK_ICON_WIDTH=0
    /**
     * 默认是否抢夺事件
     */
    val DEFAULT_LOOT=false

    var mApplication: Application? = null
        get() {
            Preconditions.checkNotNull(mApplication, "Please call the AutoSizeConfig#init() first")
            return field
        }
    /**
     * 管理额外配置
     */
    val mExternalAdaptManager = ExternalSlideBackManager()

    /**
     * 设备的屏幕总宽度, 单位 px
     */
    var mScreenWidth: Int = 0

    /**
     * 设备的屏幕总高度, 单位 px, 如果 [.isUseDeviceSize] 为 `false`, 屏幕总高度会减去状态栏的高度
     */
    var mScreenHeight: Int = 0
    /**
     * 是否优先抢夺事件
     */
    var loot = DEFAULT_LOOT
    /**
     * 手指滑动距离乘以这个系数等于icon移动距离
     */
    var damp = DEFAULT_DAMP
    /**
     * 返回icon
     */
    var drawable = DEFAULT_BACK_ICON
    /**
     * 设置返回键的宽度
     */
    var width = DEFAULT_BACK_ICON_WIDTH
    /**
     *
     */
    private lateinit var  callbackImpl:SlideBackLifecycleCallbackImpl
    /**
     *  热插拔
     */
    var isStop = false
    /**
     * 触摸事件触发的区域
     */
    lateinit var touchRegionInfo: TouchRegionInfo
    /**
     * icon 出现的位置左上角
     */
    lateinit var iconLocationInfo: IconLocationInfo

    /**
     * 屏幕方向
     */
    var isVertical: Boolean = false

    var mOnBackListener: onBackListener? = null
        set(value) {
            Preconditions.checkNotNull(value, "onBackListener == null")
            field = value
        }

    fun init(
        application: Application,
        loot: Boolean = DEFAULT_LOOT,
        stategy: SlideBackStrategy = WrapperSlideBackStrategy(
            DefaultSlideBackStrategy()
        )
    ): SlideBackConfig {
        Preconditions.checkArgument(mScreenWidth == 0, "SlideBackConfig#init() can only be called once")
        Preconditions.checkNotNull(application, "application == null")
        this.mApplication = application
        this.loot = loot

        this.isVertical = application.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val screenSize = ScreenUtils.getScreenSize(application)
        mScreenWidth = screenSize[0]
        mScreenHeight = screenSize[1]

        LogUtils.d("screenWidth = $mScreenWidth, screenHeight = $mScreenHeight")

        this.touchRegionInfo= TouchRegionInfo()
        this.iconLocationInfo=IconLocationInfo()

        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onLowMemory() {
            }

            override fun onConfigurationChanged(newConfig: Configuration?) {
                if (newConfig != null) {
                    isVertical = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
                    val screenSize = ScreenUtils.getScreenSize(application)
                    mScreenWidth = screenSize[0]
                    mScreenHeight = screenSize[1]
                }
            }

        })
        callbackImpl= SlideBackLifecycleCallbackImpl(stategy)
        application.registerActivityLifecycleCallbacks(callbackImpl)
        return this
    }

    /**
     * 是否打印 Log
     *
     * @param log `true` 为打印
     */
    fun setLog(log: Boolean): SlideBackConfig {
        LogUtils.setDebug(log)
        return this
    }

    /**
     * 重新开始框架的运行
     * 框架具有 热插拔 特性, 支持在项目运行中动态停止和重新启动
     */
    fun restart() {
        Preconditions.checkNotNull(callbackImpl, "Please call the SlideBackConfig#init() first")
        synchronized(this) {
            if (isStop) {
                mApplication!!.registerActivityLifecycleCallbacks(callbackImpl)
                isStop = false
            }
        }
    }

    /**
     * 停止框架的运行
     * 框架具有 热插拔 特性, 支持在项目运行中动态停止和重新启动适配功能
     */
    fun stop(activity: Activity) {
        Preconditions.checkNotNull(callbackImpl, "Please call the SlideBackConfig#init() first")
        synchronized(this) {
            if (!isStop) {
                mApplication!!.unregisterActivityLifecycleCallbacks(callbackImpl)
                isStop = true
            }
        }
    }

    /**
     * 设置屏幕适配逻辑策略类
     *
     * @param slideBackStrategy [SlideBackStrategy]
     */
    fun setAutoAdaptStrategy(slideBackStrategy: SlideBackStrategy): SlideBackConfig {
        Preconditions.checkNotNull(slideBackStrategy, "slideBackStrategy == null")
        Preconditions.checkNotNull(callbackImpl, "Please call the SlideBackConfig#init() first")
        callbackImpl.setAutoAdaptStrategy(WrapperSlideBackStrategy(slideBackStrategy))
        return this
    }


}