package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class MemberReqSubBean {

    private int sid;//商户ID
    private String mobile;//用户手机号/会员卡号
    private int tradeMoney;//交易金额(分)
    private String verify;//
    private String serialNum; //设备序列号
    private String operator_num; //操作员号

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(int tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getOperator_num() {
        return operator_num;
    }

    public void setOperator_num(String operator_num) {
        this.operator_num = operator_num;
    }
}


