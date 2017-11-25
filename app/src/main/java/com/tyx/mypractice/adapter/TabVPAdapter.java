package com.tyx.mypractice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tyx.mypractice.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by tyx on 2017/11/22.
 */

public class TabVPAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private String[] tabTxts;

    public TabVPAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments, String[] tabTxts) {
        super(fm);
        this.fragments = fragments;
        this.tabTxts = tabTxts;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.size() > 0 ? fragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabTxts != null && tabTxts.length > 0){
            return tabTxts[position];
        } else {
            return super.getPageTitle(position);
        }
    }
}
