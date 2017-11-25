package com.tyx.mypractice.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tyx.mypractice.R;

import butterknife.BindView;

/**
 * Created by tyx on 2017/11/22.
 */

public class ShoppingCarFragment extends BaseFragment {

    @BindView(R.id.elv_pro)
    ExpandableListView elvPro;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_shopping_car;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
