package com.london.reboot.model;

public class Customer {

    String geoInfo;

    String ipAddress;

    String phoneNumber;

    public String getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(String geoInfo) {
        this.geoInfo = geoInfo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "geoInfo='" + geoInfo + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
