package com.my.library_base.utils

import com.tencent.mmkv.MMKV

class MMKVUtils private constructor(id: String) {
    private val mmkv: MMKV?
    fun setValue(key: String?, value: String?) {
        mmkv!!.encode(key, value)
    }

    fun setValue(key: String?, value: Boolean) {
        mmkv!!.encode(key, value)
    }

    fun setValue(key: String?, value: Int) {
        mmkv!!.encode(key, value)
    }

    fun getStringValue(key: String?): String? {
        return mmkv!!.decodeString(key)
    }

    fun getBooleanValue(key: String?): Boolean {
        return mmkv!!.decodeBool(key)
    }

    fun getIntValue(key: String?): Int {
        return mmkv!!.decodeInt(key)
    }

    fun remove(key: String?) {
        mmkv!!.remove(key)
    }

    val allKey: Array<String>?
        get() = mmkv!!.allKeys()

    fun contain(key: String?): Boolean {
        return mmkv!!.contains(key)
    }

    companion object {
        private var mmkvUtils: MMKVUtils? = null
        fun getInstance(id: String): MMKVUtils? {
            if (null == mmkvUtils) {
                mmkvUtils = MMKVUtils(id)
            }
            return mmkvUtils
        }
    }

    init {
        mmkv = MMKV.mmkvWithID(id)
    }
}