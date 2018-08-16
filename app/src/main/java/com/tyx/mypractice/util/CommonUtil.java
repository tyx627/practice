package com.tyx.mypractice.util;

import android.content.Context;

/**
 * 一些公共的方法
 * Created by tyx on 2017/12/1 0001.
 */

public class CommonUtil {

    private static long currentTime;    // 当前时间
    private static long lastTime;       // 最近一次点击的时间

    public static boolean isDoubleClick(){
        currentTime = System.currentTimeMillis();
        if ((currentTime - lastTime) > 1000){
            lastTime = currentTime;
            return false;
        } else {
            return true;
        }
    }

    /**
     * dp 转 px
     */
    public static int dp2px(Context context, int dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    /**
     * px 转 dp
     */
    public static int px2dp(Context context, int px){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(px/scale + 0.5f);
    }

    /**
     * sp 转 px
     */
    public static int sp2px(Context context, int sp){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

}
