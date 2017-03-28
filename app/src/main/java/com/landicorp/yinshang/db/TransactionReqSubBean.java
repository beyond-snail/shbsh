package com.landicorp.yinshang.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by u on 2017/1/6.
 */
@Entity
public class TransactionReqSubBean {
//    @Id(autoincrement = true)
//    private long id;
    private String scanUrl;//二维码地址（和接口无关字段）
    private String orderAmount;//订单金额（和接口无关字段）
    private String isUploadSuccess;//是否上传流水 1是上传了（和接口无关字段）
    private String isUndo;//是否撤销 1是撤销了（和接口无关字段）
    private int sid;//商户ID
    private String cardNo;//会员卡号
    private String password;//卡号密码(
    private int cash;//现金交易金额
    private int bankAmount;//网银支付额度
    private int couponCoverAmount;//优惠券抵用金额
    private int pointCoverAmount;//积分抵用金额
    private String couponSns;//消费优惠券序列号组
    private String clientOrderNo;//订单号（客户端生成）
    private String activateCode;//激活码
    private String merchantNo;//商户号
    private long t;//时间戳
    private String transNo;//交易流水号（第三方）
    private String authCode;//交易机构订单号（）
    private String serialNum;//交易终端设备序列号
    private int payType;//收款方式   1:支付宝 3:微信 4:百付宝 5:京东 6;刷卡 7;现金，10：刷卡撤销；11：微信撤销； 12：支付撤销；13：钱包；14：钱包撤销
    private int pointAmount;//消耗积分数
    @Id
    private String verify;


    @Generated(hash = 504904928)
    public TransactionReqSubBean(String scanUrl, String orderAmount, String isUploadSuccess,
            String isUndo, int sid, String cardNo, String password, int cash, int bankAmount,
            int couponCoverAmount, int pointCoverAmount, String couponSns, String clientOrderNo,
            String activateCode, String merchantNo, long t, String transNo, String authCode,
            String serialNum, int payType, int pointAmount, String verify) {
        this.scanUrl = scanUrl;
        this.orderAmount = orderAmount;
        this.isUploadSuccess = isUploadSuccess;
        this.isUndo = isUndo;
        this.sid = sid;
        this.cardNo = cardNo;
        this.password = password;
        this.cash = cash;
        this.bankAmount = bankAmount;
        this.couponCoverAmount = couponCoverAmount;
        this.pointCoverAmount = pointCoverAmount;
        this.couponSns = couponSns;
        this.clientOrderNo = clientOrderNo;
        this.activateCode = activateCode;
        this.merchantNo = merchantNo;
        this.t = t;
        this.transNo = transNo;
        this.authCode = authCode;
        this.serialNum = serialNum;
        this.payType = payType;
        this.pointAmount = pointAmount;
        this.verify = verify;
    }

    @Generated(hash = 127012548)
    public TransactionReqSubBean() {
    }


    public String getScanUrl() {
        return scanUrl;
    }

    public void setScanUrl(String scanUrl) {
        this.scanUrl = scanUrl;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getIsUploadSuccess() {
        return isUploadSuccess;
    }

    public void setIsUploadSuccess(String isUploadSuccess) {
        this.isUploadSuccess = isUploadSuccess;
    }

    public String getIsUndo() {
        return isUndo;
    }

    public void setIsUndo(String isUndo) {
        this.isUndo = isUndo;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(int bankAmount) {
        this.bankAmount = bankAmount;
    }

    public int getCouponCoverAmount() {
        return couponCoverAmount;
    }

    public void setCouponCoverAmount(int couponCoverAmount) {
        this.couponCoverAmount = couponCoverAmount;
    }

    public int getPointCoverAmount() {
        return pointCoverAmount;
    }

    public void setPointCoverAmount(int pointCoverAmount) {
        this.pointCoverAmount = pointCoverAmount;
    }

    public String getCouponSns() {
        return couponSns;
    }

    public void setCouponSns(String couponSns) {
        this.couponSns = couponSns;
    }

    public String getClientOrderNo() {
        return clientOrderNo;
    }

    public void setClientOrderNo(String clientOrderNo) {
        this.clientOrderNo = clientOrderNo;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }
}
