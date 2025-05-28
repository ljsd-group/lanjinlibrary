package com.lanji.mylibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.Base64;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Method {

    public static final String ENDATA = "uasydbwqiu@&(==sald51623233as@@++sadqwiu982--aslkdjilmeik56a4d564a1908=--=245456sd213a4d11-68a@@###";

    private static final String SECRET_KEY = "mnS=cr3tKey@2345"; // 16位
    private static final String INIT_VECTOR = "initV@ctor=12345"; // 16位

    public static String encrypt() {
        // 1. 解析公钥字符串
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal((ENDATA + System.currentTimeMillis() + ((int) (Math.random() * 900000) + 100000)).getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {

        }
        return "";
    }

    public static String getCountryCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkCountryIso();
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static Context getLLL(Context cc){
        String language =getLaun(cc);
        Configuration config = cc.getResources().getConfiguration();
        config.setLocale(new Locale(language));
        Context context = cc.createConfigurationContext(config);
        return context;
    }
    public static void saveLaun(Context context, String laun) {
        SharedPreferences sp = context.getSharedPreferences("STOCK_LANJIN_LAUN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("stock_view_laun", laun);
        editor.commit();
    }

    public static String getLaun(Context context) {
        SharedPreferences sp = context.getSharedPreferences("STOCK_LANJIN_LAUN", Context.MODE_PRIVATE);
        return sp.getString("stock_view_laun",  Locale.getDefault().getLanguage());
    }
}
