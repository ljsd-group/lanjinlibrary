package com.lanji.library.aaa;


import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.lanji.library.R;
import com.lanji.library.login.LoginPresenter;
import com.lanji.library.login.LoginView;
import com.lanji.mylibrary.base.BaseMvpActivity;
import com.lanji.mylibrary.http.BaseModel;
import com.lanji.mylibrary.mvp.BaseMvpView;
import com.lanji.mylibrary.mvp.CreatePresenter;


/**
 * 例子2：多个Presenter和使用 getPresenter 方法获取实例
 */
@CreatePresenter(presenter = {LoginPresenter.class})
public  class ExampleActivity2 extends BaseMvpActivity<LoginPresenter> implements LoginView<User> {

    @Override
    protected int getContentView() {
        return R.layout.aaaa;
    }

    @Override
    public void init() {
        LoginPresenter mLoginPresenter = getPresenter();

        mLoginPresenter.login();
    }

    @Override
    public void loginSuccess() {
        Log.i("ExampleActivity1", "登陆成功");
    }

    @Override
    public void showPDialog() {

    }

    @Override
    public void closePDialog() {

    }

    @Override
    public void onSuccess(User model) {
        Log.i("model", model.toString());
    }

    @Override
    public void onError(String error) {

    }
}

