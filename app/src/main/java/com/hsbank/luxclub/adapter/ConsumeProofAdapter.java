package com.hsbank.luxclub.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.constants.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * name：zhuzhenghua
 * create time:2016.5.25
 */
public class ConsumeProofAdapter extends BaseAdapter {
    private AppCompatActivity appCompatActivity = null;
    public ArrayList<String> datas= null;

    public ConsumeProofAdapter(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public void addDatas(ArrayList<String> datas) {
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
            convertView = appCompatActivity.getLayoutInflater().inflate(
                    R.layout.layout_customer_voucher_gridview_item, parent, false);
            holder = new ViewHolder();
            holder.img_voucher = (ImageView) convertView.findViewById(R.id.img_voucher);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String imageUrl = datas.get(position);
        ImageLoader.getInstance().displayImage(imageUrl,
                holder.img_voucher, Constants.networkOptions);
        return convertView;
    }

    private class ViewHolder {
        public ImageView img_voucher;
    }
}
