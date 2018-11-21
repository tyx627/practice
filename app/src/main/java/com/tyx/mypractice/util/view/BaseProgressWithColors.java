package com.tyx.mypractice.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tyx.mypractice.R;
import com.tyx.mypractice.util.CommonUtil;

public abstract class BaseProgressWithColors extends View {

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 选中区域边框的画笔
     */
    private Paint selecetdPaint;
    /**
     * 整个控件的宽度
     */
    public int viewWidth;
    /**
     * 阴影部分的宽度
     */
    private float shadowWidth;
    /**
     * 阴影部分的颜色
     */
    private int shadowColor;
    /**
     * 进度条的宽度
     */
    private float progressWidth;
    /**
     * 进度条的颜色数组
     */
    private String[] mColors;
    /**
     * 进度条的文字数组
     */
    private String[] mTexts;
    /**
     * 每段颜色对应的区域的长度
     */
    private float areaWidth;
    /**
     * 当前进度
     */
    private float progress;
    /**
     * 绘制选中效果的时候，需要偏移1dp
     */
    private int oneDp;
    /**
     * 2dp转换为px的宽度
     */
    private int twoDp;

    public BaseProgressWithColors(Context context) {
        this(context, null);
    }

    public BaseProgressWithColors(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseProgressWithColors(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.ProgressColors);
        shadowWidth = typedArray.getDimension(R.styleable.ProgressColors_shadowWidth, CommonUtil.dp2px(getContext(), 4));
        shadowColor = typedArray.getColor(R.styleable.ProgressColors_shadowColor, Color.parseColor("#cccccc"));
        progressWidth = typedArray.getDimension(R.styleable.ProgressColors_progressWidth, CommonUtil.dp2px(getContext(), 8));
        typedArray.recycle();
        initView();
    }

    private void initView() {
        oneDp = CommonUtil.dp2px(getContext(), 1);
        twoDp = oneDp * 2;
        initPaints();
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint);
        // 初始化进度条颜色和文字
        mColors = setColors();
        mTexts = setTexts();
    }

    /**
     * 初始化画笔
     */
    private void initPaints() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selecetdPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selecetdPaint.setColor(Color.WHITE);
        selecetdPaint.setStrokeWidth(twoDp);
        selecetdPaint.setStyle(Paint.Style.STROKE);
        selecetdPaint.setShadowLayer(shadowWidth, 1F, 1F, shadowColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 获取整个控件的宽度
        viewWidth = right - left;
        areaWidth = mColors.length > 1 ? viewWidth / mColors.length : viewWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mColors.length > 0){
            drawBackgroundProgress(canvas);
        }
    }

    /**
     * 画进度条背景
     * @param canvas 画布
     */
    private void drawBackgroundProgress(Canvas canvas) {
        // 当前进度跨过的区域个数，向下取整
        int areaNum = (int) Math.floor(progress/areaWidth);
        for (int i = 0; i < mColors.length; i++) {
            mPaint.setColor(Color.parseColor(mColors[i]));
            mPaint.setStrokeWidth(progressWidth);
            mPaint.setStyle(Paint.Style.FILL);
            if (mColors.length == 1){
                // 只有一种颜色，一个区域
                mPaint.setStrokeCap(Paint.Cap.ROUND);
                // 这里前后预留 progressWidth/2 是预留给线段末端的弧形的，如果不预留，则弧形不显示
                canvas.drawLine(progressWidth/2, shadowWidth + progressWidth/2, viewWidth-progressWidth/2, shadowWidth + progressWidth/2, mPaint);
            } else if (i == 0){
                // 当前区域被选中，当前进度在该区域
                if (areaNum == i){
                    // 第一段区域，左边是圆的，选中状态
                    drawFirstSelectedArea(canvas);
                } else {
                    // 第一段区域，左边是圆的
                    drawFirstNormalArea(canvas);

                }
            } else if (i == mColors.length - 1){
                // 最后一段区域，右边是圆
                // 先画线段，再画半圆
                canvas.drawLine(areaWidth * i, shadowWidth + progressWidth/2, viewWidth - progressWidth/2, shadowWidth + progressWidth/2, mPaint);
                // 画半圆
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawArc(viewWidth - progressWidth, shadowWidth, viewWidth, progressWidth + shadowWidth, 90F, -180F, true, mPaint);
                } else {
                    RectF rectF = new RectF(viewWidth - progressWidth, shadowWidth, viewWidth, progressWidth + shadowWidth);
                    canvas.drawArc(rectF, 90F, -180F, true, mPaint);
                }
            } else {
                canvas.drawLine(areaWidth * i, shadowWidth + progressWidth/2, areaWidth * (i + 1), shadowWidth + progressWidth/2, mPaint);
            }
            mPaint.reset();
        }
    }

    /**
     * 画进度条第一个区域，选中状态
     * @param canvas
     */
    private void drawFirstSelectedArea(Canvas canvas) {
        // 整体先画外面的白色边框，然后再填充里面的部分
        // 第一个区域左边是圆弧
        // 选中效果的白边，厚度2dp，往内缩1dp，往外1dp
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 画弧形边框
            canvas.drawArc(shadowWidth, shadowWidth, progressWidth + shadowWidth, progressWidth + shadowWidth, 90F, 180F, false, selecetdPaint);
            // 画直线部分的边框
            canvas.drawRect(progressWidth/2 + oneDp + shadowWidth, shadowWidth, areaWidth - oneDp, progressWidth + shadowWidth, selecetdPaint);
            // 填充里面的弧形
            canvas.drawArc(oneDp + shadowWidth, shadowWidth + oneDp, progressWidth+ shadowWidth, progressWidth + shadowWidth - oneDp, 90F, 180F, true, mPaint);
            // 填充里面的直线
            canvas.drawRect(progressWidth/2  + shadowWidth, shadowWidth + oneDp, areaWidth - twoDp, progressWidth + shadowWidth - oneDp, mPaint);
        } else {
            RectF rectF = new RectF(oneDp, shadowWidth - oneDp, progressWidth, progressWidth + shadowWidth + oneDp);
            canvas.drawArc(rectF, -90F, 180F, false, selecetdPaint);
            canvas.drawRect(new RectF(progressWidth/2 + oneDp, shadowWidth - oneDp, areaWidth - oneDp, progressWidth + shadowWidth + oneDp), selecetdPaint);
            canvas.drawArc(new RectF(twoDp, shadowWidth + oneDp, progressWidth, progressWidth + shadowWidth - oneDp), 90F, 180F, true, mPaint);
            canvas.drawRect(new RectF(progressWidth/2  + shadowWidth, shadowWidth + oneDp, areaWidth - twoDp, progressWidth + shadowWidth - oneDp), mPaint);
        }
    }

    /**
     * 画进度条第一个区域，正常状态，非选中
     * @param canvas
     */
    private void drawFirstNormalArea(Canvas canvas) {
        // 第一个区域左边是圆弧
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(0, shadowWidth, progressWidth, progressWidth + shadowWidth, 90F, 180F, true, mPaint);
        } else {
            RectF rectF = new RectF(0, shadowWidth, progressWidth, progressWidth + shadowWidth);
            canvas.drawArc(rectF, -90F, 180F, true, mPaint);
        }
        // 画线段
        canvas.drawLine(progressWidth/2, shadowWidth + progressWidth/2, areaWidth, shadowWidth + progressWidth/2, mPaint);
    }

    /**
     * 设置进度条上面的颜色
     * @return 进度条颜色数组
     */
    public abstract String[] setColors();

    /**
     * 设置进度条下面各种颜色对应的文字
     * @return 进度条颜色对应的文字数组
     */
    public abstract String[] setTexts();

    /**
     * 设置进度条的进度
     * @param progress 进度
     */
    public void setProgress(float progress){
        this.progress = progress;
        invalidate();
    }
}
