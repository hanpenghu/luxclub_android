package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.constants.GlobalData;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.java.collection.MapUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 我的零钱包页面
 * name：zhuzhenghua
 * create time:2016.4.12
 */
public class CoinPurseActivity extends ProjectBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_purse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    /**
     * 控件初始化
     */
    private void viewHandler() {
        setToolbarTitle(R.string.txt_coin);
        mViewHelper.getView(R.id.setitem_consumption).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.setitem_recharge).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.profit_View).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.wallet_profit).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.annualized_rates).setOnClickListener(clickListener);
        Map<String, Object> MyData = GlobalData.getInstance().getMyData();
        String walletBalance = MapUtil.getString(MyData, "walletBalance");
        if (walletBalance == null || walletBalance.equals("")) {
            walletBalance = "0";
        }
        BigDecimal priceBigDecimal = new BigDecimal(walletBalance);
        String walletBalanceStr = String.format("%1$,.2f",
                priceBigDecimal.doubleValue());
        mViewHelper.setText(R.id.txt_wallet_last_profit, MapUtil.getString(MyData,"walletLastProfit"));
        mViewHelper.setText(R.id.txt_wallet_balance, String.valueOf(walletBalanceStr));
        mViewHelper.setText(R.id.txt_annualized_rates, MapUtil.getString(MyData,"annualizedRates"));
        mViewHelper.setText(R.id.txt_wallet_profit, MapUtil.getString(MyData,"walletProfit"));
    }

    /**
     * 界面所有控件的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.setitem_consumption:
                    CoinPurseRecordsHistory.show(mContext);
                    break;
                case R.id.setitem_recharge:
                    CoinPurseRechargeHistory.show(mContext);
                    break;
                case R.id.profit_View:
                case R.id.wallet_profit:
                    CoinPurseProfitActivity.show(mContext);
                    break;
                case R.id.annualized_rates:
                    View parentView = mViewHelper.getView(R.id.parentView);
                    MyPopupWindow.showRechargeDiscount(mContext, parentView, getString(R.string.txt_annualized_rates_explain), "会员的零钱按照年化3.68%进行计息，" +
                            " 按照年息/365按天计算。利息每天晚上12点进行结算，以结算当时会员零钱包中的金额为基数，按照日息计算， 利息结算完成后自动加入用户的零钱包中（零钱包只能用于小费）");
                    break;
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, CoinPurseActivity.class);
        context.startActivity(intent);
    }
}
