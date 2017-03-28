package com.landicorp.yinshang.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
}
