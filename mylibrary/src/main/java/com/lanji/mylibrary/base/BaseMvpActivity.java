package com.lanji.mylibrary.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lanji.mylibrary.http.BaseModel;
import com.lanji.mylibrary.mvp.BaseMvpView;
import com.lanji.mylibrary.mvp.BasePresenter;
import com.lanji.mylibrary.mvp.PresenterDispatch;
import com.lanji.mylibrary.mvp.PresenterProviders;


public abstract class BaseMvpActivity<P extends BasePresenter> extends NormalActivity {

    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;


    @Override
    public void addViewIntoContent(Bundle bundle) {
        addView(getContentView());
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


    protected abstract int getContentView();

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
