package com.tyx.mypractice.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.util.Random;

import static com.tyx.mypractice.util.FallObject.Builder.changeBitmapSize;

/**
 * 雪花飘落动画中，对下落物体进行封装，builder设计模式
 * Created by tyx on 2017/11/24.
 */

public class FallObject {

    private static final int defaultSpeed = 10;     // 默认下降速度

    private boolean isSpeedRandom;      // 物体初始下降速度比例是否随机
    private boolean isSizeRandom;       // 物体初始大小比例是否随机

    private  Bitmap bitmap;
    public Builder builder;

    private Random random;
    private int parentWidth;  // 父容器宽度
    private int parentHeight; // 父容器高度
    private float objectWidth;      // 下落物体宽度
    private float objectHeight;     // 下落物体高度
    private int initX;
    private int initY;
    private float presentX;     // 当前位置X坐标
    private float presentY;     // 当前位置Y坐标
    private int initSpeed;    // 初始下降速度
    private float presentSpeed;     // 当前下降速度

    public FallObject(Builder builder, int parentWidth, int parentHeight){
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
        this.builder = builder;
        random = new Random();
        // 随机物体的X坐标
        initX = random.nextInt(parentWidth);
        // 随机物体的Y坐标，并让物体一开始从屏幕顶部下落
        initY = random.nextInt(parentHeight) - parentHeight;
        presentX = initX;
        presentY = initY;

        initSpeed = builder.initSpeed;
        isSpeedRandom = builder.isSpeedRandom;
        isSizeRandom = builder.isSizeRandom;
        randomSpeed();
        randomSize();
    }

    private void randomSize() {
        if(isSizeRandom){
            float r = (random.nextInt(10)+1)*0.1f;
            float rW = r * builder.bitmap.getWidth();
            float rH = r * builder.bitmap.getHeight();
            bitmap = changeBitmapSize(builder.bitmap,(int)rW,(int)rH);
        }else {
            bitmap = builder.bitmap;
        }
        objectWidth = bitmap.getWidth();
        objectHeight = bitmap.getHeight();
    }

    /**
     * 随机物体初始下落速度
     */
    private void randomSpeed() {
        if (isSpeedRandom){
            presentSpeed = (float)((random.nextInt(3) + 1) * 0.1 + 1) * initSpeed;
        } else {
            presentSpeed = initSpeed;
        }
    }

    private FallObject(Builder builder){
        this.builder = builder;
        initSpeed = builder.initSpeed;
        bitmap = builder.bitmap;
        isSpeedRandom = builder.isSpeedRandom;
        isSizeRandom = builder.isSizeRandom;
    }

    public static final class Builder{
        private boolean isSpeedRandom;      // 物体初始下降速度比例是否随机
        private boolean isSizeRandom;       // 物体初始大小比例是否随机

        private int initSpeed;
        private Bitmap bitmap;

        public Builder(Bitmap bitmap){
            this.initSpeed = defaultSpeed;
            this.bitmap = bitmap;
            this.isSpeedRandom = false;
            this.isSizeRandom = false;
        }

        public Builder(Drawable drawable){
            this.initSpeed = defaultSpeed;
            this.bitmap = drawableToBitmap(drawable);
            this.isSpeedRandom = false;
            this.isSizeRandom = false;
        }

        /**
         * drawable图片资源转bitmap
         * @param drawable
         * @return
         */
        public static Bitmap drawableToBitmap(Drawable drawable) {
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565
            );
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        /**
         * 设置物体的初始下落速度
         * @param speed
         * @return builder
         */
        public Builder setSpeed(int speed){
            this.initSpeed = speed;
            return this;
        }

        /**
         * 设置物体的初始下落速度
         * @param speed
         * @param isRandomSpeed 物体初始下降速度比例是否随机
         * @return
         */
        public Builder setSpeed(int speed, boolean isRandomSpeed){
            this.initSpeed = speed;
            this.isSpeedRandom = isRandomSpeed;
            return this;
        }

        public FallObject build(){
            return new FallObject(this);
        }

        /**
         * 设置物体大小
         * @param w
         * @param h
         * @return
         */
        public Builder setSize(int w, int h){
            this.bitmap = changeBitmapSize(this.bitmap, w, h);
            return this;
        }

        /**
         * 设置物体大小
         * @param w
         * @param h
         * @param isRandomSize 物体初始大小比例是否随机
         * @return
         */
        public Builder setSize(int w, int h, boolean isRandomSize){
            this.bitmap = changeBitmapSize(this.bitmap, w, h);
            this.isSizeRandom = isRandomSize;
            return this;
        }

        /**
         * 改变bitmap的大小
         * @param bitmap 目标bitmap
         * @param newW 目标宽度
         * @param newH 目标高度
         * @return
         */
        public static Bitmap changeBitmapSize(Bitmap bitmap, int newW, int newH) {
            int oldW = bitmap.getWidth();
            int oldH = bitmap.getHeight();
            // 计算缩放比例
            float scaleWidth = ((float)newW) / oldW;
            float scaleHeight = ((float)newH) / oldH;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, oldW, oldH, matrix, true);
            return bitmap;
        }
    }

    /**
     * 绘制物体对象
     * @param canvas
     */
    public void drawObject(Canvas canvas){
        moveObject();
        canvas.drawBitmap(bitmap, presentX, presentY, null);
    }

    /**
     * 移动物体对象
     */
    private void moveObject() {
        moveY();
        if (presentY > parentHeight){
            reset();
        }
    }

    /**
     * 重置object位置
     */
    private void reset() {
        presentY = -objectHeight;
//        presentSpeed = initSpeed;
        randomSpeed();
    }

    /**
     * Y轴上的移动逻辑
     */
    private void moveY() {
        presentY += presentSpeed;
    }

}
