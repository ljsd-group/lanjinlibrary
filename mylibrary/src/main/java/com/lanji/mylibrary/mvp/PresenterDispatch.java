package com.lanji.mylibrary.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * create by lzx
 * time:2018/7/27
 */
public class PresenterDispatch {
    private PresenterProviders mProviders;

    public PresenterDispatch(PresenterProviders providers) {
        mProviders = providers;
    }

    public <P extends BasePresenter> void attachView(Context context, Object view) {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.attachView(context, view);
            }
        }
    }

    public <P extends BasePresenter> void detachView() {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.detachView();
            }
        }
    }

    public <P extends BasePresenter> void onDestroyPresenter() {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onDestroyPresenter();
            }
        }
    }
}
