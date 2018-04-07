package com.hsbank.luxclub.util.dialog.dialog_fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.RoundImageView;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.util.dialog.listener.DialogFragmentListener;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Author:      chenliuchun
 * Date:        17/2/23
 * Description: 自定义对话框，用于活动入口
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class PromotionDialogFragment extends DialogFragment implements View.OnClickListener{

    public static final String IMAGE_URL = "image_url";
    private static DialogFragmentListener mOnDialogListener;

    /**
     * 创建实例方法
     * @param url 图片地址
     * @param onDialogListener 对话框回调接口
     * @return 对话框实例
     */
    public static PromotionDialogFragment newInstance(String url, DialogFragmentListener onDialogListener) {
        PromotionDialogFragment dialogFragment = new PromotionDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL, url);
        mOnDialogListener = onDialogListener;
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String image_url = getArguments().getString(IMAGE_URL);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 无默认标题
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 无任何背景
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.dialog_promotion, container, false);
        RoundImageView img = (RoundImageView) view.findViewById(R.id.img);
        view.findViewById(R.id.img).setOnClickListener(this);
        view.findViewById(R.id.img_close).setOnClickListener(this);

        ImageLoader.getInstance().displayImage(image_url, img, Constants.networkOptions);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (mOnDialogListener == null) return;
        switch (v.getId()) {
            case R.id.img:
                mOnDialogListener.onDialogClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
                getDialog().dismiss();
                break;
            case R.id.img_close:
                mOnDialogListener.onDialogClick(getDialog(), DialogInterface.BUTTON_NEGATIVE);
                break;
        }
    }

}
