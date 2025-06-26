package com.lanji.mylibrary.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lanji.mylibrary.mvp.BasePresenter;
import com.lanji.mylibrary.mvp.PresenterDispatch;
import com.lanji.mylibrary.mvp.PresenterProviders;


/**
 * create by lzx
 * time:2018/7/30
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends NormalFragment {

    protected Context mContext;
    protected Activity mActivity;

    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public int getLayoutId() {
        return getLayoutIdMvp();
    }

    @Override
    public void init() {
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);

        mPresenterDispatch.attachView(getActivity(), this);
        initMvp();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    /**
     * 获取布局
     */
    public abstract int getLayoutIdMvp();

    public abstract void initMvp();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenterDispatch.detachView();
    }

    @Override
    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }
}
