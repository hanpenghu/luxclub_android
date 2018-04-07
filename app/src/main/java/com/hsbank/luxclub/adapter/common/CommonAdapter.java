package com.hsbank.luxclub.adapter.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      chen_liuchun
 * Date:        2016/6/21
 * Description: 适用于加载更多的适配器, 功能更加完善
 * Attention：   本类参考baseAdapter开源项目的CommonAdapter改写而来，在升级的时候需要注意兼容性
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 * 2016-7-5     clc         2           补充多个方法
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int layoutId;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, int layoutId) {
        this(context, layoutId, new ArrayList<T>());
    }

    public void clearDatas() {
        if (mDatas == null) {
            return;
        }
        mDatas.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> datas) {
        if (datas == null) {
            return;
        }
        mDatas = datas;
        notifyDataSetChanged();
    }

    public T removeItem(int position){
        return mDatas.remove(position);
    }

    public void addDatas(List<T> datas, boolean noRepeat) {
        if (mDatas == null || datas == null) {
            return;
        }
        if (noRepeat) {
            mDatas.removeAll(datas);
        }
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

    /**
     * 填充具体item数据的方法
     * @param holder
     * @param t
     * @param position
     */
    public abstract void convert(ViewHolder holder, T t, int position);

}
