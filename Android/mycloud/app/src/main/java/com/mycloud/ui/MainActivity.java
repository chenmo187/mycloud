package com.mycloud.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mycloud.R;

/**
 * Created by clarechen on 2016/2/1.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private View tabs[];
    private View tab1, tab2, tab3;
    private Fragment fragments[];
    private Fragment tab1fragment;
    private Fragment tab2fragment;
    private Fragment tab3fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void initView() {
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tabs = new View[]{tab1, tab2, tab3};
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab1fragment = new Tab1Fragment();
        tab2fragment = new Tab2Fragment();
        tab3fragment = new Tab3Fragment();
        fragments = new Fragment[]{tab1fragment, tab2fragment, tab3fragment};
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentcontain, tab1fragment).
                add(R.id.fragmentcontain, tab2fragment).
                add(R.id.fragmentcontain, tab3fragment).
                commit();
        changeTab(0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                changeTab(0);
                break;
            case R.id.tab2:
                changeTab(1);
                break;
            case R.id.tab3:
                changeTab(2);
                break;
        }
    }


    private void changeTab(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < tabs.length; i++) {
            if (i == position) {
                if (i == 0) {
                    setHeader("云笔记");
                } else if (i == 1) {
                    setHeader("云共享");
                } else if (i == 2) {
                    setHeader("我");
                }
                tabs[i].setSelected(true);
                transaction.show(fragments[i]);
            } else {
                tabs[i].setSelected(false);
                transaction.hide(fragments[i]);
            }
        }
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        
    }
}
