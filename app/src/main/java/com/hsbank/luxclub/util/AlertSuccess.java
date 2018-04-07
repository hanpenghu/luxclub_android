package com.hsbank.luxclub.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsbank.luxclub.R;

/**
 * 成功打勾动画
 * name：zhuzhenghua
 * create time:2016.5.18
 */
public class AlertSuccess extends Dialog {

    public AlertSuccess(Context context) {
        super(context);
    }

    public AlertSuccess(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        /**文字提示*/
        private String title;
        /**动画*/
        private AnimationDrawable animation;

        public Builder(Context context,String title) {
            this.context = context;
            this.title = title;
        }

        public AlertSuccess create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final AlertSuccess dialog = new AlertSuccess(context, R.style.custom_loading_dialog);
            View layout = inflater.inflate(R.layout.layout_dialog_animation, null);
            animation = (AnimationDrawable) context.getResources().getDrawable(R.drawable.animation_set_gesture_success);
            ((ImageView)layout.findViewById(R.id.img_gesture_success)).setImageDrawable(animation);
            ((TextView)layout.findViewById(R.id.txt_title)).setText(title);
            animation.start();
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    animation.stop();
                }
            }, 1500);
            return dialog;
        }

    }

}
