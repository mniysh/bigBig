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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.crop.FroyoAlbumDirFactory;
import com.ms.ebangw.crop.GetPathFromUri4kitkat;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.CropImageUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

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
    private final int REQUEST_CROP = 8;
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
        L.d("onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(Constants.KEY_CATEGORY);
        }

        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString(Constants.KEY_CURRENT_IMAGE_PATH);
            whichPhoto = savedInstanceState.getString(Constants.KEY_WHICH_PHOTO);
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
    }

    /**
     * 选择反面照片
     */
    @OnClick(R.id.btn_select_back)
    public void selectBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
        selectPhoto();
    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto() {
        whichPhoto = Constants.PHOTO_FRONT;
        captureImageByCamera();
    }

    /**
     * 拍背面身份证照
     */
    @OnClick(R.id.btn_photo_back)
    public void takeBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
        captureImageByCamera();
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


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
//        File albumF = Environment.getExternalStoragePublicDirectory(
//            Environment.DIRECTORY_PICTURES);
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
                        L.d("failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            L.d("External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private String getAlbumName() {
        return "crop";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != mActivity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CAMERA ) { //拍照返回
            handleBigCameraPhoto();

        }else if (requestCode == REQUEST_PICK) {
            Uri uri = data.getData();
            L.d("way", "uri: " + uri);

            try {
                String path = GetPathFromUri4kitkat.getPath(mActivity, uri);
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

        }else if (requestCode == REQUEST_CROP) {        //剪切后返回
            handleCropBitmap(data);
        }
    }


    public void handleCropBitmap(Intent intent) {
        if (intent == null) {
            return;
        }
        UploadImageResult imageResult = intent.getParcelableExtra(Constants.KEY_UPLOAD_IMAGE_RESULT);
        MyApplication myApplication = (MyApplication) mActivity.getApplication();
        Bitmap bitmap = myApplication.mBitmap;

        String id = imageResult.getId();
        AuthInfo authInfo = ((InvestorAuthenActivity) mActivity).getAuthInfo();
        switch (whichPhoto) {
            case Constants.PHOTO_FRONT:
                frontIv.setImageBitmap(bitmap);
                authInfo.setFrontImageId(id);
                isFrontUploaded = true;
                break;

            case Constants.PHOTO_BACK:
                backIv.setImageBitmap(bitmap);
                authInfo.setBackImageId(id);
                isBackUploaded = true;
                break;
        }

    }

    public void goCropActivity() {

        Intent intent = new Intent(mActivity, CropImageActivity.class);
        startActivityForResult(intent, REQUEST_CROP);

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
        Bitmap bitmap = BitmapUtil.getimage(path);
        int bitmapDegree = CropImageUtil.getBitmapDegree(path);
        if (bitmapDegree != 0) {
            bitmap = CropImageUtil.rotateBitmapByDegree(bitmap, bitmapDegree);
        }

        MyApplication application = (MyApplication) mActivity.getApplication();
        application.mBitmap = bitmap;

        Intent intent = new Intent(mActivity, CropImageActivity.class);
        startActivityForResult(intent, REQUEST_CROP);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.KEY_CURRENT_IMAGE_PATH, mCurrentPhotoPath);
        outState.putString(Constants.KEY_WHICH_PHOTO, whichPhoto);
        L.d("onSaveInstanceState: " + mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }
}
