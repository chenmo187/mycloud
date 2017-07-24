package com.mycloud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.mycloud.MyCloudApplication;
import com.mycloud.R;
import com.mycloud.adapter.NoteShareAdapter;
import com.mycloud.net.HttpClient;
import com.mycloud.net.HttpListener;
import com.mycloud.net.URLConfig;
import com.mycloud.pojo.Note;
import com.mycloud.pojo.NoteResult;
import com.mycloud.pojo.User;
import com.mycloud.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by clarechen on 2016/2/1.
 */
public class Tab2Fragment extends BaseFragment {
    private View view;
    private ListView listView;
    private MaterialRefreshLayout materialRefreshLayout;
    private NoteShareAdapter adapter;
    private List<Note> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab2, container, false);
        initView();
        getShareNote();
        return view;
    }

    @Override
    void initView() {
        listView = (ListView) view.findViewById(R.id.listview);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.materialRefreshLayout);
        adapter = new NoteShareAdapter((BaseActivity) getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("note", (Note) parent.getItemAtPosition(position));
                ((BaseActivity) getActivity()).next(ShareNoteDetailActivity.class, intent);
            }
        });
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                list.clear();
                getShareNote();
            }
        });
    }


    private void getShareNote() {
        HttpClient.getInstance().post((BaseActivity) getActivity(), URLConfig.GET_SHARE_NOTE, NoteResult.class, null, new HttpListener<NoteResult>() {
            @Override
            public void onReallySuccess(NoteResult result) {
                adapter.addAll(result.data);
            }

            @Override
            public void onFinish() {
                materialRefreshLayout.finishRefreshing();
            }
        });
    }


}
