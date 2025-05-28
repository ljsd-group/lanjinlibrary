package com.lanji.mylibrary.http;

import retrofit2.Call;
import retrofit2.Callback;

public class HttpUtil<T> {

    public void Request(Call<T> call,HttpCallBack callBack){
        if(callBack!=null)callBack.start();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, retrofit2.Response<T> response) {
                if (response.isSuccessful()) {
                    if(callBack!=null)callBack.onSuccess(response.body());
                }else {
                    if(callBack!=null)callBack.onError("http error "+response.code());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if(callBack!=null)callBack.onError("http throwable "+t.toString());
            }
        });
    }
}
