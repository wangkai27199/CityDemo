package com.zl.citydemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * create user zhaolei  代码无bug
 * 时间:2017/9/20
 */

public class MyAdapter extends BaseAdapter {
    private List<String> list;
    private Context con;

    public MyAdapter(List<String> list, Context con) {
        this.list = list;
        this.con = con;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return list != null ? list.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(con, R.layout.item, null);
            viewHolder.text = convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(list.get(position));
        return convertView;
    }


    static class ViewHolder {
        TextView text;
    }
}
