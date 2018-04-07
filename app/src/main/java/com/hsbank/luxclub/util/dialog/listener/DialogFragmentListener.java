package com.hsbank.luxclub.util.dialog.listener;

import android.content.DialogInterface;

import java.io.Serializable;

/**
 * Author:      chen_liuchun
 * Date:        2016/6/16
 * Description: 自定义DialogFragment操作的接口回调
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public interface DialogFragmentListener extends Serializable {
    void onDialogClick(DialogInterface dialog, int which);
}
