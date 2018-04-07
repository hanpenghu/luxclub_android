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
 * 失败打叉动画
 * name：zhuzhenghua
 * create time:2016.5.18
 */
public class AlertFail extends Dialog {

    public AlertFail(Context context) {
        super(context);
    }

    public AlertFail(Context context, int theme) {
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

        public AlertFail create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final AlertFail dialog = new AlertFail(context, R.style.custom_loading_dialog);
            View layout = inflater.inflate(R.layout.layout_dialog_animation, null);
            animation = (AnimationDrawable) context.getResources().getDrawable(R.drawable.animation_set_card_password_fail);
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
