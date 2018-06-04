package com.tyx.mypractice.util.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tyx.mypractice.R;

/**
 * 自定义的蓝牙扫描动画类
 * Created by tyx on 2018/5/2 0002.
 */

public class BlueToothScanViewNew extends RelativeLayout {

    private Context mContext;
    private int screenWidth;        // 屏幕宽度
    private int height;             // 布局的高度
    private int radius;             // 半径
//    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;
    private boolean isScanning = false;     // 是否正在扫描中
    private int btnWidth;

    private boolean downRight = false;      // ACTION_DOWN的点是否在按钮上
    private boolean upRight = false;        // ACTION_UP的点是否在按钮上
    private float rotation;                 // 旋转的角度
    private Paint circleParint;
    private OnScanStateChangeListener scanStateChangeListener;
    private ImageView ivDefault;
    private ImageView ivScan;
    private ImageView ivBtn;

    public BlueToothScanViewNew(Context context) {
        this(context, null);
    }

    public BlueToothScanViewNew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlueToothScanViewNew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    // 初始化
    private void initView() {
        // 获取屏幕宽度
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        // 设置高度为屏幕宽度的7/10
        height = 7 * screenWidth / 10;
        radius = screenWidth / 2;
        btnWidth = dp2px(mContext, 90);

        circleParint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleParint.setColor(mContext.getResources().getColor(R.color.color_input_line));
        circleParint.setStyle(Paint.Style.STROKE);
        circleParint.setStrokeWidth(dp2px(mContext, 1));

        initImageView();
    }

    // 初始化按钮的ImageView和扫描动画的ImageView
    private void initImageView() {
//        ivDefault = new ImageView(mContext);
//        LayoutParams defaultLp = new LayoutParams(screenWidth, screenWidth);
//        ivDefault.setLayoutParams(defaultLp);
//        ivDefault.setImageResource(R.mipmap.bg_wave_default);
//        addView(ivDefault);

        ivScan = new ImageView(mContext);
        LayoutParams scanLp = new LayoutParams(screenWidth, screenWidth);
        ivScan.setLayoutParams(scanLp);
        ivScan.setImageResource(R.mipmap.bg_wave_scanning);
        addView(ivScan);

        ivBtn = new ImageView(mContext);
        LayoutParams btnLp = new LayoutParams(dp2px(mContext, 90), dp2px(mContext, 90));
        ivBtn.setLayoutParams(btnLp);
        ivBtn.setImageResource(R.mipmap.bt_bluebooth_default);
        addView(ivBtn);

        ivBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(screenWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int childCount = getChildCount();
//        if (childCount > 0){
//            for (int i = 0; i < childCount; i++) {
//                View child = getChildAt(i);
//                if (null != child){
//                    child.layout(0, 0, screenWidth, height);
//                }
//            }
//        }
//        ivDefault.layout(0, 0, screenWidth, screenWidth);
        ivScan.layout(0, 0, screenWidth, screenWidth);
        ivBtn.layout(radius - btnWidth / 2, radius - btnWidth / 2, radius + btnWidth / 2, radius + btnWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(bitmap1, 0, 0, null);
        canvas.drawCircle(radius, radius, radius - dp2px(mContext, 1), circleParint);
        canvas.drawCircle(radius, radius, radius * 2/3, circleParint);
        canvas.drawCircle(radius, radius,  radius/ 3, circleParint);
        super.onDraw(canvas);
    }

    /**
     * 开始扫描
     */
    public void startScan(){
//        isScanning = true;
//        removeCallbacks(scanR);
//        postDelayed(scanR, 100);
        Animation animation = new RotateAnimation(0, 360f, radius, radius);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        ivScan.startAnimation(animation);
    }

    /**
     * 停止扫描
     */
//    public void stopScan(){
//        releaseBitmap();
//        isScanning = false;
//        removeCallbacks(scanR);
//        invalidate();
//    }

    // 释放掉bitmap
    private void releaseBitmap() {
        if (null != bitmap2){
            bitmap2.recycle();
            bitmap2 = null;
        }
        if (null != bitmap3){
            bitmap3.recycle();
            bitmap3 = null;
        }
        if (null != bitmap4){
            bitmap4.recycle();
            bitmap4 = null;
        }
    }

    public void setScanStateChangeListener(OnScanStateChangeListener scanStateChangeListener){
        this.scanStateChangeListener = scanStateChangeListener;
    }

    public interface OnScanStateChangeListener{
        void start();
        void stop();
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
