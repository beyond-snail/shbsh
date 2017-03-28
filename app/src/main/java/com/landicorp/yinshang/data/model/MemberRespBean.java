package com.landicorp.yinshang.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 2017/1/9.
 */

public class MemberRespBean implements Parcelable {
    protected MemberRespBean(Parcel in) {
        memberName = in.readString();
        memberCardNo = in.readString();
        mobile = in.readString();
        frequency_min = in.readInt();
        pointUseMax = in.readInt();
        point = in.readInt();
        pointChangeRate = in.readInt();
        couponNum = in.readInt();
        isMember = in.readByte() != 0;
        freePassword = in.readByte() != 0;
    }

    public static final Creator<MemberRespBean> CREATOR = new Creator<MemberRespBean>() {
        @Override
        public MemberRespBean createFromParcel(Parcel in) {
            return new MemberRespBean(in);
        }

        @Override
        public MemberRespBean[] newArray(int size) {
            return new MemberRespBean[size];
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
        parcel.writeString(mobile);
        parcel.writeInt(frequency_min);
        parcel.writeInt(pointUseMax);
        parcel.writeInt(point);
        parcel.writeInt(pointChangeRate);
        parcel.writeInt(couponNum);
        parcel.writeByte((byte) (isMember ? 1 : 0));
        parcel.writeByte((byte) (freePassword ? 1 : 0));
    }

    public String memberName;//用户名
    public String memberCardNo;//会员卡号
    public String mobile;//会员手机号
    public int frequency_min;//积分最小使用数
    public int pointUseMax;//积分最大使用数
    public int point;//当前积分数
    public int pointChangeRate;//积分金额兑换比例
    public int couponNum;//优惠券总数
    public boolean isMember;//是否为会员
    public boolean freePassword;//是否免密码
    public ArrayList<CouponBean> coupons;//优惠券列表

}
