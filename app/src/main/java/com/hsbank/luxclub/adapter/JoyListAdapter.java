package com.hsbank.luxclub.adapter;

import android.support.v7.app.AppCompatActivity;
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
 * Created by chen_liuchun on 2016/3/16.
 * KTV 列表适配器
 */
public class JoyListAdapter extends BaseAdapter {
    private AppCompatActivity mAppCompatActivity = null;
    private List<Map<String, Object>> mDatas = null;

    public JoyListAdapter(AppCompatActivity appCompatActivity) {
        this.mAppCompatActivity = appCompatActivity;
    }

    public void setDatas(List<Map<String, Object>> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<Map<String, Object>> datas) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<Map<String, Object>>();
        }else {
            mDatas.removeAll(datas);
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void clearDatas() {
        if (this.mDatas != null) {
            this.mDatas.clear();
        }
        notifyDataSetChanged();
    }

    public List<Map<String, Object>> getmDatas() {
        return this.mDatas;
    }

    @Override
    public int getCount() {
        int count = mDatas == null ? 0 : mDatas.size();
        return count;
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mAppCompatActivity.getLayoutInflater().inflate(
                    R.layout.item_grid_model, parent, false);

            holder = new ViewHolder();
            holder.img_item_cover = (ImageView) convertView.findViewById(R.id.img_item_cover);
            holder.txt_item_title = (TextView) convertView.findViewById(R.id.txt_item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Map<String, Object> dataItem = getItem(position);
        holder.txt_item_title.setText(MapUtil.getString(dataItem, "siteName"));

        String siteImageUrl = MapUtil.getString(dataItem, "siteImageUrl");
        holder.img_item_cover.setTag(siteImageUrl);
        if (holder.img_item_cover.getTag() != null && holder.img_item_cover.getTag().equals(siteImageUrl)) {
            ImageLoader.getInstance().displayImage(siteImageUrl, holder.img_item_cover, Constants.networkOptions);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView img_item_cover;
        TextView txt_item_title;
    }

}
