package com.example.proshine001.webapplication.bean;
import java.io.Serializable;

/**
 * Name: ChargeRecordsInfo
 * Author: liuan
 * creatTime:2017-08-02 18:09
 * Email:1377093782@qq.com
 */
public class ChargeRecordsInfo implements Serializable {

    public String beaginTime;
    public String beaginLevel;
    public String endLevel;
    public String charegTime;
    public int boostTime;
    public int coolerTime;

    public void setBeaginTime(String beaginTime) {
        this.beaginTime = beaginTime;
    }

    public void setBeaginLevel(String beaginLevel) {
        this.beaginLevel = beaginLevel;
    }

    public void setEndLevel(String endLevel) {
        this.endLevel = endLevel;
    }

    public void setCharegTime(String charegTime) {
        this.charegTime = charegTime;
    }

    public void setBoostTime(int boostTime) {
        this.boostTime = boostTime;
    }

    public void setCoolerTime(int coolerTime) {
        this.coolerTime = coolerTime;
    }

    @Override
    public String toString() {
        return "ChargeRecordsInfo{" +
                "beaginTime='" + beaginTime + '\'' +
                ", beaginLevel='" + beaginLevel + '\'' +
                ", endLevel='" + endLevel + '\'' +
                ", charegTime='" + charegTime + '\'' +
                ", boostTime=" + boostTime +
                ", coolerTime=" + coolerTime +
                '}';
    }
}
