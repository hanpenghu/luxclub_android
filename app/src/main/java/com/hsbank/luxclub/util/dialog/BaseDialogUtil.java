package com.hsbank.luxclub.util.dialog;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      chen_liuchun
 * Date:        2016/6/14
 * Description: 对话框DialogFragment工具类基类
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class BaseDialogUtil {

    public static List<String> mTags = new ArrayList<>();

    /**
     * 取消当前界面所有的对话框
     *
     * @param context 当前activity的上下文
     */
    public static int dismiss(Context context) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        for (String tag : mTags) {
            Fragment prev = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
            if (prev != null) {
                ft.remove(prev);
            }
        }
        return ft.commit();
    }

    /**
     * 取消当前界面指定tag的对话框
     *
     * @param context 当前activity的上下文
     * @param tag     指定取消的tag
     */
    public static void dismiss(Context context, String tag) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Fragment prev = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
    }

    /**
     * 判断当前界面是否存在指定的tag的对话框
     *
     * @param context 当前activity的上下文
     * @param tag     指定的tag
     */
    public static boolean find(Context context, String tag) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Fragment prev = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
        return prev != null;
    }

}
