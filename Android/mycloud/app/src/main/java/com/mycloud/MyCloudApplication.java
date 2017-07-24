package com.mycloud;

import android.app.Application;

import com.mycloud.net.NetworkUtils;
import com.mycloud.net.VolleyRequestQueue;
import com.mycloud.pojo.User;
import com.mycloud.utils.DensityUtils;
import com.mycloud.utils.SharedPreferenceUtils;
import com.mycloud.utils.ToastUtils;

/**
 * Created by clarechen on 2016/2/2.
 */
public class MyCloudApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initUtils();
    }

    private void initUtils() {
        ToastUtils.setContext(this);
        NetworkUtils.setContext(this);
        VolleyRequestQueue.setContext(this);
        SharedPreferenceUtils.initSharedPreference(this);
        DensityUtils.setContext(this);
    }

}
