package com.mg.axe.paintsimple;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by AxeChen on 2017/12/22.
 * 圆形进度条
 * 根据半径画出制定圆形进度条，这里将不会根据width和height的值来设置进度条的大小
 */

public class RoundProgress extends View {

    // 默认的的半径为
    private final int DEFAULT_RADIUS = 100;

    // 默认的画笔宽度
    private final int DEFAULT_PROGRESS_WIDTH = 10;

    // 默认的内边框和外边框的颜色
    private final int DEFAULT_BORDER_COLOR = Color.GRAY;

    // 默认的内边框和外边框的宽度
    private final int DEFAULT_BORDER_WIDTH = 2;

    // 默认的字体大小
    private final int DEFAULT_TEXT_SIZE = 30;

    // 默认的字体颜色
    private final int DEFAULT_TEXT_COLOR = Color.BLACK;

    private final int DEFAULT_TYPE_PERCENT = 0;

    private final int DEFAULT_TYPE_DIVISION = 1;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 进度
     */
    private int mProgress = 0;

    /**
     * 圆形进度条的半径
     */
    private int mRadius = 100;

    /**
     * 进度条的宽度
     */
    private int mProgressWidth = 50;

    /**
     * 等待条的宽度
     */
    private int mWaitBarWidth = 50;

    /**
     * 进度条的宽度
     */
    private int mViewWidth = 0;

    /**
     * 进度条的高度
     */
    private int mViewHeight = 0;

    /**
     * 进度条的颜色
     */
    private int mProgressBarColor = 0;

    /**
     * 等待条的宽度
     */
    private int mWaitBarColor = 0;

    /**
     * 是否展示外层的边框（默认不展示）
     */
    private boolean isShowOutsideBorder = false;

    /**
     * 是否展示内层的边框（默认不展示）
     */
    private boolean isShowInsideBorder = false;

    /**
     * 外层边框的颜色
     */
    private int mOutsideBorderColor = 0;

    /**
     * 内层边框的颜色
     */
    private int mInsideBorderColor = 0;

    /**
     * 外层边框的宽度
     */
    private int mOutsideBorderWidth = 0;

    /**
     * 内层边框的宽度
     */
    private int mInsideBorderWidth = 0;

    /**
     * 字体的大小
     */
    private int mTextSize = 0;

    /**
     * 字体的颜色
     */
    private int mTextColor = 0;

    /**
     * 字体的样式
     */
    private int mTextType = 0;

    private boolean isShowProgressStr;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getAttrs(context, attrs);
    }

    /**
     * 用于初始化一些东西
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    /**
     * 获取从xml中自定义的属性
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.RoundProgress_radius, DEFAULT_RADIUS);
        mProgressWidth = typedArray.getDimensionPixelSize(R.styleable.RoundProgress_progress_width, DEFAULT_PROGRESS_WIDTH);
        mWaitBarWidth = typedArray.getDimensionPixelSize(R.styleable.RoundProgress_wait_bar_width, mProgressWidth);

        mProgressBarColor = typedArray.getColor(R.styleable.RoundProgress_progress_bar_color, context.getResources().getColor(R.color.colorPrimary));
        mWaitBarColor = typedArray.getColor(R.styleable.RoundProgress_wait_bar_color, context.getResources().getColor(android.R.color.background_light));

        isShowInsideBorder = typedArray.getBoolean(R.styleable.RoundProgress_show_inside_border, false);
        isShowOutsideBorder = typedArray.getBoolean(R.styleable.RoundProgress_show_outside_border, false);
        mInsideBorderColor = typedArray.getColor(R.styleable.RoundProgress_inside_border_color, DEFAULT_BORDER_COLOR);
        mOutsideBorderColor = typedArray.getColor(R.styleable.RoundProgress_outside_border_color, DEFAULT_BORDER_COLOR);
        mInsideBorderWidth = typedArray.getDimensionPixelSize(R.styleable.RoundProgress_inside_border_width, DEFAULT_BORDER_WIDTH);
        mOutsideBorderWidth = typedArray.getDimensionPixelSize(R.styleable.RoundProgress_outside_border_width, DEFAULT_BORDER_WIDTH);

        mTextSize = typedArray.getDimensionPixelSize(R.styleable.RoundProgress_progress_text_size, DEFAULT_TEXT_SIZE);
        mTextColor = typedArray.getColor(R.styleable.RoundProgress_progress_text_color, DEFAULT_TEXT_COLOR);
        mTextType = typedArray.getInteger(R.styleable.RoundProgress_progress_text_type, DEFAULT_TYPE_PERCENT);
        isShowProgressStr = typedArray.getBoolean(R.styleable.RoundProgress_show_progress_str, true);

        typedArray.recycle();
    }

    /**
     * 根据半径和画笔的宽度设置进度条的实际宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = mRadius * 2 + Math.max(mProgressWidth, mWaitBarWidth);
        if (isShowOutsideBorder) {
            mViewWidth += mOutsideBorderWidth * 2;
        }
        mViewHeight = mViewWidth;
        setMeasuredDimension(mViewWidth, mViewHeight);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        // 画背景
        drawBackground(canvas);
        // 画进度
        drawProgress(canvas);
        // 画文字
        drawText(canvas);
        canvas.restore();
    }

    /**
     * 画背景
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        // 画border
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mPaint.setStrokeWidth(mOutsideBorderWidth);
        mPaint.setColor(mOutsideBorderColor);
        if (isShowOutsideBorder) {
            canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, mRadius + mProgressWidth / 2 + mOutsideBorderWidth / 2, mPaint);
        }

        mPaint.setColor(mInsideBorderColor);
        mPaint.setStrokeWidth(mInsideBorderWidth);
        if (isShowInsideBorder) {
            canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, mRadius - mProgressWidth / 2 - mInsideBorderWidth / 2, mPaint);
        }

        mPaint.reset();
        mPaint.setColor(mWaitBarColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mWaitBarWidth);
        mPaint.setAntiAlias(true);
        // 画底边的颜色
        canvas.drawCircle(mViewWidth / 2, mViewHeight / 2, mRadius, mPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawProgress(Canvas canvas) {
        mPaint.setColor(mProgressBarColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mProgressWidth + 2);
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        if (isShowOutsideBorder) {
            left = mOutsideBorderWidth + mProgressWidth / 2;
            right = mViewWidth - mOutsideBorderWidth - mProgressWidth / 2;
        } else {
            left = mProgressWidth / 2;
            right = mViewWidth - mProgressWidth / 2;
        }
        top = left;
        bottom = right;
        // 画进度
        canvas.drawArc(left,
                top,
                right,
                bottom,
                0,
                360 * mProgress / 100,
                false, mPaint);
    }

    /**
     * 画文字
     */
    private void drawText(Canvas canvas) {
        if (!isShowProgressStr) {
            return;
        }

        mPaint.reset();
        // 画文字（文字在布局中间）
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setAntiAlias(true);

        String progressStr;
        if (mTextType == DEFAULT_TYPE_PERCENT) {
            progressStr = String.valueOf(mProgress) + "%";
        } else {
            progressStr = String.valueOf(mProgress) + "/100";
        }
        float x = mPaint.measureText(progressStr);
        FontMetrics fontMetrics = mPaint.getFontMetrics();
        float y = mRadius + mProgressWidth / 2 - ((fontMetrics.descent + fontMetrics.ascent) / 2);
        x = mRadius - x / 2 + mProgressWidth / 2;
        if (isShowOutsideBorder) {
            x = x + mOutsideBorderWidth;
            y = mRadius + mProgressWidth / 2 + mOutsideBorderWidth - ((fontMetrics.descent + fontMetrics.ascent) / 2);
        }
        canvas.drawText(progressStr, x, y, mPaint);
    }

    /**
     * 进度条的范围0 - 100
     *
     * @param progress
     * @return
     */
    public void setProgress(int progress) {
        if (progress > 100) {
            mProgress = 100;
        } else if (progress < 0) {
            mProgress = 0;
        }
        this.mProgress = progress;
        invalidate();
    }

}
