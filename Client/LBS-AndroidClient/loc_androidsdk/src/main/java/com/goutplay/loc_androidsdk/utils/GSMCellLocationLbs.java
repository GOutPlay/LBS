package com.goutplay.loc_androidsdk.utils;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.util.List;

/**
 * Created by goerver on 16-8-21.
 */
public class GSMCellLocationLbs {
    private static final String TAG = "GMSCellLocation";

    public static void getCurrentLocation(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // 返回值MCC + MNC
        String operator = mTelephonyManager.getNetworkOperator();
        int mcc = Integer.parseInt(operator.substring(0, 3));
        int mnc = Integer.parseInt(operator.substring(3));
        Log.i(TAG, " MCC = " + mcc + "\t MNC = " + mnc + "\t");
        // 中国移动和中国联通获取LAC、CID的方式
        GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
        int lac = location.getLac();
        int cellId = location.getCid();



        // 中国电信获取LAC、CID的方式
        CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
        lac = location1.getNetworkId();
        cellId = location1.getBaseStationId();
        cellId /= 16;

        Log.i(TAG, " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId);
        // 获取邻区基站信息
        List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
        StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
        for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
            sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
            sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
            sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
        }

        Log.i(TAG, " 获取邻区基站信息:" + sb.toString());
    }

}
