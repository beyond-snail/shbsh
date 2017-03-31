package com.landicorp.yinshang.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Created by u on 2017/1/9.
 */

@Entity
public class LoginRespBean {

    public int sid;//商户ID
    public String merchantNo;//商户号
    public String terminalNo;//商户终端号
    public String licenseNo;//营业执照号
    public String accountName;//收款账户
    public String accountBank;//收款银行
    public String accountNo;//收款帐号
    public String terminalName;//终端名称
    public String other;//授权码
    public String fyMerchantNo;//扫码商户号
    public String fyMerchantName;//扫码商户名称
    public String activeCode;//激活码
    public int scanPayType;//扫码支付类型
    public String operatList;//权限列表
    @Transient
    public List<OperatorList> operator_list; //操作员列表



    @Generated(hash = 1000895250)
    public LoginRespBean() {
    }







    @Generated(hash = 910985904)
    public LoginRespBean(int sid, String merchantNo, String terminalNo,
            String licenseNo, String accountName, String accountBank,
            String accountNo, String terminalName, String other,
            String fyMerchantNo, String fyMerchantName, String activeCode,
            int scanPayType, String operatList) {
        this.sid = sid;
        this.merchantNo = merchantNo;
        this.terminalNo = terminalNo;
        this.licenseNo = licenseNo;
        this.accountName = accountName;
        this.accountBank = accountBank;
        this.accountNo = accountNo;
        this.terminalName = terminalName;
        this.other = other;
        this.fyMerchantNo = fyMerchantNo;
        this.fyMerchantName = fyMerchantName;
        this.activeCode = activeCode;
        this.scanPayType = scanPayType;
        this.operatList = operatList;
    }







    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getFyMerchantNo() {
        return fyMerchantNo;
    }

    public void setFyMerchantNo(String fyMerchantNo) {
        this.fyMerchantNo = fyMerchantNo;
    }

    public String getFyMerchantName() {
        return fyMerchantName;
    }

    public void setFyMerchantName(String fyMerchantName) {
        this.fyMerchantName = fyMerchantName;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public int getScanPayType() {
        return scanPayType;
    }

    public void setScanPayType(int scanPayType) {
        this.scanPayType = scanPayType;
    }

    public String getOperatList() {
        return operatList;
    }

    public void setOperatList(String operatList) {
        this.operatList = operatList;
    }

    public List<OperatorList> getOperatorLists() {
        return operator_list;
    }

    public void setOperatorLists(List<OperatorList> operatorLists) {
        this.operator_list = operatorLists;
    }
}
