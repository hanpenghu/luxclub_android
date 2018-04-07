package com.hsbank.luxclub.util.dialog.dialog_fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


/**
 * Author:      chen_liuchun
 * Date:        2017-02-14
 * Description: 标准列表对话框
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class ListDialogFragment extends DialogFragment {

    // bundle取值键
    private static final String TITLE = "title";
    private static final String ARRAY = "array";

    private static DialogInterface.OnClickListener mClickListener;

    /**
     * 创建实例方法
     * @param title
     * @param onClickListener
     * @return 对话框实例
     */
    public static ListDialogFragment newInstance(
            CharSequence title, String[] array, DialogInterface.OnClickListener onClickListener) {
        ListDialogFragment listDialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(TITLE, title);
        bundle.putStringArray(ARRAY, array);
        mClickListener = onClickListener;
        listDialogFragment.setArguments(bundle);
        return listDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence title = getArguments().getCharSequence(TITLE);
        String[] array = getArguments().getStringArray(ARRAY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setItems(array, mClickListener);
        return builder.create();
    }

}
