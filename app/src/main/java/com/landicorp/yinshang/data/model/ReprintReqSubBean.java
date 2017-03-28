package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class ReprintReqSubBean {

    private String clientOrderNo;//
    private String verify;
    private int sid;

    public String getClientOrderNo() {
        return clientOrderNo;
    }

    public void setClientOrderNo(String clientOrderNo) {
        this.clientOrderNo = clientOrderNo;
    }

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
}
