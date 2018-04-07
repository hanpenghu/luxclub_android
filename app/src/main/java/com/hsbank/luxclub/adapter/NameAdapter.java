package com.hsbank.luxclub.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.hsbank.luxclub.R;

import java.util.ArrayList;
import java.util.Map;

public class NameAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Map<String, Object>> mCityList;
    private Context mContext;
    private String mFirstChar;
    private String mPreFirstPinyin;

    public NameAdapter(Context context, ArrayList<Map<String, Object>> persons) {
        mCityList = persons;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_list_city, null);
            holder = new ViewHolder();
            holder.txt_city_name = (TextView) convertView.findViewById(R.id.txt_city_title);
            holder.txt_first_char = (TextView) convertView.findViewById(R.id.txt_city_catalog);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String, Object> city = mCityList.get(position);
        mFirstChar = String.valueOf(city.get("firstChar"));
        if (position == 0) {
            mPreFirstPinyin = "-";
        } else {
            mPreFirstPinyin = String.valueOf(mCityList.get(position - 1).get("firstChar"));
        }
        holder.txt_city_name.setText(String.valueOf(city.get("areaName")));
        holder.txt_first_char.setVisibility(TextUtils.equals(mPreFirstPinyin, mFirstChar) ? View.GONE
                : View.VISIBLE);
        holder.txt_first_char.setText(mFirstChar);
        return convertView;
    }

    public void refresh(ArrayList<Map<String, Object>> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.mCityList = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView txt_city_name;
        TextView txt_first_char;
    }

}
