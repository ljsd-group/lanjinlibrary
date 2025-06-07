package com.lanji.library;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lanji.mylibrary.base.NormalFragment;
import com.lanji.mylibrary.inject.ViewInject;

import org.w3c.dom.Text;

public class bbb extends NormalFragment {

@ViewInject(R.id.text)
    TextView textView;
    @Override
    public void init() {
        textView.setText("sdfdsfdsfds");
    }

    @Override
    public int getLayoutId() {
        return R.layout.bbbb;
    }
}
