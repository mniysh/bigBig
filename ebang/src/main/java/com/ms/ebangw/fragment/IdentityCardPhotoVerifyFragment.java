package com.ms.ebangw.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;
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
public class IdentityCardPhotoVerifyFragment extends BaseFragment {
    private static final String CATEGORY = "category";
    private String whichPhoto;
    private String category;
    private String mCurrentPhotoPath;
    private View contentLayout;
    /**正面身份证上传*/
    @Bind(R.id.btn_upload_front)
    Button uploadFrontBtn;
    /**背面身份证上传*/
    @Bind(R.id.btn_upload_back)
    Button uploadBackBtn;
    /**正面身份证拍照*/
    @Bind(R.id.btn_photo_front)
    Button photoFrontBtn;
    /**正面身份证拍照*/
    @Bind(R.id.btn_photo_back)
    Button photoBackBtn;
    @Bind(R.id.iv_front)
    ImageView frontIv;
    @Bind(R.id.iv_back)
    ImageView backIv;


    public static IdentityCardPhotoVerifyFragment newInstance(String category) {
        IdentityCardPhotoVerifyFragment fragment = new IdentityCardPhotoVerifyFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public IdentityCardPhotoVerifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
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

    private void dispatchTakePictureIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri uri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, Constants.REQUEST_PICK);
            }
        }
    }

    /**
     * 选择正面照片
     */
    @OnClick(R.id.btn_upload_front)
    public void selectFrontPhoto() {
        whichPhoto = Constants.PHOTO_FRONT;
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.KEY_PHOTO, Constants.PHOTO_FRONT);
//        Intent intent = new Intent(mActivity, SelectPhotosDirActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
        File dir = new File(Constants.LOGS_AND_IMGS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, Constants.CHOOSE_GALLERY);
    }

    /**
     * 选择反面照片
     */
    @OnClick(R.id.btn_upload_back)
    public void selectBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
        File dir = new File(Constants.LOGS_AND_IMGS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, Constants.CHOOSE_GALLERY);
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.KEY_PHOTO, Constants.PHOTO_BACK);
//        Intent intent = new Intent(mActivity, SelectPhotosDirActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);

    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto() {
        whichPhoto = Constants.PHOTO_FRONT;
        dispatchTakePictureIntent();

    }

    /**
     * 拍背面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
        dispatchTakePictureIntent();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_PICK && resultCode == mActivity.RESULT_OK) {
            beginCrop(data.getData());
        }else if (requestCode == Constants.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        );
        if(!image.exists()){
            image.mkdirs();
        }

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(mActivity.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(mActivity);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == mActivity.RESULT_OK) {
            if (whichPhoto == Constants.PHOTO_FRONT) {
                frontIv.setImageURI(Crop.getOutput(result));
            }else {
                backIv.setImageURI(Crop.getOutput(result));
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            T.show("选取图片失败");
        }
    }

}
