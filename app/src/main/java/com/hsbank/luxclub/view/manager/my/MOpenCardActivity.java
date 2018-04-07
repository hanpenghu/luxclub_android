package com.hsbank.luxclub.view.manager.my;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.CardOpenBean;
import com.hsbank.luxclub.model.ServantItemBean;
import com.hsbank.luxclub.mywidget.flowlayout.FlowLayout;
import com.hsbank.luxclub.mywidget.flowlayout.TagAdapter;
import com.hsbank.luxclub.mywidget.flowlayout.TagFlowLayout;
import com.hsbank.luxclub.provider.apis.ActivateCardApis;
import com.hsbank.luxclub.provider.apis.ServantApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.ErrorTipBean;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.tool.ToastUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.java.collection.ListUtil;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Author:      chenliuchun
 * Date:        17/5/19
 * Description: 推荐开卡
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class MOpenCardActivity extends ProjectBaseActivity implements View.OnClickListener {

    private CardOpenBean cardBean;
    // 移动管家列表
    private List<ServantItemBean> servantList;

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MOpenCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_open_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.txt_recommend_card));
        mViewHelper.setOnClickListener(R.id.btn_confirm, this);
        mViewHelper.setOnClickListener(R.id.edt_member_birthday, this);

        EditText edt_member_birthday = mViewHelper.getView(R.id.edt_member_birthday);
        edt_member_birthday.setInputType(InputType.TYPE_NULL); // 禁止弹出软键盘
        // 点击弹出日期选择框
        edt_member_birthday.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && v.getId() == R.id.edt_member_birthday) {
                    new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            String birthday = DateFormatUtils.ISO_DATE_FORMAT.format(calendar);
                            mViewHelper.setText(R.id.edt_member_birthday, birthday);
                        }
                    }, 1985, 0, 1).show();

                }
                return false;
            }
        });

        RetrofitHelper.getInstance().create(ActivateCardApis.class, true).recommend(ProjectUtil.getManagerToken()).compose(RxUtil.<CardOpenBean>compose(this)).subscribe(new APISubscriber<CardOpenBean>() {
            @Override
            public void onNext(CardOpenBean cardOpenBean) {
                cardBean = cardOpenBean;
                updateUi(cardOpenBean);
            }
        });

        // 获取移动管家列表
        RetrofitHelper.getInstance().create(ServantApis.class, true).list(ProjectUtil.getManagerToken()).compose(RxUtil.<List<ServantItemBean>>compose(this)).subscribe(new APISubscriber<List<ServantItemBean>>() {
            @Override
            public void onNext(List<ServantItemBean> list) {
                ServantItemBean bean = new ServantItemBean("", "请选择移动管家");
                list.add(0, bean);
                updateServantList(list);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 0x01, 0, R.string.txt_open_card_record);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0x01:
                MOpenCardListActivity.show(this, JsonUtil.toJson(cardBean.getCardRuleList()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 更新移动管家 Spinner 可选数据
     */
    private void updateServantList(List<ServantItemBean> list) {
        List<String> servantNameList = parseServantList(list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, servantNameList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spr_servant = mViewHelper.getView(R.id.spr_servant);
        spr_servant.setAdapter(arrayAdapter);
    }

    private void updateUi(final CardOpenBean cardBean) {
        Spinner spr_rule_name = mViewHelper.getView(R.id.spr_rule_name);
        Spinner spr_steward = mViewHelper.getView(R.id.spr_steward);
        final TagFlowLayout tagflow_interest = mViewHelper.getView(R.id.tagflow_interest);

        // 开卡日期
        String date = DateFormatUtils.ISO_DATE_FORMAT.format(Calendar.getInstance());
        mViewHelper.setText(R.id.txt_open_date, date);

        // 更新售卡规则 Spinner 可选数据
        final List<CardOpenBean.CardRuleListBean> cardRuleList = cardBean.getCardRuleList();
        List<String> ruleList = parseRuleList(cardRuleList);
        ArrayAdapter<String> ruleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ruleList);
        ruleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spr_rule_name.setAdapter(ruleAdapter);
        spr_rule_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewHelper.setText(R.id.txt_card_amount, cardRuleList.get(position).getAmount());
                mViewHelper.setText(R.id.txt_actual_amount, cardRuleList.get(position).getActualAmount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 更新管家 Spinner 可选数据
        List<CardOpenBean.StewardListBean> stewardList = cardBean.getStewardList();
        List<String> stewardNameList = parseStewardList(stewardList);
        ArrayAdapter<String> stewardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stewardNameList);
        stewardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spr_steward.setAdapter(stewardAdapter);

        // 更新兴趣选择项
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        tagflow_interest.setAdapter(new TagAdapter<String>(parseInterestList(cardBean.getInterestList())) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) inflater.inflate(R.layout.item_flow_textview_narrow, tagflow_interest, false);
                textView.setText(s);
                return textView;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                if (verifyData()) {
                    // 获取选中的售卡规则和管家
                    Spinner spr_rule_name = mViewHelper.getView(R.id.spr_rule_name);
                    int rulePosition = spr_rule_name.getSelectedItemPosition();
                    int ruleId = cardBean.getCardRuleList().get(rulePosition).getRuleId();
                    Spinner spr_steward = mViewHelper.getView(R.id.spr_steward);
                    int stewardPosition = spr_steward.getSelectedItemPosition();
                    String stewardId = cardBean.getStewardList().get(stewardPosition).getStewardId();

                    Spinner spr_servant = mViewHelper.getView(R.id.spr_servant);
                    int servantPosition = spr_servant.getSelectedItemPosition();
                    String servantId = servantList.get(servantPosition).getStewardId();

                    String memberName = mViewHelper.getText(R.id.edt_member_name);
                    String phoneNum = mViewHelper.getText(R.id.edt_phone_num);
                    String memberBirthday = mViewHelper.getText(R.id.edt_member_birthday);
                    String remarks = mViewHelper.getText(R.id.edt_remark);

                    String interestIds = getInterestIds();

                    // 提交开卡信息
                    RetrofitHelper.getInstance()
                            .create(ActivateCardApis.class, true)
                            .confirm(ProjectUtil.getManagerToken(), ruleId,
                                    memberName, memberBirthday, phoneNum,
                                    stewardId, servantId, interestIds, remarks)
                            .compose(RxUtil.<String>compose(this))
                            .subscribe(new APISubscriber<String>() {

                                @Override
                                public void onNext(String cardNum) {
                                    ToastUtil.show("开卡成功： " + cardNum);
                                    finish();
                                    MOpenCardListActivity.show(mContext, JsonUtil.toJson(cardBean.getCardRuleList()));
                                }

                                @Override
                                public void onCustomError(ErrorTipBean errorTip) {
                                    if (errorTip.isCustomError()) {
                                        ToastUtil.show("卡号已售空");
                                    }
                                }
                    });
                }
                break;
        }
    }

    /**
     * 解析返回售卡规则列表
     *
     * @param ruleBean
     * @return
     */
    private List<String> parseRuleList(List<CardOpenBean.CardRuleListBean> ruleBean) {
        List<String> list = new ArrayList<>();
        for (CardOpenBean.CardRuleListBean bean : ruleBean) {
            list.add(bean.getRuleName());
        }
        return list;
    }

    /**
     * 解析返回移动管家列表
     *
     * @param stewardBean
     * @return
     */
    private List<String> parseServantList(List<ServantItemBean> stewardBean) {
        List<String> list = new ArrayList<>();
        for (ServantItemBean bean : stewardBean) {
            list.add(bean.getStewardName());
        }
        return list;
    }

    /**
     * 解析返回管家列表
     *
     * @param stewardBean
     * @return
     */
    private List<String> parseStewardList(List<CardOpenBean.StewardListBean> stewardBean) {
        List<String> list = new ArrayList<>();
        for (CardOpenBean.StewardListBean bean : stewardBean) {
            list.add(bean.getStewardName());
        }
        return list;
    }

    /**
     * 解析返回兴趣列表
     *
     * @param interestBean
     * @return
     */
    private List<String> parseInterestList(List<CardOpenBean.InterestListBean> interestBean) {
        List<String> list = new ArrayList<>();
        for (CardOpenBean.InterestListBean bean : interestBean) {
            list.add(bean.getInterestName());
        }
        return list;
    }

    /**
     * 校验输入项是否合法
     *
     * @return
     */
    private boolean verifyData() {
        String memberName = mViewHelper.getText(R.id.edt_member_name);
        String phoneNum = mViewHelper.getText(R.id.edt_phone_num);

        if (TextUtils.isEmpty(memberName)) {
            ToastUtil.show("请输入会员姓名");
            return false;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.show("请输入预留手机号");
            return false;
        }
        return true;
    }

    /**
     * 获取选取的兴趣 ID
     *
     * @return
     */
    private String getInterestIds() {
        TagFlowLayout tagflow_interest = mViewHelper.getView(R.id.tagflow_interest);
        Set<Integer> selectedList = tagflow_interest.getSelectedList();
        List<String> interestList = new ArrayList<>();
        for (Integer index : selectedList) {
            interestList.add(cardBean.getInterestList().get(index).getInterestId());
        }
        return ListUtil.listToString(interestList, ",");
    }
}
