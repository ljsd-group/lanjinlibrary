package com.lanji.mylibrary.base;

import android.os.Bundle;

import com.lanji.mylibrary.mvp.BasePresenter;
import com.lanji.mylibrary.mvp.PresenterDispatch;
import com.lanji.mylibrary.mvp.PresenterProviders;


public abstract class BaseMvpActivity<P extends BasePresenter> extends NormalActivity {

    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;


    @Override
    public void addViewIntoContent(Bundle bundle) {
        addView(getLayout());
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);

        mPresenterDispatch.attachView(this, this);
        init();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(getContentView());
//    }


    protected abstract int getLayout();

    public abstract void init();

    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterDispatch.detachView();
    }

}
