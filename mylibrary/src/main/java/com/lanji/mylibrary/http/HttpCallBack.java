package com.lanji.mylibrary.http;

public interface HttpCallBack<T> {
    public void onSuccess(T t);
    public void onError(String msg);
}
