package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.AlbumDetailBean;
import com.hsbank.luxclub.provider.apis.SiteInfoApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

/**
 * 店家相册查看页面
 * Created by chen_liuchun on 2016/3/26.
 */
public class RoomAlbumActivity extends ProjectBaseActivity {
    public static final String KEY_ALBUM_ID = "key_album_id";
    private ArrayList<String> imageUrls;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_album;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarTitle("");

        long albumId = getIntent().getLongExtra(KEY_ALBUM_ID, 0);
        if (albumId == 0) return;

        RetrofitHelper.getInstance()
                .create(SiteInfoApis.class, true)
                .albumDetails(albumId)
                .compose(RxUtil.<AlbumDetailBean>compose(this))
                .subscribe(new APISubscriber<AlbumDetailBean>() {
                    @Override
                    public void onNext(AlbumDetailBean albumDetailBean) {
                        updateUI(albumDetailBean);
                    }
                });
    }

    private void updateUI(AlbumDetailBean albumDetailBean) {
        setToolbarTitle(albumDetailBean.getAlbumTitle());

        GridView gridView = mViewHelper.getView(R.id.gridview);
        imageUrls = albumDetailBean.getImageUrl();
        CommonAdapter<String> adapter = new CommonAdapter<String>(this, R.layout.item_gridview_img, imageUrls) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {
                ImageView img = holder.getView(R.id.img);
                ImageLoader.getInstance().displayImage(s, img, Constants.networkOptions);
            }
        };
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumActivity.show(mContext, imageUrls, position);
            }
        });
    }

    public static void show(Context context, long albumId) {
        Intent intent = new Intent(context, RoomAlbumActivity.class);
        intent.putExtra(KEY_ALBUM_ID, albumId);
        context.startActivity(intent);
    }
}
