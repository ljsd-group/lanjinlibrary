package com.lanji.mylibrary.easy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lanji.mylibrary.R;
import com.lanji.mylibrary.easy.ILoadMoreView;
import com.lanji.mylibrary.spinkit.SpinKitView;


/**
 * Created by guanaj on 16/9/22.
 */

public class SimpleLoadMoreView extends FrameLayout implements ILoadMoreView {

    private SpinKitView spinKitView;
    private View view;

    public SimpleLoadMoreView(Context context) {
        this(context, null);
    }

    public SimpleLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = inflate(context, R.layout.default_load_more, this);
        spinKitView = (SpinKitView) view.findViewById(R.id.spin_kit);

    }


    @Override
    public void reset() {
        spinKitView.setVisibility(INVISIBLE);
    }

    @Override
    public void loading() {
        spinKitView.setVisibility(VISIBLE);
    }

    @Override
    public void loadComplete() {
        spinKitView.setVisibility(INVISIBLE);

    }

    @Override
    public void loadFail() {
        spinKitView.setVisibility(INVISIBLE);

    }

    @Override
    public void loadNothing() {
        spinKitView.setVisibility(INVISIBLE);
    }

    @Override
    public View getCanClickFailView() {
        return view;
    }


}
