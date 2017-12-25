package com.mg.axe.paintsimple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zaifeng on 2017/12/22.
 */

public class DiscretePathEffectDemoView extends View {
    public DiscretePathEffectDemoView(Context context) {
        this(context,null);
    }

    public DiscretePathEffectDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiscretePathEffectDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDiscretePathEffectDemo(canvas);
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        return paint;
    }

    private void drawDiscretePathEffectDemo(Canvas canvas){
        Paint paint = getPaint();
        Path path = getPath();

        canvas.drawPath(path,paint);
        /**
         * 把原有的路线,在指定的间距处插入一个突刺
         * 第一个这些突出的“杂点”的间距,值越小间距越短,越密集
         * 第二个是突出距离
         */
        canvas.translate(0,200);
        paint.setPathEffect(new DiscretePathEffect(2,5));
        canvas.drawPath(path,paint);

        canvas.translate(0,200);
        paint.setPathEffect(new DiscretePathEffect(6,5));
        canvas.drawPath(path,paint);


        canvas.translate(0,200);
        paint.setPathEffect(new DiscretePathEffect(6,15));
        canvas.drawPath(path,paint);
    }

    private Path getPath() {
        Path path = new Path();
        path.moveTo(0, 100);
        for (int i = 0; i <= 40; i++) {
            path.lineTo(i * 35, (float) (Math.random() * 150));
        }
        return path;
    }
}
