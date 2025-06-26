package com.lanji.library;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lanji.library.login.LoginPresenter;
import com.lanji.library.login.LoginView;
import com.lanji.mylibrary.base.BaseMvpFragment;
import com.lanji.mylibrary.base.NormalFragment;
import com.lanji.mylibrary.easy.EasyRefreshLayout;
import com.lanji.mylibrary.inject.ViewInject;
import com.lanji.mylibrary.mvp.CreatePresenter;
import com.lanji.mylibrary.mvp.PresenterVariable;

import org.w3c.dom.Text;

@CreatePresenter(presenter = {LoginPresenter.class})
public class bbb extends BaseMvpFragment  implements LoginView {
    @PresenterVariable
    private LoginPresenter mLoginPresenter;
@ViewInject(R.id.text)
    TextView textView;
    @Override
    public void initMvp() {
        mLoginPresenter.login();
        textView.setText("sdfdsfdsfds");
        EasyRefreshLayout refreshLayout=getView().findViewById(R.id.ea);
        refreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        refreshLayout.refreshComplete();
    }
},2000);
            }
        });
    }

    @Override
    public int getLayoutIdMvp() {
        return R.layout.bbbb;
    }

    @Override
    public void loginSuccess(Object o) {
        Log.i("ExampleFragment", "登陆成功");
    }

    @Override
    public void showPDialog() {

    }

    @Override
    public void closePDialog() {

    }
}
