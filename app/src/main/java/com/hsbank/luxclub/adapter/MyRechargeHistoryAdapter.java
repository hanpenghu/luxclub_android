package com.hsbank.luxclub.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.java.type.DatetimeUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * name：zhuzhenghua
 * create time:2016.3.11
 */
public class MyRechargeHistoryAdapter extends BaseAdapter {
    private FragmentActivity fragmentActivity = null;
    public List<Map<String, Object>> datas = null;
    private ArrayList<Map<String, Double>> list = null;

    public ArrayList<Map<String, Double>> getList() {
        return list;
    }

    public void setList(ArrayList<Map<String, Double>> list) {
        this.list = list;
    }

    public MyRechargeHistoryAdapter(FragmentActivity fragmentActivity) {
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
                    R.layout.layout_my_recharge_consumption_item, parent, false);
            holder = new ViewHolder();
            holder.mRlyYear = (RelativeLayout) convertView.findViewById(R.id.mRlyYear);
            holder.txtUserTime = (TextView) convertView.findViewById(R.id.txtUserTime);
            holder.mTxtSiteName = (TextView) convertView.findViewById(R.id.txt_site_name);
            holder.mTxtCreateDate = (TextView) convertView.findViewById(R.id.txt_create_date);
            holder.mTxtFeeMoney = (TextView) convertView.findViewById(R.id.mTxtFeeMoney);
            holder.imLine = (ImageView) convertView.findViewById(R.id.imLine);
            holder.imLineLong = (ImageView) convertView.findViewById(R.id.imLineLong);
            holder.mTxtMoneySum = (TextView) convertView.findViewById(R.id.mTxtMoneySum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String date = DatetimeUtil.formatDate(MapUtil.getString(datas.get(position), "createDate"),
                fragmentActivity.getString(R.string.txt_from_format),
                fragmentActivity.getString(R.string.txt_account_date));
        ArrayList<Map<String, Double>> dataList = getList();
        for(int i=0;i<dataList.size();i++){
           if(dataList.get(i).get(date)!=null){
               double dateValues = dataList.get(i).get(date);
               BigDecimal priceBigDecimal = new BigDecimal(dateValues);
               String dateValueStr = String.format("%1$,.2f",
                       priceBigDecimal.doubleValue());
               holder.mTxtMoneySum.setText(fragmentActivity.getString(R.string.txt_recharge_consumption) + "+" + String.valueOf(dateValueStr));
               break;
           }
        }
        holder.mRlyYear.setVisibility(View.GONE);
        Map dataItemBefore = position > 0 ? datas.get(position - 1) : null;
        Map dataItemCurrent = datas.get(position);
        Map dataItemLast = position < getCount() - 1 ? datas.get(position + 1) : null;
        String groupOpDtBefore = "";
        if (dataItemBefore != null) {
            groupOpDtBefore = DatetimeUtil.formatDate(MapUtil.getString(dataItemBefore, "createDate"),
                    fragmentActivity.getString(R.string.txt_from_format),
                    fragmentActivity.getString(R.string.txt_account_date));
        }
        String groupOpDtCurrent = DatetimeUtil.formatDate(MapUtil.getString(dataItemCurrent, "createDate"),
                fragmentActivity.getString(R.string.txt_from_format),
                fragmentActivity.getString(R.string.txt_account_date));
        String groupOpDtLast = "";
        if (dataItemLast != null) {
            groupOpDtLast = DatetimeUtil.formatDate(MapUtil.getString(dataItemLast, "createDate"),
                    fragmentActivity.getString(R.string.txt_from_format),
                    fragmentActivity.getString(R.string.txt_account_date));
        }
        if (dataItemBefore == null) {
            holder.mRlyYear.setVisibility(View.VISIBLE);
        } else {
            if (groupOpDtCurrent.equals(groupOpDtBefore)) {
                holder.mRlyYear.setVisibility(View.GONE);
            } else {
                holder.mRlyYear.setVisibility(View.VISIBLE);
            }
        }
        if (dataItemLast == null) {
            holder.imLine.setVisibility(View.INVISIBLE);
        } else {
            if (groupOpDtCurrent.equals(groupOpDtLast)) {
                holder.imLine.setVisibility(View.VISIBLE);
            } else {
                holder.imLine.setVisibility(View.INVISIBLE);
            }
        }
        if (dataItemLast == null) {
            holder.imLineLong.setVisibility(View.VISIBLE);
        } else {
            holder.imLineLong.setVisibility(View.INVISIBLE);
        }
        String createDate = DatetimeUtil.formatDate(MapUtil.getString(dataItemCurrent, "createDate"),
                fragmentActivity.getString(R.string.txt_from_format),
                fragmentActivity.getString(R.string.txt_create_date));
        holder.txtUserTime.setText(groupOpDtCurrent);
        holder.mTxtSiteName.setText(fragmentActivity.getString(R.string.txt_recharge_card));
        holder.mTxtCreateDate.setText(createDate);
        BigDecimal priceBigDecimal = new BigDecimal(MapUtil.getString(dataItemCurrent, "feeMoney"));
        String feeMoney = String.format("%1$,.2f",
                priceBigDecimal.doubleValue());
        holder.mTxtFeeMoney.setText( "+" + feeMoney);
        return convertView;
    }

    private class ViewHolder {
        public RelativeLayout mRlyYear;
        public TextView mTxtMoneySum;
        public TextView txtUserTime;
        public TextView mTxtSiteName;
        public TextView mTxtCreateDate;
        public TextView mTxtFeeMoney;
        public ImageView imLine;
        public ImageView imLineLong;
    }
}
