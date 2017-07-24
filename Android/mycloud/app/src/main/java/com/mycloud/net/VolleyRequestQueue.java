package com.mycloud.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by clarechen on 2016/1/27.
 */
public class VolleyRequestQueue {
    private volatile static RequestQueue mRequestQueue;
    private static Context context;
    private VolleyRequestQueue() {
    }

    public static RequestQueue getInstance() {
        if (mRequestQueue == null) {
            synchronized (VolleyRequestQueue.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        return mRequestQueue;
    }


    public static void setContext(Context context) {
        VolleyRequestQueue.context = context;
    }
}

