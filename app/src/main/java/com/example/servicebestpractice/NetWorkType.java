package com.example.servicebestpractice;

public enum NetWorkType {

    NETWORK_WIFI("WiFi"),
    NETWORK_4G("4G"),
    NETWORK_2G("2G"),
    NETWORK_3G("3G"),
    NETWORK_UNKNOWN("Unknown"),
    NETWORK_NO("No network");

    private String desc;
    NetWorkType(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
