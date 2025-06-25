package com.lanji.mylibrary.base;

import android.os.Bundle;

import com.lanji.mylibrary.R;

public abstract class BaseListActivity extends NormalActivity{
    @Override
    public void addViewIntoContent(Bundle bundle) {
        addView(R.layout.app_head);
        addView(R.layout.activity_list);
        OnCreate();
    }
    public abstract void OnCreate();
}
