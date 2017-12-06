package com.tyx.mypractice.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.tyx.mypractice.util.view.CustomVPPager;

import java.util.ArrayList;

/**
 * 自适应高度viewpager的adapter
 * Created by tyx on 2017/12/6 0006.
 */

public class AdaptiveHeightVPAdapter extends PagerAdapter {

    private ArrayList<CustomVPPager> pagers;

    public AdaptiveHeightVPAdapter(ArrayList<CustomVPPager> pagers){
        this.pagers = pagers;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pagers.get(position).initView(container));
        return pagers.get(position).rootView;
    }

    @Override
    public int getCount() {
        return null == pagers? 0:pagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.d("======", (view == object) + "");
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagers.get(position).rootView);
    }
}
