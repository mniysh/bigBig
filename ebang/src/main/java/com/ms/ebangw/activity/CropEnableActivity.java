package com.ms.ebangw.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.commons.OnCropImageCallback;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.utils.L;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class CropEnableActivity extends BaseActivity implements OnCropImageCallback {
    private static final int REQUEST_PICK = 4;
    private static final int REQUEST_CAMERA = 6;
    private static final int REQUEST_CROP = 8;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private com.ms.ebangw.crop.AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private String mCurrentPhotoPath;
    private View currentView;
    private String imageType;

    /**
     * 初始化View
     */
    abstract public void initView();

    /**
     * 初始化数据
     */
    abstract public void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            mCurrentPhotoPath = savedInstanceState.getString(Constants.KEY_CURRENT_IMAGE_PATH);
        }
    }

    /**
     * 选择相册图片
     * @param view
     * @param imageType 图片的处理类型，公开的，私有的
     */
    public void selectPhoto(View view, @CropImageActivity.ImageType String imageType) {
        // 选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK);
        currentView = view;
        this.imageType = imageType;
    }



    /**
     * 拍照
     */
    public void captureImageByCamera(View view, @CropImageActivity.ImageType String imageType) {

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
        currentView = view;
        this.imageType = imageType;
    }



    private void goCropActivity(String imagePath) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_UPLOAD_IMAGE_TYPE, imageType);
        bundle.putString(Constants.KEY_ORIGIN_IMAGE_PATH, imagePath);
        Intent intent = new Intent(this, CropImageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CROP);
    }


    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic(mCurrentPhotoPath , 400, 800);
            galleryAddPic();
            mCurrentPhotoPath = null;
        }
    }

    private void setPic(String path, int targetW, int targetH) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_UPLOAD_IMAGE_TYPE, CropImageActivity.TYPE_PRIVATE);
        bundle.putString(Constants.KEY_ORIGIN_IMAGE_PATH, path);
        Intent intent = new Intent(this, CropImageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CROP);

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
//            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
//            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());
            storageDir = Environment.getExternalStorageDirectory();

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

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }


    @Override
    public void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult) {
        L.d(cropedImagePath + "imageResult: " + imageResult.toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.KEY_CURRENT_IMAGE_PATH, mCurrentPhotoPath);
        L.d("Fragment onSaveInstanceState: " + outState);
        super.onSaveInstanceState(outState);
    }
}
