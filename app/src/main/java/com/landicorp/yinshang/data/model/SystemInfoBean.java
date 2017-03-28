package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class SystemInfoBean {

    private String Vendor;//设备厂商
    private String SN;//硬件序列号
    private String TermType;//设备机型;
    private String SoftwareVer ;//智能桌面版本
    private SystemLocationInfoBean Location;//定位信息

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getTermType() {
        return TermType;
    }

    public void setTermType(String termType) {
        TermType = termType;
    }

    public String getSoftwareVer() {
        return SoftwareVer;
    }

    public void setSoftwareVer(String softwareVer) {
        SoftwareVer = softwareVer;
    }

    public SystemLocationInfoBean getLocation() {
        return Location;
    }

    public void setLocation(SystemLocationInfoBean location) {
        Location = location;
    }
}


