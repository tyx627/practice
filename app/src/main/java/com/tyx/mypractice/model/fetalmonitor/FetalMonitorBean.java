package com.tyx.mypractice.model.fetalmonitor;

/**
 * 【胎儿监护】 监测拿到的数据实体bean
 * Created by tyx on 2018/4/12 0012.
 */

public class FetalMonitorBean {

    private int rate;                 //胎心一 = [30, 240] 或者 = 0
    private int rate2;                //胎心二 = [30, 240] 或者 = 0
    private byte toco;                 //宫缩曲线 = [0, 100]

    private byte devicePower;           //设备电量 = [0, 4]
    private byte signal;                //胎心信号质量 = [1, 3]
    private byte afm;     		       //自动胎动曲线 = [0, 40]

    private byte afmFlag;               //自动胎动标记，=0，没有，=1，记一次自动胎动
    private byte fmFlag;                //手动胎动标记 ，=0，没有，=1，记一次手动胎动
    private byte tocoFlag;              //宫缩复位标记 ，=0，没有，=1，记一次宫缩复位

    private byte isRate;                //是否含有 rate 数据，=0，没有，=1，有
    private byte isRate2;               //是否含有 rate2 数据，=0，没有，=1，有
    private byte isToco;                //是否含有 toco 数据，=0，没有，=1，有
    private byte isAfm;                 //是否含有 afm 数据，=0，没有，=1，有

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate2() {
        return rate2;
    }

    public void setRate2(int rate2) {
        this.rate2 = rate2;
    }

    public byte getToco() {
        return toco;
    }

    public void setToco(byte toco) {
        this.toco = toco;
    }

    public byte getDevicePower() {
        return devicePower;
    }

    public void setDevicePower(byte devicePower) {
        this.devicePower = devicePower;
    }

    public byte getSignal() {
        return signal;
    }

    public void setSignal(byte signal) {
        this.signal = signal;
    }

    public byte getAfm() {
        return afm;
    }

    public void setAfm(byte afm) {
        this.afm = afm;
    }

    public byte getAfmFlag() {
        return afmFlag;
    }

    public void setAfmFlag(byte afmFlag) {
        this.afmFlag = afmFlag;
    }

    public byte getFmFlag() {
        return fmFlag;
    }

    public void setFmFlag(byte fmFlag) {
        this.fmFlag = fmFlag;
    }

    public byte getTocoFlag() {
        return tocoFlag;
    }

    public void setTocoFlag(byte tocoFlag) {
        this.tocoFlag = tocoFlag;
    }

    public byte getIsRate() {
        return isRate;
    }

    public void setIsRate(byte isRate) {
        this.isRate = isRate;
    }

    public byte getIsRate2() {
        return isRate2;
    }

    public void setIsRate2(byte isRate2) {
        this.isRate2 = isRate2;
    }

    public byte getIsToco() {
        return isToco;
    }

    public void setIsToco(byte isToco) {
        this.isToco = isToco;
    }

    public byte getIsAfm() {
        return isAfm;
    }

    public void setIsAfm(byte isAfm) {
        this.isAfm = isAfm;
    }
}
