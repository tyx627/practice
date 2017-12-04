package com.tyx.mypractice.util.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tyx.mypractice.R;
import com.tyx.mypractice.ui.activity.CustomViewPagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class CustomVPPager implements View.OnClickListener {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rtv_yes)
    TextView rtvYes;
    @BindView(R.id.rtv_no)
    TextView rtvNo;

    private Context mContext;
    private String mContent;
    private int index;
    private ViewPager vp;
    private CustomViewPagerActivity.PagerCallBack callBack;

    public CustomVPPager(Context context, String content, int index, ViewPager vp) {
        this.mContext = context;
        this.mContent = content;
        this.index = index;
        this.vp = vp;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_custom_viewpager, null);
        ButterKnife.bind(mContext, view);

        tvContent.setText(mContent);
        rtvYes.setOnClickListener(this);
        rtvNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.rtv_yes == v.getId()){
            if (callBack != null){
                callBack.click(index);
            }
        } else if (R.id.rtv_no == v.getId()){
            if (callBack != null){
                callBack.click(index);
            }
        }
    }

    public void setClaaBack(CustomViewPagerActivity.PagerCallBack callBack){
        this.callBack = callBack;
    }
}
