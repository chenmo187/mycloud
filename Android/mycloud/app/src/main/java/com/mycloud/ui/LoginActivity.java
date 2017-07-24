package com.mycloud.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.mycloud.MyCloudApplication;
import com.mycloud.R;
import com.mycloud.memory.Memory;
import com.mycloud.net.HttpClient;
import com.mycloud.net.HttpListener;
import com.mycloud.net.URLConfig;
import com.mycloud.pojo.User;
import com.mycloud.utils.SharedPreferenceUtils;
import com.mycloud.utils.Utils;

import java.util.HashMap;

/**
 * Created by clarechen on 2016/2/2.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtPhone, mEtPWD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setHeader("登录");
        initView();
    }

    @Override
    public void initView() {
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPWD = (EditText) findViewById(R.id.et_pwd);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        String username = SharedPreferenceUtils.getString("username", "");
        String pwd = SharedPreferenceUtils.getString("password", "");
        mEtPhone.setText(username);
        mEtPWD.setText(pwd);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd) && getIntent().getBooleanExtra("autologin", true)) {
            readyForLogin();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                readyForLogin();
                break;
            case R.id.btn_register:
                next(RegisterActivity.class);
                break;
            default:
                break;
        }
    }


    private void readyForLogin() {
        String phone = mEtPhone.getText().toString().trim();
        String pwd = mEtPWD.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToastMsg("请输入手机号");
        } else if (TextUtils.isEmpty(pwd)) {
            showToastMsg("请输入密码");
        } else if (!Utils.isPhoneNumber(phone)) {
            showToastMsg("请输入正确的手机号");
        } else {
            login(phone, pwd);
        }
    }

    private void login(final String phone, final String pwd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", pwd);
        HttpClient.getInstance().post(this, URLConfig.LOGIN, User.class, params, new HttpListener<User>() {
            @Override
            public void onReallySuccess(User user) {
                Memory.setUser(user);
                SharedPreferenceUtils.getEditor().putString("username", phone).putString("password", pwd).commit();
                next(MainActivity.class);
                finish();
            }
        });
    }

}
