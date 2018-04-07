package com.hsbank.luxclub.mywidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决listview嵌套gridview显示不全的问题
 * Created by chen_liuchun on 2016/3/24.
 */
public class MeansureGridView extends GridView {

    public MeansureGridView(Context context) {
        super(context);
    }

    public MeansureGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeansureGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**makeMeasureSpec方法中的两个参数的意思
         * 1.布局空间的大小
         * 2.布局模式（子控件需要多大空间，就扩展到空间）*/
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}