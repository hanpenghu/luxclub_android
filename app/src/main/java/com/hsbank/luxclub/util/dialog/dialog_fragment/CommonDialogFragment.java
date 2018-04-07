package com.hsbank.luxclub.util.dialog.dialog_fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.hsbank.luxclub.util.dialog.listener.DialogFragmentListener;


/**
 * Author:      chen_liuchun
 * Date:        2016/6/14
 * Description: 系统DialogFragment的标准封装
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class CommonDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    // bundle取值键
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String POSITIV_STR = "positive_str";
    private static final String NEGATIVE_STR = "negative_str";
    private static final String NEUTRAL_STR = "neutral_str";
    private static final String CANCELABLE = "cancelable";

    private static DialogFragmentListener sDialogFragmentListener;

    /**
     * 创建实例方法
     * @param title
     * @param message
     * @param positive_str
     * @param negative_str
     * @param neutral_str
     * @param cancelable
     * @param onDialogListener
     * @return 对话框实例
     */
    public static CommonDialogFragment newInstance(
            CharSequence title, CharSequence message,
            CharSequence positive_str, CharSequence negative_str, CharSequence neutral_str,
            boolean cancelable, DialogFragmentListener onDialogListener) {
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(TITLE, title);
        bundle.putCharSequence(MESSAGE, message);
        bundle.putCharSequence(POSITIV_STR, positive_str);
        bundle.putCharSequence(NEGATIVE_STR, negative_str);
        bundle.putCharSequence(NEUTRAL_STR, neutral_str);
        bundle.putBoolean(CANCELABLE, cancelable);
        sDialogFragmentListener = onDialogListener;
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence title = getArguments().getCharSequence(TITLE);
        CharSequence message = getArguments().getCharSequence(MESSAGE);
        CharSequence positive_str = getArguments().getCharSequence(POSITIV_STR);
        CharSequence negative_str = getArguments().getCharSequence(NEGATIVE_STR);
        CharSequence neutral_str = getArguments().getCharSequence(NEUTRAL_STR);
        boolean cancelable = getArguments().getBoolean(CANCELABLE, true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive_str, this)
                .setNegativeButton(negative_str, this)
                .setNeutralButton(neutral_str, this)
                .setCancelable(cancelable);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (sDialogFragmentListener == null)  return;
        sDialogFragmentListener.onDialogClick(dialog, which);
    }
}
