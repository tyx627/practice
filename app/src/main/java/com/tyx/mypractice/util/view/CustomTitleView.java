package com.tyx.mypractice.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tyx.mypractice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tyx on 2017/11/16.
 */

public class CustomTitleView extends RelativeLayout {

    @BindView(R.id.title_tv_left)
    TextView titleTvLeft;
    @BindView(R.id.title_tv_middle)
    TextView titleTvMiddle;
    @BindView(R.id.title_tv_right)
    TextView titleTvRight;

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    // 初始化布局
    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView);
        String leftText = typedArray.getString(R.styleable.CustomTitleView_leftText);
        int leftImg = typedArray.getResourceId(R.styleable.CustomTitleView_leftImg, 0);
        String middleText = typedArray.getString(R.styleable.CustomTitleView_middleText);
        int middleImg = typedArray.getResourceId(R.styleable.CustomTitleView_middleImg, 0);
        String rightText = typedArray.getString(R.styleable.CustomTitleView_rightText);
        int rightImg = typedArray.getResourceId(R.styleable.CustomTitleView_rightImg, 0);
        typedArray.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.title_view_layout, this);
        ButterKnife.bind(view);

        if (!TextUtils.isEmpty(leftText)){

            titleTvLeft.setText(leftText);
        } else {
            titleTvLeft.setVisibility(INVISIBLE);
        }
        if (!TextUtils.isEmpty(middleText)){
            titleTvMiddle.setText(middleText);
        } else {
            titleTvMiddle.setVisibility(INVISIBLE);
        }
        if (!TextUtils.isEmpty(rightText)){
            titleTvRight.setText(rightText);
        } else {
            titleTvRight.setVisibility(INVISIBLE);
        }

    }

    public void setClickListener(OnClickListener listener){
        if (null == listener){
            return;
        }
        if (titleTvLeft.getVisibility() == VISIBLE){
            titleTvLeft.setOnClickListener(listener);
        }
        if (titleTvRight.getVisibility() == VISIBLE){
            titleTvRight.setOnClickListener(listener);
        }
    }

}
