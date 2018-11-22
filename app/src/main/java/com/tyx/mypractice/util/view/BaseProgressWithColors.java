package com.tyx.mypractice.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
     * 画游标白色边框的画笔
     */
    private Paint cursorPaint;
    /**
     * 画文字的画笔
     */
    private Paint textPaint;
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
     * 开始画文字的Y轴高度
     */
    private float txtBeginHeight;
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
    /**
     * 游标的外宽度，7dp
     */
    private int sevenDp;
    /**
     * 游标的内宽度，4dp
     */
    private int fourDp;
    /**
     * 文字高度
     */
    private float textHeight;

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
        sevenDp = CommonUtil.dp2px(getContext(), 7);
        fourDp = CommonUtil.dp2px(getContext(), 4);
        initPaints();
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint);
        // 初始化进度条颜色和文字
        mColors = setColors();
        mTexts = setTexts();
        // 确定画文字的高度
        // 暂定进度条下面间隔5dp处
        txtBeginHeight = shadowWidth * 2 + progressWidth + CommonUtil.dp2px(getContext(), 5);
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
        // 设置阴影
        selecetdPaint.setShadowLayer(shadowWidth, 1F, 1F, shadowColor);

        cursorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cursorPaint.setColor(Color.WHITE);
        cursorPaint.setStrokeWidth(sevenDp);
        cursorPaint.setStyle(Paint.Style.FILL);
        cursorPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#666666"));
        textPaint.setTextSize(CommonUtil.sp2px(getContext(), 10));
        textPaint.setTextAlign(Paint.Align.CENTER);
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
            // 画进度条
            drawBackgroundProgress(canvas);
            // 画文字
            drawText(canvas);
        }
    }

    /**
     * 画进度条
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
                    drawCursor(canvas, i);
                } else {
                    // 第一段区域，左边是圆的
                    drawFirstNormalArea(canvas);
                }
            } else if (i == mColors.length - 1){
                // 最右侧的区域被选中
                if (areaNum == i){
                    drawLastSelectedArea(canvas, i);
                    drawCursor(canvas, i);
                } else {
                    drawLastNormalArea(canvas, i);
                }
            } else {
                // 中间区域被选中
                if (areaNum == i){
                    drawCenterSelectedArea(canvas, i);
                    drawCursor(canvas, i);
                } else {
                    canvas.drawLine(areaWidth * i, shadowWidth + progressWidth/2, areaWidth * (i + 1), shadowWidth + progressWidth/2, mPaint);
                }
            }
            mPaint.reset();
        }
    }

    /**
     * 画文字
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        if (null == mTexts || mTexts.length <= 0){
            return;
        }
        for (int i = 0; i < mTexts.length; i++) {
            if (!TextUtils.isEmpty(mTexts[i])) {
                // 文字的高度
                textHeight = getTextHeight(textPaint, mTexts[i]);
                // 文字显示在当前区域中间位置
                canvas.drawText(mTexts[i], areaWidth * i + areaWidth/2, txtBeginHeight + textHeight, textPaint);
            }
        }
    }

    /**
     * 画游标
     * @param canvas 画布
     * @param i 当前区域对应的颜色数组下标
     */
    private void drawCursor(Canvas canvas, int i){
        // 游标总宽度7dp，高度 = 阴影高度 * 2 + 进度条高度；没有阴影，白色边框2dp，颜色和当前区域颜色一致
        // 总体思路是先画白色圆头线段，然后用当前颜色再覆盖一条圆头线段
        // 先画白色背景，因为线段的圆头要预留位置，如果从y=0开始，则上半部分的圆头不显示，所以要先预留圆头的位置，游标宽度的一半，多加1px是为了防止切边效果
        // 同理，底部也要预留圆头位置，否则游标高度会超过整个进度条宽度
        canvas.drawLine(progress, sevenDp/2 + 1, progress, shadowWidth * 2 + progressWidth - sevenDp/2 - 1, cursorPaint);
        // 填充里面的颜色，因为边框是2dp，所以里面的宽度是 7 - 2 * 2 = 3dp，但是因为宽度为3dp的话，线段两端的圆头显示模糊，所以调为4
        mPaint.setStrokeWidth(fourDp);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(progress, sevenDp/2 , progress, shadowWidth * 2 + progressWidth - sevenDp/2, mPaint);
    }

    /**
     * 画进度条中间区域，选中状态
     * @param canvas 画布
     * @param i 已经经过的区域个数
     */
    private void drawCenterSelectedArea(Canvas canvas, int i) {
        // 画外面白色边框
        canvas.drawRect(areaWidth * i + oneDp, shadowWidth, areaWidth * (i + 1) - oneDp, progressWidth + shadowWidth, selecetdPaint);
        // 填充边框内部
        canvas.drawRect(areaWidth * i + twoDp, shadowWidth + oneDp, areaWidth * (i + 1) - twoDp, progressWidth + shadowWidth - oneDp, mPaint);
    }

    /**
     * 画进度条最后一个区域，选中状态
     * @param canvas 画布
     * @param i 已经经过的区域个数
     */
    private void drawLastSelectedArea(Canvas canvas, int i) {
        // 整体先画外面的白色边框，然后再填充里面的部分
        // 第一个区域左边是圆弧
        // 选中效果的白边，厚度2dp，往内缩1dp，往外1dp
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 画弧形边框
            canvas.drawArc(viewWidth - progressWidth - shadowWidth, shadowWidth, viewWidth - shadowWidth, progressWidth + shadowWidth, 90F, -180F, false, selecetdPaint);
            // 填充里面的弧形
            canvas.drawArc(viewWidth - progressWidth - shadowWidth, shadowWidth + oneDp, viewWidth - shadowWidth - oneDp, progressWidth + shadowWidth - oneDp, 90F, -180F, true, mPaint);
        } else {
            RectF rectF = new RectF(viewWidth - progressWidth, shadowWidth, progressWidth, progressWidth + shadowWidth);
            canvas.drawArc(rectF, 90F, -180F, false, selecetdPaint);
            canvas.drawArc(new RectF(viewWidth - progressWidth - shadowWidth, shadowWidth + oneDp, viewWidth - shadowWidth - oneDp, progressWidth + shadowWidth - oneDp), 90F, -180F, true, mPaint);
        }
        // 画直线部分的边框
        canvas.drawRect(areaWidth * i + oneDp, shadowWidth, viewWidth - progressWidth/2 - shadowWidth, progressWidth + shadowWidth, selecetdPaint);
        // 填充里面的直线
        canvas.drawRect(areaWidth * i + twoDp, shadowWidth + oneDp, viewWidth - progressWidth/2 - shadowWidth, progressWidth + shadowWidth - oneDp, mPaint);
    }

    /**
     * 画进度条最后一个区域，非选中状态
     * @param canvas 画布
     * @param i 已经经过的区域个数
     */
    private void drawLastNormalArea(Canvas canvas, int i) {
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
    }

    /**
     * 画进度条第一个区域，选中状态
     * @param canvas 画布
     */
    private void drawFirstSelectedArea(Canvas canvas) {
        // 整体先画外面的白色边框，然后再填充里面的部分
        // 第一个区域左边是圆弧
        // 选中效果的白边，厚度2dp，往内缩1dp，往外1dp
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 画弧形边框
            // 要求弧形左边的阴影也要全部显示，所以left设置为shadowWidth；这里的progressWidth其实就是弧形的直径
            canvas.drawArc(shadowWidth, shadowWidth, progressWidth + shadowWidth, progressWidth + shadowWidth, 90F, 180F, false, selecetdPaint);
            // 填充里面的弧形
            canvas.drawArc(oneDp + shadowWidth, shadowWidth + oneDp, progressWidth+ shadowWidth, progressWidth + shadowWidth - oneDp, 90F, 180F, true, mPaint);
        } else {
            RectF rectF = new RectF(shadowWidth, shadowWidth, progressWidth + shadowWidth, progressWidth + shadowWidth);
            canvas.drawArc(rectF, 90F, 180F, false, selecetdPaint);
            canvas.drawArc(new RectF(oneDp + shadowWidth, shadowWidth + oneDp, progressWidth+ shadowWidth, progressWidth + shadowWidth - oneDp), 90F, 180F, true, mPaint);
        }
        // 画直线部分的边框
        canvas.drawRect(progressWidth/2 + oneDp + shadowWidth, shadowWidth, areaWidth - oneDp, progressWidth + shadowWidth, selecetdPaint);
        // 填充里面的直线
        canvas.drawRect(progressWidth/2  + shadowWidth, shadowWidth + oneDp, areaWidth - twoDp, progressWidth + shadowWidth - oneDp, mPaint);
    }

    /**
     * 画进度条第一个区域，正常状态，非选中
     * @param canvas 画布
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
     * 获取文字的高度
     * @param paint 画笔
     * @param str 字符串
     * @return 文字高度
     */
    private float getTextHeight(Paint paint, String str) {
        float height = 0;
        if (null != paint && !TextUtils.isEmpty(str)) {
            Paint.FontMetrics fm = paint.getFontMetrics();
            height = fm.descent - fm.ascent;
        }
        return height;
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
