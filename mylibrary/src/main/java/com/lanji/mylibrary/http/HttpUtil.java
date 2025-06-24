package com.lanji.mylibrary.http;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lanji.mylibrary.utils.LogUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class HttpUtil {

    public static void Request(Call call, HttpCallBack callBack) {
        Request(null, call, callBack, false);
    }

    public static void Request(Context mContext, Call call, HttpCallBack callBack) {
        Request(mContext, call, callBack, true);
    }

    private static void Request(Context mContext, Call call, HttpCallBack callBack, boolean isToask) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull retrofit2.Response response) {
                LogUtils.i("url:"+call.request().url());
                if (response.isSuccessful()) {
                    BaseModel model = (BaseModel) response.body();
                    if (model == null) {
                        if (isToask && mContext != null) {
                            Toast.makeText(mContext, "model=null", Toast.LENGTH_SHORT).show();
                        }
                        if (callBack != null) callBack.onError("model=null");
                        return;
                    }
                    LogUtils.i("model:"+model.toString());
                    if (model.code == 200) {
                        if (isToask && mContext != null && !TextUtils.isEmpty(model.message)) {
                            Toast.makeText(mContext, model.message, Toast.LENGTH_SHORT).show();
                        }
                        if (callBack != null) callBack.onSuccess(model);
                    } else {
                        if (isToask && mContext != null) {
                            Toast.makeText(mContext, "code=" + model.code, Toast.LENGTH_SHORT).show();
                        }
                        if (callBack != null) callBack.onError("code=" + model.code);
                    }
                } else {
                    LogUtils.i("response is no isSuccessful");
                    if (isToask && mContext != null) {
                        Toast.makeText(mContext, "http is no isSuccessful " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    if (callBack != null)
                        callBack.onError("http is no isSuccessful " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                LogUtils.i("onFailure:"+call.request().url()+","+t.getMessage());
                if (isToask && mContext != null) {
                    Toast.makeText(mContext, "http onFailure " + t.toString(), Toast.LENGTH_SHORT).show();
                }
                if (callBack != null) callBack.onError("http onFailure " + t.toString());
            }
        });
    }
}

