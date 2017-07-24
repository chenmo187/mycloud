package com.mycloud.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.mycloud.R;
import com.mycloud.net.HttpClient;
import com.mycloud.net.HttpListener;
import com.mycloud.net.URLConfig;
import com.mycloud.pojo.User;
import com.mycloud.utils.Utils;

import java.util.HashMap;

/**
 * Created by clarechen on 2016/2/16.
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setHeaderAndBack("意见反馈");
        initView();
    }

    @Override
    public void initView() {
        mEtFeedback = (EditText) findViewById(R.id.et_feedback);
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submitFeedback();
                break;
            default:
                break;
        }
    }

    private void submitFeedback() {
        String feedback = mEtFeedback.getText().toString().trim();
        if (TextUtils.isEmpty(feedback)) {
            showToastMsg("说点啥吧");
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("content", feedback);
        params.put("platform", "Android");
        params.put("version", Utils.getVersion(this));
        HttpClient.getInstance().post(this, URLConfig.SUBMIT_FEEDBACK, User.class, params, new HttpListener<User>() {
            @Override
            public void onReallySuccess(User user) {
                showToastMsg("提交成功");
                finish();
            }
        });
    }
}
