package com.mycloud.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.mycloud.R;
import com.mycloud.net.HttpClient;
import com.mycloud.net.HttpListener;
import com.mycloud.net.URLConfig;
import com.mycloud.pojo.BaseResult;
import com.mycloud.utils.Utils;

import java.util.HashMap;

/**
 * Created by clarechen on 2016/2/23.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtPhone, mEtPWD, mEtPWDConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setHeaderAndBack("注册");
        initView();
    }

    @Override
    public void initView() {
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPWD = (EditText) findViewById(R.id.et_pwd);
        mEtPWDConfirm = (EditText) findViewById(R.id.et_pwd_confirm);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                readForRegister();
                break;
            default:
                break;
        }
    }

    private void readForRegister() {
        String phone = mEtPhone.getText().toString().trim();
        String pwd = mEtPWD.getText().toString().trim();
        String pwdConfirm = mEtPWDConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToastMsg("请输入手机号");
        } else if (TextUtils.isEmpty(pwd)) {
            showToastMsg("请输入密码");
        } else if (TextUtils.isEmpty(pwdConfirm)) {
            showToastMsg("请输入确认密码");
        } else if (!Utils.isPhoneNumber(phone)) {
            showToastMsg("请输入正确的手机号");
        } else if (!pwd.equals(pwdConfirm)) {
            showToastMsg("两次密码输入不一致");
        } else {
            register(phone, pwd);
        }


    }

    private void register(String phone, String pwd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", pwd);
        HttpClient.getInstance().post(this, URLConfig.REGISTER, BaseResult.class, params, new HttpListener<BaseResult>() {
            @Override
            public void onReallySuccess(BaseResult result) {
                showToastMsg("注册成功");
                finish();
            }
        });
    }
}
