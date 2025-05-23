package com.lanji.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

	private SharedPreferences mShared = null;
	private static SharedUtil mUtil = null;



	public static synchronized SharedUtil getInstance(Context context) {
		if (mUtil == null) {
			mUtil = new SharedUtil(context.getApplicationContext());
		}
		return mUtil;
	}

	private SharedUtil(Context ctx) {
		mShared = ctx.getSharedPreferences("LAN_JING_DATA", Context.MODE_PRIVATE);
	}

	public void clear(){
		mShared.edit().clear().commit();
	}

	public boolean getBoolean(String key, boolean defValue) {
		return mShared.getBoolean(key, defValue);
	}

	public void putBoolean(String key, boolean value) {
		mShared.edit().putBoolean(key, value).commit();
	}

	public void remove(String key) {
		mShared.edit().remove(key).commit();
	}

	public float getFloat(String key, float defValue) {
		return mShared.getFloat(key, defValue);
	}

	public void putFloat(String key, float value) {
		mShared.edit().putFloat(key, value).commit();
	}

	public long getLong(String key, long defValue) {
		return mShared.getLong(key, defValue);
	}

	public void putLong(String key, long value) {
		mShared.edit().putLong(key, value).commit();
	}

	public int getInt(String key, int defValue) {
		return mShared.getInt(key, defValue);
	}

	public void putInt(String key, int value) {
		mShared.edit().putInt(key, value).commit();
	}

	public String getString(String key, String defValue) {
		return mShared.getString(key, defValue);
	}

	public void putString(String key, String value) {
		mShared.edit().putString(key, value).commit();
	}
}
