package com.lanji.mylibrary.base;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.lanji.mylibrary.R;
import com.lanji.mylibrary.inject.ViewUtils;
import com.lanji.mylibrary.interfaces.LeftLayoutCallBack;
import com.lanji.mylibrary.interfaces.RightLayoutCallBack;


/*
 *
 * 功能描述：所有activity基类
 * 作者：Xiao
 */

public abstract class NormalActivity extends Base2Activity {
    private FrameLayout mBaseContent;
    protected LinearLayout mBaseLayout;

    public ImageView mImageLeft, mImageRight;
    public TextView mTextViewLeft, mTextViewTitle, mTextViewRight, mTextLoading;
    public View mLayoutLeft, mLayoutRight;
    private RightLayoutCallBack mRightLayoutCallBack;
    private LeftLayoutCallBack mLeftLayoutCallBack;
    private View mLayoutProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);

        mBaseLayout = findViewById(R.id.base_layout);
        int headId = getHeadView();
        if (headId > 0) {
            View view = getLayoutInflater().inflate(headId, mBaseLayout);
            mImageLeft = view.findViewById(R.id.app_header_left_image);
            mTextViewLeft = view.findViewById(R.id.app_header_left_text);
            mLayoutLeft = view.findViewById(R.id.app_header_left_view);
            if (mLayoutLeft != null)
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
            if (mLayoutRight != null)
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
            mTextViewTitle.setText(getResources().getString(stringId));
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
            mTextViewLeft.setText(getResources().getString(stringId));
            mTextViewLeft.setVisibility(VISIBLE);
        }
    }

    public void setRightImage(int drawable) {
        if (mImageRight != null) {
            mImageRight.setImageResource(drawable);
            mImageRight.setVisibility(VISIBLE);
        }
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
        mTextLoading.setText(getResources().getString(tips));
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

}
