package com.tyx.mypractice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyx.mypractice.R;
import com.tyx.mypractice.ui.activity.BaseActivity;
import com.tyx.mypractice.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/21.
 */

public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.PracticeHolder> {

    private String[] mDatas;
    private BaseActivity mActivity;
    private MainActivity.ItemClickListener listener;

    public PracticeAdapter(BaseActivity activity, MainActivity.ItemClickListener listener) {
        mActivity = activity;
        this.listener = listener;
    }

    public void setData(String[] datas) {
        mDatas = datas;
        if (mDatas == null || mDatas.length <= 0) {
            mDatas[0] = mActivity.getString(R.string.no_data);
        }
    }

    @Override
    public PracticeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_practice_ls, parent, false);
        return new PracticeHolder(view);
    }

    @Override
    public void onBindViewHolder(PracticeHolder holder, final int position) {
        holder.tvItem.setText(mDatas[position]);
        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener){
                    listener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.length;
    }

    static class PracticeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item)
        TextView tvItem;

        public PracticeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

