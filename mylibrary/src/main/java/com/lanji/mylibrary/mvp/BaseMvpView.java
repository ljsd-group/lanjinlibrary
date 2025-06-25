package com.lanji.mylibrary.mvp;


import com.lanji.mylibrary.http.BaseModel;

/**
 * create by lzx
 * time:2018/7/27
 */
public interface BaseMvpView<T> {
    void showPDialog();

    void closePDialog();

    void onSuccess(T model);

    void onError(String error);
}
