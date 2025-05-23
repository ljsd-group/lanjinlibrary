package com.lanji.mylibrary;

import android.app.Application;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.lanji.mylibrary.utils.LogUtils;

import java.util.Map;

public abstract class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String key=initAppFireKey();
        if(!TextUtils.isEmpty(key)){
            ininAppFire(key);
        }

    }


    private void ininAppFire(String key){
        AppsFlyerConversionListener conversionDataListener=new  AppsFlyerConversionListener(){
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                LogUtils.i("Appfire init  Success");
            }

            @Override
            public void onConversionDataFail(String s) {
                LogUtils.i("Appfire init  Fail "+s);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {

            }

            @Override
            public void onAttributionFailure(String s) {
                LogUtils.i("Appfire init  Failure "+s);
            }
        };

        // 初始化 AppsFlyer SDK
        AppsFlyerLib.getInstance().init(key, conversionDataListener, this);
        AppsFlyerLib.getInstance().start(this);
    }

    public abstract String initAppFireKey();
}
