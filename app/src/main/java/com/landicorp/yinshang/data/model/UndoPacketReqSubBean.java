package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class UndoPacketReqSubBean {

    private String old_trade_order_num;//原订单号
    private String new_trade_order_num;//新订单号
    private String action;//操作动作
    private String payType;//支付方式
    private String verify;
    private String sid;//（钱包撤销独有的字段）
    private String operator_num; //操作员号

    public String getOld_trade_order_num() {
        return old_trade_order_num;
    }

    public void setOld_trade_order_num(String old_trade_order_num) {
        this.old_trade_order_num = old_trade_order_num;
    }

    public String getNew_trade_order_num() {
        return new_trade_order_num;
    }

    public void setNew_trade_order_num(String new_trade_order_num) {
        this.new_trade_order_num = new_trade_order_num;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getOperator_num() {
        return operator_num;
    }

    public void setOperator_num(String operator_num) {
        this.operator_num = operator_num;
    }
}
