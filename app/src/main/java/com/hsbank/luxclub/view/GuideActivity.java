package com.hsbank.luxclub.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.luxclub.view.main.my.BindCardActivity;
import com.hsbank.util.android.util.SharedPreferencesUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import static android.view.WindowManager.LayoutParams;

/**
 * Author:      chen_liuchun
 * Date:        2017/5/15
 * Description: APP引导（欢迎）页面
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class GuideActivity extends ProjectBaseActivity implements View.OnClickListener {

    private static final int[] GUIDE_IMGS = new int[]{
            R.drawable.img_guide_01,
            R.drawable.img_guide_02,
            R.drawable.img_guide_03};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //<1>.设置窗体全屏
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        //<2>.视图元素处理器
        viewHandler();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    /**
     * 视图元素处理器
     */
    private void viewHandler() {
        final ViewPager viewpager = mViewHelper.getView(R.id.viewpager_guide);
        MagicIndicator indicator = mViewHelper.getView(R.id.indicator_guide);
        viewpager.setAdapter(pagerAdapter);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.guide_translate);
        // 引导页第一页的动画
        viewpager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    viewpager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    viewpager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                View img = viewpager.findViewById(R.id.img_ship);
                img.startAnimation(animation);
            }
        });
        // 引导页第二页动画
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        View img = viewpager.findViewById(R.id.img_plane);
                        img.startAnimation(animation);
                        break;
                }
            }
        });

        CircleNavigator circleNavigator = new CircleNavigator(this);
        circleNavigator.setCircleCount(GUIDE_IMGS.length);
        circleNavigator.setCircleColor(getResources().getColor(R.color.yellow_lux));
        indicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(indicator, viewpager);
    }

    // viewpager适配器
    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return GUIDE_IMGS.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup viewGroup = null;
            switch (position) {
                case 0:
                    viewGroup = (ViewGroup) LayoutInflater.from(getBaseContext()).inflate(R.layout.item_guide_1st, container, false);
                    break;
                case 1:
                    viewGroup = (ViewGroup) LayoutInflater.from(getBaseContext()).inflate(R.layout.item_guide_2nd, container, false);
                    break;
                case 2:
                    viewGroup = (ViewGroup) LayoutInflater.from(getBaseContext()).inflate(R.layout.item_guide_3rd, container, false);
                    viewGroup.findViewById(R.id.txt_bind).setOnClickListener(GuideActivity.this);
                    viewGroup.findViewById(R.id.txt_jump).setOnClickListener(GuideActivity.this);
                    break;
            }
            container.addView(viewGroup);
            return viewGroup;
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 跳过绑卡，直接进入APP
            case R.id.txt_bind:
                BindCardActivity.show(mContext);
                break;
            // 绑定会员卡
            case R.id.txt_jump:
                MainActivity.show(mContext, 0);
                break;
        }
        //引导页只显示一次
        SharedPreferencesUtil.getInstance().setBoolean(ProjectConstant.IS_FIRST_ENTER, false);
        finish();
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

}
