package com.hsbank.luxclub.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.java.type.DatetimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 个人信息列表适配器
 * name：pengwei
 * create time:2016.4.5
 */
public class MessagePageListAdapter extends BaseAdapter {
    private FragmentActivity fragmentActivity = null;
    public List<Map<String, Object>> datas = null;
    String singleLine = "";
    public MessagePageListAdapter(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }
    public String getSingleLine() {
        return singleLine;
    }

    public void setSingleLine(String singleLine) {
        this.singleLine = singleLine;
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
                    R.layout.item_list_manager_my, parent, false);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.txtImage = (ImageView) convertView.findViewById(R.id.imageView);
            holder.mTxtOrderCode = (TextView) convertView.findViewById(R.id.txt_order_code);
            holder.mTxtCreateDate = (TextView) convertView.findViewById(R.id.txt_create_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map dataItemCurrent = datas.get(position);
        String createDate = DatetimeUtil.formatDate(MapUtil.getString(dataItemCurrent, "createDate"),
                fragmentActivity.getString(R.string.txt_from_format),
                fragmentActivity.getString(R.string.txt_create_date));
        setTextOmit(holder.txtTitle,MapUtil.getString(dataItemCurrent,"title"));
       // holder.txtTitle.setText(MapUtil.getString(dataItemCurrent, "title"));
        holder.mTxtOrderCode.setText(MapUtil.getString(dataItemCurrent, "content"));
        holder.mTxtCreateDate.setText(createDate);
        if(MapUtil.getString(dataItemCurrent, "status").equals("2")){
            holder.txtTitle.setTextColor(fragmentActivity.getResources().getColor(R.color.gray_deep_x));
           holder.txtImage.setImageResource(R.drawable.shape_circle_white_point);
        }else{
            holder.txtTitle.setTextColor(fragmentActivity.getResources().getColor(R.color.white));
            holder.txtImage.setImageResource(R.drawable.shape_circle_point);
        }
        if(getSingleLine()==String.valueOf(position)){
            holder.mTxtOrderCode.setSingleLine(false);
        }else{
            holder.mTxtOrderCode.setSingleLine(true);
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView txtTitle;
        public TextView mTxtOrderCode;
        public TextView mTxtCreateDate;
        public ImageView txtImage;
    }

    /**
     * 超过8个字显示"..."
     * @param tv
     * @param text
     */
    public void setTextOmit(TextView tv, String text) {
        if (tv == null) {
            return;
        }
        if (text == null) {
            text = "";
        }
        if (text.length() > 10) {
            text = text.substring(0, 10) + "...";
        }
        tv.setText(text);
    }
}
