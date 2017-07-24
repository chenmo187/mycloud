package com.mycloud.ui;

import android.os.Bundle;

import com.mycloud.R;
import com.mycloud.utils.LogTool;
import com.mycloud.utils.RSAUtils;

import java.security.GeneralSecurityException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by clarechen on 2016/2/1.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                next(LoginActivity.class);
                finish();
            }
        }, 2000);

        try {
            LogTool.d(RSAUtils.encrypt("{\"cmd\":\"3001\",\"ID\":\"2115010041574d4233520167b1e28f828f\",\"SN\":\"12345678\",\"supplierCode\":\"1440049\",\"imei\":null,\"vin\":\"12345678\",\"mcuModel\":\"YC-DD2000-V3-FIRMWARE\",\"mcuVersion\":\"LJU70W1Z7FG075386\",\"sysModel\":\"YC-DD2000-V4\",\"sysVersion\":\"V156\",\"isAdasIntalled\":\"0\",\"adasHardVer\":\"\",\"adasSoftVer\":\"\"}\n"));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initView() {

    }
}
