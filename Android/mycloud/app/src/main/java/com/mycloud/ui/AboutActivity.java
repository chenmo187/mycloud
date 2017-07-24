package com.mycloud.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.mycloud.R;
import com.mycloud.utils.Utils;

/**
 * Created by clarechen on 2016/2/16.
 */
public class AboutActivity extends BaseActivity {
    private TextView mTvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setHeaderAndBack("关于");
        initView();
    }

    @Override
    public void initView() {
        mTvVersion = (TextView) findViewById(R.id.tv_version);
        mTvVersion.setText("版本 "+ Utils.getVersion(this) + "\n power by Clark Chen");
    }
}
