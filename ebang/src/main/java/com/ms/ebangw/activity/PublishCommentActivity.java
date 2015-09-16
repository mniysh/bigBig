package com.ms.ebangw.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.ms.ebangw.R;
import com.tencent.weibo.sdk.android.component.PublishActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 发表评论页面
 * @author admin
 *
 */
public class PublishCommentActivity extends BaseActivity {

	private ImageView camera,pic_pu01;
	private PopupWindow pw;
	private LinearLayout layout;
	private int REQUESTCODE01=1;
	private int REQUESTCODE02=2;
	private Bitmap bit;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_comment);
		initView();
		initData();
		operation();
	}

	@Override
	public void initView() {
		camera = (ImageView) findViewById(R.id.camera);
		layout = (LinearLayout) getLayoutInflater().inflate(R.layout.popcontent, null, false);
		pic_pu01=(ImageView) findViewById(R.id.act_pu_iv01);
	}

	@Override
	public void initData() {
		initTitle(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PublishCommentActivity.this.finish();
			}
		},"返回","发表评论",null,null);
	}

	private void operation() {
		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				pw = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
				pw.setBackgroundDrawable(new BitmapDrawable());

				//选中相机按钮
				layout.findViewById(R.id.bun_camera).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						pw.dismiss();
						backgroundAlpha(1.0f);
						Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(intent, REQUESTCODE02);
					}
				});
				//选中手机选图按钮
				layout.findViewById(R.id.bun_photo).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						pw.dismiss();
						backgroundAlpha(1.0f);
						Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						intent.putExtra("return-true", true);
						intent.putExtra("outputX", 10);
						intent.putExtra("outputY", 10);
						startActivityForResult(intent, REQUESTCODE01);

					}
				});
				//选中取消按钮
				layout.findViewById(R.id.bun_cancal).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						pw.dismiss();
						backgroundAlpha(1.0f);
					}
				});
				pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
					@Override
					public void onDismiss() {
						backgroundAlpha(1.0f);
					}
				});
				backgroundAlpha(0.5f);
				pw.setOutsideTouchable(true);
				pw.showAtLocation(camera, Gravity.CENTER_VERTICAL, 0, 0);
			}
		});
	}
	/**
	 * 设置添加屏幕的背景透明度
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha)
	{
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		getWindow().setAttributes(lp);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUESTCODE01){
			getImage(data);

		}else if(requestCode==REQUESTCODE02){
			getImageForCamera(data);
		}
	}
	//手机相机的
	private void getImageForCamera(Intent data) {
		// TODO Auto-generated method stub

		//获取Bitmap对象
		final Bitmap bit= (Bitmap) data.getExtras().get("data");
		//指定文件路径,缓存到手机
		File file=new File("/mnt/sdcard/ebang/Image/");
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream fos=null;

		try {
			fos=new FileOutputStream(new File(file+"/aa.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bit.compress(CompressFormat.PNG, 100, fos);//压缩文件到路径
		pic_pu01.setImageBitmap(bit);

	}

	private void getImage(Intent data) {
		// TODO Auto-generated method stub
		if(data==null){
			return;
		}
		if(data.getExtras().get("data")==null){
			return;
		}
		if(data!=null){
			Uri uri=data.getData();
			try {
				bit=MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file=new File("/mnt/sdcard/Bmob/Image/");
			if(!file.exists()){
				file.mkdirs();
			}
			FileOutputStream fos=null;
			try {
				fos=new FileOutputStream(new File(file+"/aa.png"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bit.compress(CompressFormat.PNG, 100, fos);//压缩文件到路径
			pic_pu01.setImageBitmap(bit);

		}
	}

}
