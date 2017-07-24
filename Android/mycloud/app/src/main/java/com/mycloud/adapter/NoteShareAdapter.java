package com.mycloud.adapter;

import android.view.View;
import android.widget.TextView;

import com.mycloud.R;
import com.mycloud.pojo.Note;
import com.mycloud.ui.BaseActivity;
import com.mycloud.utils.Utils;

import java.util.List;

/**
 * Created by clarechen on 2016/2/2.
 */
public class NoteShareAdapter extends BaseAdapter<Note, NoteShareAdapter.ViewHolder> {

    public NoteShareAdapter(BaseActivity context, List<Note> list) {
        super(context, list, R.layout.item_note);
    }

    @Override
    public void findView(View view, ViewHolder viewHolder) {
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);
        viewHolder.tv_shareby = (TextView) view.findViewById(R.id.tv_shareby);
        viewHolder.tv_time = (TextView) view.findViewById(R.id.tv_time);
    }

    @Override
    public void setItemView(ViewHolder viewHolder, Note item, int position) {
        viewHolder.tv_title.setText(item.note);
        viewHolder.tv_content.setText(item.content);
        viewHolder.tv_shareby.setText("share by :" + Utils.getStringMaybeNull(item.shareby));
        viewHolder.tv_time.setText(Utils.parseTime(item.latestupdatetime));
    }

    @Override
    public ViewHolder getViewHolder() {
        return new ViewHolder();
    }

    static class ViewHolder {
        public TextView tv_title;
        public TextView tv_content;
        public TextView tv_shareby;
        public TextView tv_time;
    }

}
