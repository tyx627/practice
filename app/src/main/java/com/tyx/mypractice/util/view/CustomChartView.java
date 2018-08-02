package com.tyx.mypractice.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    private Context mContext;
    private Paint linePaintW;   // 画线的大小有差异，这是稍微宽一点的画笔
    private Paint linePaintN;   // 画线的大小有差异，这是稍微细一点的画笔
    private Paint linePaintT;   // 用于写文字刻度的笔
    private Paint linePaintL;   // 用于画胎心曲线的笔

    private int screenWidth;    // 屏幕宽度
    private int viewHeight;   // 控件高度
    private int oneMinWidth;    // 1分钟的宽度
    private int twentySecWidth; // 20秒的宽度


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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = heightMeasureSpec;
        setMeasuredDimension(screenWidth, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
