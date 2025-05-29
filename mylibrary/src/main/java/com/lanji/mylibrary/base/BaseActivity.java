package com.lanji.mylibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lanji.mylibrary.R;
import com.lanji.mylibrary.inject.ViewUtils;
import com.lanji.mylibrary.loadingdialog.MProgressDialog;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ZhiHuiShiTang);
        setContentView(getLayoutId());
        ViewUtils.inject(this);
        initView();
    }

    public abstract int getLayoutId();
    public  abstract void initView();


    public MProgressDialog mLoadingDialog;
    private boolean isCancelable = false;

    public final void showDialog() {
        showDialog(false);
    }


    public final void showDialog(boolean iscancle) {
        isCancelable = iscancle;
        if (mLoadingDialog == null) {
            try {
                mLoadingDialog = new MProgressDialog(this);
                mLoadingDialog.setCanceledOnTouchOutside(isCancelable);
                mLoadingDialog.showNoText();
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else {
            mLoadingDialog.showNoText();
        }
    }

    /**
     * 取消加载对话框
     */
    public final void closeDialog() {
        try {
            if (mLoadingDialog != null) mLoadingDialog.dismiss();
            isCancelable = false;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
