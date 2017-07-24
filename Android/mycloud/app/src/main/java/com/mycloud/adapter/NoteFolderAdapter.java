package com.mycloud.adapter;

import android.view.View;
import android.widget.TextView;

import com.mycloud.R;
import com.mycloud.pojo.NoteFolder;
import com.mycloud.ui.BaseActivity;

import java.util.List;

/**
 * Created by clarechen on 2016/2/2.
 */
public class NoteFolderAdapter extends BaseAdapter<NoteFolder, NoteFolderAdapter.ViewHolder> {

    public NoteFolderAdapter(BaseActivity context, List<NoteFolder> list) {
        super(context, list, R.layout.item_note_folder);
    }

    @Override
    public void findView(View view, ViewHolder viewHolder) {
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);
    }

    @Override
    public void setItemView(ViewHolder viewHolder, NoteFolder item, int position) {
        viewHolder.tv_title.setText(item.folder);
        viewHolder.tv_content.setText(item.folder);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new ViewHolder();
    }

    static class ViewHolder {
        public TextView tv_title;
        public TextView tv_content;
    }

}
