package com.tyx.mypractice.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tyx.mypractice.R;
import com.tyx.mypractice.adapter.PracticeAdapter;
import com.tyx.mypractice.util.view.CustomDialog;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements CustomDialog.DialogClickListener {

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
                case 2:
                    showDialog();
                    break;
                case 3:
                    goActivity(CustomViewPagerActivity.class);
                    break;
                case 4:
                    goActivity(ArcAnimateActivity.class);
                    break;
                case 5:
                    goActivity(VideoPlayerActivity.class);
                    break;
                case 6:
                    goActivity(BlueToothScanActivity.class);
                    break;
                case 7:
                    goActivity(CustomChartActivity.class);
                    break;
                default:
                    break;
            }
        }
    };

    // 弹出自定义的CustomDialog
    private void showDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this, R.layout.view_dialog_confirm);
        CustomDialog dialog = builder.setTitle(R.id.tv_title, "dialog测试", R.style.dialog_test_title)
                .setContent(R.id.tv_content, "CustomDialog弹框成功！")
                .setPositiveText(R.id.tv_sure, R.string.sure)
                .setPositiveListener(this)
                .setNegativeText(R.id.tv_cancel, R.string.cancle)
                .setNegativeListener(this)
                .setDialogStyle(R.style.CustomDialog)
                .build();
        dialog.show();
    }

    // CustomDialog按钮的回调
    @Override
    public void click(CustomDialog dialog, View view) {
        if (view.getId() == R.id.tv_sure){
            Toast.makeText(this, "确定", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        } else if (view.getId() == R.id.tv_cancel){
            Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    public interface ItemClickListener {
        void onClick(int position);
    }

    private void goActivity(Class mClass){
        Intent intent = new Intent(mActivity, mClass);
        mActivity.startActivity(intent);
    }

}
