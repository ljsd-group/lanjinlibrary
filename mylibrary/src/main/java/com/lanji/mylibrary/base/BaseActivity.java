package com.lanji.mylibrary.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

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
        setDarkStatusWhite(getStatusBarWhite());
        initView();
    }

    public  abstract int getLayoutId();
    public  abstract void initView();

    public boolean getStatusBarWhite (){
        return true;
    }

    private MProgressDialog mLoadingDialog;
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

    /**
     *bDark   true   黑色     false   白色
     */
    public void setDarkStatusWhite(boolean bDark) {
        View decorView = getWindow().getDecorView();
        //修改状态栏颜色只需要这行代码
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));//这里对应的是状态栏的颜色，就是style中colorPrimaryDark的颜色
        int vis = decorView.getSystemUiVisibility();
        if (bDark) {
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decorView.setSystemUiVisibility(vis);
    }
}
