package com.ms.ebangw.social;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.AlbumStorageDirFactory;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.crop.GetPathFromUri4kitkat;
import com.ms.ebangw.dialog.DatePickerFragment;
import com.ms.ebangw.dialog.SelectPhotoDialog;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProvinceAndCityView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SocialPartyPublishActivity extends BaseActivity {
    private final int REQUEST_PICK = 4;
    private final int REQUEST_CAMERA = 6;
    private final int REQUEST_CROP = 8;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private String mCurrentPhotoPath;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private List<String> images;

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
    private String title;
    private String address;
    private String num;
    private String startTime;
    private String endTime;
    private String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_party_publish);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CAMERA) { //拍照返回
            handleBigCameraPhoto();

        } else if (requestCode == REQUEST_PICK) {
            Uri uri = data.getData();
            Log.d("way", "uri: " + uri);

            try {
                String path = GetPathFromUri4kitkat.getPath(this, uri);
                MyApplication myApplication = (MyApplication)getApplication();
                myApplication.imagePath = path;
                goCropActivity();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CROP) {        //剪切后返回
            L.d("AuthenticationFragment-->" + "REQUEST_CROP");
            handleCropBitmap(data);
        }

    }

    public void handleCropBitmap(Intent intent) {
        if (intent == null) {
            return;
        }
        UploadImageResult imageResult = intent.getParcelableExtra(Constants.KEY_UPLOAD_IMAGE_RESULT);
        MyApplication myApplication = (MyApplication) getApplication();
        images.add(imageResult.getUrl());
        String imagePath = myApplication.imagePath;
        Bitmap bitmap = BitmapUtil.getImage(imagePath);
        ivPic.setImageBitmap(bitmap);
    }


    public void goCropActivity() {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_UPLOAD_IMAGE_TYPE, CropImageActivity.TYPE_PUBLIC);
        Intent intent = new Intent(this, CropImageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CROP);

    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic(mCurrentPhotoPath, 400, 800);
            galleryAddPic();
            mCurrentPhotoPath = null;
        }
    }

    private void setPic(String path, int targetW, int targetH) {

        MyApplication application = (MyApplication) mActivity.getApplication();
        application.imagePath = path;
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.KEY_HEAD_IMAGE, true);
        Intent intent = new Intent(mActivity, CropImageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CROP);

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
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
                startActivityForResult(new Intent(SocialPartyPublishActivity.this, CropImageActivity
                    .class), 1001);
            }
        });
    }

    //拍照与选择图片剪切相关

    public void selectPhoto() {
        // 选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK);
    }


    /**
     * 拍照
     */
    public void captureImageByCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f;

        try {
            f = createImageFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            if (takePictureIntent.resolveActivity(getPackageManager()) != null && f != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private String getAlbumName() {
        return "crop";
    }

    public void showSelectPhotoDialog() {
        SelectPhotoDialog selectPhotoDialog = SelectPhotoDialog.newInstance("", "");
        selectPhotoDialog.setSelectListener(new SelectPhotoDialog.OnSelectListener() {
            @Override
            public void onCameraSelected() {
                captureImageByCamera();
            }

            @Override
            public void onPhotoSelected() {
                selectPhoto();
            }
        });

        selectPhotoDialog.show(getFragmentManager(), "SelectPhotoDialog");
//        captureImageByCamera();

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

        images = new ArrayList<>();
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
        startTime = tvStartTime.getText().toString().trim();
        endTime = tvEndTime.getText().toString().trim();
        theme = etTheme.getText().toString().trim();

        if (isRight()) {
            DataAccessUtil.socialPublish(title, pac.getProvinceId(), pac.getCityId(), address, num,
                startTime, endTime, theme, "", new JsonHttpResponseHandler() {
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

        return true;
    }

}
