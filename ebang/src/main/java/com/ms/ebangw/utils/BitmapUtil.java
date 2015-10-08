package com.ms.ebangw.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author yanzi
 */
public class BitmapUtil {

        /**
         * 根据图片路径获取图片
         * @param srcPath
         * @return
         */
        public static Bitmap getimage(String srcPath) {
                BitmapFactory.Options newOpts = new BitmapFactory.Options();
                // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
                newOpts.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

                newOpts.inJustDecodeBounds = false;
                int w = newOpts.outWidth;
                int h = newOpts.outHeight;
                // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
                float hh = 400f;// 这里设置高度为800f
                float ww = 400f;// 这里设置宽度为480f
                // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                int be = 1;// be=1表示不缩放
                if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                        be = (int) (newOpts.outWidth / ww);
                } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                        be = (int) (newOpts.outHeight / hh);
                }
                if (be <= 0)
                        be = 1;
                newOpts.inSampleSize = be;// 设置缩放比例
                newOpts.inPreferredConfig = Config.RGB_565;
                // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
                FileInputStream fileInputStream = null;
                try {
                        fileInputStream = new FileInputStream(srcPath);
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(fileInputStream, null, newOpts);
//                bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
                if(bitmap != null){
                        L.d("xxx", "bitmap不为空");
                }
                return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        }

        public static Bitmap compressImage(Bitmap image) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                int options = 100;
                while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                        baos.reset();// 重置baos即清空baos
                        image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                        options -= 10;// 每次都减少10
                }
                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
                Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
                return bitmap;
        }

        public static Bitmap comp(Bitmap image) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                        baos.reset();// 重置baos即清空baos
                        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
                }
                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                BitmapFactory.Options newOpts = new BitmapFactory.Options();
                // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
                newOpts.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
                newOpts.inJustDecodeBounds = false;
                int w = newOpts.outWidth;
                int h = newOpts.outHeight;
                // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
                float hh = 800f;// 这里设置高度为800f
                float ww = 480f;// 这里设置宽度为480f
                // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                int be = 1;// be=1表示不缩放
                if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                        be = (int) (newOpts.outWidth / ww);
                } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                        be = (int) (newOpts.outHeight / hh);
                }
                if (be <= 0)
                        be = 1;
                newOpts.inSampleSize = be;// 设置缩放比例
                // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
                isBm = new ByteArrayInputStream(baos.toByteArray());
                bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
                return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        }

        /**
         * bitmap ==> byte[]
         * @param bm
         * @return
         */
        public static byte[] Bitmap2Bytes(Bitmap bm) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                return baos.toByteArray();
        }

        public static byte[] getCompressedBitmap(String path, int targetW, int targetH, int maxBytes) {
                // Get the dimensions of the View
//		int targetW = 400;
//		int targetH = 800;

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytes = baos.toByteArray();
                return bytes;

        }
	
	/**
	 * @param drawable
	 * drawable ת  Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		// ȡ drawable �ĳ���
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		
		// ȡ drawable ����ɫ��ʽ
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// ������Ӧ bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// ������Ӧ bitmap �Ļ���
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// �� drawable ���ݻ���������
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 * @param bitmap
	 * @param roundPx
	 * ��ȡԲ��ͼƬ
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
