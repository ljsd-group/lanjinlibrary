package com.lanji.library;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lanji.mylibrary.base.NormalFragment;

public class bbb extends NormalFragment {


    @Override
    public void init() {
showProgressBar("加载中...");
    }

    @Override
    public int getLayoutId() {
        return R.layout.bbbb;
    }
}
