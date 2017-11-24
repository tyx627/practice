package com.tyx.mypractice.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tyx.mypractice.R;
import com.tyx.mypractice.util.FallObject;
import com.tyx.mypractice.util.FallingView;

import butterknife.BindView;

/**
 * Created by tyx on 2017/11/24.
 */

public class FallingSnowActivity extends BaseActivity {

    @BindView(R.id.falling_view)
    FallingView fallingView;

    private Paint snowPaint;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_fallingsnow;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绘制雪球bitmap
        snowPaint = new Paint();
        snowPaint.setColor(Color.WHITE);
        snowPaint.setStyle(Paint.Style.FILL);
        bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmapCanvas.drawCircle(25, 25, 25, snowPaint);

        // 初始化一个雪球样式的fallObject
        FallObject.Builder builder = new FallObject.Builder(getResources().getDrawable(R.drawable.snow));
        FallObject fallObject = builder
                .setSpeed(10, true)
                .setSize(50, 50, true)
                .build();
        fallingView.addFallObject(fallObject, 50);  // 添加50个雪球对象
    }
}
