package com.lanji.mylibrary.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

public class CustomCircularProgressBar extends ProgressBar {

    private int mCircleColor = Color.BLUE;
    private int mCircleThickness = 5;
    private float mProgress = 0;

    public CustomCircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取视图的宽高
        int width = getWidth();
        int height = getHeight();

        // 计算圆心坐标
        int centerX = width / 2;
        int centerY = height / 2;

        // 计算半径
        int radius = Math.min(centerX, centerY);

        // 设置画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 抗锯齿
        paint.setStrokeWidth(mCircleThickness); // 进度条厚度
        paint.setStyle(Paint.Style.STROKE); // 设置为描边
        paint.setColor(mCircleColor); // 进度条颜色

        // 绘制弧线
        canvas.drawArc(new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
                0, 360 * mProgress, false, paint);
    }

    public void setCircleColor(int color) {
        mCircleColor = color;
        invalidate();
    }

    public void setCircleThickness(int thickness) {
        mCircleThickness = thickness;
        invalidate();
    }

    // 动画部分
    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000); // 动画持续时间
        animator.setRepeatCount(ValueAnimator.INFINITE); // 无限循环
        animator.setInterpolator(new LinearInterpolator()); // 使用线性插值

        animator.addUpdateListener(animation -> {
            mProgress = (float) animation.getAnimatedValue();
            invalidate();
        });

        animator.start();
    }
}
