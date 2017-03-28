package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class PrintReqSubBean {

    private int sid;//扫码支付类型
    private String clientOrderNo;
    private String verify;//

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getClientOrderNo() {
        return clientOrderNo;
    }

    public void setClientOrderNo(String clientOrderNo) {
        this.clientOrderNo = clientOrderNo;
    }
}
