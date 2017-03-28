package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class UndoReqSubBean {

    private String old_trade_order_num;//原订单号
    private String new_trade_order_num;//新订单号
    private String authCode;//交易机构订单号 （非钱包撤销独有的字段）
    private String action;//操作动作
    private String payType;//支付方式
    private String verify;
    private long t;

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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
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

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }
}
