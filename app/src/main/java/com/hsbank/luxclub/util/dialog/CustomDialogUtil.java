package com.hsbank.luxclub.util.dialog;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hsbank.luxclub.util.dialog.dialog_fragment.PromotionDialogFragment;
import com.hsbank.luxclub.util.dialog.dialog_fragment.UpdateDialogFragment;
import com.hsbank.luxclub.util.dialog.listener.DialogFragmentListener;

/**
 * Author:      chen_liuchun
 * Date:        2016/4/22
 * Description: 非标准对话框工具类，标准对话框见
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class CustomDialogUtil extends BaseDialogUtil{

    /**
     * 用于app更新
     * @param context 当前活动页面
     * @param type 对话框类型 TYPE_UPDATE->普通更新，TYPE_FORCE->强制更新, TYPE_UNNEED->无需更新
     * @param content 更新内容
     */
    public static void showUpdateDialog(Context context, int type, String content, DialogFragmentListener onDialogListener) {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        DialogFragment dialogFragment = UpdateDialogFragment.newInstance(type, content, onDialogListener);
        String tag = UpdateDialogFragment.class.getSimpleName();
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

    /**
     * 用于活动入口
     *
     * @param context 当前活动页面
     * @param url     活动图片地址
     */
    public static void showPromotionDialog(Context context, String url, DialogFragmentListener onDialogListener) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        DialogFragment dialogFragment = PromotionDialogFragment.newInstance(url, onDialogListener);
        String tag = PromotionDialogFragment.class.getSimpleName();
        dismiss(context, tag);
        dialogFragment.show(ft, tag);
        mTags.add(tag);
    }

}
