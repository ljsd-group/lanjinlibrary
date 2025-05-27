package com.lanji.mylibrary.instance;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanji.mylibrary.model.EventModel;
import com.lanji.mylibrary.utils.LogUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppEvent {
    private final String TAG = "AppEvent";
    private static AppEvent mAppEvent;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Context context;
    private String filename = "event.json";
    private List<EventModel> eventModelList;
    private ExecutorService singleThreadExecutor;

    //Notice   google-services.json in app, AND   id 'com.google.gms.google-services' in build.gradle
    public static AppEvent getInstance() {
        if (mAppEvent == null)
            mAppEvent = new AppEvent();

        return mAppEvent;
    }

    public void init(Context context) {
        this.context = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        getJson(context);
    }

    public void addEvent(String eventname) {
        if (eventModelList == null) {
            LogUtils.i(TAG, "eventModel is null, not init  or  event.json no exiest");
            return;
        }
        for (EventModel model : eventModelList) {
            if (model.eventType == null || model.eventType.isEmpty()) {
                break;
            }
            if (eventname.equals(model.eventName)) {
                for (EventModel.EventType params : model.eventType) {
                    List<EventModel.EventParams> eventParams = params.eventParams;
                    if ("appfire".equals(params.typeName)) {
                        if (eventParams == null || eventParams.isEmpty()) {
                            addEventAppFire(model.eventName);
                        } else {
                            Map<String, Object> bundle = new HashMap<>();
                            for (EventModel.EventParams p : eventParams) {
                                bundle.put(p.paramsKey, p.paramsValue);
                            }
                            addEventAppFire(model.eventName, bundle);
                        }

                    } else if ("firebase".equals(params.typeName)) {
                        if (eventParams == null || eventParams.isEmpty()) {
                            addEventFirebase(model.eventName, null);
                        } else {
                            Bundle bundle = new Bundle();
                            for (EventModel.EventParams p : eventParams) {
                                bundle.putString(p.paramsKey, p.paramsValue);
                            }
                            addEventFirebase(model.eventName, bundle);
                        }
                    }
                }
            }
        }
    }

    public void addEventFirebase(String eventName, Bundle eventParams) {
        if (mFirebaseAnalytics == null) {
            throw new RuntimeException("AppEvent not init");
        }
        LogUtils.i("Firebase event---> eventName:" + eventName + ",eventParams:" + eventParams.toString());
        mFirebaseAnalytics.logEvent(eventName, eventParams);
    }

    public void addEventAppFire(String eventName) {
        addEventAppFire(eventName, null);
    }

    public void addEventAppFire(String eventName, Map<String, Object> eventParams) {
        if (context == null) {
            throw new RuntimeException("AppEvent not init");
        }
        LogUtils.i("AppFire event---> eventName:" + eventName + ",eventParams:" + eventParams.toString());
        AppsFlyerRequestListener listener = new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                LogUtils.v("Appfire Event Success-->eventName:" + eventName);
            }

            @Override
            public void onError(int p0, @NonNull String p1) {
                LogUtils.v("Appfire Event Error:" +
                        "error code: " + p0
                        + "error description: " + p1);
            }


        };
        AppsFlyerLib.getInstance().logEvent(context, eventName, eventParams, listener);
    }

    private void getJson(Context context) {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(filename));
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line = "";
                    String Result = "";
                    while ((line = bufReader.readLine()) != null)
                        Result += line;
                    eventModelList = new Gson().fromJson(Result, new TypeToken<List<EventModel>>() {
                    }.getType());
                    LogUtils.i(TAG, "eventModel:" + eventModelList.size());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.i(TAG, e.toString());
                }
            }
        });
    }
}
