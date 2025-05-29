package com.lanji.library;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.lanji.mylibrary.base.BaseActivity;
import com.lanji.mylibrary.inject.ViewInject;
import com.lanji.library.R;

public class aaaa extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.aaaa;
    }
    @SuppressLint("NonConstantResourceId")
    @ViewInject(R.id.text11)
    TextView textView;

    @Override
    public void initView() {
        textView.setText("111111111111");
    }
}
