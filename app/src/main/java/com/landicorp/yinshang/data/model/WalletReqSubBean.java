package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class WalletReqSubBean {

    private String TranCode;//交易码
    private String QrCode;//用户二维码
    private String MerchantId;//商户编号
    private String TerminalNo;//终端号
    private String OrgOrderNum;//订单号
    private String OrgTranDateTime;//交易时间
    private String SysTraceNum;//交易流水号
    private String TranAmt;//收款金额
    private String OrderCurrency;//币种
    private String verify;
    private String operator_num; //操作员号

    public String getTranCode() {
        return TranCode;
    }

    public void setTranCode(String tranCode) {
        TranCode = tranCode;
    }

    public String getQrCode() {
        return QrCode;
    }

    public void setQrCode(String qrCode) {
        QrCode = qrCode;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getTerminalNo() {
        return TerminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        TerminalNo = terminalNo;
    }

    public String getOrgOrderNum() {
        return OrgOrderNum;
    }

    public void setOrgOrderNum(String orgOrderNum) {
        OrgOrderNum = orgOrderNum;
    }

    public String getOrgTranDateTime() {
        return OrgTranDateTime;
    }

    public void setOrgTranDateTime(String orgTranDateTime) {
        OrgTranDateTime = orgTranDateTime;
    }

    public String getSysTraceNum() {
        return SysTraceNum;
    }

    public void setSysTraceNum(String sysTraceNum) {
        SysTraceNum = sysTraceNum;
    }

    public String getTranAmt() {
        return TranAmt;
    }

    public void setTranAmt(String tranAmt) {
        TranAmt = tranAmt;
    }

    public String getOrderCurrency() {
        return OrderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        OrderCurrency = orderCurrency;
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
