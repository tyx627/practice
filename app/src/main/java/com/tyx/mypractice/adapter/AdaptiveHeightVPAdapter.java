package com.tyx.mypractice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tyx.mypractice.ui.fragment.CustomVPFragment;

import java.util.ArrayList;

/**
 * 自适应高度viewpager的adapter
 * Created by tyx on 2017/12/6 0006.
 */

public class AdaptiveHeightVPAdapter extends FragmentPagerAdapter {

    private ArrayList<CustomVPFragment> pagers;

    public AdaptiveHeightVPAdapter(FragmentManager fm, ArrayList<CustomVPFragment> pagers) {
        super(fm);
        this.pagers = pagers;
    }

    @Override
    public Fragment getItem(int position) {
        return null == pagers? null:pagers.get(position);
    }

    @Override
    public int getCount() {
        return null == pagers? 0 : pagers.size();
    }
}
