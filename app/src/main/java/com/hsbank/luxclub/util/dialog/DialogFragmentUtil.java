package com.hsbank.luxclub.util.dialog;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hsbank.luxclub.util.dialog.dialog_fragment.CommonDialogFragment;
import com.hsbank.luxclub.util.dialog.listener.DialogFragmentListener;

/**
 * Author:      chen_liuchun
 * Date:        2016/6/14
 * Description: 对话框DialogFragment工具类, 标准封装, 区别于{@link CustomDialogUtil}
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class DialogFragmentUtil extends BaseDialogUtil{

    /**
     * 创建最简单的dialog
     * @param context
     * @param content
     */
    public static void message(Context context, String content) {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(null, content, null, null, null, true, null);
        String tag = CommonDialogFragment.class.getSimpleName() + "_" + "message";
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建最简单的dialog
     * @param context
     * @param contentId
     */
    public static void message(Context context, int contentId) {
        String content = getString(context, contentId);
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(null, content, null, null, null, true, null);
        String tag = CommonDialogFragment.class.getSimpleName() + "_" + "message";
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建最简单的dialog
     * @param context
     * @param content
     * @param tag
     */
    public static void message(Context context, String content, String tag) {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(null, content, null, null, null, true, null);
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建最简单的dialog
     * @param context
     * @param contentId
     * @param tag
     */
    public static void message(Context context, int contentId, String tag) {
        String content = getString(context, contentId);
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(null, content, null, null, null, true, null);
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建带标题和内容的dialog
     * @param context
     * @param title
     * @param content
     * @param positive
     */
    public static void title(Context context, String title, String content, String positive) {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(title, content, positive, null, null, true, null);
        String tag = CommonDialogFragment.class.getSimpleName() + "_" + "title";
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建带标题和内容的dialog
     * @param context
     * @param titleId
     * @param contentId
     * @param positive
     */
    public static void title(Context context, int titleId, int contentId, String positive) {
        String title = getString(context, titleId);
        String content = getString(context, contentId);
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(title, content, positive, null, null, true, null);
        String tag = CommonDialogFragment.class.getSimpleName() + "_" + "title";
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建带标题和内容的dialog
     * @param context
     * @param title
     * @param content
     * @param positive
     * @param tag
     */
    public static void title(Context context, String title, String content, String positive, String tag) {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(title, content, positive, null, null, true, null);
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建标准的对话框
     * @param context
     * @param title
     * @param content
     * @param positive
     * @param negative
     * @param onDialogListener
     */
    public static void standard(Context context, String title, String content, String positive, String negative, DialogFragmentListener onDialogListener) {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(title, content, positive, negative, null, true, onDialogListener);
        String tag = CommonDialogFragment.class.getSimpleName() + "_" + "standard";
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建标准的对话框
     * @param context
     * @param titleId
     * @param contentId
     * @param positiveId
     * @param negativeId
     * @param onDialogListener
     */
    public static void standard(Context context, int titleId, int contentId, int positiveId, int negativeId, DialogFragmentListener onDialogListener) {
        String title = getString(context, titleId);
        String content = getString(context, contentId);
        String positive = getString(context, positiveId);
        String negative = getString(context, negativeId);
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(title, content, positive, negative, null, true, onDialogListener);
        String tag = CommonDialogFragment.class.getSimpleName() + "_" + "standard";
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 创建标准的对话框
     * @param context
     * @param title
     * @param content
     * @param positive
     * @param negative
     * @param onDialogListener
     * @param tag
     */
    public static void standard(Context context, String title, String content, String positive, String negative, DialogFragmentListener onDialogListener, String tag) {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(title, content, positive, negative, null, true, onDialogListener);
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    private static String getString(Context context, int stringId){
        if (stringId == 0){
            return  null;
        } else {
            return context.getString(stringId);
        }
    }
}
