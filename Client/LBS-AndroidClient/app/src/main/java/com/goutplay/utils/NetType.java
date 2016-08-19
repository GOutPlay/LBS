package com.goutplay.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by goerver on 16-8-19.
 */
public class NetType {
    private Context mContext;
    private LocationManager mLocationManager;

    public NetType(Context mContext, LocationManager mLocationManager) {
        this.mContext = mContext;
        this.mLocationManager = mLocationManager;
    }

    /**
     * 判断GPS是否开启
     * @return
     */
    public boolean isGPSEnabled() {
        if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.i(Thread.currentThread().getName(), "isGPSEnabled");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断Network是否开启(包括移动网络和wifi)
     * @return
     */
    public boolean isNetworkEnabled() {
        return (isWIFIEnabled() || isTelephonyEnabled());
    }

    /**
     * 判断移动网络是否开启
     * @return
     */
    public boolean isTelephonyEnabled() {
        boolean enable = false;
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (telephonyManager.getNetworkType() != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                enable = true;
                Log.i(Thread.currentThread().getName(), "isTelephonyEnabled");
            }
        }

        return enable;
    }

    /**
     * 判断wifi是否开启
     */
    public boolean isWIFIEnabled() {
        boolean enable = false;
         WifiManager wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()) {
            enable = true;
            Log.i(Thread.currentThread().getName(), "isWIFIEnabled");
        }
        return enable;
    }
}
