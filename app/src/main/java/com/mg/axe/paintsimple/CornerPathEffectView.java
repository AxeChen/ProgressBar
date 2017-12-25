package com.mg.axe.paintsimple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zaifeng on 2017/12/22.
 */

public class CornerPathEffectView extends View {

    public CornerPathEffectView(Context context) {
        this(context, null);
    }

    public CornerPathEffectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerPathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCornerPathEffect(canvas);
    }

    /**
     * 使线的转角变得圆滑
     *
     * @param canvas
     */
    private void drawCornerPathEffect(Canvas canvas) {
        Paint paint = getPaint();
        Path path = new Path();
        path.moveTo(100, 600);
        path.lineTo(400, 100);
        path.lineTo(700, 900);

        canvas.drawPath(path, paint);
        paint.setColor(Color.RED);
        paint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(path, paint);

        paint.setPathEffect(new CornerPathEffect(200));
        paint.setColor(Color.YELLOW);
        canvas.drawPath(path, paint);
    }
}
