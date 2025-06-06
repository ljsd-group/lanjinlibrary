package com.lanji.mylibrary.base;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lanji.mylibrary.R;
import com.lanji.mylibrary.inject.ViewUtils;
import com.lanji.mylibrary.interfaces.LeftLayoutCallBack;
import com.lanji.mylibrary.interfaces.RightLayoutCallBack;
import com.lanji.mylibrary.loadingdialog.MProgressDialog;


/*
 *
 * 功能描述：所有activity基类
 * 作者：Xiao
 */

public abstract class NormalActivity extends AppCompatActivity {
    private FrameLayout mBaseContent;
    protected LinearLayout mBaseLayout;

    public ImageView mImageLeft, mImageRight;
    public TextView mTextViewLeft, mTextViewTitle, mTextViewRight, mTextLoading;
    public View mLayoutLeft, mLayoutRight;
    private RightLayoutCallBack mRightLayoutCallBack;
    private LeftLayoutCallBack mLeftLayoutCallBack;
    private View mLayoutProgressBar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ZhiHuiShiTang);
        setContentView(R.layout.layout_base);
        mContext=this;
        setDarkStatusWhite(getStatusBarWhite());

        mBaseLayout = findViewById(R.id.base_layout);
        int headId = getHeadView();
        if (headId > 0) {
            View view = getLayoutInflater().inflate(headId, mBaseLayout);
            mImageLeft = view.findViewById(R.id.app_header_left_image);
            mTextViewLeft = view.findViewById(R.id.app_header_left_text);
            mLayoutLeft = view.findViewById(R.id.app_header_left_view);
            mLayoutLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLeftLayoutCallBack != null) {
                        mLeftLayoutCallBack.LeftClick();
                    } else
                        finish();
                }
            });
            mTextViewTitle = view.findViewById(R.id.app_header_title);
            mImageRight = view.findViewById(R.id.app_header_right_image);
            mTextViewRight = view.findViewById(R.id.app_header_right_text);
            mLayoutRight = view.findViewById(R.id.app_header_right_view);
            mLayoutRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRightLayoutCallBack != null) mRightLayoutCallBack.RightClick();
                }
            });
        }
        View view = getLayoutInflater().inflate(R.layout.layout_content, mBaseLayout);
        mBaseContent = view.findViewById(R.id.base_content);
        mLayoutProgressBar = view.findViewById(R.id.base_progress);
        mTextLoading = view.findViewById(R.id.text_loading);
        addViewIntoContent(savedInstanceState);
    }

    public void addView(int layoutId) {
        if (layoutId > 0) {
            getLayoutInflater().inflate(layoutId, mBaseContent);
            ViewUtils.injectAll(this);
        } else {
            try {
                throw new Exception("layout ID can not be 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setTitle(int stringId) {
        if (stringId > 0)
            mTextViewTitle.setText(ContextCompat.getString(this, stringId));
    }

    public void setTitle(String title) {
        mTextViewTitle.setText(title);
    }

    public void setLeftImage(int drawable) {
        mImageLeft.setImageResource(drawable);
    }

    public void setLeftText(String leftText) {
        if (mTextViewLeft != null) {
            mTextViewLeft.setText(leftText);
            mTextViewLeft.setVisibility(VISIBLE);
        }
    }

    public void setLeftText(int stringId) {
        if (mTextViewLeft != null) {
            mTextViewLeft.setText(ContextCompat.getString(this, stringId));
            mTextViewLeft.setVisibility(VISIBLE);
        }
    }

    public void setRightImage(int drawable) {
        if (mImageRight != null)
            mImageRight.setImageResource(drawable);
    }

    public void setRightText(String leftText) {
        if (mTextViewRight != null) {
            mTextViewRight.setText(leftText);
            mTextViewRight.setVisibility(VISIBLE);
        }
    }

    public void showProgressBar() {
        mLayoutProgressBar.setVisibility(VISIBLE);
        if (mTextLoading.getVisibility() == VISIBLE)
            mTextLoading.setVisibility(GONE);
    }

    public void showProgressBar(String tips) {
        mLayoutProgressBar.setVisibility(VISIBLE);
        mTextLoading.setText(tips);
        if (mTextLoading.getVisibility() == GONE)
            mTextLoading.setVisibility(VISIBLE);
    }

    public void showProgressBar(int tips) {
        mLayoutProgressBar.setVisibility(VISIBLE);
        mTextLoading.setText(ContextCompat.getString(this, tips));
        if (mTextLoading.getVisibility() == GONE)
            mTextLoading.setVisibility(VISIBLE);
    }

    public void closeProgressBar() {
        mLayoutProgressBar.setVisibility(GONE);
    }

    /**
     * 此方法必须在子类onCreate的时候调用
     */
    public abstract void addViewIntoContent(Bundle bundle);

    public int getHeadView() {
        return R.layout.app_head;
    }

    public void setLeftClickCallBack(LeftLayoutCallBack mLeftLayoutCallBack) {
        this.mLeftLayoutCallBack = mLeftLayoutCallBack;
    }

    public void setRightClickCallBack(RightLayoutCallBack mRightLayoutCallBack) {
        this.mRightLayoutCallBack = mRightLayoutCallBack;
    }

    public boolean getStatusBarWhite() {
        return true;
    }

    private MProgressDialog mLoadingDialog;
    private boolean isCancelable = false;

    public  void showDialog() {
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
