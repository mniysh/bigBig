package com.ms.ebangw.userAuthen.developers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.BankVerifyFragment;
import com.ms.ebangw.utils.L;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 个人用户认证
 */
public class DevelopersAuthenActivity extends BaseActivity {
	/**
	 * 要认证的用户类型
	 */
	private String category;
	private File imageFile;
	private TotalRegion totalRegion;

	/**
	 * 认证的信息
	 */
	private AuthInfo authInfo;

	private List<Fragment> list;
	private FragmentManager fm;
	private DevelopersIdentityCardFragment  identifyFragment;
	private DevelopersBaseInfoFragment personBaseInfoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_developers_authen);
		ButterKnife.bind(this);
		Bundle extras = getIntent().getExtras();
		category = extras.getString(Constants.KEY_CATEGORY, Constants.INVESTOR);
		totalRegion = (TotalRegion) extras.getSerializable(Constants.KEY_TOTAL_REGION);
		initView();
		initData();
	}

	public void initView() {
		initTitle(null, "返回", "个人认证", null, null);

	}

	@Override
	public void initData() {
		fm = getFragmentManager();
		personBaseInfoFragment = DevelopersBaseInfoFragment.newInstance(category);

		getFragmentManager().beginTransaction().replace(R.id.fl_content,personBaseInfoFragment
		).commit();

	}

	public void goNext() {

		identifyFragment = DevelopersIdentityCardFragment.newInstance(category);
		getFragmentManager().beginTransaction().replace(R.id.fl_content, identifyFragment)
			.addToBackStack("IdentityCardPhotoVerifyFragment").commit();

	}

	/**
	 * 身份证照片验证
	 */
	public void goVerifyBank() {

		getFragmentManager().beginTransaction().replace(R.id.fl_content,
			BankVerifyFragment.newInstance(category)).addToBackStack
			("BankVerifyFragment").commit();

	}

	public AuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(AuthInfo authInfo) {
		this.authInfo = authInfo;
	}


	/*** 打开照相机     */
	public void openCamera(){
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(Environment.getExternalStorageDirectory() + "/Images");
		if(!file.exists()){
			file.mkdirs();
		}
		imageFile = new File(Environment.getExternalStorageDirectory() + "/Images/",
			"cameraImg" + String.valueOf(System.currentTimeMillis()) + ".jpg");

		Uri mUri = Uri.fromFile(imageFile);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
		cameraIntent.putExtra("return-data", true);
		startActivityForResult(cameraIntent, Constants.REQUEST_CAMERA);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		L.d("onActivityResult");

		if (requestCode == Constants.REQUEST_CAMERA && resultCode == RESULT_OK) { //拍照返回
			Uri uri;
			if (null == data) {
				uri = Uri.fromFile(imageFile);
			}else {
				uri = data.getData();
			}


			beginCrop(uri);

		}else if (requestCode == Crop.REQUEST_PICK&& resultCode == RESULT_OK) {
			beginCrop(data.getData());

		}else if (requestCode == Crop.REQUEST_CROP) {
			identifyFragment.handleCrop(resultCode, data);			//在Fragment中处理剪切后的图片
		}
	}

	private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped.png"));
		Crop.of(source, destination).asSquare().start(this);
	}

	public void selectPhoto() {

		// 选择图片
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, Crop.REQUEST_PICK);
//		Crop.pickImage(this);
	}

	public File uriToFile(Uri uri) {
//		Uri uri = data.getData();

		String[] proj = { MediaStore.Images.Media.DATA };

		Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);

		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		actualimagecursor.moveToFirst();

		String img_path = actualimagecursor.getString(actual_image_column_index);

		File file = new File(img_path);
		return file;
	}

	public TotalRegion getTotalRegion() {
		return totalRegion;
	}

	public void commit() {

	}

	/**
	 * 提交认证信息
	 */
	public void commitInvestorAuthentication() {



	}

//	public void commitHeadmanAuthentication() {
//
//
//
//
//	}
//
//	public void commitDevelopersAuthentication() {
//
//	}
//
//	public void commitWorkerAuthentication() {
//
//	}





}
