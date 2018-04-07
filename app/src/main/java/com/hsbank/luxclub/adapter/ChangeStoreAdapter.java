package com.hsbank.luxclub.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hsbank.luxclub.R;
import com.hsbank.util.java.collection.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * name：zhuzhenghua
 * create time:2016.4.26
 */
public class ChangeStoreAdapter extends BaseAdapter {
    private FragmentActivity fragmentActivity = null;
    public List<Map<String, Object>> datas = null;
    public int index = -1;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    public ChangeStoreAdapter(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public void addDatas(List<Map<String, Object>> datas) {
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }
        int count = datas.size();
        for (int i = 0; i < count; i++) {
            this.datas.add(datas.get(i));
        }
        notifyDataSetChanged();
    }

    public void clearDatas() {
        if (this.datas != null) {
            this.datas.clear();
        }
    }

    public List<Map<String, Object>> getDatas() {
        return this.datas;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (datas != null) {
            count = datas.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = fragmentActivity.getLayoutInflater().inflate(
                    R.layout.layout_change_store_item, parent, false);
            holder = new ViewHolder();
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_address = (TextView) convertView.findViewById(R.id.txt_address);
            holder.imLine = (ImageView) convertView.findViewById(R.id.imLine);
            holder.imLineLong = (ImageView) convertView.findViewById(R.id.imLineLong);
            holder.image_check = (ImageView) convertView.findViewById(R.id.image_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map dataItem = datas.get(position);
        holder.txt_name.setText(MapUtil.getString(dataItem,"siteName"));
        holder.txt_address.setText(MapUtil.getString(dataItem,"siteAddr"));
        if(position==dataItem.size()-1){
            holder.imLineLong.setVisibility(View.VISIBLE);
        }else{
            holder.imLine.setVisibility(View.VISIBLE);
        }
        if(getIndex()==position){
            holder.image_check.setBackground(fragmentActivity.getResources().getDrawable(R.drawable.icon_store_sel_uncheck));
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView txt_name;
        public TextView txt_address;
        public ImageView imLine;
        public ImageView imLineLong;
        public ImageView image_check;
    }
}

