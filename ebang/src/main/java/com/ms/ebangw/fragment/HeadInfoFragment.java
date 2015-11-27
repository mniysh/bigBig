package com.ms.ebangw.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.crop.FroyoAlbumDirFactory;
import com.ms.ebangw.crop.GetPathFromUri4kitkat;
import com.ms.ebangw.dialog.SelectPhotoDialog;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.scancode.MipcaActivityCapture;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.setting.SettingAllActivity;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用户头部信息
 * @author wangkai
 */
public class HeadInfoFragment extends BaseFragment {
    private final int REQUEST_PICK = 4;
    private final int REQUEST_CAMERA = 6;
    private final int REQUEST_CROP = 8;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private com.ms.ebangw.crop.AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private String mCurrentPhotoPath;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private ViewGroup contentLayout;
    @Bind(R.id.tv_name)
    TextView nameTv;
    @Bind(R.id.tv_phone)
    TextView phoneTv;
    @Bind(R.id.tv_rank)
    TextView rankTv;
    @Bind(R.id.iv_head)
    ImageView headIv;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    public HeadInfoFragment() {
        // Required empty public constructor
    }

    public static HeadInfoFragment newInstance(String param1, String param2) {
        HeadInfoFragment fragment = new HeadInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != mActivity.RESULT_OK) {
            L.d("AuthenticationFragment + resultCode != mActivity.RESULT_OK");
            return;
        }
        if (requestCode == REQUEST_CAMERA) { //拍照返回
            L.d("AuthenticationFragment-->" + "REQUEST_CAMERA");

            handleBigCameraPhoto();

        } else if (requestCode == REQUEST_PICK) {
            L.d("AuthenticationFragment-->" + "REQUEST_PICK");
            Uri uri = data.getData();
            Log.d("way", "uri: " + uri);

            try {
                String path = GetPathFromUri4kitkat.getPath(mActivity, uri);
                MyApplication myApplication = (MyApplication) mActivity.getApplication();
                myApplication.imagePath = path;
                goCropActivity();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CROP) {        //剪切后返回
            L.d("AuthenticationFragment-->" + "REQUEST_CROP");
            handleCropBitmap(data);
        }else if (requestCode == SCANNIN_GREQUEST_CODE && resultCode == mActivity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            //显示扫描到的内容
            String result = bundle.getString("result");
            L.d("二维码扫描结果: " + result);
            workerRecommendHeadman(result);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_head_info, null);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle(null, null, "我的信息", "设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置跳转
                Intent intent = new Intent(mActivity, SettingAllActivity.class);
                mActivity.startActivityForResult(intent, Constants.REQUEST_EXIT);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = getUser();

        if (TextUtils.equals(user.getCategory(), Constants.WORKER) && !TextUtils.equals("1", user
            .getIs_have_headman())) {
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setImageResource(R.drawable.scan_code);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, MipcaActivityCapture.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                }
            });
        }else {
            ivBack.setVisibility(View.GONE);
        }

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        initHeadInfo();
        mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
    }

    /**
     * 用户信息
     */
    public void initHeadInfo() {
        headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPhotoDialog();
            }
        });
        loadAvatar();
        User user = getUser();
        if (null != user) {
            String rank = user.getRank();
            setNickName();
            rankTv.setText("等级：" + rank + " 级");
        }
    }

    private void setNickName() {
        L.d("xxx", "现在的用户昵称是" + getUser().getNick_name());
        User user = getUser();
        if (null != user) {
            phoneTv.setText(user.getPhone());
            nameTv.setText(user.getNick_name());
        }
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
            if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null && f !=
                null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
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

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic(mCurrentPhotoPath, 400, 800);
            galleryAddPic();
            mCurrentPhotoPath = null;
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mActivity.sendBroadcast(mediaScanIntent);
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

    public void goCropActivity() {

        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.KEY_HEAD_IMAGE, true);
        Intent intent = new Intent(mActivity, CropImageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CROP);

    }

    public void handleCropBitmap(Intent intent) {
        if (intent == null) {
            return;
        }
        UploadImageResult imageResult = intent.getParcelableExtra(Constants.KEY_UPLOAD_IMAGE_RESULT);
        MyApplication myApplication = (MyApplication) mActivity.getApplication();

        String imagePath = myApplication.imagePath;
        Bitmap bitmap = BitmapUtil.getImage(imagePath);
        headIv.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.KEY_CURRENT_IMAGE_PATH, mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }

    public void loadAvatar() {
        User user = getUser();
        String head_image = user.getHead_img();
        if (!TextUtils.isEmpty(head_image)) {
            String imageUrl = DataAccessUtil.getImageUrl(head_image);
            Picasso.with(mActivity).load(Uri.parse(imageUrl)).into(headIv);
        }
    }

    /**
     * 工人扫码推荐工长
     * @param headmanId
     */
    private void workerRecommendHeadman(String headmanId) {
        DataAccessUtil.workerRecommendHeadman(headmanId, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    boolean b = DataParseUtil.processDataResult(response);

                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
