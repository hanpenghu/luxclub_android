package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.ModelDetialBean;
import com.hsbank.luxclub.provider.apis.ModelApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * 模特详情页面
 * Created by chen_liuchun on 2016/3/15.
 */
public class ModelDetailActivity extends ProjectBaseActivity {

    public static final String MODEL_ID = "model_id";
    private List<String> modelAlbum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_model_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    protected void viewHandler() {
        setToolbarTitle(R.string.txt_model);
        String modelId = getIntent().getStringExtra(MODEL_ID);

        RetrofitHelper.getInstance()
                .create(ModelApis.class, true)
                .details(modelId)
                .compose(RxUtil.<ModelDetialBean>compose(this))
                .subscribe(new APISubscriber<ModelDetialBean>() {
                    @Override
                    public void onNext(ModelDetialBean modelDetialBean) {
                        updateUI(modelDetialBean);
                    }
                });
    }

    private void updateUI(ModelDetialBean modelDetial) {
        setToolbarTitle(modelDetial.getNickname());
        modelAlbum = modelDetial.getModelAlbum();
        ViewPager viewPager = mViewHelper.getView(R.id.viewpager);
        CircleIndicator circleIndicator = mViewHelper.getView(R.id.indicator);
        viewPager.setAdapter(pagerAdapter);
        circleIndicator.setViewPager(viewPager);

        mViewHelper
                .setText(R.id.txt_name, modelDetial.getNickname())
                .setText(R.id.txt_age, modelDetial.getAge() + getString(R.string.txt_year_age))
                .setText(R.id.txt_country, modelDetial.getCountry())
                .setText(R.id.txt_height, getString(R.string.txt_height) + modelDetial.getHeight())
                .setText(R.id.txt_weight, getString(R.string.txt_weight) + modelDetial.getWeight())
                .setText(R.id.txt_bwh, getString(R.string.txt_bwh) + modelDetial.getMeasurements())
                .setText(R.id.txt_speciality, getString(R.string.txt_speciality) + modelDetial.getSpeciality());
    }

    // viewpager适配器
    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return modelAlbum.size();
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
            ImageView imageview = new ImageView(getBaseContext());
            ImageLoader.getInstance().displayImage(modelAlbum.get(position), imageview, Constants.networkOptions);
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageview);
            return imageview;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order:
                ModelOrderActivity.show(this, getIntent().getStringExtra(MODEL_ID));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *  打开此场所详情页面
     * @param context
     * @param modelId
     */
    public static void show(Context context, String modelId) {
        Intent intent = new Intent();
        intent.putExtra(MODEL_ID, modelId);
        intent.setClass(context, ModelDetailActivity.class);
        context.startActivity(intent);
    }
}
