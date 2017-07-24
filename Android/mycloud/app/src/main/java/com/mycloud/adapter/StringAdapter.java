package com.mycloud.adapter;

import android.view.View;
import android.widget.TextView;

import com.mycloud.R;
import com.mycloud.pojo.Note;
import com.mycloud.ui.BaseActivity;

import java.util.List;

/**
 * Created by clarechen on 2016/2/2.
 */
public class StringAdapter extends BaseAdapter<String, StringAdapter.ViewHolder> {

    public StringAdapter(BaseActivity context, List<String> list) {
        super(context, list, R.layout.item_string);
    }

    @Override
    public void findView(View view, ViewHolder viewHolder) {
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
    }

    @Override
    public void setItemView(ViewHolder viewHolder, String item, int position) {
        viewHolder.tv_title.setText(item);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new ViewHolder();
    }

    static class ViewHolder {
        public TextView tv_title;
    }

}
