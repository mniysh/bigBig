package com.ms.ebangw.social;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.CropEnableActivity;
import com.ms.ebangw.adapter.PartyImageAddAdapter;
import com.ms.ebangw.bean.Party;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.dialog.DatePickerFragment;
import com.ms.ebangw.dialog.SelectPhotoDialog;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.setting.SocialPartyUtil;
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
 *
 * @author wangkai
 */
public class SocialPartyPublishActivity extends CropEnableActivity {
    public final static String CAMERA = "camera";

    @Bind(R.id.gv)
    GridView gv;
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

    @Bind(R.id.tv_commit)
    TextView tvCommit;
    @Bind(R.id.tv_preview)
    TextView tvPreview;
    @Bind(R.id.et_price)
    EditText etPrice;
    private PartyImageAddAdapter adapter;

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

        tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preview();

            }
        });

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                publishParty();

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

        initGridView();
    }

    private void initGridView() {
        ArrayList<String> list = new ArrayList<>();
        list.add(CAMERA);
        adapter = new PartyImageAddAdapter(this, list);
        adapter.setCameraClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPhotoDialog();
            }
        });
        gv.setAdapter(adapter);
    }

    private Party getPreviewParty() {
        String title = etTitle.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String num = etPeopleNum.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String startTime = tvStartTime.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();
        String theme = etTheme.getText().toString().trim();

        Party party = new Party();
        party.setTitle(title);
        party.setProvince(pac.getCurrentProvince().getName());
        party.setCity(pac.getCurrentCity().getName());
        party.setArea_other(address);
        party.setNumber_people(num);
        party.setPrice(price);
        party.setStart_time(startTime);
        party.setEnd_time(endTime);
        party.setTheme(theme);
        party.setProvinceId(pac.getProvinceId());
        party.setCityId(pac.getCityId());

        List<String> list = adapter.getList();
        list.remove(CAMERA);
        party.setActive_image(list);

        return party;

    }

    /**
     * 发布
     */
    public void publishParty() {
        Party party = getPreviewParty();
        if (SocialPartyUtil.isRight(party)) {

            String title = party.getTitle();
            String address = party.getArea_other();
            String num = party.getNumber_people();
            String provinceId = party.getProvinceId();
            String cityId = party.getCityId();
            String startTime = party.getStart_time();
            String endTime = party.getEnd_time();
            String theme = party.getTheme();
            String imagesJson = JsonUtil.createGsonString(party.getActive_image());
            String price = party.getPrice();

            DataAccessUtil.socialPublish(title, provinceId, cityId, address, num,
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

    public void preview() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_PARTY_STR, getPreviewParty());
        Intent intent = new Intent(SocialPartyPublishActivity.this, SocialPartyPreviewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @Override
    public void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult) {
        super.onCropImageSuccess(view, cropedImagePath, imageResult);
        updateImages(imageResult.getUrl());
    }

    /**
     * 上传成功后更新图片列表
     * @param imageUrl
     */
    public void updateImages(String  imageUrl) {
        List<String> list = adapter.getList();
        if (null != list) {
            if (list.contains(CAMERA)) {
                list.add(list.indexOf(CAMERA), imageUrl);
            }

            adapter.notifyDataSetChanged();
        }
    }
}
