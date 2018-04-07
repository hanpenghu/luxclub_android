package com.hsbank.luxclub.view.servant;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.ImageAddAdapter2;
import com.hsbank.luxclub.model.UploadImageBean;
import com.hsbank.luxclub.mywidget.MyGridView;
import com.hsbank.luxclub.provider.apis.IndexApis;
import com.hsbank.luxclub.provider.apis.ServantApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ImageUtil;
import com.hsbank.luxclub.util.MediaUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.manager.order.ConsumeCredentialsActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.java.arithmetic.Base64;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

/**
 * 上传服务总结 补充总结
 * Created by chenliuchun on 2018/1/16.
 */

public class UploadSummaryActivity extends ProjectBaseActivity implements View.OnClickListener {

    private ArrayList<String> consumeImgNames;
    private ImageAddAdapter2 consumeAdapter;
    private static final String ORDER_ID = "order_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload_summary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("服务总结");

        MyGridView gridview_upload = mViewHelper.getView(R.id.gridview_upload);
//        mViewHelper.setOnClickListener(R.id.btn_cancel, this);
        mViewHelper.setOnClickListener(R.id.btn_confirm, this);

        consumeImgNames = new ArrayList<>();
        consumeAdapter = new ImageAddAdapter2(this);
        gridview_upload.setAdapter(consumeAdapter);
        gridview_upload.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position) == null) {
                    // 弹窗添加图片
                    addImageDialog();
                } else {
                    String orderId = getIntent().getStringExtra(ORDER_ID);
                    ConsumeCredentialsActivity.show(mContext, consumeAdapter.getDatas(),
                            position, "network", orderId);
                }
            }
        });

    }

    /**
     * 弹窗添加图片
     */
    private void addImageDialog() {
        String[] array = new String[]{"拍照", "从手机相册选择"};
        new AlertDialog.Builder(this)
                .setTitle("请选择图片添加来源")
                .setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // 拍照
                                ImageUtil.imageChooseSwitch(mContext, ImageUtil.TAKE_BIG_PICTURE);
                                break;
                            case 1:
                                // 相册选取
                                MediaUtil.takeAlbum(ImageUtil.CHOOSE_BIG_PICTURE, UploadSummaryActivity.this);
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImageUtil.TAKE_BIG_PICTURE:
                    ImageUtil.zipImage(ImageUtil.getImageFilePathName());
                    final String imageUrl = ImageUtil.getImageFilePathName();
                    if (!TextUtils.isEmpty(imageUrl)) {
                        RetrofitHelper.getInstance()
                                .create(IndexApis.class, true)
                                .uploadImage(ProjectUtil.getManagerToken(), Base64.encodeFile(imageUrl))
                                .compose(RxUtil.<UploadImageBean>compose(this))
                                .subscribe(new APISubscriber<UploadImageBean>() {
                                    @Override
                                    public void onNext(UploadImageBean uploadImageBean) {
                                        consumeAdapter.addData(imageUrl);
                                        consumeImgNames.add(0, uploadImageBean.getImageName());
                                    }
                                });
                    }
                    break;
                case ImageUtil.CHOOSE_BIG_PICTURE:
                    final String path = MediaUtil.getRealFilePath(data.getData());
                    ImageUtil.zipImage(path);
                    if (!TextUtils.isEmpty(path)) {
                        RetrofitHelper.getInstance()
                                .create(IndexApis.class, true)
                                .uploadImage(ProjectUtil.getManagerToken(), Base64.encodeFile(path))
                                .compose(RxUtil.<UploadImageBean>compose(this))
                                .subscribe(new APISubscriber<UploadImageBean>() {
                                    @Override
                                    public void onNext(UploadImageBean uploadImageBean) {
                                        consumeAdapter.addData(path);
                                        consumeImgNames.add(0, uploadImageBean.getImageName());
                                    }
                                });
                    }
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                String summary = mViewHelper.getText(R.id.edt_service_summary).trim();
                if (TextUtils.isEmpty(summary)) {
                    AlertUtil.getInstance().alertMessage(mContext, "请填写服务总结");
                    return;
                }

                String[] strings = consumeImgNames.toArray(new String[]{});
                String images = ArrayUtils.toString(strings).replace("{", "").replace("}", "");
                String orderId = getIntent().getStringExtra(ORDER_ID);
                RetrofitHelper.getInstance()
                        .create(ServantApis.class, true)
                        .submitSummary(ProjectUtil.getManagerToken(), orderId, summary, images)
                        .compose(RxUtil.compose(this))
                        .subscribe(new APISubscriber<Object>() {
                            @Override
                            public void onNext(Object o) {
                                AlertUtil.getInstance().alertMessage(mContext, "上传总结成功");

                                finish();
                            }
                        });
                break;
        }
    }

    public static void show(Context context, String orderId) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.setClass(context, UploadSummaryActivity.class);
        context.startActivity(intent);
    }
}
