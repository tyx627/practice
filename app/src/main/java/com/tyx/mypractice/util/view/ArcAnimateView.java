package com.tyx.mypractice.util.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 扇形动画
 * Created by tyx on 2017/12/21.
 */

public class ArcAnimateView extends View {

    private int imgResId;
    private Shader shader;
    private Paint paint;
    private int size;
    private int width;
    private float angle;
    private boolean showArc;

    public ArcAnimateView(Context context) {
        super(context);
    }

    public ArcAnimateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcAnimateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (imgResId != 0){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgResId);
            shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
        showArc = true;
        angle = -90;
        invalidate();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (angle < 270){
                angle += 10;
                invalidate();
            } else {
                removeCallbacks(runnable);
            }
        }
    };

    public void setImageRes(int resId){
        imgResId = resId;
    }

    public void setSize(int size){
        this.size = size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = widthMeasureSpec;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showArc) {
//        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
//        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
//        paint.setShader(shader);
            if (size == 0) {
                size = this.getWidth();
            }
//        canvas.drawCircle(size/2, size/2, size/2, paint);
            Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint1.setXfermode(xfermode);
            paint1.setColor(Color.GREEN);
            paint1.setStyle(Paint.Style.FILL);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawArc(0, 0, size, size, -90, angle + 90, true, paint1);
            } else {
                RectF rectF = new RectF(0, 0, size, size);
                canvas.drawArc(rectF, -90, angle + 90, true, paint1);
            }
//        canvas.restoreToCount(saved);
            if (angle >= 270) {
                showArc = false;
                invalidate();
            }
            postDelayed(runnable, 50);
        }
    }
}
