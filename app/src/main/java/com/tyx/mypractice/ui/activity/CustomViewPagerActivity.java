package com.tyx.mypractice.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tyx.mypractice.R;
import com.tyx.mypractice.adapter.AdaptiveHeightVPAdapter;
import com.tyx.mypractice.ui.fragment.CustomVPFragment;
import com.tyx.mypractice.util.view.AdaptiveHeightViewPager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 自定义的viewpager，禁止手势左右滑动，自适应view的高度
 * Created by tyx on 2017/12/4 0004.
 */

public class CustomViewPagerActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.view_pager)
    AdaptiveHeightViewPager viewPager;
    private String[] questions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        questions = getResources().getStringArray(R.array.vp_questions);
        ArrayList<CustomVPFragment> fragments = new ArrayList<>();
        if (null != questions && questions.length > 0) {
            for (int i = 0; i < questions.length; i++) {
                CustomVPFragment fragment = new CustomVPFragment();
                Bundle bundle = new Bundle();
                bundle.putString("question", questions[i]);
                bundle.putInt("index", i);
                fragment.setArguments(bundle);
                fragment.setCallBack(callBack);
                fragment.setViewPager(viewPager);
                fragments.add(fragment);
            }
            AdaptiveHeightVPAdapter mAdapter = new AdaptiveHeightVPAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(mAdapter);
        }
    }

    private void initView() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 设置不可手势左右滑动
        viewPager.setNoScroll(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_custom_viewpager;
    }

    private PagerCallBack callBack = new PagerCallBack() {
        @Override
        public void click(int index) {
            if (index < questions.length - 1) {
                viewPager.setCurrentItem(index + 1);
            } else {
                finish();
            }
        }
    };

    public interface PagerCallBack {
        void click(int index);
    }
}
