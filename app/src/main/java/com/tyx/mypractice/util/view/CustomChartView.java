package com.tyx.mypractice.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.tyx.mypractice.R;
import com.tyx.mypractice.model.fetalmonitor.TimeData;
import com.tyx.mypractice.util.CommonUtil;

/**
 * 自定义的曲线图
 */
public class CustomChartView extends View {

    private static final int FETALLATTICENUM = 16;  // 胎心曲线区域，垂直方向格子的个数
    private static final int TOCOLATTICENUM = 10;   // 宫缩曲线区域，垂直方向格子的个数
    private static final int MINSINONESCREEN = 4;  // 一屏幕显示多少分钟的数据

    private Context mContext;
    private Scroller mScroller;
    private Paint linePaintW;   // 画线的大小有差异，这是稍微宽一点的画笔
    private Paint linePaintN;   // 画线的大小有差异，这是稍微细一点的画笔
    private Paint linePaintT;   // 用于写文字刻度的笔
    private Paint linePaintL;   // 用于画胎心曲线的笔
    private Paint greenAreaPaint;   // 用于画绿色区域的笔
    private Paint whitePaint;   // 用于画坐标文字背景的画笔，白色背景

    private Rect bgRect;    // 整个view的背景，白色的矩形
    private int screenWidth;    // 屏幕宽度
    private int viewHeight;   // 控件高度
    private int viewWidth;      // 整个图表的宽度，目前的方案是 screenWidth <= viewWidth <= 3*screenWidth，因为最多缓存前后两个屏幕宽度
    private int chartHeight;    // 图表的高度，因为图表上下要留一点空白位置，所以这个高度和控件高度是不一致的
    private float oneMinWidth;    // 1分钟的宽度
    private float twentySecWidth; // 20秒的宽度
    private float onePointWidth;    // 一个点的宽度
    private float fetalAreaHeight;    // 胎心曲线区域，垂直方向的高度
    private float fetalLatticeHeight;    // 胎心曲线区域，垂直方向一个格子的高度
    private float tocoAreaHeight;     // 宫缩曲线区域，垂直方向的高度
    private float tocoLatticeHeight;    // 宫缩曲线区域，垂直方向一个格子的高度
    private float tocoStartY;     // 宫缩曲线区域垂直方向起始位置
    private float fetalYPointHeight;  // 胎心区域垂直方向一个点的高度
    private float tocoYPointHeight;     // 宫缩区域垂直方向一个点的高度
    private int textSize; // 文字大小
    private int fetalMax = 210; // 胎心最大值
    private int tocoMax = 100;  // 胎心最小值
    private int startTime = 1;  // 时间坐标的开始值
    private Paint.FontMetrics fontMetrics;  // 字体的宽高等信息的类
    private float coorWidth;    // 坐标文字的宽度
    private float coorHeight;    // 坐标文字的高度
    private int tenDpHeight;    // 10dp的高度，单位为px

    private TimeData[] datas;   // 曲线的数据

    public CustomChartView(Context context) {
        this(context, null);
    }

    public CustomChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mScroller = new Scroller(context);
        viewConfiguration = ViewConfiguration.get(context);
        initView();
    }

    // 初始化一些数据，例如画笔之类的
    private void initView() {
        textSize = CommonUtil.sp2px(mContext, 10);
        tenDpHeight = CommonUtil.dp2px(mContext, 10);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        viewWidth = screenWidth;
        oneMinWidth = screenWidth / MINSINONESCREEN;  // 一屏幕显示MINSINTONESCREEN分钟数据，求一分钟数据的宽度
        twentySecWidth = oneMinWidth / 3;   // 一分钟数据分三个小格
//        onePointWidth = (float) (screenWidth / MINSINONESCREEN / 60 / 2 + 0.5);  // 一屏幕显示480个数据，一秒钟画2个数据,4 * 60 * 2 = 480 // 这个误差太大
        onePointWidth = twentySecWidth / 40;  // 20秒画40个数据
        initPaint();
        // 获取坐标文字的长宽
        fontMetrics = linePaintT.getFontMetrics();
        String str = String.valueOf(fetalMax);
        coorWidth = linePaintT.measureText(str);
        coorHeight = getTextHeight(linePaintT, str);
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
        linePaintT.setTextSize(textSize);
        linePaintT.setTextAlign(Paint.Align.CENTER);

        // 画曲线的画笔
        linePaintL = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaintL.setColor(mContext.getResources().getColor(R.color.color_333));
        linePaintL.setStrokeWidth(CommonUtil.dp2px(mContext, 1));

        // 画绿色区域的画笔
        greenAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenAreaPaint.setColor(mContext.getResources().getColor(R.color.color_336DE016));
        greenAreaPaint.setStyle(Paint.Style.FILL);

        // 画坐标文字背景的画笔，白色背景
        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(mContext.getResources().getColor(R.color.white));
        whitePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (MeasureSpec.EXACTLY == heightMode){
            viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            viewHeight = (int)(screenWidth * 0.8);
        }
        chartHeight = viewHeight - CommonUtil.dp2px(mContext, 20);
        fetalAreaHeight = (float) (chartHeight * 0.64); // 胎心曲线区域总共占高度的0.64
        fetalLatticeHeight = fetalAreaHeight / FETALLATTICENUM; // 胎心曲线区域垂直方向每个格子的高度
        tocoAreaHeight = (float) (chartHeight * 0.28); // 宫缩曲线区域总共占高度的0.28
        tocoLatticeHeight = tocoAreaHeight / TOCOLATTICENUM;    // 宫缩曲线区域垂直方向每个格子高度
        tocoStartY = (FETALLATTICENUM + 2) * fetalLatticeHeight;    // 宫缩区域和胎心区域，中间间隔高度为2个fetalLatticeHeight，胎心区域开始的高度
        // 胎心区域垂直方向值为60-210，所以求出每个点所占高度
        fetalYPointHeight = fetalAreaHeight / 160;
        // 宫缩区域垂直方向值为0-100，所以求出每个点所占高度
        tocoYPointHeight = tocoAreaHeight / 100;
        // 背景为白色，这里准备绘制一个白色的矩形
        bgRect = new Rect(0, 0, viewWidth, viewHeight);
//        if (MeasureSpec.getSize(widthMeasureSpec) < screenWidth){
//            setMeasuredDimension(screenWidth, viewHeight);
//        } else {
//            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), viewHeight);
//        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(bgRect, whitePaint);
        drawBaseChart(canvas);  // 画曲线图背景
        drawGreenArea(canvas);  // 画绿色区域背景
        drawCoordinate(canvas); // 画坐标文字
        drawCurve(canvas);  // 画曲线
    }

    // 画曲线
    private void drawCurve(Canvas canvas) {
        if (null != datas && datas.length > 0){
            canvas.save();
            canvas.translate(0, tenDpHeight);
//            Path path = new Path();
            for (int i = 1; i < datas.length; i++) {
                int lastRate = datas[i - 1].heartRate;
                int currRate = datas[i].heartRate;
                int lastToco = datas[i - 1].tocoWave;
                int currToco = datas[i].tocoWave;
                int status1 = datas[i].status1;
                // 画胎心曲线
                if (currRate <= 210 && currRate >= 50) {    // 胎心范围值
                    if (lastRate <= 210 && lastRate >= 50){ // 没有断线
                        canvas.drawLine((i - 1)*onePointWidth + twentySecWidth, (210 - lastRate) * fetalYPointHeight, i*onePointWidth + twentySecWidth, (210 - currRate) * fetalYPointHeight, linePaintL);
//                            path.lineTo(i*onePointWidth + twentySecWidth, (210 - currRate) * fetalYPointHeight);
                    } else {
//                            path.moveTo(twentySecWidth, (210 - currRate) * fetalYPointHeight);
                    }
                }
                // 画宫缩曲线
                if (currToco >= 0 && currToco <= 100){  // 宫缩范围值
                    if (lastToco <= 100 && lastToco >= 0){
                        canvas.drawLine((i - 1)*onePointWidth + twentySecWidth, tocoStartY + (100 - lastToco) * tocoYPointHeight, i*onePointWidth + twentySecWidth, tocoStartY + (100 - currToco) * tocoYPointHeight, linePaintL);
                    }
                }
            }
//            canvas.drawPath(path, linePaintL);
            canvas.restore();
        }
    }

    // 画坐标文字
    private void drawCoordinate(Canvas canvas) {
        canvas.save();
        canvas.translate(0, tenDpHeight);
        // 胎心区域坐标文字
        for (int i = 0; i <= FETALLATTICENUM; i+=3) {
            for(float j = twentySecWidth; j <= viewWidth; j+= 8 * twentySecWidth){
                // 先画一个白色背景的矩形，这里+4 -4是为了矩形的高度更窄
                Rect fetalRect;
//                if (i == 0){    // 第一个坐标，是在横线下方的
//                    fetalRect = new Rect((int)(j - coorWidth/2), (int)(i*fetalLatticeHeight - coorHeight/2 + 4), (int)(j + coorWidth/2), (int)(i*fetalLatticeHeight + coorHeight/2 - 4));
//                } else {
                    fetalRect = new Rect((int)(j - coorWidth/2), (int)(i*fetalLatticeHeight - coorHeight/2 + 4), (int)(j + coorWidth/2), (int)(i*fetalLatticeHeight + coorHeight/2 - 4));
//                }
                if (i >= 5 && i<= 10){  // 绿色区域，背景是绿色，直接画绿色矩形，会和背景重合，颜色会变深，所以先画个白底，再画绿色
                    canvas.drawRect(fetalRect, whitePaint);
                    canvas.drawRect(fetalRect, greenAreaPaint);
                } else {
                    canvas.drawRect(fetalRect, whitePaint);
                }
                // 再画坐标文字
                // 这里要注意y坐标的计算，因为在文字的坐标系里面，坐标都是以文字基线为坐标轴X轴的，基线以上为负数，基线以下为正数，所以top是负数，bottom是正数
                canvas.drawText(String.valueOf(fetalMax), fetalRect.centerX(), fetalRect.centerY() - fontMetrics.bottom/2 - fontMetrics.top/2, linePaintT);
            }
            fetalMax-=30;
        }
        // 宫缩区域坐标文字
        for (float i = 0; i <= 10; i+=2) {
            for(float j = twentySecWidth; j <= viewWidth; j+= 8 * twentySecWidth) {
                Rect tocoRect = new Rect((int)(j - coorWidth/2), (int)(tocoStartY + i*tocoLatticeHeight - coorHeight/2 + 4), (int)(j + coorWidth/2), (int)(tocoStartY + i*tocoLatticeHeight + coorHeight/2 - 4));
                canvas.drawRect(tocoRect, whitePaint);
                canvas.drawText(String.valueOf(tocoMax), tocoRect.centerX(), tocoRect.centerY() - fontMetrics.bottom/2 - fontMetrics.top/2, linePaintT);
            }
            tocoMax-=20;
        }
        // 画时间坐标文字
        for (int i = 0; i <= viewWidth/twentySecWidth; i++) {
            if (i != 1 && i % 3 == 1){  // 0分钟的坐标不画
                // 胎心区域和宫缩区域之间的间隔，高度是2*fetalLatticeHeight，这里fetalAreaHeight + fetalLatticeHeight + coorHeight/2是为了让时间坐标文字高度居中
                canvas.drawText(startTime + "'", i * twentySecWidth, fetalAreaHeight + fetalLatticeHeight, linePaintT);
                startTime+=1;
            }
        }
        // 重新初始化这些值
        fetalMax = 210;
        tocoMax = 100;
        startTime = 1;
        canvas.restore();
    }

    // 画绿色背景区域
    private void drawGreenArea(Canvas canvas) {
        canvas.save();
        canvas.translate(0, tenDpHeight);
        // 绿色区域是110-160
        Rect rect = new Rect(0, (int)(fetalYPointHeight * 50), viewWidth, (int)(fetalYPointHeight * 100));
        canvas.drawRect(rect, greenAreaPaint);
        canvas.restore();
    }

    // 画背景图
    private void drawBaseChart(Canvas canvas) {
        canvas.save();
        canvas.translate(0, tenDpHeight);
        // 先画胎心横向区域背景
        for (int i = 0; i <= FETALLATTICENUM; i++) {
            if (i % 3 != 0){
                canvas.drawLine(0, i * fetalLatticeHeight, viewWidth, i * fetalLatticeHeight, linePaintN);
            } else {
                canvas.drawLine(0, i * fetalLatticeHeight, viewWidth, i * fetalLatticeHeight, linePaintW);
            }
        }
        // 画宫缩横向区域背景
        for (int i = 0; i <= TOCOLATTICENUM; i++) {
            if (i % 2 == 0) {
                canvas.drawLine(0, i * tocoLatticeHeight + tocoStartY, viewWidth, i * tocoLatticeHeight + tocoStartY, linePaintW);
            } else {
                canvas.drawLine(0, i * tocoLatticeHeight + tocoStartY, viewWidth, i * tocoLatticeHeight + tocoStartY, linePaintN);
            }
        }
        // 垂直区域背景
        for (int i = 0; i <= viewWidth/twentySecWidth; i++) {
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

    private int mLastX;
    private int mCurrX;
    private VelocityTracker velocityTracker;
    private ViewConfiguration viewConfiguration;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float scrollX = getScrollX();
        getParent().requestDisallowInterceptTouchEvent(true);
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastX = mCurrX = (int) event.getX();
                return true;

            case MotionEvent.ACTION_MOVE:
                mCurrX = (int) event.getX();
                int deltaX = mLastX - mCurrX;
                Log.d("onTouchEvent", "scrollX - deltaX : " + (scrollX - deltaX));
                Log.d("onTouchEvent", "scrollX + deltaX : " + (scrollX + deltaX));
                if (scrollX + deltaX > 0 && (scrollX + deltaX) <= (viewWidth - screenWidth)) {
                    scrollBy(deltaX, 0);
                }
                mLastX = mCurrX;
                return true;

            case MotionEvent.ACTION_UP:
                mCurrX = (int) event.getX();
//                velocityTracker.computeCurrentVelocity(1000);  //计算1秒内滑动过多少像素
//                int xVelocity = (int) velocityTracker.getXVelocity();
//                if (Math.abs(xVelocity) > viewConfiguration.getScaledMinimumFlingVelocity()) {  //滑动速度可被判定为抛动
//                    mScroller.fling(getScrollX(), 0, -xVelocity, 0, 0, viewWidth - screenWidth, 0, 0);
//                }
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }

//    @Override
//    public void computeScroll() {
//        super.computeScroll();
//        if (mScroller.computeScrollOffset()){
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            invalidate();
//        }
//    }

    // 获取文字的高度
    private float getTextHeight(Paint paint, String str) {
        float height = 0;
        if (null != paint && !TextUtils.isEmpty(str)) {
            Paint.FontMetrics fm = paint.getFontMetrics();
            height = fm.descent - fm.ascent;
        }
        return height;
    }

    /**
     * 设置曲线的数据
     * @param datas 曲线数据
     */
    public void setDatas(TimeData[] datas){
        this.datas = datas;
        // 根据数据量的大小来判断图表宽度
        // MINSINTONESCREEN * 60 * 2，一秒钟画2个数据，这里是一屏幕宽度要展示的数据量
//        if (datas.length <= 0 || datas.length <= MINSINONESCREEN * 60 * 2){
//            viewWidth = screenWidth;
//        } else if (datas.length <= 2 * MINSINONESCREEN * 60 * 2){   // 数据量少于等于两屏幕
//            viewWidth = 2 * screenWidth;
//        } else {
//            viewWidth = 3 * screenWidth;    // 这里是想要只画包括前后共3屏幕的图，节省一点资源，但是这样子的话，需要判断时候要重新画图？
//        }
//        viewWidth = screenWidth + (datas.length / (MINSINONESCREEN * 60 * 2) * screenWidth);
        viewWidth = (int)(onePointWidth * datas.length + screenWidth/2);
        requestLayout();
        invalidate();
    }
}
