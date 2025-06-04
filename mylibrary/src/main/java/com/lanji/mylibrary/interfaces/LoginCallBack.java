package com.lanji.mylibrary.interfaces;

public interface LoginCallBack {
    void onStart();

    void onSuccess(String accToken);

    void onError(String errorMsg);
}
