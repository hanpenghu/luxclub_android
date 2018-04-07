package com.hsbank.luxclub.util.dialog.dialog_fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.dialog.listener.DialogFragmentListener;
import com.hsbank.util.android.util.AlertUtil;


/**
 * Author:      chen_liuchun
 * Date:        2016/6/16
 * Description: 自定义对话框，用于应用更新
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class UpdateDialogFragment extends DialogFragment implements View.OnClickListener,DialogInterface.OnKeyListener {

    public static final String TYPE = "type";
    public static final String CONTENT = "content";
    // 弹窗类型
    public static final int TYPE_UPDATE = 0x11;
    public static final int TYPE_FORCE = 0x12;
    public static final int TYPE_UNNEED = 0x13;

    private static DialogFragmentListener sDialogFragmentListener;

    /**
     * 创建实例方法
     * @param type 对话框类型 TYPE_UPDATE->普通更新，TYPE_FORCE->强制更新, TYPE_UNNEED->无需更新
     * @param content 更新内容
     * @param onDialogListener 对话框回调接口
     * @return 对话框实例
     */
    public static UpdateDialogFragment newInstance(int type, String content, DialogFragmentListener onDialogListener) {
        UpdateDialogFragment dialogFragment = new UpdateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        bundle.putString(CONTENT, content);
        sDialogFragmentListener = onDialogListener;
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int type = getArguments().getInt(TYPE);
        String content = getArguments().getString(CONTENT);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 无默认标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 无任何背景
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(this);

        View view = inflater.inflate(R.layout.dialog_update, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        Button btn_update = (Button) view.findViewById(R.id.btn_right);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_left);
        Button btn_middle = (Button) view.findViewById(R.id.btn_middle);

        txt_content.setText(content);

        switch (type) {
            case TYPE_UPDATE:
                btn_update.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_middle.setVisibility(View.GONE);
                btn_update.setOnClickListener(this);
                btn_cancel.setOnClickListener(this);
                break;
            case TYPE_UNNEED:
                btn_update.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                btn_middle.setVisibility(View.VISIBLE);
                btn_middle.setText("我知道了");
                btn_middle.setOnClickListener(this);
                break;
            case TYPE_FORCE:
                btn_update.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                btn_middle.setVisibility(View.VISIBLE);
                btn_middle.setText("立即更新");
                btn_middle.setOnClickListener(this);
                break;
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        int type = getArguments().getInt(TYPE);
        if (sDialogFragmentListener == null) return;
        switch (v.getId()) {
            case R.id.btn_right:
                sDialogFragmentListener.onDialogClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
                getDialog().cancel();
                break;
            case R.id.btn_left:
                sDialogFragmentListener.onDialogClick(getDialog(), DialogInterface.BUTTON_NEGATIVE);
                getDialog().cancel();
                break;
            case R.id.btn_middle:
                if (type == TYPE_FORCE){
                    sDialogFragmentListener.onDialogClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
                }else if (type == TYPE_UNNEED){
                    sDialogFragmentListener.onDialogClick(getDialog(), DialogInterface.BUTTON_NEGATIVE);
                    getDialog().cancel();
                }
                break;
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        int type = getArguments().getInt(TYPE);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (type == TYPE_FORCE){
                AlertUtil.getInstance().alertMessage(getActivity(), "请立即更新应用版本");
                return true;
            }
        }
        return false;
    }
}
