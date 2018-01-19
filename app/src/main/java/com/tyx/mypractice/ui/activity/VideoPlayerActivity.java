package com.tyx.mypractice.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tyx.mypractice.R;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 视频播放器   这里用的是饺子视频播放器
 * Created by tyx on 2018/1/17 0017.
 */

public class VideoPlayerActivity extends BaseActivity {

    @BindView(R.id.player)
    JZVideoPlayerStandard player;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPlayer();
    }

    private void initPlayer() {
        player.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");
//        player.thumbImageView.("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

    @Override
    public void onBackPressed() {
        if (player.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.releaseAllVideos();
    }
}
