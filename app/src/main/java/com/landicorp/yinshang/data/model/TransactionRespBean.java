package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/9.
 */

public class TransactionRespBean  {
    public String point_url;//积分领取二维码路径
    public int point;//本次产生的积分额度
    public int pointCurrent;//当前剩余积分
    public String coupon;// 优惠券名称
    public String title_url;//优惠券领取二维码路径
    public int money;//优惠券金额
    public int backAmt;//返利金额


    @Override
    public String toString() {
        return "TransactionRespBean{" +
                "point_url='" + point_url + '\'' +
                ", point=" + point +
                ", pointCurrent=" + pointCurrent +
                ", coupon='" + coupon + '\'' +
                ", title_url='" + title_url + '\'' +
                ", money=" + money +
                ", backAmt=" + backAmt +
                '}';
    }
}
