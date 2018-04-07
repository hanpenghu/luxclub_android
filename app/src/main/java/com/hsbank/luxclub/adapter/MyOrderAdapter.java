package com.hsbank.luxclub.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.util.java.collection.MapUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * name：zhuzhenghua
 * create time:2015.11.19
 */
public class MyOrderAdapter extends BaseAdapter{
    private FragmentActivity fragmentActivity = null;
    public List<Map<String, Object>> datas = null;

    public MyOrderAdapter(FragmentActivity fragmentActivity) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = fragmentActivity.getLayoutInflater().inflate(
                    R.layout.layout_order_listview_item, parent, false);
            holder = new ViewHolder();
            holder.txt_site_name = (TextView) convertView.findViewById(R.id.txt_site_name);
            holder.txt_order_state = (TextView) convertView.findViewById(R.id.txt_order_state);
            holder.img_site_image_url = (ImageView) convertView.findViewById(R.id.img_site_image_url);
            holder.txt_create_date = (TextView) convertView.findViewById(R.id.txt_create_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map dataItem = datas.get(position);
        holder.txt_site_name.setText(MapUtil.getString(dataItem, "siteName"));
        holder.txt_order_state.setText(MapUtil.getString(dataItem, "orderStateName"));
        Object urlTag = holder.img_site_image_url.getTag();
        if (urlTag == null || !urlTag.toString().equals(MapUtil.getString(dataItem, "siteImageUrl"))) {
            ImageLoader.getInstance().displayImage(MapUtil.getString(dataItem, "siteImageUrl"),
                    holder.img_site_image_url, Constants.networkOptions);
        }
        holder.txt_create_date.setText(MapUtil.getString(dataItem, "createDate"));
        return convertView;
    }

    private class ViewHolder {
        public TextView txt_site_name;
        public TextView txt_order_state;
        public ImageView img_site_image_url;
        public TextView txt_create_date;
    }
}
