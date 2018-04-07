package com.hsbank.luxclub.util.tool;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hsbank.luxclub.MyApp;
import com.hsbank.luxclub.R;

/**
 * Author:      chenliuchun
 * Date:        17/3/9
 * Description: 吐司工具类，任意线程内使用
 * Modification History:
 *      2017-06-20：增加自定义布局，修复显示时间不更新问题
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ToastUtil {

    private static Toast toast;

    private ToastUtil() {
    }

    public static void show(final CharSequence text) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            showMain(text, false);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showMain(text, false);
                }
            });
        }
    }

    public static void showLong(final CharSequence text) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            showMain(text, true);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showMain(text, true);
                }
            });
        }
    }

    private static void showMain(CharSequence text, boolean longTime) {
        if (toast == null) {
            toast = new Toast(MyApp.getContext());
            View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.toast_custom, null);
            TextView txt_toast = (TextView) view.findViewById(R.id.txt_toast);
            txt_toast.setText(text);
            toast.setDuration(longTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setView(view);

        } else {
            TextView txt_toast = (TextView) toast.getView().findViewById(R.id.txt_toast);
            txt_toast.setText(text);
            toast.setDuration(longTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
