package com.tyx.mypractice.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tyx.mypractice.R;
import com.tyx.mypractice.adapter.TabVPAdapter;
import com.tyx.mypractice.util.CustomTitleView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by tyx on 2017/11/21.
 */

public class ShoppingCarPracActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_view)
    CustomTitleView titleView;
    @BindView(R.id.fl_content)
    RelativeLayout flContent;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;

    private TabVPAdapter mAdapter;
    private TextView tvItemTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initClickListener();
    }

    private void initView() {
        initTab();
        initViewPager();
    }

    private void initTab() {
        tlTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        tlTab.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.red));

//        tlTab.addTab(tlTab.newTab().setCustomView(R.layout.item_tab));
//        tvItemTab = (TextView)findViewById(R.id.item_tab);
//        tvItemTab.setText(getResources().getString(R.string.index));
//        tvItemTab.setTextColor(getResources().getColor(R.color.red));
//        tvItemTab.setTextSize(15);

        tlTab.setupWithViewPager(viewpager);
    }

    private void initViewPager() {
        String[] tabTxts = getResources().getStringArray(R.array.tab_txt);
        FragmentManager manager = getSupportFragmentManager();
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new MallFragment());
        fragments.add(new ShoppingCarFragment());
        fragments.add(new MineFragment());
        mAdapter = new TabVPAdapter(manager, fragments, tabTxts);
        viewpager.setAdapter(mAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initClickListener() {
        titleView.setClickListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_shoppingcar;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.title_tv_left:
                this.finish();
                break;
            case R.id.title_tv_right:
                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
