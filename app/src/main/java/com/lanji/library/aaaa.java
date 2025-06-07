package com.lanji.library;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lanji.mylibrary.base.Base2Activity;
import com.lanji.mylibrary.base.BaseActivity;
import com.lanji.mylibrary.base.NormalActivity;
import com.lanji.mylibrary.dialog.LoginDialog;
import com.lanji.mylibrary.inject.ViewInject;
import com.lanji.library.R;
import com.lanji.mylibrary.interfaces.LoginCallBack;
import com.lanji.mylibrary.interfaces.LoginDataCallBack;
import com.lanji.mylibrary.utils.LogUtils;

public class aaaa extends Base2Activity {
    @ViewInject(R.id.text)
    TextView textViewl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaaa);
        textViewl.setText("ddddddd");
        LogUtils.i("ssss"+(mContext==null));

                FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        bbb v = new bbb();
        transaction.add(R.id.fragment1, v);
        transaction.commitAllowingStateLoss();
    }
    //    @Override
//    public int getLayoutId() {
//        return com.lanji.mylibrary.R.layout.layout_base;
//    }
//    @SuppressLint("NonConstantResourceId")
//
//    @Override
//    public void initView() {

//        LoginDialog v= new LoginDialog(this, com.lanji.mylibrary.R.layout.dialog_login, new LoginCallBack() {
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onSuccess(String accToken) {
//
//            }
//
//            @Override
//            public void onError(String errorMsg) {
//
//            }
//        });
//        v.show();
//    }

//    @Override
//    public void addViewIntoContent(Bundle bundle) {
//        addView(R.layout.aaaa);
//        setTitle("1");
//        setLeftText("d");
//        setLeftImage(R.mipmap.icon_back);
//        setRightImage(R.mipmap.icon_back);
//        setRightText("fgg");
//        FragmentManager fManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fManager.beginTransaction();
//        bbb v = new bbb();
//        transaction.add(R.id.fragment1, v);
//        transaction.commitAllowingStateLoss();
//    }
//
//    @Override
//    public int getHeadView() {
//        return R.layout.app_head;
//
//    }
}
