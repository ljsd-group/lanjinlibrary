package com.lanji.library;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.lanji.mylibrary.base.BaseActivity;
import com.lanji.mylibrary.dialog.LoginDialog;
import com.lanji.mylibrary.inject.ViewInject;
import com.lanji.library.R;
import com.lanji.mylibrary.interfaces.LoginCallBack;
import com.lanji.mylibrary.interfaces.LoginDataCallBack;

public class aaaa extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.aaaa;
    }
    @SuppressLint("NonConstantResourceId")
    @ViewInject(R.id.text11)
    TextView textView;

    @Override
    public void initView() {
        textView.setText("111111111111");

        LoginDialog v= new LoginDialog(this, com.lanji.mylibrary.R.layout.dialog_login, new LoginCallBack() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String accToken) {

            }

            @Override
            public void onError(String errorMsg) {

            }
        });
        v.show();
    }

}
