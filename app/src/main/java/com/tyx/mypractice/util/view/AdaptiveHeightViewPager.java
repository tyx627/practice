package com.tyx.mypractice.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.HashMap;

/**
 * 禁止手势左右滑动
 * 高度自适应内部view的viewpager
 * Created by tyx on 2017/12/5.
 */

public class AdaptiveHeightViewPager extends NoScrollViewPager {

    private int currentIndex;   // 当前页面的index
    private int height = 0;
    private HashMap<Integer, View> mChildrenViews = new HashMap<>();

    public AdaptiveHeightViewPager(Context context) {
        super(context);
    }

    public AdaptiveHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildrenViews.size() > currentIndex){
            View child = mChildrenViews.get(currentIndex);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // 重置高度
    public void resetHeight(int currentIndex){
        this.currentIndex = currentIndex;
        if (mChildrenViews.size() > currentIndex){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
            if (params == null){
                params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            } else {
                params.height = height;
            }
            setLayoutParams(params);
        }
    }

    // 保存对应的view和position
    public void setViewAndPosition(View view, int position){
        mChildrenViews.put(position, view);
    }
}
