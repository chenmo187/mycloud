package com.mycloud.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.mycloud.MyCloudApplication;
import com.mycloud.R;
import com.mycloud.adapter.NoteAdapter;
import com.mycloud.memory.Memory;
import com.mycloud.net.HttpClient;
import com.mycloud.net.HttpListener;
import com.mycloud.net.URLConfig;
import com.mycloud.pojo.AddNoteResult;
import com.mycloud.pojo.BaseResult;
import com.mycloud.pojo.Note;
import com.mycloud.pojo.NoteFolder;
import com.mycloud.pojo.NoteResult;
import com.mycloud.utils.DensityUtils;
import com.mycloud.view.CustomDialogWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by clarechen on 2016/2/15.
 */
public class NoteListActivity extends EventBusActivity<Note> implements View.OnClickListener {
    private SwipeMenuListView listView;
    private MaterialRefreshLayout materialRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private NoteAdapter adapter;
    private List<Note> list = new ArrayList<>();
    private NoteFolder noteFolder;
    private CustomDialogWithView customDialog;
    private EditText etAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);
        noteFolder = (NoteFolder) getIntent().getSerializableExtra("notefolder");
        setHeaderAndBack(noteFolder.folder);
        initView();
    }


    @Override
    public void initView() {
        listView = (SwipeMenuListView) findViewById(R.id.listview);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.materialRefreshLayout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        adapter = new NoteAdapter(this, list);
        listView.setAdapter(adapter);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getNote(noteFolder.id, true);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("note", (Note) parent.getItemAtPosition(position));
                next(NoteDetailActivity.class, intent);
            }
        });
        initSwipeListView();
        getNote(noteFolder.id, false);
    }


    private void initSwipeListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(DensityUtils.dip2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(DensityUtils.dip2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        listView.setMenuCreator(creator);


        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                deleteNote(list.get(position).id);
                return false;
            }
        });
    }

    private void getNote(String folderid, final boolean clean) {
        HashMap<String, String> params = new HashMap<>();
        params.put("folderid", folderid);
        HttpClient.getInstance().post(this, URLConfig.GET_NOTE, NoteResult.class, params, new HttpListener<NoteResult>() {
            @Override
            public void onReallySuccess(NoteResult noteResult) {
                if (clean) {
                    list.clear();
                }
                adapter.addAll(noteResult.data);
            }

            @Override
            public void onFinish() {
                materialRefreshLayout.finishRefreshing();
            }
        });
    }

    @Override
    void onEventBusCallBack(Note event) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id.equals(event.id)) {
                list.set(i, event);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private void initDialog() {
        View view = getLayoutInflater()
                .inflate(R.layout.dialog_addnote, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        etAddNote = (EditText) view.findViewById(R.id.et_addfolder);
        customDialog = new CustomDialogWithView(this, view, true, true);
        customDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                initDialog();

                break;
            case R.id.btn_confirm:
                ready2addnote();
                break;
            default:
                break;
        }
    }

    private void ready2addnote() {
        String noteName = etAddNote.getText().toString().trim();
        if (TextUtils.isEmpty(noteName)) {
            showToastMsg("请输入笔记");
        } else if (checknote(noteName)) {
            addnote(noteName);
        }
    }

    private boolean checknote(String noteName) {
        for (Note note : list) {
            if (note.note.equals(noteName)) {
                showToastMsg("已经存在相同的笔记");
                return false;
            }
        }
        return true;
    }

    private void deleteNote(final String noteid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("noteid", noteid);
        HttpClient.getInstance().post(this, URLConfig.DELETE_NOTE, BaseResult.class, params, new HttpListener<BaseResult>() {
                    @Override
                    public void onReallySuccess(BaseResult result) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).id.equals(noteid)) {
                                adapter.remove(i);
                                return;
                            }
                        }
                    }
                }
        );
    }


    private void addnote(String note) {
        HashMap<String, String> params = new HashMap<>();
        params.put("folderid", noteFolder.id);
        params.put("note", note);
        HttpClient.getInstance().post(this, URLConfig.CREATE_NOTE, AddNoteResult.class, params, new HttpListener<AddNoteResult>() {
            @Override
            public void onReallySuccess(AddNoteResult result) {
                showToastMsg("添加成功");
                adapter.add(result.data);
            }

            @Override
            public void onFinish() {
                customDialog.dismiss();
            }
        });
    }
}


