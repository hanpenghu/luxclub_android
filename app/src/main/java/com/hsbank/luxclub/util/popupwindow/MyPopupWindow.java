package com.hsbank.luxclub.util.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.view.main.my.event.OrderFunction;
import com.hsbank.luxclub.util.ImageUtil;

/**
 * name：zhuzhenghua
 * create time:2015.11.19
 */
public class MyPopupWindow {
    /**
     * 提示框：有交互功能
     */
    public static PopupWindow popupWindow = null;

    /**
     * 普通界面提示
     *
     * @param context
     * @param parentView
     * @param txtTitle
     * @param txtContext
     * @param txtConfirm
     * @param myPopupWindowFunction
     */
    public static void showConfirmWin(Context context, View parentView,
                                      String txtTitle, String txtContext, String txtConfirm,
                                      final PopupWindowFunction myPopupWindowFunction) {
        closePopup();
        LayoutInflater mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup viewConfirmAlert = (ViewGroup) mLayoutInflater.inflate(
                R.layout.popupwindow_confirm_alert, null, true);
        popupWindow = new PopupWindow(
                viewConfirmAlert, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);

        popupWindow.setFocusable(true);// 设置PopupWindow可获得焦点
        popupWindow.setOutsideTouchable(true);// 设置外部区域可触摸
        popupWindow.setTouchable(true); //设置PopupWindow可触摸
        //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        TextView txtConfirmTitle = (TextView) viewConfirmAlert
                .findViewById(R.id.txtConfirmTitle);
        TextView txtConfirmContext = (TextView) viewConfirmAlert
                .findViewById(R.id.txtConfirmContext);
        Button btnCancel = (Button) viewConfirmAlert
                .findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) viewConfirmAlert
                .findViewById(R.id.btnConfirm);
        if (txtTitle.equals("")) {
            txtConfirmTitle.setVisibility(View.GONE);
        }
        txtConfirmTitle.setText(txtTitle);
        txtConfirmContext.setText(txtContext);
        btnConfirm.setText(txtConfirm);
        viewConfirmAlert.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePopup();
                }
                return false;
            }
        });
        viewConfirmAlert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closePopup();
                return false;
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopup();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopup();
                if (myPopupWindowFunction != null) {
                    myPopupWindowFunction.popupWinFunction("");
                }
            }
        });
        try {
            popupWindow
                    .setAnimationStyle(R.style.anim_wait_window);
            popupWindow.showAtLocation(parentView,
                    Gravity.CENTER, 0, 0);
            popupWindow.update();
        } catch (Exception e) {
        }
    }

    /**
     * 解绑卡弹框
     *
     * @param context
     * @param parentView
     * @param str1                  标题
     * @param str2                  内容
     * @param myPopupWindowFunction 解绑卡回调
     */
    public static void showUnbindCard(final Context context, View parentView, String str1, String str2, final PopupWindowFunction myPopupWindowFunction) {
        closePopup();
        LayoutInflater mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup viewImageChoose = (ViewGroup) mLayoutInflater.inflate(
                R.layout.popup_window_card_managemen, null, true);
        popupWindow = new PopupWindow(
                viewImageChoose, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupWindow
                .setBackgroundDrawable(new PaintDrawable(0));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final View viewLayout = viewImageChoose
                .findViewById(R.id.viewLayout);
        Button btnImageTake = (Button) viewImageChoose
                .findViewById(R.id.btnImageTake);
        btnImageTake.setText(str1);
        Button btnUnbindCard = (Button) viewImageChoose
                .findViewById(R.id.btnUnbindCard);
        btnUnbindCard.setText(str2);
        Button btnCancel = (Button) viewImageChoose
                .findViewById(R.id.btnCancel);
        viewLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popupwindow_visible));
        viewImageChoose.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    viewLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popupwindow_gone));
                    closePopup();
                }
                return false;
            }
        });
        btnImageTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnUnbindCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPopupWindowFunction != null) {
                    myPopupWindowFunction.popupWinFunction("");
                }
                closePopup();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLayout.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.popupwindow_gone));
                closePopup();
            }
        });
        try {
            popupWindow.showAtLocation(parentView,
                    Gravity.CENTER, 0, 0);
            popupWindow.update();
        } catch (Exception e) {
        }
    }

    /**
     * 充值优惠提示框
     *
     * @param context
     * @param parentView
     * @param str1       标题
     * @param str2       内容
     */
    public static void showRechargeDiscount(final Context context, View parentView, String str1, String str2) {
        closePopup();
        LayoutInflater mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup viewImageChoose = (ViewGroup) mLayoutInflater.inflate(
                R.layout.popup_window_recharge_discount, null, true);
        popupWindow = new PopupWindow(
                viewImageChoose, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupWindow
                .setBackgroundDrawable(new PaintDrawable(0));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final View viewLayout = viewImageChoose
                .findViewById(R.id.rly_recharge_discount);
        ImageView btnImageTake = (ImageView) viewImageChoose
                .findViewById(R.id.close);
        TextView title = (TextView) viewImageChoose
                .findViewById(R.id.title);
        title.setText(str1);
        TextView txt_context = (TextView) viewImageChoose
                .findViewById(R.id.txt_context);
        txt_context.setText(str2);
        viewLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popupwindow_visible));
        viewImageChoose.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    viewLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popupwindow_gone));
                    closePopup();
                }
                return false;
            }
        });
        btnImageTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopup();
            }
        });
        try {
            popupWindow.showAtLocation(parentView,
                    Gravity.CENTER, 0, 0);
            popupWindow.update();
        } catch (Exception e) {
        }
    }

    public static void closePopup() {
        try {
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                popupWindow = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 订单筛选
     *
     * @param context
     * @param parentView
     */
    public static void showFilterOrderState(final Context context, View parentView, final OrderFunction orderFunction, int state) {
        closePopup();
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup viewImageChoose = (ViewGroup) mLayoutInflater.inflate(R.layout.pop_filter_order_state, null, true);
        popupWindow = new PopupWindow(
                viewImageChoose, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupWindow
                .setBackgroundDrawable(new PaintDrawable(0));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final View viewLayout = viewImageChoose
                .findViewById(R.id.viewLayout);
        LinearLayout order_state1 = (LinearLayout) viewImageChoose
                .findViewById(R.id.order_state1);
        final TextView txt_customer_order_state1 = (TextView)viewImageChoose.findViewById(R.id.txt_customer_order_state1);
        final ImageView txt_is_check1 = (ImageView)viewImageChoose.findViewById(R.id.txt_is_check1);
        final TextView txt_customer_order_state2 = (TextView)viewImageChoose.findViewById(R.id.txt_customer_order_state2);
        final ImageView txt_is_check2 = (ImageView)viewImageChoose.findViewById(R.id.txt_is_check2);
        final TextView txt_customer_order_state3 = (TextView)viewImageChoose.findViewById(R.id.txt_customer_order_state3);
        final ImageView txt_is_check3 = (ImageView)viewImageChoose.findViewById(R.id.txt_is_check3);
        final TextView txt_customer_order_state4 = (TextView)viewImageChoose.findViewById(R.id.txt_customer_order_state4);
        final ImageView txt_is_check4 = (ImageView)viewImageChoose.findViewById(R.id.txt_is_check4);
        final TextView txt_customer_order_state5 = (TextView)viewImageChoose.findViewById(R.id.txt_customer_order_state5);
        final ImageView txt_is_check5 = (ImageView)viewImageChoose.findViewById(R.id.txt_is_check5);
        if(state!=-1){
            if(state==1){
                txt_customer_order_state1.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_is_check1.setBackgroundResource(R.drawable.icon_list_arrow_right);
            }else if(state==2){
                txt_customer_order_state2.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_is_check2.setBackgroundResource(R.drawable.icon_list_arrow_right);
            }else if(state==3){
                txt_customer_order_state3.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_is_check3.setBackgroundResource(R.drawable.icon_list_arrow_right);
            }else if(state==4){
                txt_customer_order_state4.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_is_check4.setBackgroundResource(R.drawable.icon_list_arrow_right);
            }else if(state==0){
                txt_customer_order_state5.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_is_check5.setBackgroundResource(R.drawable.icon_list_arrow_right);
            }
        }
        order_state1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_customer_order_state1.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_customer_order_state2.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state3.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state4.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state5.setTextColor(context.getResources().getColor(R.color.white));
                txt_is_check1.setBackgroundResource(R.drawable.icon_list_arrow_right);
                txt_is_check2.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check3.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check4.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check5.setBackgroundResource(R.drawable.setting_view_arrow);
                if (orderFunction != null) {
                    orderFunction.popupWinFunction(viewImageChoose, 1);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closePopup();
                    }
                }, 200);
            }
        });
        LinearLayout order_state2 = (LinearLayout) viewImageChoose
                .findViewById(R.id.order_state2);
        order_state2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_customer_order_state1.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state2.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_customer_order_state3.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state4.setTextColor(context.getResources().getColor(R.color.white));
                txt_is_check1.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check2.setBackgroundResource(R.drawable.icon_list_arrow_right);
                txt_is_check3.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check4.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check5.setBackgroundResource(R.drawable.setting_view_arrow);
                if (orderFunction != null) {
                    orderFunction.popupWinFunction(viewImageChoose, 2);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closePopup();
                    }
                }, 200);
            }
        });
        LinearLayout order_state3 = (LinearLayout) viewImageChoose
                .findViewById(R.id.order_state3);
        order_state3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_customer_order_state1.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state2.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state3.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_customer_order_state4.setTextColor(context.getResources().getColor(R.color.white));
                txt_is_check1.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check2.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check3.setBackgroundResource(R.drawable.icon_list_arrow_right);
                txt_is_check4.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check5.setBackgroundResource(R.drawable.setting_view_arrow);
                if (orderFunction != null) {
                    orderFunction.popupWinFunction(viewImageChoose, 3);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closePopup();
                    }
                }, 200);
            }
        });
        LinearLayout order_state4 = (LinearLayout) viewImageChoose
                .findViewById(R.id.order_state4);
        order_state4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_customer_order_state1.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state2.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state3.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state4.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_is_check1.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check2.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check3.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check4.setBackgroundResource(R.drawable.icon_list_arrow_right);
                txt_is_check5.setBackgroundResource(R.drawable.setting_view_arrow);
                if (orderFunction != null) {
                    orderFunction.popupWinFunction(viewImageChoose, 4);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closePopup();
                    }
                }, 200);
            }
        });
        LinearLayout order_state5 = (LinearLayout) viewImageChoose
                .findViewById(R.id.order_state5);
        order_state5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_customer_order_state1.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state2.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state3.setTextColor(context.getResources().getColor(R.color.white));
                txt_customer_order_state4.setTextColor(context.getResources().getColor(R.color.yellow_lux));
                txt_is_check1.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check2.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check3.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check4.setBackgroundResource(R.drawable.setting_view_arrow);
                txt_is_check5.setBackgroundResource(R.drawable.icon_list_arrow_right);
                if (orderFunction != null) {
                    orderFunction.popupWinFunction(viewImageChoose, 0);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closePopup();
                    }
                }, 200);
            }
        });
        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopup();
            }
        });
        viewLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popupwindow_visible_top));
        viewImageChoose.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    viewLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popupwindow_gone));
                    closePopup();
                }
                return false;
            }
        });
        try {
            popupWindow.showAtLocation(parentView,
                    Gravity.CENTER, 0,100);
            popupWindow.update();
        } catch (Exception e) {
        }
    }

    /**
     * 消费凭证选择
     *
     * @param context
     * @param parentView
     */
    public static void showImageChange(final Context context, View parentView) {
        closePopup();
        LayoutInflater mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup viewImageChoose = (ViewGroup) mLayoutInflater.inflate(
                R.layout.popup_window_image_change, null, true);
        popupWindow = new PopupWindow(
                viewImageChoose, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT, true);
        popupWindow
                .setBackgroundDrawable(new PaintDrawable(0));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final View viewLayout = viewImageChoose
                .findViewById(R.id.viewLayout);
        Button btn_image_take = (Button) viewImageChoose
                .findViewById(R.id.btn_image_take);
        Button btn_album_change = (Button) viewImageChoose
                .findViewById(R.id.btn_album_change);
        Button btnCancel = (Button) viewImageChoose
                .findViewById(R.id.btnCancel);
        viewLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popupwindow_visible));
        viewImageChoose.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    viewLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.popupwindow_gone));
                    closePopup();
                }
                return false;
            }
        });
        btn_image_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopup();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageUtil.imageChooseSwitch(context, ImageUtil.TAKE_BIG_PICTURE);
                    }
                }, 500);
            }
        });
        btn_album_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopup();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageUtil.imageChooseSwitch(context, ImageUtil.CHOOSE_BIG_PICTURE);
                    }
                }, 500);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLayout.startAnimation(AnimationUtils.loadAnimation(context,
                        R.anim.popupwindow_gone));
                closePopup();
            }
        });
        try {
            popupWindow.showAtLocation(parentView,
                    Gravity.CENTER, 0, 0);
            popupWindow.update();
        } catch (Exception e) {
        }
    }
}
