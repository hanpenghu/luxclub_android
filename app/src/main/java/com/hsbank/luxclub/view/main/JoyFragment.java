package com.hsbank.luxclub.view.main;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.JoyPageBean;
import com.hsbank.luxclub.mywidget.flowlayout.FlowLayout;
import com.hsbank.luxclub.mywidget.flowlayout.TagAdapter;
import com.hsbank.luxclub.mywidget.flowlayout.TagFlowLayout;
import com.hsbank.luxclub.mywidget.recyclerview_pager.LayoutAdapter;
import com.hsbank.luxclub.mywidget.recyclerview_pager.RecyclerViewPager;
import com.hsbank.luxclub.mywidget.recyclerview_pager.TabLayoutSupport;
import com.hsbank.luxclub.provider.apis.EventApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.UIUtil;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.hsbank.luxclub.view.main.joy.JoyListActivity;
import com.hsbank.luxclub.view.main.joy.ModelListActivity;
import com.hsbank.util.project.util.BaseProjectApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen_liuchun
 * on 2016/3/15.
 */
public class JoyFragment extends ProjectBaseFragment {

    private FragmentActivity mActivity;
    private RecyclerViewPager mRecyclerView;
    private List<JoyPageBean> mPagerDatas;
    private LayoutAdapter layoutAdapter;
    private TabLayout tabLayout;
    private CheckBox ckb_tab;
    private PopupWindow popupWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joy;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 视图元素处理器
        viewHandler();

        RetrofitHelper.getInstance()
                .create(EventApis.class, false)
                .getCarouselList(BaseProjectApi.getInstance().getClient())
                .compose(RxUtil.<List<JoyPageBean>>compose(mContext))
                .subscribe(new APISubscriber<List<JoyPageBean>>() {
                    @Override
                    public void onNext(List<JoyPageBean> joyPageList) {
                        mPagerDatas = joyPageList;
                        layoutAdapter.setmDatas(joyPageList);
//                        initPopup();
                        // 关联recyclerview和tabLayout
                        tabLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                // fix: SupportLibrary bug：tablayout indicator初始化不显示
                                TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, layoutAdapter);
                            }
                        });

                    }
                });
    }

    private void viewHandler() {
        mRecyclerView = mViewHelper.getView(R.id.recyclerview_pager);
        tabLayout = mViewHelper.getView(R.id.tabLayout);

        // 设置recyclerview pager
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layout);

        layoutAdapter = new LayoutAdapter(getActivity());
        layoutAdapter.setOnItemClickLitener(itemClickLitener);

        mRecyclerView.setAdapter(layoutAdapter);
        mRecyclerView.setHasFixedSize(true); // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能

//        ckb_tab = mViewHelper.getView(R.id.ckb_tab);
//        ckb_tab.setOnCheckedChangeListener(checkedChangeListener);
    }

    /**
     * 初始化 tab popupwindow
     */
    private void initPopup(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_main_tab, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ckb_tab.setChecked(false);
            }
        });

        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        final TagFlowLayout tagflow = (TagFlowLayout) view.findViewById(R.id.tagflow);
        List<String> list = new ArrayList<>();
        for (JoyPageBean pageBean : mPagerDatas) {
            list.add(pageBean.getTitle());
        }
        tagflow.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) inflater.inflate(R.layout.item_flow_textview, tagflow, false);
                textView.setText(s);
                return textView;
            }

//            @Override
//            public boolean setSelected(int position, String s) {
//                return position == 0;
//            }
        });
        tagflow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                tabLayout.getTabAt(position).select();
                return true;
            }
        });
    }

    /**
     * 页面点击事件监听器
     */
    private LayoutAdapter.OnItemClickLitener itemClickLitener = new LayoutAdapter.OnItemClickLitener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mPagerDatas !=null) {
                JoyPageBean joyPageBean = mPagerDatas.get(position);
                if (joyPageBean.getSiteType() == 7) {
                    ModelListActivity.show(mContext,joyPageBean.getTitle());
                } else {
                    JoyListActivity.show(mActivity, joyPageBean.getSiteType(), joyPageBean.getTitle());
                }
            }
        }
    };

    /**
     * tab 弹窗开关监听器
     */
     private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (isChecked){
                int dy = tabLayout.getHeight()*2 - UIUtil.getStatusBarHeight(mContext);
                int position = tabLayout.getSelectedTabPosition();

                TagFlowLayout tagflow = (TagFlowLayout) popupWindow.getContentView().findViewById(R.id.tagflow);
                tagflow.getAdapter().setSelectedList(position);
                popupWindow.showAtLocation(mViewHelper.getContentView(), Gravity.TOP, 0, dy);
            }
        }
    };


}
