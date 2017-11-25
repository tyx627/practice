package com.tyx.mypractice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tyx.mypractice.R;
import com.tyx.mypractice.adapter.PracticeAdapter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private PracticeAdapter mAdapter;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        initView();
    }

    private void initView() {
        // 获取数据
        String[] lsTxt = getResources().getStringArray(R.array.practice_ls);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        mAdapter = new PracticeAdapter(this, listener);
        mAdapter.setData(lsTxt);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    private ItemClickListener listener = new ItemClickListener() {
        @Override
        public void onClick(int position) {
            switch (position){
                // 购物车
                case 0:
                    goActivity(ShoppingCarPracActivity.class);
                    break;
                case 1:
                    goActivity(FallingSnowActivity.class);
                    break;
                default:
                    break;
            }
        }
    };

    public interface ItemClickListener {
        void onClick(int position);
    }

    private void goActivity(Class mClass){
        Intent intent = new Intent(mActivity, mClass);
        mActivity.startActivity(intent);
    }

}
