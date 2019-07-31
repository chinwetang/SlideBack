package com.chinwe.lib_slide_back.external

import com.chinwe.lib_slide_back.utils.Preconditions

/**
 * 管理额外配置相关
 */
class ExternalSlideBackManager {
    private var mCancelAdaptList = arrayListOf<String>()
    private var mExternalAdaptInfos= mutableMapOf<String,ExternalSlideBackInfo>()
    var isRun = false

    /**
     * 将不需要侧滑返回的Activity添加进来
     */
    @Synchronized
    fun addCancelAdaptOfActivity(targetClass: Class<*>): ExternalSlideBackManager {
        Preconditions.checkNotNull(targetClass, "targetClass == null")
        if (!isRun) {
            isRun = true
        }
        mCancelAdaptList.add(targetClass.canonicalName)
        return this
    }

    /**
     * 将需要单独配置的Activity添加进来
     */
    @Synchronized
    fun addExternalAdaptInfoOfActivity(targetClass: Class<*>, info: ExternalSlideBackInfo): ExternalSlideBackManager {
        Preconditions.checkNotNull(targetClass, "targetClass == null")
        if (!isRun) {
            isRun = true
        }
        mExternalAdaptInfos.put(targetClass.canonicalName, info)
        return this
    }

    /**
     * 判断某个Activity是否被标记为取消
     */
    @Synchronized
    fun isCancelSideslip(targetClass: Class<*>): Boolean {
        Preconditions.checkNotNull(targetClass, "targetClass == null")
        return mCancelAdaptList.contains(targetClass.canonicalName)
    }

    /**
     * 判断某个Activity是否进行了特殊配置
     */
    @Synchronized
    fun getExternalAdaptInfoOfActivity(targetClass: Class<*>): ExternalSlideBackInfo? {
        Preconditions.checkNotNull(targetClass, "targetClass == null")
        return  mExternalAdaptInfos[targetClass.canonicalName]
    }


}