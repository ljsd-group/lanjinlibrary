package com.lanji.library.login;


import com.lanji.mylibrary.http.BaseModel;
import com.lanji.mylibrary.mvp.BaseMvpView;
import com.lanji.mylibrary.mvp.BasePresenter;

/**
 * create by lzx
 * time:2018/7/27
 */
public interface LoginView<T> extends BaseMvpView<T>{
    void loginSuccess();
}
