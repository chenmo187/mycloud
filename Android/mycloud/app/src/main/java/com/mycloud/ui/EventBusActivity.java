package com.mycloud.ui;

import android.os.Bundle;

import de.greenrobot.event.EventBus;

/**
 * Created by clarechen on 2016/2/15.
 */
public abstract class EventBusActivity<T> extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    public void onEventMainThread(T event) {
        onEventBusCallBack(event);
    }

    abstract void onEventBusCallBack(T event);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
