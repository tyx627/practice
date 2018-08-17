package com.tyx.mypractice.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.tyx.mypractice.R;
import com.tyx.mypractice.model.fetalmonitor.FetalMonitorBean;
import com.tyx.mypractice.model.fetalmonitor.TimeData;
import com.tyx.mypractice.util.DownLoadUtil;
import com.tyx.mypractice.util.view.CustomChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 自定义的曲线图
 */
public class CustomChartActivity extends BaseActivity {

    @BindView(R.id.custom_chart_view)
    CustomChartView mCustomChartView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_custom_chart;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        String filePath = Environment.getExternalStorageDirectory() + "/fetalmonitor/datacache/5b724d79e4b066ba13db674c.json";
        getDataFromLocal(filePath);
    }

    // 从本地获取曲线数据文件
    private void getDataFromLocal(final String dataFilePath) {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                String dataJson = DownLoadUtil.getInstance().getDataFromLocal(dataFilePath);
                if (!TextUtils.isEmpty(dataJson)) {
                    e.onNext(dataJson);
                } else {
                    e.onError(null);
                }
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new ResourceSubscriber<String>() {
            @Override
            public void onNext(String dataJsonStr) {
                dealData(dataJsonStr);
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(CustomChartActivity.this, "数据读取出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void dealData(final String dataJson) {
        Flowable.create(new FlowableOnSubscribe<TimeData[]>() {
            @Override
            public void subscribe(FlowableEmitter<TimeData[]> e) throws Exception {
                try {
                    // 将读出来的内容转换成集合
                    List<FetalMonitorBean> fetalMonitorBeans = new ArrayList<>();
                    List<FetalMonitorBean> monitorBeans = JSONArray.parseArray(dataJson, FetalMonitorBean.class);
                    // 蓝牙一秒产生4个数据，但是画图0.5秒取一个数据
                    for (int i = 0; i < monitorBeans.size(); i++) {
                        if (i % 2 == 0) {
                            // 由于取得是双数脚标的值，但是可能在单数脚标的时候有胎动，如果直接过滤，则会造成胎动数据丢失，所以加上这个判断
                            if (i > 1 && 1 == monitorBeans.get(i - 1).getFmFlag()) {
                                monitorBeans.get(i).setFmFlag((byte) 1);
                            }
                            fetalMonitorBeans.add(monitorBeans.get(i));
                        }
                    }

                    e.onNext(getTimeDatas(fetalMonitorBeans));

                } catch (Exception exception) {
                    e.onError(null);
                    exception.printStackTrace();
                }
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new ResourceSubscriber<TimeData[]>() {
            @Override
            public void onNext(TimeData[] timeDatas) {
                if (null != mCustomChartView)
                mCustomChartView.setDatas(timeDatas);
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(CustomChartActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    // 获取画图需要的数据集合
    private TimeData[] getTimeDatas(List<FetalMonitorBean> fetalMonitorBeans) {
        TimeData[] timeDatas = new TimeData[fetalMonitorBeans.size()];
        for (int i = 0; i < fetalMonitorBeans.size(); i++) {
            byte status1 = (byte) ((fetalMonitorBeans.get(i).getSignal() & 0x03)
                    + ((fetalMonitorBeans.get(i).getAfmFlag() << 2) & 0x04)
                    + ((fetalMonitorBeans.get(i).getFmFlag() << 3) & 0x08)
                    + ((fetalMonitorBeans.get(i).getTocoFlag() << 4) & 0x10));
            TimeData timeData = new TimeData(fetalMonitorBeans.get(i).getRate(), fetalMonitorBeans.get(i).getToco(), fetalMonitorBeans.get(i).getAfm(), status1, 0, fetalMonitorBeans.get(i).getFmFlag());
            timeDatas[i] = timeData;
        }
        return timeDatas;
    }

    @OnClick(R.id.custom_chart_view)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.custom_chart_view:
                break;
        }
    }
}
