package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class LoginReqSubBean {

    private String pos_number;//扫码支付类型
    private String verify;//

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getPos_number() {
        return pos_number;
    }

    public void setPos_number(String pos_number) {
        this.pos_number = pos_number;
    }

}
