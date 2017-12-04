package com.tyx.mypractice.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tyx.mypractice.R;

/**
 * 自定义的viewpager，禁止手势左右滑动，自适应view的高度
 * Created by tyx on 2017/12/4 0004.
 */

public class CustomViewPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_viewpager);
        initView();
        String[] questions = getResources().getStringArray(R.array.vp_questions);
    }

    private void initView() {

    }

    @Override
    public int getLayoutResId() {
        return 0;
    }

    private PagerCallBack callBack = new PagerCallBack() {
        @Override
        public void click(int index) {

        }
    };

    public interface PagerCallBack{
        void click(int index);
    }
}
