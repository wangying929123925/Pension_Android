package com.warehouse.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author :Cuixiaoyang
 * @date :2019/9/23 9:00
 * @description: 操作SharePreference的工具类
 */
public class SPUtils {
    private static SPUtils instance;
    private SharedPreferences mSharedPreferences, mSafePreferences;
    public static final String FILE_NAME = "GeneralStore";
    public static final String SAFE_FILE_NAME = "SafeStore";
    private SharedPreferences.Editor mEitor, mSafeEidtor;
    private Context mContext;

    public synchronized static SPUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SPUtils(context);
        }
        return instance;
    }


    private SPUtils(Context context) {
        this.mContext = context;
        init();
    }
    public void init() {
        mSharedPreferences = mContext.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEitor = mSharedPreferences.edit();
        mSafePreferences = mContext.getSharedPreferences(SAFE_FILE_NAME, Context.MODE_PRIVATE);
        mSafeEidtor = mSafePreferences.edit();
    }

    public void putString(String key, String value) {
        mEitor.putString(key, value);
        mEitor.commit();
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key,defValue);
    }

    public void putBoolean(String key, Boolean value) {
        mEitor.putBoolean(key, value);
        mEitor.commit();
    }

    public Boolean getBoolean(String key, Boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public void putInt(String key, int value) {
        mEitor.putInt(key, value);
        mEitor.commit();
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

}
