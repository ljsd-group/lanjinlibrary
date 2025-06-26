package com.lanji.library.aaa;


import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lanji.library.R;
import com.lanji.library.bbb;
import com.lanji.library.login.LoginPresenter;
import com.lanji.library.login.LoginView;
import com.lanji.mylibrary.base.BaseMvpActivity;
import com.lanji.mylibrary.mvp.CreatePresenter;


/**
 * 例子2：多个Presenter和使用 getPresenter 方法获取实例
 */
@CreatePresenter(presenter = {LoginPresenter.class})
public  class ExampleActivity2 extends BaseMvpActivity<LoginPresenter> implements LoginView<User> {

    @Override
    protected int getLayout() {
        return R.layout.aaaa;
    }

    @Override
    public void init() {
                FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        bbb v = new bbb();
        transaction.add(R.id.fragment1, v);
        transaction.commitAllowingStateLoss();
        LoginPresenter mLoginPresenter = getPresenter();

        mLoginPresenter.login();
    }

    @Override
    public void loginSuccess(User model) {
        Log.i("model", model.toString());
    }

    @Override
    public void showPDialog() {

    }

    @Override
    public void closePDialog() {

    }
}

