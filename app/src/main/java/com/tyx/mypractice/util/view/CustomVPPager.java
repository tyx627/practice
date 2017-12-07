package com.tyx.mypractice.util.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyx.mypractice.R;
import com.tyx.mypractice.ui.activity.CustomViewPagerActivity;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class CustomVPPager extends View implements View.OnClickListener {

//    @BindView(R.id.tv_content)
    private TextView tvContent;
//    @BindView(R.id.rtv_yes)
    private TextView rtvYes;
//    @BindView(R.id.rtv_no)
    private TextView rtvNo;

    private Context mContext;
    private String mContent;
    private int index;
    private ViewPager vp;
    private CustomViewPagerActivity.PagerCallBack callBack;
    public View rootView;

    public CustomVPPager(Context context) {
        super(context);
    }

    public CustomVPPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    public CustomVPPager(Context context, String content, int index, ViewPager vp) {
//        this.mContext = context;
//        this.mContent = content;
//        this.index = index;
//        this.vp = vp;
////        initView();
//    }

    public View initView(ViewGroup container) {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.view_custom_viewpager, container, false);
//        ButterKnife.bind(mContext, rootView);
        tvContent = (TextView)rootView.findViewById(R.id.tv_content);
        rtvYes = (TextView)rootView.findViewById(R.id.rtv_yes);
        rtvNo = (TextView)rootView.findViewById(R.id.rtv_no);

        tvContent.setText(mContent);
        rtvYes.setOnClickListener(this);
        rtvNo.setOnClickListener(this);
        return rootView;
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

    public void setCallBack(CustomViewPagerActivity.PagerCallBack callBack){
        this.callBack = callBack;
    }
}
