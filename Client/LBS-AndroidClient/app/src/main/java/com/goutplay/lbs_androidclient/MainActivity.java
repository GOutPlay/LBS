package com.goutplay.lbs_androidclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.goutplay.loc_androidsdk.utils.GSMCellLocationLbs;

public class MainActivity extends AppCompatActivity {
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    this.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    public void getCellInfo(View view) {
        GSMCellLocationLbs.getCurrentLocation(this);
    }
}
