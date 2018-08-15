package com.tyx.mypractice.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.tyx.mypractice.R;
import com.tyx.mypractice.util.CommonUtil;

/**
 * 自定义的曲线图
 */
public class CustomChartView extends View {

    private static final int fetalLatticeNum = 16;  // 胎心曲线区域，垂直方向格子的个数
    private static final int tocoLatticeNum = 10;   // 宫缩曲线区域，垂直方向格子的个数

    private Context mContext;
    private Paint linePaintW;   // 画线的大小有差异，这是稍微宽一点的画笔
    private Paint linePaintN;   // 画线的大小有差异，这是稍微细一点的画笔
    private Paint linePaintT;   // 用于写文字刻度的笔
    private Paint linePaintL;   // 用于画胎心曲线的笔
    private Paint greenAreaPaint;   // 用于画绿色区域的笔

    private int screenWidth;    // 屏幕宽度
    private int viewHeight;   // 控件高度
    private float oneMinWidth;    // 1分钟的宽度
    private float twentySecWidth; // 20秒的宽度
    private float fetalAreaHeight;    // 胎心曲线区域，垂直方向的高度
    private float fetalLatticeHeight;    // 胎心曲线区域，垂直方向一个格子的高度
    private float tocoAreaHeight;     // 宫缩曲线区域，垂直方向的高度
    private float tocoLatticeHeight;    // 宫缩曲线区域，垂直方向一个格子的高度
    private float tocoStartY;     // 宫缩曲线区域垂直方向起始位置
    private float fetalYPointHeight;  // 胎心区域垂直方向一个点的高度

    public CustomChartView(Context context) {
        this(context, null);
    }

    public CustomChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    // 初始化一些数据，例如画笔之类的
    private void initView() {
        initPaint();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        oneMinWidth = screenWidth / 4;  // 一屏幕显示4分钟数据
        twentySecWidth = oneMinWidth / 3;   // 一分钟数据分三个小格
    }

    private void initPaint() {
        // 画线的大小有差异，这是稍微宽一点的
        linePaintW = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaintW.setColor(mContext.getResources().getColor(R.color.color_f789e));
        linePaintW.setStrokeWidth(CommonUtil.dp2px(mContext, 1));

        // 这是细一点的
        linePaintN = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaintN.setColor(mContext.getResources().getColor(R.color.color_f789e));
        linePaintN.setStrokeWidth(1);

        // 写刻度的画笔
        linePaintT = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaintT.setColor(mContext.getResources().getColor(R.color.color_333));
        linePaintT.setStrokeWidth(CommonUtil.dp2px(mContext, 1));
        linePaintT.setTextSize(14);

        // 画曲线的画笔
        linePaintL = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaintL.setColor(mContext.getResources().getColor(R.color.color_333));
        linePaintL.setStrokeWidth(CommonUtil.dp2px(mContext, 1));

        // 画绿色区域的画笔
        greenAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenAreaPaint.setColor(mContext.getResources().getColor(R.color.color_336DE016));
        greenAreaPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (MeasureSpec.EXACTLY == heightMode){
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            viewHeight = heightSize;
        } else {
            viewHeight = (int)(screenWidth * 0.8);
        }
        fetalAreaHeight = (float) (viewHeight * 0.64); // 胎心曲线区域总共占高度的0.64
        fetalLatticeHeight = fetalAreaHeight / fetalLatticeNum; // 胎心曲线区域垂直方向每个格子的高度
        tocoAreaHeight = (float) (viewHeight * 0.28); // 宫缩曲线区域总共占高度的0.28
        tocoLatticeHeight = tocoAreaHeight / tocoLatticeNum;    // 宫缩曲线区域垂直方向每个格子高度
        tocoStartY = (fetalLatticeNum + 2) * fetalLatticeHeight;    // 宫缩区域和胎心区域，中间间隔高度为2个fetalLatticeHeight，胎心区域开始的高度
        // 胎心区域垂直方向值为60-210，所以求出每个点所占高度
        fetalYPointHeight = fetalAreaHeight / 160;
        setMeasuredDimension(screenWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBaseChart(canvas);
        drawGreenArea(canvas);
    }

    // 画绿色背景区域
    private void drawGreenArea(Canvas canvas) {
        canvas.save();
        // 绿色区域是110-160
        Rect rect = new Rect(0, (int)(fetalYPointHeight * 50), screenWidth, (int)(fetalYPointHeight * 100));
        canvas.drawRect(rect, greenAreaPaint);
        canvas.restore();
    }

    // 画背景图
    private void drawBaseChart(Canvas canvas) {
        canvas.save();
        // 先画胎心横向区域背景
        for (int i = 0; i <= fetalLatticeNum; i++) {
            if (i % 3 != 0){
                canvas.drawLine(0, i * fetalLatticeHeight, screenWidth, i * fetalLatticeHeight, linePaintN);
            } else {
                canvas.drawLine(0, i * fetalLatticeHeight, screenWidth, i * fetalLatticeHeight, linePaintW);
            }
        }
        // 画宫缩横向区域背景
        for (int i = 0; i <= tocoLatticeNum; i++) {
            if (i % 2 == 0) {
                canvas.drawLine(0, i * tocoLatticeHeight + tocoStartY, screenWidth, i * tocoLatticeHeight + tocoStartY, linePaintW);
            } else {
                canvas.drawLine(0, i * tocoLatticeHeight + tocoStartY, screenWidth, i * tocoLatticeHeight + tocoStartY, linePaintN);
            }
        }
        // 垂直区域背景
        for (int i = 0; i <= 12; i++) {
            if (i % 3 != 1){
                canvas.drawLine(i * twentySecWidth, 0, i * twentySecWidth, fetalAreaHeight, linePaintN);
                canvas.drawLine(i * twentySecWidth, tocoStartY, i * twentySecWidth, tocoStartY + tocoAreaHeight, linePaintN);
            } else {
                canvas.drawLine(i * twentySecWidth, 0, i * twentySecWidth, fetalAreaHeight, linePaintW);
                canvas.drawLine(i * twentySecWidth, tocoStartY, i * twentySecWidth, tocoStartY + tocoAreaHeight, linePaintW);
            }
        }
        canvas.restore();
    }

}
