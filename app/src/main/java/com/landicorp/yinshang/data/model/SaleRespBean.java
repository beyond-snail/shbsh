package com.landicorp.yinshang.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by u on 2017/1/9.
 */

public class SaleRespBean implements Parcelable {

    protected SaleRespBean(Parcel in) {
        memberName = in.readString();
        memberCardNo = in.readString();
        tradeMoney = in.readInt();
        realMoney = in.readInt();
        pointCoverMoney = in.readInt();
        couponSns = in.readString();
        couponName = in.readString();
        couponNum = in.readInt();
        couponCoverMoney = in.readInt();
    }

    public static final Creator<SaleRespBean> CREATOR = new Creator<SaleRespBean>() {
        @Override
        public SaleRespBean createFromParcel(Parcel in) {
            return new SaleRespBean(in);
        }

        @Override
        public SaleRespBean[] newArray(int size) {
            return new SaleRespBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(memberName);
        parcel.writeString(memberCardNo);
        parcel.writeInt(tradeMoney);
        parcel.writeInt(realMoney);
        parcel.writeInt(pointCoverMoney);
        parcel.writeString(couponSns);
        parcel.writeString(couponName);
        parcel.writeInt(couponNum);
        parcel.writeInt(couponCoverMoney);
    }

    public String memberName;//会员名称
    public String memberCardNo;//会员卡号
    public int tradeMoney;//交易金额
    public int realMoney;//实收金额
    public int pointCoverMoney;//积分抵用金额
    public String couponSns;//消费优惠券序列号组
    public String couponName;//优惠券名称
    public int couponNum;//消费优惠券数
    public int couponCoverMoney;//优惠券抵消金额

}
