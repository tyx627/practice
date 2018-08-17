package com.tyx.mypractice.model.fetalmonitor;

/**
 * 将画图要用的data类抽离出来
 * 每个时间戳的数据
 * Created by tyx on 2018/4/19 0019.
 */

public class TimeData {

    /** 0.5秒刷新一下 */
    public static final int PRE = 500;
    /** 无数据 */
    public static final int NONE = 0;
    /** 有数据 */
    public static final int BEAT = 1;

    /** 自动获取胎动 */
    public int beatZd = NONE;
    /** 手动获取胎动 */
    public int beatBd = NONE;
    /** 心跳频率（次/分） */
    public int heartRate;
    /** 自动胎动波形 */
    public int afmWave;
    /** 宫缩曲线 */
    public int tocoWave;
    /**
     * 状态1
     * <p>
     * bit1:bit0：胎心信号质量
     * <p>
     * 01：信号质量差
     * <p>
     * 10：信号质量一般
     * <p>
     * 11：信号质量好
     * <p>
     * bit2：自动胎动标记，1 表示自动检测到一次胎动，否则为0
     * <p>
     * bit3：手动胎动标记，1 表示孕妇感知到一次胎动，否则为0
     * <p>
     * bit7:bit4：为0
     * */
    public int status1;
    /**
     * 状态2
     * <p>
     * bit2:bit0：设备电量，0-4 档
     * <p>
     * bit3：设备状态，1 表示在充电，0 表示没有在充电
     * */
    public int status2;

    public TimeData() {

    }

    public TimeData(int heartRate, int beatBd) {

        /** 心跳频率（次/分） */
        this.heartRate = heartRate;
        /** 主动点击胎动 */
        this.beatBd = beatBd;
    }

    public TimeData(int heartRate, int tocoWave, int afmWave, int status1,
                    int status2, int beatBd) {

        this.heartRate = heartRate;
        this.tocoWave = tocoWave;
        this.afmWave = afmWave;
        this.status1 = status1;
        this.status2 = status2;
        this.beatBd = beatBd;
        if (beatBd != 0)
            this.status1 = this.status1 & (~(1 << 2)) | (1 << 2);
    }

}
