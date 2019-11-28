package com.example.servicebestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

public class NetStateChangeReceiver extends BroadcastReceiver{

    private NetWorkType mType = NetWorkUtil.getNetworkType(MyApplication.getContext());

    private static class InstanceHolder{
        private static final NetStateChangeReceiver INSTANCE = new NetStateChangeReceiver();
    }

    private List<NetStateChangeObserver> mObservers = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            NetWorkType networkType = NetWorkUtil.getNetworkType(context);
            notifyObservers(networkType);
        }
    }

    public static void registerReceiver(Context context){
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver( InstanceHolder.INSTANCE,intentFilter);
    }

    public static void unRegisterReceiver(Context context){
        context.unregisterReceiver( InstanceHolder.INSTANCE);
    }

    public static void registerObserver(NetStateChangeObserver observer){
        if (observer == null) {
            return;
        }
        if (!InstanceHolder.INSTANCE.mObservers.contains(observer)){
            InstanceHolder.INSTANCE.mObservers.add(observer);
        }
    }

    public static void unRegisterObserver(NetStateChangeObserver observer){
        if (observer == null) {
            return;
        }
        if (InstanceHolder.INSTANCE.mObservers == null) {
            return;
        }
        InstanceHolder.INSTANCE.mObservers.remove(observer);
    }

    private void notifyObservers(NetWorkType networkType){
        if (mType == networkType) {
            return;
        }
        // mType = networkType;
        if (networkType == NetWorkType.NETWORK_NO){
            for (NetStateChangeObserver observer : mObservers){
                observer.onNetDisconnected();
            }
        } else if (networkType == NetWorkType.NETWORK_WIFI && mType == NetWorkType.NETWORK_4G) {
            for (NetStateChangeObserver observer : mObservers){
                observer.onNetConnected4GToWifi();
            }
        } else if (networkType == NetWorkType.NETWORK_4G && mType == NetWorkType.NETWORK_WIFI) {
            for (NetStateChangeObserver observer : mObservers){
                observer.onNetConnectedWifiTo4G();
            }
        } else if (mType == NetWorkType.NETWORK_NO && networkType != NetWorkType.NETWORK_NO) {
            for (NetStateChangeObserver observer : mObservers){
                observer.onNetConnected(networkType);
            }
        }
    }

}
