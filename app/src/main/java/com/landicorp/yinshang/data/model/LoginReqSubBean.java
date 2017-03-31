package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class LoginReqSubBean {

    private String pos_number;//扫码支付类型
    private String verify;//
    private String operator_num; //操作员
    private String operator_password; //操作员密码

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

    public String getOperator_num() {
        return operator_num;
    }

    public void setOperator_num(String operator_num) {
        this.operator_num = operator_num;
    }

    public String getOperator_password() {
        return operator_password;
    }

    public void setOperator_password(String operator_password) {
        this.operator_password = operator_password;
    }
}
