package com.lanji.mylibrary.base;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.lanji.mylibrary.R;
import com.lanji.mylibrary.inject.ViewUtils;
import com.lanji.mylibrary.loadingdialog.MProgressDialog;

public abstract class NormalFragment extends Fragment {
    private FrameLayout mBaseContent;
    public Context mContext;
    private View mLayoutProgressBar;
    public TextView  mTextLoading;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.layout_content, container,false);
        mLayoutProgressBar = view2.findViewById(R.id.base_progress);
        mTextLoading = view2.findViewById(R.id.text_loading);
        mBaseContent = view2.findViewById(R.id.base_content);
        View view = getLayoutInflater().inflate(getLayoutId(),null);
        mBaseContent.addView(view);
        ViewUtils.inject(view2);
        return view2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public abstract void init();

    public abstract int getLayoutId();

    public MProgressDialog mLoadingDialog;
    private boolean isCancelable = false;

    public final void showDialog() {
        showDialog(false);
    }


    public final void showDialog(boolean iscancle) {
        isCancelable = iscancle;
        if (mLoadingDialog == null) {
            try {
                mLoadingDialog = new MProgressDialog(getContext());
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
        mTextLoading.setText(ContextCompat.getString(mContext, tips));
        if (mTextLoading.getVisibility() == GONE)
            mTextLoading.setVisibility(VISIBLE);
    }

    public void closeProgressBar() {
        mLayoutProgressBar.setVisibility(GONE);
    }
}
