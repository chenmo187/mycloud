package com.mycloud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mycloud.R;
import com.mycloud.utils.ActivityUtils;
import com.mycloud.view.CustomLoadingDialog;

/**
 * Created by clarechen on 2016/2/1.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private CustomLoadingDialog customLoadingDialog;
    private static Toast mToast;
    private static Handler mToastHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public abstract void initView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customLoadingDialog = new CustomLoadingDialog(this);
    }


    public void setHeaderAndBack(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) findViewById(R.id.title)).setText(title);
        toolbar.setTitle(null);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setHeaderAndBack(String title, View.OnClickListener onClickListener) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) findViewById(R.id.title)).setText(title);
        toolbar.setTitle(null);
        toolbar.setNavigationOnClickListener(onClickListener);
    }

    public void setHeader(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) findViewById(R.id.title)).setText(title);
        toolbar.setTitle(null);
        toolbar.setNavigationIcon(null);
    }

    public void setTvRight(String text, View.OnClickListener onClickListener) {
        TextView tvRight = (TextView) findViewById(R.id.tv_right);
        if (!TextUtils.isEmpty(text)) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(onClickListener);
        } else {
            tvRight.setVisibility(View.GONE);
        }

    }


    public void showToastMsg(String msg) {

        mToastHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        }
        mToastHandler.postDelayed(r, 5 * 1000);
        mToast.show();

    }

    public void showWaiting() {
        try {
            customLoadingDialog.showDialog();
        } catch (Exception e) {

        }

    }

    public void stopWaiting() {
        try {
            customLoadingDialog.dimissDialog();
        } catch (Exception e) {

        }

    }

    public void next(Class<?> nextActivity,
                     Intent intent) {
        ActivityUtils.next(this, nextActivity, intent);
    }

    public void next(Class<?> nextActivity,
                     Intent intent, int reqcode) {
        ActivityUtils.next(this, nextActivity, intent, reqcode);
    }

    public void next(Class<?> nextActivity,
                     int reqcode) {
        ActivityUtils.next(this, nextActivity, reqcode);
    }

    public void next(Class<?> nextActivity) {
        ActivityUtils.next(this, nextActivity);
    }


}
