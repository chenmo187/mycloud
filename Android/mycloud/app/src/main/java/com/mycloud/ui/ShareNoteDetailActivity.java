package com.mycloud.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.mycloud.R;
import com.mycloud.adapter.StringAdapter;
import com.mycloud.net.HttpClient;
import com.mycloud.net.HttpListener;
import com.mycloud.net.URLConfig;
import com.mycloud.pojo.BaseResult;
import com.mycloud.pojo.Note;
import com.mycloud.pojo.User;
import com.mycloud.view.PopWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by clarechen on 2016/2/15.
 */
public class ShareNoteDetailActivity extends BaseActivity implements View.OnClickListener {
    private Note note;
    private EditText mEtNoteContent;
    private String content;
    private StringAdapter adapter;
    private PopWindow popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notedetail);
        note = (Note) getIntent().getSerializableExtra("note");
        setHeaderAndBack(note.note);
        initView();
    }



    @Override
    public void initView() {
        mEtNoteContent = (EditText) findViewById(R.id.et_notecontent);
        mEtNoteContent.setText(note.content);
        mEtNoteContent.setEnabled(false);
        findViewById(R.id.fab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                initPopwindow(v);
                break;
            default:
                break;
        }
    }

    private void initPopwindow(View v) {
        List<String> list = new ArrayList<>();
        list.add("点赞");
        adapter = new StringAdapter(this, list);
        popWindow = new PopWindow(this, adapter, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    zan();
                } else if (position == 1) {
                    share();
                }
            }
        });
        popWindow.showPopupWindow(v);
    }


    private void zan() {
        HashMap<String, String> params = new HashMap<>();
        params.put("noteid", note.id);
        HttpClient.getInstance().post(this, URLConfig.ZAN, BaseResult.class, params, new HttpListener<BaseResult>() {
            @Override
            public void onReallySuccess(BaseResult result) {
                showToastMsg("点赞成功");
            }

            @Override
            public void onFinish() {
                popWindow.dismiss();
            }

        });
    }

    private void share() {
        HashMap<String, String> params = new HashMap<>();
        params.put("noteid", note.id);
        HttpClient.getInstance().post(this, URLConfig.SHARE, BaseResult.class, params, new HttpListener<BaseResult>() {
            @Override
            public void onReallySuccess(BaseResult result) {
                showToastMsg("共享成功");
            }

            @Override
            public void onFinish() {
                popWindow.dismiss();
            }

        });
    }

}
