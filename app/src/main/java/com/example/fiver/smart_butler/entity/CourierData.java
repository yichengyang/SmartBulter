package com.example.fiver.smart_butler.entity;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.entity
 *  文件名:  CourierData
 *  创建者:  YYC
 *  创建时间:  17/6/30 下午6:27
 *  描述:  快递查询实体
 */

public class CourierData {
    private String Datetime;
    private String remark;
    private String zone;

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String Datetime) {
        this.Datetime = Datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CourierData{" +
                "Datetime='" + Datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
