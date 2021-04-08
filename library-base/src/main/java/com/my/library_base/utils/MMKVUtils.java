package com.my.library_base.utils;

import com.my.library_base.constants.Constants;
import com.tencent.mmkv.MMKV;

public class MMKVUtils {
    private static MMKVUtils mmkvUtils;
    private MMKV mmkv;

    private MMKVUtils(String id) {
        mmkv = MMKV.mmkvWithID(id);
    }

    public static MMKVUtils getInstance() {
        if (null == mmkvUtils) {
            mmkvUtils = new MMKVUtils(Constants.MMKV_ID);
        }
        return mmkvUtils;
    }

    public void setValue(String key, String value) {
        mmkv.encode(key, value);
    }

    public void setValue(String key, boolean value) {
        mmkv.encode(key, value);
    }

    public void setValue(String key, int value) {
        mmkv.encode(key, value);
    }

    public String getStringValue(String key) {
        return mmkv.decodeString(key, "");
    }

    public boolean getBooleanValue(String key) {
        return mmkv.decodeBool(key, false);
    }

    public int getIntValue(String key) {
        return mmkv.decodeInt(key, -1);
    }

    public void remove(String key) {
        mmkv.remove(key);
    }

    public String[] getAllKey() {
        return mmkv.allKeys();
    }

    public boolean contain(String key) {
        return mmkv.contains(key);
    }
}
