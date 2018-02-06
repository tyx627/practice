package com.tyx.mypractice.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * 视频播放器
 * Created by tyx on 2018/1/19 0019.
 */

public class MyVideoPlayer extends JZVideoPlayerStandard {

    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 这是播放控件初始化的时候最先调用的
    @Override
    public void init(Context context) {
        super.init(context);
        JZVideoPlayerStandard.SAVE_PROGRESS = false;
        Log.d("MyVideoPlayer", "init");
    }

    // 这是控件里所有控件的onClick响应函数，比如监听开始按钮的点击，全屏按钮的点击，空白的点击，retry按钮的点击，等。如果你想拦截这些点击的响应或者继承这些点击的响应，那么复写此函数
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Log.d("MyVideoPlayer", "onClick " + v.getId());
    }

    // 在JCVideoPlayer中此函数主要响应了全屏之后的手势控制音量和进度
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("MyVideoPlayer", "onTouch");
        return super.onTouch(v, event);
    }

    // 用户触发的视频开始播放
    @Override
    public void startVideo() {
        super.startVideo();
        Log.d("MyVideoPlayer", "startVideo");
    }

    // 进入preparing状态，正在初始化视频
    @Override
    public void onStatePrepared() {
        super.onStatePrepared();
        Log.d("MyVideoPlayer", "onStatePrepared");
    }

    // preparing之后进入播放状态
    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        Log.d("MyVideoPlayer", "onStatePlaying");
    }

    // 暂停视频，进入暂停状态
    @Override
    public void onStatePause() {
        super.onStatePause();
        Log.d("MyVideoPlayer", "onStatePause");
    }

    // 进入错误状态
    @Override
    public void onStateError() {
        super.onStateError();
        Log.d("MyVideoPlayer", "onStateError");
    }

    // 进入视频自动播放完成状态
    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        Log.d("MyVideoPlayer", "onStateAutoComplete");
    }

    // android.media.MediaPlayer回调的info
    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
        Log.d("MyVideoPlayer", "onInfo");
    }

    // android.media.MediaPlayer回调的error
    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
        Log.d("MyVideoPlayer", "onError");
    }

    // 进入全屏
    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
        Log.d("MyVideoPlayer", "startWindowFullscreen");
    }

    // 退出全屏
    @Override
    public void startWindowTiny() {
        super.startWindowTiny();
        Log.d("MyVideoPlayer", "startWindowTiny");
    }

    private long sumTime = 0;

    @Override
    public void setProgressAndText(int progress, long position, long duration) {
        super.setProgressAndText(progress, position, duration);
        sumTime += 300;
        Log.d("MyVideoPlayer", "setProgressAndText position == " + position + "            sumTime == " + sumTime + "         duration == " + duration);
    }

    // 退出的时候会回调这个
    @Override
    public void onCompletion() {
        super.onCompletion();
        Log.d("MyVideoPlayer", "onCompletion");
    }

    // 自动播放完成
    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        Log.d("MyVideoPlayer", "onAutoCompletion");
    }
}
