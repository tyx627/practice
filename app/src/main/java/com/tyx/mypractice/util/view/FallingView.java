package com.tyx.mypractice.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * 雪花飘落的动画
 * Created by tyx on 2017/11/24.
 */

public class FallingView extends View {

    private Context mContext;
    private AttributeSet mAttrs;

    private Paint testPaint;
    private int snowY;

    private static final int defaultWidth = 600;    // 默认宽度
    private static final int defaultHeight = 1000;  // 默认高度
    private static final int intervalTime = 5;      // 重绘间隔时间
    private int viewHeight;
    private int viewWidth;

    private List<FallObject> fallObjects;

    public FallingView(Context context) {
        this(context, null);
    }

    public FallingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        init();
    }

    private void init() {
//        testPaint = new Paint();
//        testPaint.setColor(Color.WHITE);
//        testPaint.setStyle(Paint.Style.FILL);
//        snowY = 0;
        fallObjects = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        setMeasuredDimension(width, height);

        viewHeight = height;
        viewWidth = width;
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST){
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(100, snowY, 25, testPaint);
//        getHandler().postDelayed(runnable, intervalTime);   // 间隔一段时间再进行重绘
        if (fallObjects.size() > 0){
            for (int i = 0; i < fallObjects.size(); i++){
                // 然后进行绘制
                fallObjects.get(i).drawObject(canvas);
            }
            // 隔一段时间重绘一次，动画效果
            getHandler().postDelayed(runnable, intervalTime);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            snowY += 15;
//            if (snowY > viewHeight){    // 超过屏幕则重置雪球位置
//                snowY = 0;
//            }
            invalidate();
        }
    };

    /**
     * 向view添加下落物体对象
     * @param fallObject 下落物体对象
     * @param num 下落物体数量
     */
    public void addFallObject(final FallObject fallObject, final int num){
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                for (int i = 0; i < num; i++){
                    FallObject newFallObject = new FallObject(fallObject.builder, viewWidth, viewHeight);
                    fallObjects.add(newFallObject);
                }
                invalidate();
                return true;
            }
        });
    }
}
