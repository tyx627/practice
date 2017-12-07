package com.tyx.mypractice.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tyx.mypractice.R;
import com.tyx.mypractice.ui.activity.CustomViewPagerActivity;
import com.tyx.mypractice.util.view.AdaptiveHeightViewPager;

import butterknife.BindView;


/**
 * 自适应高度的viewpager的fragment
 * Created by tyx on 2017/12/7 0007.
 */

public class CustomVPFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_ques_count)
    TextView tvQuesCount;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rtv_yes)
    TextView rtvYes;
    @BindView(R.id.rtv_no)
    TextView rtvNo;

    private CustomViewPagerActivity.PagerCallBack callBack;
    private String question;
    private int index;
    private AdaptiveHeightViewPager viewPager;

    @Override
    public int getLayoutResId() {
        return R.layout.view_custom_viewpager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState){
            question = getArguments().getString("question", "");
            index = getArguments().getInt("index", 0);
        } else {
            question = savedInstanceState.getString("question");
            index = savedInstanceState.getInt("index");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (TextUtils.isEmpty(question)){
            outState.putString("question", question);
        }
        outState.putInt("index", index);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != viewPager){
            viewPager.setViewAndPosition(view, index);
        }
        tvContent.setText(question);
        rtvYes.setOnClickListener(this);
        rtvNo.setOnClickListener(this);
    }

    public void setCallBack(CustomViewPagerActivity.PagerCallBack callBack) {
        this.callBack = callBack;
    }

    public void setViewPager(AdaptiveHeightViewPager viewPager){
        this.viewPager = viewPager;
    }

    @Override
    public void onClick(View v) {
        if (null == callBack){
            return;
        }
        if (R.id.rtv_yes == v.getId()){
            callBack.click(index);
        } else if (R.id.rtv_no == v.getId()){
            callBack.click(index);
        }
    }
}
