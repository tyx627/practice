package com.tyx.mypractice.util;

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

}
