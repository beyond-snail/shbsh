package com.landicorp.yinshang.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by u on 2017/1/6.
 */

public class CouponBean implements Parcelable, Comparator {

    public CouponBean() {

    }
    protected CouponBean(Parcel in) {
        id = in.readInt();
        money = in.readInt();
        sn = in.readString();
        name = in.readString();
        remark = in.readString();
        canMultiChoose = in.readByte() != 0;
    }

    public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
        @Override
        public CouponBean createFromParcel(Parcel in) {
            return new CouponBean(in);
        }

        @Override
        public CouponBean[] newArray(int size) {
            return new CouponBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(money);
        parcel.writeString(sn);
        parcel.writeString(name);
        parcel.writeString(remark);
        parcel.writeByte((byte) (canMultiChoose ? 1 : 0));
    }

    @Override
    public int compare(Object arg0, Object arg1) {
        CouponBean bean0 =(CouponBean)arg0;
        CouponBean bean1 =(CouponBean)arg1;
        //首先比较年龄，如果年龄相同，则比较名字
        int flag=Integer.valueOf(bean1.getMoney()).compareTo(Integer.valueOf(bean0.getMoney()));
//        if(flag==0){
//            return Integer.valueOf(bean0.getMoney()).compareTo(user1.getName());
//        }else{
        return flag;
//        }
    }

    public boolean equals(Object o){
        if(!(o instanceof CouponBean))
            return false;
        CouponBean p = (CouponBean)o;
            return p.sn == sn && p.id == id;
     }

    public int id;//优惠券ID
    public int money;//金额（分）
    public String sn;//优惠券券号
    public String name;//优惠券名称
    public String remark;//说明信息
    public boolean canMultiChoose;//是否可多选

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isCanMultiChoose() {
        return canMultiChoose;
    }

    public void setCanMultiChoose(boolean canMultiChoose) {
        this.canMultiChoose = canMultiChoose;
    }
}
