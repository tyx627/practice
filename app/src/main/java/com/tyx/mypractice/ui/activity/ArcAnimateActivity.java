package com.tyx.mypractice.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.tyx.mypractice.R;
import com.tyx.mypractice.util.view.ArcAnimateView;

import butterknife.BindView;

/**
 * Created by tyx on 2017/12/21.
 */

public class ArcAnimateActivity extends BaseActivity {

    @BindView(R.id.arc_animate)
    ArcAnimateView arcAnimate;
    @BindView(R.id.btn_init)
    Button btnInit;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_arc_animate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arcAnimate.setImageRes(R.drawable.test);
        arcAnimate.init();
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arcAnimate.init();
            }
        });
    }
}
