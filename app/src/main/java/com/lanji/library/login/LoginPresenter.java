package com.lanji.library.login;


import com.lanji.library.aaa.User;
import com.lanji.mylibrary.http.BaseModel;
import com.lanji.mylibrary.mvp.BasePresenter;

/**
 * create by lzx
 * time:2018/7/26
 */
public class LoginPresenter extends BasePresenter<LoginView<BaseModel>> {

    public void login() {
        User baseModel=new User();
        baseModel.message="1111";
        mView.loginSuccess(baseModel);
    }
}
