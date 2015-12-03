package com.ms.ebangw.social;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.CropEnableActivity;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.dialog.DatePickerFragment;
import com.ms.ebangw.dialog.SelectPhotoDialog;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.JsonUtil;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProvinceAndCityView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 社区，发布
 * @author wangkai
 */
public class SocialPartyPublishActivity extends CropEnableActivity {
    private List<String> mImageUrls;

    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.pac)
    ProvinceAndCityView pac;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.et_people_num)
    EditText etPeopleNum;
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.et_theme)
    EditText etTheme;
    @Bind(R.id.iv_pic)
    ImageView ivPic;
    @Bind(R.id.tv_commit)
    TextView tvCommit;
    @Bind(R.id.tv_preview)
    TextView tvPreview;
    @Bind(R.id.et_price)
    EditText etPrice;

    private String title;
    private String address;
    private String num;
    private String startTime;
    private String endTime;
    private String theme;
    private String imagesJson;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_party_publish);
        ButterKnife.bind(this);
        initView();
        initData();

    }








    @Override
    public void initView() {
        initTitle(null, "返回", "发布活动", null, null);
        TotalRegion areaFromAssets = getAreaFromAssets();
        pac.setProvinces(areaFromAssets.getProvince());

        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateSelectDialog("start");
            }
        });

        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateSelectDialog("end");
            }
        });

        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPhotoDialog();
            }
        });
    }


    public void showSelectPhotoDialog() {
        SelectPhotoDialog selectPhotoDialog = SelectPhotoDialog.newInstance("", "");
        selectPhotoDialog.setSelectListener(new SelectPhotoDialog.OnSelectListener() {
            @Override
            public void onCameraSelected(View view) {
                captureImageByCamera(view, CropImageActivity.TYPE_PUBLIC);
            }

            @Override
            public void onPhotoSelected(View view) {
                selectPhoto(view, CropImageActivity.TYPE_PUBLIC);
            }
        });

        selectPhotoDialog.show(getFragmentManager(), "SelectPhotoDialog");

    }

    private void showDateSelectDialog(final String type) {
        DatePickerFragment dialog = new DatePickerFragment();
        dialog.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String s = format.format(calendar.getTime());
                if (TextUtils.equals("start", type)) {
                    tvStartTime.setText(s);
                } else {
                    tvEndTime.setText(s);
                }
            }
        });
        dialog.show(getFragmentManager(), "DatePickerFragment");
    }

    @Override
    public void initData() {

        mImageUrls = new ArrayList<>();
        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishParty();
            }
        });
    }

    /**
     * 发布
     */
    private void publishParty() {
        title = etTitle.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        num = etPeopleNum.getText().toString().trim();
        price = etPrice.getText().toString().trim();
        startTime = tvStartTime.getText().toString().trim();
        endTime = tvEndTime.getText().toString().trim();
        theme = etTheme.getText().toString().trim();

        Gson gson = new Gson();
        imagesJson = JsonUtil.createGsonString(mImageUrls);

        if (isRight()) {
            DataAccessUtil.socialPublish(title, pac.getProvinceId(), pac.getCityId(), address, num,
                startTime, endTime, price, theme, imagesJson, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            boolean b = DataParseUtil.processDataResult(response);
                            if (b) {
                                finish();
                            }
                        } catch (ResponseException e) {
                            e.printStackTrace();
                            T.show(e.getMessage());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        showProgressDialog();
                    }
                });
        }
    }

    private void preview() {



    }

    private boolean isRight() {

        if (TextUtils.isEmpty(title)) {
            T.show("请输入标题");
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            T.show("请输入工地地址");
            return false;
        }

        if (TextUtils.isEmpty(num)) {
            T.show("请输入人数");
            return false;
        }

        if (TextUtils.isEmpty(price)) {
            T.show("请输价格");
            return false;
        }

        if (TextUtils.isEmpty(startTime)) {
            T.show("请输入开始时间");
            return false;
        }

        if (TextUtils.isEmpty(endTime)) {
            T.show("请输入结束时间");
            return false;
        }

        if (TextUtils.isEmpty(theme)) {
            T.show("请输入活动主题");
            return false;
        }
         if (TextUtils.isEmpty(imagesJson)) {
            T.show("请上传照片");
            return false;
        }

        return true;
    }

    @Override
    public void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult) {
        super.onCropImageSuccess(view, cropedImagePath, imageResult);
            mImageUrls.add(imageResult.getName());
            Bitmap bitmap = BitmapUtil.getImage(cropedImagePath);
            ivPic.setImageBitmap(bitmap);
    }
}
