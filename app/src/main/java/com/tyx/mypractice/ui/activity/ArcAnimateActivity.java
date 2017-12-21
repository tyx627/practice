package com.tyx.mypractice.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tyx.mypractice.R;
import com.tyx.mypractice.util.view.ArcAnimateView;

import butterknife.BindView;

/**
 * Created by tyx on 2017/12/21.
 */

public class ArcAnimateActivity extends BaseActivity {

    @BindView(R.id.arc_animate)
    ArcAnimateView arcAnimate;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_arc_animate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arcAnimate.setImageRes(R.drawable.test);
        arcAnimate.init();
    }
}
