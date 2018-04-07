package com.hsbank.luxclub.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import com.hsbank.luxclub.util.constants.GlobalData;
import com.hsbank.util.java.http.UrlUtil;
import com.hsbank.util.java.tool.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单提交凭证图片公共类
 * 2016.5.18
 * zhuzhenghua
 */
public class ImageUtil {
	public static final int TAKE_BIG_PICTURE = 0;
	public static final int CHOOSE_BIG_PICTURE = 1;
	public static final int CROP_BIG_PICTURE = 2;

	private static Uri getImageUri() {
		return Uri.parse("file://" + getImageFilePathName());
	}

	public static void imageCrop(String filePathName, Context context,
			int aspectX, int aspectY, int outputX, int outputY, int requestCode) {
		if (context != null && filePathName != null) {
			File file = new File(filePathName);
			if (file.exists()) {
				Uri imageUri = Uri.fromFile(file);
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", aspectX);
				intent.putExtra("aspectY", aspectY);
				intent.putExtra("outputX", outputX);
				intent.putExtra("outputY", outputY);
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				intent.putExtra("return-data", false);
				intent.putExtra("outputFormat",
						Bitmap.CompressFormat.JPEG.toString());
				intent.putExtra("noFaceDetection", false);
				((Activity) context)
						.startActivityForResult(intent, requestCode);
			}
		}
	}

	public static Bitmap decodeFilePathAsBitmap(String filePathName) {
		return BitmapFactory.decodeFile(filePathName);
	}

	public static String getImageFilePathName() {
		String imageFilePathName = ProjectCache.FILE_PATH_PHOTO;
		FileUtil.createFile(imageFilePathName);
		return (imageFilePathName + GlobalData.getInstance().avatar);
	}

	public static String getNewImageFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return (dateFormat.format(date) + ".jpg");
	}

	public static void imageChooseSwitch(Context context, int requestCode) {
		if (context == null) {
			return;
		}
		Intent intent = null;
		switch (requestCode) {
		case TAKE_BIG_PICTURE:
			GlobalData.getInstance().avatar = getNewImageFileName();
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
			((Activity) context).startActivityForResult(intent,
					TAKE_BIG_PICTURE);
			break;

			// 对于MIUI系统会崩溃
		case CHOOSE_BIG_PICTURE:
			GlobalData.getInstance().avatar = getNewImageFileName();
			intent = new Intent(Intent.ACTION_PICK);
			// intent = new Intent(Intent.ACTION_GET_CONTENT);
			// intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 214);
			intent.putExtra("outputY", 214);
			intent.putExtra("scale", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
			intent.putExtra("return-data", false);
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", false);
			((Activity) context).startActivityForResult(intent,
					CHOOSE_BIG_PICTURE);
			break;

		default:
			break;
		}
	}

	public static String compressBitmapToFile(String filePathName) {
		return compressBitmapToFile(filePathName, "_COMPRESS", 90, 60, 10, 200);
	}

	public static String compressBitmapToFile(String filePathName,
			String compressExtend, int startOptions, int endOptions,
			int stepOptions, int compressSize) {
		String fileName = UrlUtil.getFileName(filePathName);
		String fileType = UrlUtil.getFileExt(filePathName);
		String newFileName = String.format("%s%s.%s",
				fileName.replace(String.format(".%s", fileType), ""),
				compressExtend, fileType);
		newFileName = filePathName.replace(fileName, newFileName);
		Bitmap bitmap = decodeFilePathAsBitmap(filePathName);
		File saveFile = new File(newFileName);
		String result = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, startOptions, byteArrayOutputStream);
		// 循环判断如果压缩后图片是否大于compressSize，并且startOptions大于endOptions，就继续压缩
		while (byteArrayOutputStream.toByteArray().length / 1024 > compressSize
				&& startOptions >= endOptions) {
			byteArrayOutputStream.reset();
			startOptions -= stepOptions;
			bitmap.compress(Bitmap.CompressFormat.JPEG, startOptions, byteArrayOutputStream);
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
			fileOutputStream.write(byteArrayOutputStream.toByteArray());
			fileOutputStream.flush();
			fileOutputStream.close();
			result = newFileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 图片压缩分辨率
	 * @param filePath
     */
	public static void zipImage(String filePath) {

        BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opt);

		int bmpWidth = opt.outWidth;
		int bmpHeght = opt.outHeight;

		int screenWidth = 600;
		int screenHeight = 600;

		opt.inSampleSize = 1;
		if (bmpWidth > bmpHeght) {
			if (bmpWidth > screenWidth)
				opt.inSampleSize = bmpWidth / screenWidth;
		} else {
			if (bmpHeght > screenHeight)
				opt.inSampleSize = bmpHeght / screenHeight;
		}
		opt.inJustDecodeBounds = false;

        int degree = readPictureDegree(filePath);
        LogUtil.i2("degree: " + degree);
		Bitmap bmp = BitmapFactory.decodeFile(filePath, opt);

		if (degree !=0){
            bmp = rotateImage(degree, bmp);
        }

		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			bmp.compress(Bitmap.CompressFormat.JPEG, 95, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        bmp.recycle();
		bmp = null;
	}

	public static void zipImage2(String savePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(savePath, options);
		options.inSampleSize = computeInitialSampleSize(options, 600, 600 * 600);
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(savePath, options);
		try {
			FileOutputStream fos = new FileOutputStream(savePath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bitmap.recycle();
		bitmap = null;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
										int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
												int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree 旋转角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle 旋转角度
     * @param bitmap 原图
     * @return bitmap 旋转后的图片
     */
    public static Bitmap rotateImage(int angle, Bitmap bitmap) {
        // 图片旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 得到旋转后的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}