package com.example.servicebestpractice;

public interface NetStateChangeObserver {
    void onNetDisconnected();
    void onNetConnected(NetWorkType networkType);
    void onNetConnectedWifiTo4G();
    void onNetConnected4GToWifi();
}
