package com.landicorp.yinshang.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by u on 2017/1/6.
 */
@Entity
public class PayInfoBean {

    private String resCode;//
    private String resDesc;//
    private String merchantName;//;
    private String merchantNo;//
    private String terminalNo;
    private String operNo;
    private String amt;
    private String batchNo;
    private String traceNo;
    private String refNo;
    private String authNo;
    private String expDate;
    private String cardNo;
    private String cardIssuerCode;
    private String cardAcquirerCode;
    private String cardInputType;
    private String transChnName;
    private String transEngName;
    private String date;
    private String time;
    private String memInfo;//是个json数据
    private String isReprint;
    private String vendor;
    private String model;
    private String version;
    private String qrcode;
    private String eSignJpeg;
    private String ARQC;
    private String UnprNo;
    private String ATC;
    private String TVR;
    private String TSI;
    private String AID;
    private String AIP;
    private String APPLAB;
    private String APPNAME;
    private String CVM;
    private String TermCap;
    private String IAD;
    private String CSN;
    private String cardOrg;
    private String payNo;//pos通支付独有字段
    private String transName;//pos通支付独有字段

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getAuthNo() {
        return authNo;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardIssuerCode() {
        return cardIssuerCode;
    }

    public void setCardIssuerCode(String cardIssuerCode) {
        this.cardIssuerCode = cardIssuerCode;
    }

    public String getCardAcquirerCode() {
        return cardAcquirerCode;
    }

    public void setCardAcquirerCode(String cardAcquirerCode) {
        this.cardAcquirerCode = cardAcquirerCode;
    }

    public String getCardInputType() {
        return cardInputType;
    }

    public void setCardInputType(String cardInputType) {
        this.cardInputType = cardInputType;
    }

    public String getTransChnName() {
        return transChnName;
    }

    public void setTransChnName(String transChnName) {
        this.transChnName = transChnName;
    }

    public String getTransEngName() {
        return transEngName;
    }

    public void setTransEngName(String transEngName) {
        this.transEngName = transEngName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMemInfo() {
        return memInfo;
    }

    public void setMemInfo(String memInfo) {
        this.memInfo = memInfo;
    }

    public String getIsReprint() {
        return isReprint;
    }

    public void setIsReprint(String isReprint) {
        this.isReprint = isReprint;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String geteSignJpeg() {
        return eSignJpeg;
    }

    public void seteSignJpeg(String eSignJpeg) {
        this.eSignJpeg = eSignJpeg;
    }

    public String getARQC() {
        return ARQC;
    }

    public void setARQC(String ARQC) {
        this.ARQC = ARQC;
    }

    public String getUnprNo() {
        return UnprNo;
    }

    public void setUnprNo(String unprNo) {
        UnprNo = unprNo;
    }

    public String getATC() {
        return ATC;
    }

    public void setATC(String ATC) {
        this.ATC = ATC;
    }

    public String getTVR() {
        return TVR;
    }

    public void setTVR(String TVR) {
        this.TVR = TVR;
    }

    public String getTSI() {
        return TSI;
    }

    public void setTSI(String TSI) {
        this.TSI = TSI;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public String getAIP() {
        return AIP;
    }

    public void setAIP(String AIP) {
        this.AIP = AIP;
    }

    public String getAPPLAB() {
        return APPLAB;
    }

    public void setAPPLAB(String APPLAB) {
        this.APPLAB = APPLAB;
    }

    public String getAPPNAME() {
        return APPNAME;
    }

    public void setAPPNAME(String APPNAME) {
        this.APPNAME = APPNAME;
    }

    public String getCVM() {
        return CVM;
    }

    public void setCVM(String CVM) {
        this.CVM = CVM;
    }

    public String getTermCap() {
        return TermCap;
    }

    public void setTermCap(String termCap) {
        TermCap = termCap;
    }

    public String getIAD() {
        return IAD;
    }

    public void setIAD(String IAD) {
        this.IAD = IAD;
    }

    public String getCSN() {
        return CSN;
    }

    public void setCSN(String CSN) {
        this.CSN = CSN;
    }

    public String getCardOrg() {
        return cardOrg;
    }

    public void setCardOrg(String cardOrg) {
        this.cardOrg = cardOrg;
    }

    public String getExtOrderNo() {
        return extOrderNo;
    }

    public void setExtOrderNo(String extOrderNo) {
        this.extOrderNo = extOrderNo;
    }

    public String getESignJpeg() {
        return this.eSignJpeg;
    }

    public void setESignJpeg(String eSignJpeg) {
        this.eSignJpeg = eSignJpeg;
    }

    private String extOrderNo;

    @Generated(hash = 1753919970)
    public PayInfoBean(String resCode, String resDesc, String merchantName,
            String merchantNo, String terminalNo, String operNo, String amt,
            String batchNo, String traceNo, String refNo, String authNo,
            String expDate, String cardNo, String cardIssuerCode,
            String cardAcquirerCode, String cardInputType, String transChnName,
            String transEngName, String date, String time, String memInfo,
            String isReprint, String vendor, String model, String version,
            String qrcode, String eSignJpeg, String ARQC, String UnprNo,
            String ATC, String TVR, String TSI, String AID, String AIP,
            String APPLAB, String APPNAME, String CVM, String TermCap, String IAD,
            String CSN, String cardOrg, String payNo, String transName,
            String extOrderNo) {
        this.resCode = resCode;
        this.resDesc = resDesc;
        this.merchantName = merchantName;
        this.merchantNo = merchantNo;
        this.terminalNo = terminalNo;
        this.operNo = operNo;
        this.amt = amt;
        this.batchNo = batchNo;
        this.traceNo = traceNo;
        this.refNo = refNo;
        this.authNo = authNo;
        this.expDate = expDate;
        this.cardNo = cardNo;
        this.cardIssuerCode = cardIssuerCode;
        this.cardAcquirerCode = cardAcquirerCode;
        this.cardInputType = cardInputType;
        this.transChnName = transChnName;
        this.transEngName = transEngName;
        this.date = date;
        this.time = time;
        this.memInfo = memInfo;
        this.isReprint = isReprint;
        this.vendor = vendor;
        this.model = model;
        this.version = version;
        this.qrcode = qrcode;
        this.eSignJpeg = eSignJpeg;
        this.ARQC = ARQC;
        this.UnprNo = UnprNo;
        this.ATC = ATC;
        this.TVR = TVR;
        this.TSI = TSI;
        this.AID = AID;
        this.AIP = AIP;
        this.APPLAB = APPLAB;
        this.APPNAME = APPNAME;
        this.CVM = CVM;
        this.TermCap = TermCap;
        this.IAD = IAD;
        this.CSN = CSN;
        this.cardOrg = cardOrg;
        this.payNo = payNo;
        this.transName = transName;
        this.extOrderNo = extOrderNo;
    }

    @Generated(hash = 691896998)
    public PayInfoBean() {
    }
}


