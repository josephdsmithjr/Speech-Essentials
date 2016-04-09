package com.speechessentials.speechessentials.Utility;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;

/**
 * Created by josephdsmithjr on 6/8/2015.
 */
public class ConnectionHelper {

//    public String checkInternetStrength(Context context) {
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        int linkSpeed = wifiManager.getConnectionInfo().getRssi();
//        int wifiLevel = WifiManager.calculateSignalLevel(linkSpeed,5);
//        int strength = 0;
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            int networkType = telephonyManager.getNetworkType();
//            CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
//            CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
//            strength = cellSignalStrengthGsm.getDbm();
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        String wifiAndCellStrength = "Wifi Strength: " + linkSpeed + " Cell Strength: " + strength;
//        return wifiAndCellStrength;
//    }

    public int checkInternetStrength(Context context) {
        int linkSpeed = 0;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            linkSpeed = wifiManager.getConnectionInfo().getRssi();
        } catch (Exception e){
            e.printStackTrace();
        }
        return linkSpeed;
    }
}
