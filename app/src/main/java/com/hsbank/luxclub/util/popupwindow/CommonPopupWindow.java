package com.hsbank.luxclub.util.popupwindow;


import android.content.Context;
import android.support.annotation.FloatRange;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Author:      chenliuchun
 * Date:        2017/9/25
 * Description: PopupWindow 通用基类
 * 参考：https://github.com/crazyqiang/AndroidStudy/blob/master/app/src/main/java/org/ninetripods/mq/study/popup/PopupWindowActivity.java
 * <p>
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class CommonPopupWindow extends PopupWindow {
    final PopupController controller;

    @Override
    public int getWidth() {
        return controller.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.mPopupView.getMeasuredHeight();
    }

    public interface ViewInterface {
        void getChildView(View view, int layoutResId);
    }

    private CommonPopupWindow(Context context) {
        controller = new PopupController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackgroundLevel(1.0f);
    }

    public static class Builder {
        private final PopupController.PopupParams params;
        private ViewInterface listener;

        public Builder(Context context) {
            params = new PopupController.PopupParams(context);
        }

        /**
         * @param layoutResId 设置PopupWindow 布局ID
         * @return Builder
         */
        public Builder setView(int layoutResId) {
            params.mView = null;
            params.layoutResId = layoutResId;
            return this;
        }

        /**
         * @param view 设置PopupWindow布局
         * @return Builder
         */
        public Builder setView(View view) {
            params.mView = view;
            params.layoutResId = 0;
            return this;
        }

        /**
         * 设置子View
         *
         * @param listener ViewInterface
         * @return Builder
         */
        public Builder setOnClickListener(ViewInterface listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 设置宽度和高度 如果不设置 默认是wrap_content
         *
         * @param width 宽
         * @return Builder
         */
        public Builder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        /**
         * 设置背景灰色程度, 值越小越暗
         *
         * @param level 0.0f-1.0f
         * @return Builder
         */
        public Builder setBackgroundLevel(@FloatRange(from = 0.0f, to = 1.0f) float level) {
            params.isShowBg = true;
            params.bgLevel = level;
            return this;
        }

        /**
         * 是否可点击Outside消失
         *
         * @param touchable 是否可点击
         * @return Builder
         */
        public Builder setOutsideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        /**
         * 设置动画
         *
         * @return Builder
         */
        public Builder setAnimationStyle(int animationStyle) {
            params.isShowAnim = true;
            params.animationStyle = animationStyle;
            return this;
        }

        public CommonPopupWindow create() {
            final CommonPopupWindow popupWindow = new CommonPopupWindow(params.mContext);
            params.apply(popupWindow.controller);
            if (listener != null && params.layoutResId != 0) {
                listener.getChildView(popupWindow.controller.mPopupView, params.layoutResId);
            }
            measureWidthAndHeight(popupWindow.controller.mPopupView);
            return popupWindow;
        }
    }

    private static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

}