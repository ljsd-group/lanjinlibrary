package com.lanji.mylibrary.instance;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lanji.mylibrary.utils.LogUtils;

import java.util.Map;

public class AppEvent {
    private static AppEvent mAppEvent;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Context context;


    //Notice   google-services.json in app, AND   id 'com.google.gms.google-services' in build.gradle
    public static AppEvent getInstance() {
        if (mAppEvent == null)
            mAppEvent = new AppEvent();

        return mAppEvent;
    }

    public void init(Context context) {
        this.context=context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void logEventFirebase(String eventName, Bundle eventParams) {
        if (mFirebaseAnalytics == null) {
            throw new RuntimeException("AppEvent not init");
        }
        mFirebaseAnalytics.logEvent(eventName, eventParams);
    }
    public void logEventAppFire(String eventName) {
        logEventAppFire(eventName,null);
    }
    public void logEventAppFire(String eventName , Map<String, Object> eventParams) {
        if (context == null) {
            throw new RuntimeException("AppEvent not init");
        }
        AppsFlyerRequestListener listener=new AppsFlyerRequestListener ()
        {
            @Override
            public void onSuccess() {
                LogUtils.v("Appfire Event Success-->eventName:"+eventName);
            }

            @Override
            public void onError(int p0, @NonNull String p1) {
                LogUtils.v("Appfire Event Error:" +
                        "error code: " + p0
                        + "error description: " + p1);
            }


        };
        AppsFlyerLib.getInstance().logEvent(context,eventName,eventParams,listener);
    }
}
