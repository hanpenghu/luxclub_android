package com.hsbank.luxclub.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.constants.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Author:      chenliuchun
 * Date:        17/2/22
 * Description: 动态添加图片适配器
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ImageAddAdapter2 extends BaseAdapter {

    /**
     * 是否可以添加新图片
     */
    private int itemNumber = 8;
    private LinkedList<String> linkedList;
    Context context;

    public ImageAddAdapter2(Context context) {
        linkedList = new LinkedList<>();
        linkedList.addLast(null);
        this.context = context;
    }

    public ArrayList<String> getDatas() {
        ArrayList<String> arrayList = new ArrayList<>();
        if (linkedList.getLast() == null) {
            linkedList.removeLast();
            arrayList.addAll(linkedList);
            linkedList.addLast(null);
            return arrayList;
        }
        arrayList.addAll(linkedList);
        return arrayList;
    }

    public boolean deleteData(int index) {
        if (linkedList.get(index) == null) {
            return false;
        } else {
            linkedList.remove(index);
            return true;
        }
    }

    public void addData(String s) {
        if (s == null) {
            return;
        }

        linkedList.addFirst(s);

        if (linkedList.getLast() == null) {
            if (linkedList.size() == itemNumber + 1) {
                linkedList.removeLast();
            }
        } else {
            if (linkedList.size() < itemNumber ) {
                linkedList.addLast(null);
            } else if (linkedList.size() > itemNumber){
                linkedList.removeFirst();
            }
        }
        notifyDataSetChanged();
        // 解决刷新不完全的问题
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, 100);
    }

    public boolean isAdd() {
        return !(linkedList.getLast() != null && linkedList.size() == itemNumber);
    }

    @Override
    public int getCount() {
        return linkedList == null ? 0 : linkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return linkedList == null ? null : linkedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_pic, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (getItem(position) == null) { //图片地址为空时设置默认图片
            holder.imageView.setImageResource(R.drawable.icon_camera_stroke);
        } else {
            ImageLoader.getInstance().displayImage("file://" + getItem(position), holder.imageView, Constants.localOptions);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }

}
