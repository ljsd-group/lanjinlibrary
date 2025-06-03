package com.lanji.mylibrary.interfaces;

public interface LoginCallBack {

    void onSuccess(String accToken);

    void onError(String errorMsg);
}
