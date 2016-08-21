package com.goutplay.loc_androidsdk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by goerver on 16-8-21.
 * http://blog.csdn.net/qq_23195583/article/details/50737942
 */
public class PermissionRequest {
    private static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;

    public static void permissionRequest(Activity activity, final String permission) {
        if (ContextCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }
}
