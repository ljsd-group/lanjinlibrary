package com.lanji.library;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lanji.mylibrary.base.NormalFragment;
import com.lanji.mylibrary.easy.EasyRefreshLayout;
import com.lanji.mylibrary.inject.ViewInject;

import org.w3c.dom.Text;

public class bbb extends NormalFragment {

@ViewInject(R.id.text)
    TextView textView;
    @Override
    public void init() {
        textView.setText("sdfdsfdsfds");
        EasyRefreshLayout refreshLayout=getView().findViewById(R.id.ea);
        refreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        refreshLayout.refreshComplete();
    }
},2000);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.bbbb;
    }
}
