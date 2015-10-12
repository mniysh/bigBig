package com.ms.ebangw.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.activity.SettingActivity;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.crop.FroyoAlbumDirFactory;
import com.ms.ebangw.crop.GetPathFromUri4kitkat;
import com.ms.ebangw.dialog.SelectPhotoDialog;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.userAuthen.developers.DevelopersAuthenActivity;
import com.ms.ebangw.userAuthen.headman.HeadmanAuthenActivity;
import com.ms.ebangw.userAuthen.investor.InvestorAuthenActivity;
import com.ms.ebangw.userAuthen.worker.WorkerAuthenActivity;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.L;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 认证的页面
 *
 * @author admin
 */
public class AuthenticationFragment extends BaseFragment implements OnClickListener {
    private final int REQUEST_PICK = 4;
    private final int REQUEST_CAMERA = 6;
    private final int REQUEST_CROP = 8;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private com.ms.ebangw.crop.AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private String mCurrentPhotoPath;


    private Button but_self, but_worker, but_foreman, but_factory;
    private View mContentView;
    private User user;

    @Bind(R.id.tv_name)
    TextView nameTv;
    @Bind(R.id.tv_phone)
    TextView phoneTv;
    @Bind(R.id.tv_rank)
    TextView rankTv;

    @Bind(R.id.tv_realName)
    TextView realNameTv;
    @Bind(R.id.tv_phone2)
    TextView phone2Tv;
    @Bind(R.id.tv_gender)
    TextView genderTv;
    @Bind(R.id.tv_native_place)
    TextView nativePlaceTv;
    @Bind(R.id.tv_work_type)
    TextView workTypeTv;
    @Bind(R.id.ll_authed)
    LinearLayout detailLayout;
    @Bind(R.id.ll_no_auth)
    LinearLayout noAuthLayout;
    @Bind(R.id.ll_workType)
    LinearLayout LWorkType;

    @Bind(R.id.iv_head)
    ImageView headIv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mCurrentPhotoPath = savedInstanceState.getString(Constants.KEY_CURRENT_IMAGE_PATH);
		}
    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.frag_authen, null);
        ButterKnife.bind(this, mContentView);
        initView();
        initData();
        return mContentView;
    }


    private void setNickName() {
        L.d("xxx", "现在的用户昵称是" + getUser().getNick_name());
        User user = getUser();
        if (null != user) {
            phoneTv.setText(user.getPhone());
            nameTv.setText(user.getNick_name());
        }
    }

    public void initView() {
        //initTitle("我的信息");

        but_self = (Button) mContentView.findViewById(R.id.btn_investor);
        but_worker = (Button) mContentView.findViewById(R.id.btn_worker);
        but_foreman = (Button) mContentView.findViewById(R.id.btn_headman);
        but_factory = (Button) mContentView.findViewById(R.id.btn_developers);

        but_self.setOnClickListener(this);
        but_worker.setOnClickListener(this);
        but_foreman.setOnClickListener(this);
        but_factory.setOnClickListener(this);

    }

    public void initCompletedUser() {
        noAuthLayout.setVisibility(View.GONE);
        detailLayout.setVisibility(View.VISIBLE);
        LWorkType.setVisibility(View.GONE);
        User user = getUser();
        String real_name = user.getReal_name();
        String gender = user.getGender();
        String phone = user.getPhone();
        String area = user.getArea();
        String craft = user.getCraft();
        realNameTv.setText(real_name);
        genderTv.setText(gender);
        phone2Tv.setText(phone);
        nativePlaceTv.setText(area);

        if(TextUtils.equals(craft,"null") || TextUtils.isEmpty(craft) || TextUtils.equals(craft,
            "Null")){
            LWorkType.setVisibility(View.GONE);

        }else {
            LWorkType.setVisibility(View.VISIBLE);
            workTypeTv.setText(craft);
        }



    }

    public void initNoAuthUser() {

        noAuthLayout.setVisibility(View.VISIBLE);
        detailLayout.setVisibility(View.GONE);


    }

    @Override
    public void initData() {
        initHeadInfo();
        User user = getUser();
        String status = user.getStatus();        //认证状态
        switch (status) {
            case "guest":                        //未申请
                initNoAuthUser();
                break;
            case "complete":                //认证审核通过
                initCompletedUser();
                break;
        }

        mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
    }


    /**
     * 用户信息
     */
    public void initHeadInfo() {
        headIv.setOnClickListener(new OnClickListener() {
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

    @Override
    public void onResume() {
        super.onResume();
        initTitle(null, null, "我的信息", "设置", new OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置跳转
                Intent intent = new Intent(mActivity, SettingActivity.class);

                mActivity.startActivityForResult(intent, Constants.REQUEST_EXIT);
            }
        });

        setNickName();
    }

    @Override
    public void onClick(View v) {    ////worker(工人)/headman(工头)/developers(开发商)/investor(个人)

        Intent intent;
        switch (v.getId()) {

            //个人认证
            case R.id.btn_investor:
                intent = new Intent(mActivity, InvestorAuthenActivity.class);
                break;
            case R.id.btn_worker:    //工人
                intent = new Intent(mActivity, WorkerAuthenActivity.class);
                break;
            case R.id.btn_headman:    //工头
                intent = new Intent(mActivity, HeadmanAuthenActivity.class);
                break;
            case R.id.btn_developers:    //开发商
                intent = new Intent(mActivity, DevelopersAuthenActivity.class);
                break;

            default:
                intent = new Intent(mActivity, InvestorAuthenActivity.class);
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_TOTAL_REGION, ((HomeActivity) mActivity).getTotalRegion());
        intent.putExtras(bundle);
        startActivity(intent);
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

}

