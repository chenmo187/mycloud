package com.mycloud.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mycloud.adapter.NoteFolderAdapter;
import com.mycloud.net.HttpClient;
import com.mycloud.net.HttpListener;
import com.mycloud.net.URLConfig;
import com.mycloud.pojo.AddNoteFolderResult;
import com.mycloud.pojo.BaseResult;
import com.mycloud.pojo.NoteFolder;
import com.mycloud.pojo.NoteFolderResult;
import com.mycloud.utils.DensityUtils;
import com.mycloud.view.CustomDialogWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by clarechen on 2016/2/1.
 */
public class Tab1Fragment extends BaseFragment implements View.OnClickListener {
    private SwipeMenuListView listView;
    private MaterialRefreshLayout materialRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private NoteFolderAdapter adapter;
    private View view;
    private List<NoteFolder> list = new ArrayList<>();
    private CustomDialogWithView customDialog;
    private EditText etAddFolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        initView();
        return view;
    }

    @Override
    void initView() {
        listView = (SwipeMenuListView) view.findViewById(R.id.listview);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.materialRefreshLayout);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        adapter = new NoteFolderAdapter((BaseActivity) this.getActivity(), list);
        listView.setAdapter(adapter);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getNoteFolder(true);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("notefolder", ((NoteFolder) parent.getItemAtPosition(position)));
                ((BaseActivity) getActivity()).next(NoteListActivity.class, intent);
            }
        });

        initSwipeListView();
        getNoteFolder(false);
    }

    private void initSwipeListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
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
                        getActivity().getApplicationContext());
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
                deleteNoteFolder(list.get(position).id);
                return false;
            }


        });
    }

    private void deleteNoteFolder(final String folderid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("folderid", folderid);
        HttpClient.getInstance().post(((BaseActivity) this.getActivity()), URLConfig.DELETE_NOTEFOLDER, BaseResult.class, params, new HttpListener<BaseResult>() {
                    @Override
                    public void onReallySuccess(BaseResult result) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).id.equals(folderid)) {
                                adapter.remove(i);
                                return;
                            }
                        }
                    }
                }
        );
    }

    private void getNoteFolder(final boolean clean) {
        HttpClient.getInstance().post(((BaseActivity) this.getActivity()), URLConfig.GET_NOTEFOLDER, NoteFolderResult.class, null, new HttpListener<NoteFolderResult>() {
            @Override
            public void onReallySuccess(NoteFolderResult noteFolderResult) {
                if (clean) {
                    list.clear();
                }
                adapter.addAll(noteFolderResult.data);
            }

            @Override
            public void onFinish() {
                materialRefreshLayout.finishRefreshing();
            }
        });
    }

    private void initDialog() {
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_addfolder, null);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        etAddFolder = (EditText) view.findViewById(R.id.et_addfolder);
        customDialog = new CustomDialogWithView(getActivity(), view, true, true);
        customDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                initDialog();

                break;
            case R.id.btn_confirm:
                ready2addfolder();
                break;
            default:
                break;
        }
    }

    private void ready2addfolder() {
        String folder = etAddFolder.getText().toString().trim();
        if (TextUtils.isEmpty(folder)) {
            ((BaseActivity) getActivity()).showToastMsg("请输入文件夹");
        } else if (checkFolder(folder)) {
            addfolder(folder);
        }
    }

    private boolean checkFolder(String folder) {
        for (NoteFolder noteFolder : list) {
            if (noteFolder.folder.equals(folder)) {
                ((BaseActivity) getActivity()).showToastMsg("已经存在相同的文件夹");
                return false;
            }
        }
        return true;
    }

    private void addfolder(String folder) {
        HashMap<String, String> params = new HashMap<>();
        params.put("folder", folder);
        HttpClient.getInstance().post((BaseActivity) getActivity(), URLConfig.CREATE_NOTE_FOLDER, AddNoteFolderResult.class, params, new HttpListener<AddNoteFolderResult>() {
            @Override
            public void onReallySuccess(AddNoteFolderResult result) {
                ((BaseActivity) getActivity()).showToastMsg("添加成功");
                adapter.add(result.data);
            }

            @Override
            public void onFinish() {
                customDialog.dismiss();
            }
        });
    }
}
