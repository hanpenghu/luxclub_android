/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hsbank.luxclub.mywidget.recyclerview_pager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.JoyPageBean;
import com.hsbank.luxclub.mywidget.RoundImageView;
import com.hsbank.luxclub.util.constants.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据RecyclerView.Adapter改造而来的适配器，结合ImageLoader加载本地图片
 * 源代码地址：https://github.com/lsjwzh/RecyclerViewPager
 */
public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> implements TabLayoutSupport.ViewPagerTabLayoutAdapter {

    private LayoutInflater mInflater;

    private List<JoyPageBean> mDatas;

    private OnItemClickLitener mOnItemClickLitener;

    public LayoutAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
        mDatas.add(new JoyPageBean());
    }

    public LayoutAdapter(Context context, List< JoyPageBean> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    public void setmDatas(List<JoyPageBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private final RoundImageView roundImg;

        SimpleViewHolder(View view) {
            super(view);
            roundImg = (RoundImageView) view.findViewById(R.id.round_img);
        }
    }

    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item_joy, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        JoyPageBean item = mDatas.get(holder.getAdapterPosition());

        if (item.getImageUrl3() != null) {
            ImageLoader.getInstance().displayImage(item.getImageUrl3(), holder.roundImg, Constants.networkOptions);
        }
        final View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(itemView, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 数据点击的监听器
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public String getPageTitle(int position) {
        return mDatas.get(position).getTitle();
    }

}
