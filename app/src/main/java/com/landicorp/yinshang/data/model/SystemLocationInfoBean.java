package com.landicorp.yinshang.data.model;

/**
 * Created by u on 2017/1/6.
 */

public class SystemLocationInfoBean {

    private String Longitude;//经度
    private String Latitude;//纬度
    private String CityCode;//城市码;
    private String SoftwareVer ;//智能桌面版本
    private String City;//城市名称

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public String getSoftwareVer() {
        return SoftwareVer;
    }

    public void setSoftwareVer(String softwareVer) {
        SoftwareVer = softwareVer;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    private String Address ;//地址
}


