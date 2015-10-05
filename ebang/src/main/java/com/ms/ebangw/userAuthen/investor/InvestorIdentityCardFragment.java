package com.ms.ebangw.userAuthen.investor;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.crop.FroyoAlbumDirFactory;
import com.ms.ebangw.crop.GetPathFromUri4kitkat;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.CropImageUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 身份证照片上传
 * @author wangkai
 */
public class InvestorIdentityCardFragment extends BaseFragment {
    private static final String CATEGORY = "category";
    private final int REQUEST_PICK = 4;
    private final int REQUEST_CAMERA = 6;
    private String mCurrentPhotoPath;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private com.ms.ebangw.crop.AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private String whichPhoto;
    private String category;
    private File imageFile;
    private final int TYPE_FRONT = 1;
    private final int TYPE_BACK = 2;

    private View contentLayout;
    /**正面身份证选择图片*/
    @Bind(R.id.btn_select_front)
    Button uploadFrontBtn;
    /**背面身份证选择图片*/
    @Bind(R.id.btn_select_back)
    Button uploadBackBtn;
    /**正面身份证拍照*/
    @Bind(R.id.btn_photo_front)
    Button photoFrontBtn;
    /**反面身份证拍照*/
    @Bind(R.id.btn_photo_back)
    Button photoBackBtn;
    @Bind(R.id.iv_front)
    ImageView frontIv;
    @Bind(R.id.iv_back)
    ImageView backIv;
    @Bind(R.id.btn_next)
    Button nextBtn;
    private boolean isFrontUploaded, isBackUploaded;


    public static InvestorIdentityCardFragment newInstance(String category) {
        InvestorIdentityCardFragment fragment = new InvestorIdentityCardFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(Constants.KEY_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = inflater.inflate(R.layout.fragment_card_verify, container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }


    /**
     * 选择正面照片
     */
    @OnClick(R.id.btn_select_front)
    public void selectFrontPhoto() {
        whichPhoto = Constants.PHOTO_FRONT;
        selectPhoto();
//        ((InvestorAuthenActivity)mActivity).selectPhoto();
    }

    /**
     * 选择反面照片
     */
    @OnClick(R.id.btn_select_back)
    public void selectBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
//        ((InvestorAuthenActivity)mActivity).selectPhoto();
    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto() {
        whichPhoto = Constants.PHOTO_FRONT;
        captureImageByCamera();
//        ((InvestorAuthenActivity)mActivity).openCamera();
    }

    /**
     * 拍背面身份证照
     */
    @OnClick(R.id.btn_photo_back)
    public void takeBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
//        ((InvestorAuthenActivity)mActivity).openCamera();
    }

    @Override
    public void initView() {
        setStarRed();
        nextBtn.setText("下一步");

    }

    @OnClick(R.id.btn_next)
    public void goNext() {

        if (isIdentifyCardUploaded()) {
            ((InvestorAuthenActivity) mActivity).goVerifyBank();
        }
    }

    private boolean isIdentifyCardUploaded() {
        if (!isFrontUploaded) {
            T.show("请上传身份证正面照");
            return false;
        }else if(!isBackUploaded){
            T.show("请上传身份证反面照");
            return false;
        }
        return true;
    }

    @Override
    public void initData() {
        mAlbumStorageDirFactory = new FroyoAlbumDirFactory();


    }

    public void handleCrop(int resultCode, Intent result) {
        if (resultCode == mActivity.RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            L.d("Uri: " + uri);
            if (whichPhoto == Constants.PHOTO_FRONT) {
                frontIv.setImageURI(Crop.getOutput(result));
//                uploadImage(uri, TYPE_FRONT);
            }else {
                backIv.setImageURI(Crop.getOutput(result));
//                uploadImage(uri, TYPE_BACK);
            }



        } else if (resultCode == Crop.RESULT_ERROR) {
            T.show("选取图片失败");
        }
    }

//    private void uploadImage(Uri uri, final int type ) {
//
//        DataAccessUtil.uploadImage(file, new JsonHttpResponseHandler() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                showProgressDialog("图片上传中...");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                dismissLoadingDialog();
//
//
//                try {
//                    if (DataParseUtil.processDataResult(response)) {
//                        UploadImageResult result = DataParseUtil.upLoadImage(response);
//                        String id = result.getId();
//                        AuthInfo authInfo = ((InvestorAuthenActivity) mActivity).getAuthInfo();
//                        if (type == TYPE_FRONT) {
//                            isFrontUploaded = true;
//                            authInfo.setFrontImageId(id);
//                        }
//
//                        if (type == TYPE_BACK) {
//                            isBackUploaded = true;
//                            authInfo.setBackImageId(id);
//                        }
//                        T.show("上传图片成功");
//                    }else {
//                        T.show("上传图片失败,请重试");
//                    }
//
//
//
//                } catch (ResponseException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//                L.d(responseString);
//                T.show("上传图片失败,请重试");
//                dismissLoadingDialog();
//            }
//        });
//    }



    /**
     * 把*变成红色
     */
    public void setStarRed() {
        int[] resId = new int[]{R.id.tv_a, R.id.tv_b};
        for (int i = 0; i < resId.length; i++) {
            TextView a = (TextView) contentLayout.findViewById(resId[i]);
            String s = a.getText().toString();
            SpannableString spannableString = new SpannableString(s);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            a.setText(spannableString);
        }
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
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }

        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
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
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA ) { //拍照返回
            if (resultCode == mActivity.RESULT_OK) {
                handleBigCameraPhoto();
            }

        }else if (requestCode == REQUEST_PICK) {
            Uri uri = data.getData();
            Log.d("way", "uri: " + uri);

            try {
                String path = GetPathFromUri4kitkat.getPath(mActivity, uri);
//                Bitmap bitmap = CropImageUtil.decodeSampledBitmapFromResource(path, 400, 800);

                Bitmap bitmap = BitmapUtil.getimage(path);
                int bitmapDegree = CropImageUtil.getBitmapDegree(path);
                if (bitmapDegree != 0) {
                    bitmap = CropImageUtil.rotateBitmapByDegree(bitmap, bitmapDegree);
                }
                MyApplication myApplication = (MyApplication) mActivity.getApplication();
                myApplication.mBitmap = bitmap;
                goCropActivity();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Toast.makeText(mActivity, "onActivityResult", Toast.LENGTH_SHORT).show();
    }

    public void goCropActivity() {

        Intent intent = new Intent(mActivity, CropImageActivity.class);
        startActivity(intent);

    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic(mCurrentPhotoPath , 400, 800);
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

//        Bitmap bitmap = CropImageUtil.decodeSampledBitmapFromResource(path, targetW, targetH);
        Bitmap bitmap = BitmapUtil.getimage(path);
        int bitmapDegree = CropImageUtil.getBitmapDegree(path);
        if (bitmapDegree != 0) {
            bitmap = CropImageUtil.rotateBitmapByDegree(bitmap, bitmapDegree);
        }

        MyApplication application = (MyApplication) mActivity.getApplication();
        application.mBitmap = bitmap;

        Intent intent = new Intent(mActivity, CropImageActivity.class);
        startActivity(intent);

    }



}
