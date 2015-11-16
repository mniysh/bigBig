package com.ms.ebangw.utils;

/**
 * 网络图片的加载《第三方框架》
 */
import java.io.File;

import android.content.Context;

import com.ms.ebangw.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoaderutils {
	private  static	ImageLoader loader;
	private  static  ImageLoaderConfiguration.Builder confbuilder;
	private   static ImageLoaderConfiguration conf;
	private   static DisplayImageOptions.Builder optbuilder;
	private  static DisplayImageOptions opt;
	//返回注册号的imageloader对象
	@SuppressWarnings("deprecation")
	public static  ImageLoader getInstance(Context context)
	{

		loader=ImageLoader.getInstance();
		confbuilder=new ImageLoaderConfiguration.Builder(context);
		File file=new File("/mnt/sdcard/ebang/pubilcImage");
		if(!file.exists()){
			file.mkdirs();
		}
		//制定sdcard缓存的路径
		confbuilder.discCache(new UnlimitedDiscCache(file));
		//缓存的图片个数
		confbuilder.discCacheFileCount(100);
		//缓存的最大容量
		confbuilder.discCacheSize(1024*1024*10);
		conf=confbuilder.build();
		loader.init(conf);
		return loader;
	}
	//返回显示图片使得opt，双缓存
	public   static DisplayImageOptions getOpt()
	{
		optbuilder=new DisplayImageOptions.Builder();
		//uri为空 加载默认图片
		optbuilder.showImageForEmptyUri(R.drawable.head);
		//图片获取失败 加载的默认图片
		optbuilder.showImageOnFail(R.drawable.head);
		optbuilder.cacheInMemory(true);//做内存缓存
		optbuilder.cacheOnDisc(true);//做硬盘缓存
		opt=optbuilder.build();
		return opt;
	}

}
