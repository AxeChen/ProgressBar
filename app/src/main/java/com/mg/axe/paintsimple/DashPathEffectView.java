package com.mg.axe.paintsimple;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Zaifeng on 2017/12/22.
 */

public class DashPathEffectView extends View {

    private Paint paint;

    /**
     * 动态改变的偏移量
     */
    private int phase;

    /**
     * 路径
     */
    private Path path;

    private int length = 15;

    public DashPathEffectView(Context context) {
        this(context, null);
    }

    public DashPathEffectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashPathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 初始化paint
        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        // 获取路径
        path = getPath(200);

        // 属性动画
        initAnimation();
    }

    private Path getPath(int trans) {
        Path path = new Path();
        path.moveTo(0, trans);
        for (int i = 0; i <= 40; i++) {
            path.lineTo(i * 35, (float) (Math.random() * 150));
        }
        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = getPath(0);
        dashLine(canvas);
        moveDashLine(canvas);
    }

    /**
     * 静止的虚线
     *
     * @param canvas
     */
    private void dashLine(Canvas canvas) {
        canvas.translate(0, 200);
        paint.setPathEffect(new DashPathEffect(new float[]{15, 20, 15, 20}, 0));
        canvas.drawPath(this.path, paint);
    }


    /**
     * 移动的虚线
     */
    private void moveDashLine(Canvas canvas) {
        canvas.translate(0, 200);
        paint.setPathEffect(new DashPathEffect(new float[]{length, length}, phase));
        canvas.drawPath(this.path, paint);
    }

    /**
     * 使用属性动画来控制移动的虚线（移动偏移量）
     */
    private void initAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 2);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                phase = value * length;
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
