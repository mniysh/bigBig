package com.ms.ebangw.Photo;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.ImageFloder;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.BitmapUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhotosActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    private GridView mGirdView;
    private File mImgDir;
    private List<String> mImgs;
    private List<String> mSelectedImage;
    private List<String> mLoadedUrls;
    String photoType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        initView();
        initData();

    }

    @Override
    public void initView() {
        initTitle("照片", "上传", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mGirdView = (GridView) findViewById(R.id.id_gridView);

    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();
        photoType = getIntent().getExtras().getString(Constants.KEY_PHOTO, Constants
            .PHOTO_FRONT);
        ImageFloder floder = (ImageFloder) extras.getSerializable(Constants.IMAGE_FLODER);

        mImgDir = new File(floder.getDir());
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".jpg") || filename.endsWith(".png")
                    || filename.endsWith(".jpeg");
            }
        }));

        MyPhotoAdapter adapter = new MyPhotoAdapter(this, mImgs, floder.getDir());
        mGirdView.setAdapter(adapter);

        mSelectedImage = adapter.mSelectedImage;
    }

    private List<Boolean> mUploadResult;
    private void upLoadPhotos(List<String> imagePaths) {
        mLoadedUrls = new ArrayList<>();
        mUploadResult = new ArrayList<>();
        for (int i = 0; i < imagePaths.size(); i++) {
            mUploadResult.add(false);
        }
        String path;
        for (int i = 0; i < imagePaths.size(); i++) {
            path = imagePaths.get(i);

            byte[] bytes = BitmapUtil.getCompressedBitmap(path, 400, 800, 1024);
        }

    }


    private void addPic(String pics) {


    }


}
