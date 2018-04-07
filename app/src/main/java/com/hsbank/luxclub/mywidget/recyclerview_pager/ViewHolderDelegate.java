package com.hsbank.luxclub.mywidget.recyclerview_pager;

import android.support.v7.widget.RecyclerView;

public abstract class ViewHolderDelegate {

    private ViewHolderDelegate() {
        throw new UnsupportedOperationException("no instances");
    }

    public static void setPosition(RecyclerView.ViewHolder viewHolder, int position) {
        // 暂时注释掉，后面反射解决
//        viewHolder.mPosition = position;
    }
}
