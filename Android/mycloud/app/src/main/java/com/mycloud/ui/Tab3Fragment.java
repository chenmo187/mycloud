package com.mycloud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycloud.R;
import com.mycloud.net.HttpClient;

/**
 * Created by clarechen on 2016/2/1.
 */
public class Tab3Fragment extends BaseFragment implements View.OnClickListener {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab3, container, false);
        initView();
        return view;
    }

    @Override
    void initView() {
        view.findViewById(R.id.btn_logout).setOnClickListener(this);
        view.findViewById(R.id.rl_about).setOnClickListener(this);
        view.findViewById(R.id.rl_feedback).setOnClickListener(this);
        view.findViewById(R.id.rl_introduction).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                Intent intent = new Intent();
                intent.putExtra("autologin", false);
                ((BaseActivity) getActivity()).next(LoginActivity.class, intent);
                break;
            case R.id.rl_about:
                ((BaseActivity) getActivity()).next(AboutActivity.class);
                break;
            case R.id.rl_feedback:
                ((BaseActivity) getActivity()).next(FeedBackActivity.class);
                break;
            case R.id.rl_introduction:
                gotoIntroduction();
                break;
            default:
                break;
        }
    }

    private void gotoIntroduction() {
        Intent intent = new Intent();
        intent.putExtra("title", "使用说明");
        intent.putExtra("url", HttpClient.getInstance().getIntroductionUrl());
        ((BaseActivity) getActivity()).next(WebViewActivity.class,intent);
    }
}
