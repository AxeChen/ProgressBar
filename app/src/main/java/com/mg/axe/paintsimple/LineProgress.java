package com.mg.axe.paintsimple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by AxeChen on 2017/12/23.
 * 线性进度条
 */

public class LineProgress extends View {

    private int defaultHeight = 60;

    private Paint mPaint;

    private int width = 0;

    private Rect mTextRect = new Rect();

    public LineProgress(Context context) {
        this(context, null);
    }

    public LineProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 需要判断宽的测量模式
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        width = size;
        setMeasuredDimension(size, defaultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        //画底部
        canvas.drawLine(30, defaultHeight - 25, width - 30, defaultHeight - 25, mPaint);

        //画进度
        mPaint.reset();
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(50);
        canvas.drawLine(30, defaultHeight - 25, width - 300, defaultHeight - 25, mPaint);

        //画文字(文字画到进度后面)
        float x = mPaint.measureText("90");

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(40);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float y =  (-(fontMetrics.descent + fontMetrics.ascent) / 2);
        mPaint.getTextBounds("90", 0, "90".length(), mTextRect);
        canvas.drawText("90", width - 300 + 40, mTextRect.height()/2+5+25, mPaint);

        canvas.restore();
    }
}
