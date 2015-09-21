package com.ms.ebangw.Photo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.ImageFloder;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.L;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 选择相册目录
 */
public class SelectPhotosDirActivity extends BaseActivity {
    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    int totalCount = 0;
    /**
     * 所有的图片
     */
    private List<String> mImgs;

    private GridView mGirdView;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();
    private ListView mListDir;
    String photoType;

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();
    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 100) {
                initDirsData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photes_dir);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle("照片");
        mListDir = (ListView) findViewById(R.id.id_list_dir);
    }

    @Override
    public void initData() {
        photoType = getIntent().getExtras().getString(Constants.KEY_PHOTO, Constants
            .PHOTO_FRONT);

        getImages();
    }



    private void initDirsData(){
        mListDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageFloder floder = (ImageFloder) view.getTag(Constants.KEY_IMAGE_FLODER);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.IMAGE_FLODER, floder);
                bundle.putString(Constants.KEY_PHOTO, photoType);

                Intent intent = new Intent(SelectPhotosDirActivity.this, PhotosActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 555);
            }
        });

        mListDir.setAdapter(new ImageDrisAdapter(this, mImageFloders));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 555 && resultCode == 555) {
            setResult(555);
            finish();
        }


    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages()
    {
        if (!Environment.getExternalStorageState().equals(
            Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[] { "image/jpeg", "image/png" },
                    MediaStore.Images.Media.DATE_MODIFIED);

                L.e("TAG", mCursor.getCount() + "");
                while (mCursor.moveToNext())
                {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));

                    L.e("TAG", path);
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath))
                    {
                        continue;
                    } else
                    {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter()
                    {
                        @Override
                        public boolean accept(File dir, String filename)
                        {
                            return filename.endsWith(".jpg")
                                || filename.endsWith(".png")
                                || filename.endsWith(".jpeg");
                        }
                    }).length;
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize)
                    {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(100);

            }
        }).start();

    }




}
