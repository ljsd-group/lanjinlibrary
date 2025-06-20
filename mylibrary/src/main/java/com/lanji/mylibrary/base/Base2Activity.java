package com.lanji.mylibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lanji.mylibrary.R;
import com.lanji.mylibrary.inject.ViewUtils;
import com.lanji.mylibrary.loadingdialog.MProgressDialog;
import com.lanji.mylibrary.utils.LogUtils;
import com.lanji.mylibrary.utils.Method;

public class Base2Activity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ZhiHuiShiTang);
        setDarkStatusWhite(getStatusBarWhite());
        mContext = this;
        LogUtils.i("---->" + getClass().getSimpleName());
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        String lan= Method.getLanguage(newBase);
        LogUtils.i("attachBaseContext lan:"+lan);
        super.attachBaseContext(Method.getLLL(newBase));
    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewUtils.inject(this);
    }

    public boolean getStatusBarWhite() {
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
     * bDark   true   黑色     false   白色
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
