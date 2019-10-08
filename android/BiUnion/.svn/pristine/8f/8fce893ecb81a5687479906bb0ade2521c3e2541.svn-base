package com.bibinet.biunion.project.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.bibinet.biunion.BuildConfig;

import java.io.File;

public class ImagePickHelper {
	public static final int PHOTO_REQUEST_TAKEPHOTO = 3111;// 拍照
	public static final int PHOTO_REQUEST_GALLERY = 3112;// 从相册中选择
	public static final int PHOTO_REQUEST_CROP = 3113;// 裁剪
    private int aspectY;
    private int aspectX;
    private boolean mIsAvatar = false;
	private File mFile;
	    
	private Activity activity;
	private String preFileName;
//	    private String fileName;

	public ImagePickHelper(final Activity activity, final File file){
		this.activity = activity;
		this.mFile= file;
		delete(file);
		initOutWH(DensityUtil.getScreenWidth(activity), DensityUtil.getScreenHeight(activity));
	}

	public ImagePickHelper(final Activity activity, final File file, int aspectX, int aspectY){
		this.activity = activity;
		this.mFile= file;
		delete(file);
		initOutWH(aspectX, aspectY);
	}

	private void initOutWH(int aspectX, int aspectY) {
		this.aspectX = aspectX;
		this.aspectY = aspectY;
	}

	public ImagePickHelper(final Activity activity,final File file,boolean isAvatar){
		this.activity = activity;
		this.mFile= file;
		this.mIsAvatar = isAvatar;
		delete(file);
	}


//		public void showPickDialog(){
//	    	fileName = System.currentTimeMillis() + ".jpg";
////	    	if(preFileName != null && !preFileName.equals(""))
////	    		deletePreFile();
//	    	ContextThemeWrapper contextThemeWrapper =
//	    		    new ContextThemeWrapper(activity, R.style.dialog_list_style);
//
//	    	String[] items = new String[] {activity.getResources().getString(R.string.myProfile_edit_activity_takephoto),
//	    			activity.getResources().getString(R.string.myProfile_edit_activity_localphoto)};
//	    	DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					switch(which){
//					//拍照
//					case 0:
//						handleCameraClick(activity, fileName);
//						break;
//					//从本地选择图片
//					case 1:
//						handleAbulmClick(activity);
//						break;
//					default :
//						break;
//
//					}
//				}
//			};
//			new AlertDialog.Builder(contextThemeWrapper).setItems(items,
//				click).show();
//	    }
	    
	    /**
	     * 处理相册点击
	     * @param activity
	     */
	    public void handleAbulmClick(Activity activity) {
			Intent intent1 = new Intent(Intent.ACTION_PICK,
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 图片的存储路径
			activity.startActivityForResult(intent1, PHOTO_REQUEST_GALLERY);
		}

		/**
	     * 处理点击相机
	     * @param activity
	     */
		public void handleCameraClick(Activity activity) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				Uri contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider", mFile);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
				activity.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
			} else {
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
				activity.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
			}
		}

//	private void deleteFile() {
//		File file = mFile;
//		if(file.exists() && file.isFile()){
//			file.delete();
//		}
//	}
//
//	private void deletePreFile() {
//		File file = mFile;
//		if(file.exists() && file.isFile()){
//			file.delete();
//	    }
//	}
//
//	private void delete(String path){
//		File dir = new File(path);
//		if (!dir.exists()) {
//			return;
//		}
//		delete(dir);
//	}
	
	private void delete(File file) {  
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	} 

	public boolean handleActivityResult(int requestCode, int resultCode, Intent data, IfImagePickhelperCallback callback){
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case PHOTO_REQUEST_TAKEPHOTO:
					Uri outputUri = Uri.fromFile(mFile);
					activity.startActivityForResult(CutForCamera(mFile), PHOTO_REQUEST_CROP);
					break;
				case PHOTO_REQUEST_GALLERY:
					if (data == null || data.getData() == null) {
						return false;
					}
					activity.startActivityForResult(CutForPhoto(data.getData()), PHOTO_REQUEST_CROP);
					break;
				//裁剪图片
				case PHOTO_REQUEST_CROP:
					if (callback != null && resultCode == Activity.RESULT_OK) {
						callback.cropSuccess(mFile);
					}
					break;
				default:
					break;
			}
			return true;
		}
		return false;
	}

	/**
	 * 图片裁剪
	 * @param uri
	 * @return
	 */
	@NonNull
	private Intent CutForPhoto(Uri uri) {
		//直接裁剪
		Intent intent = new Intent("com.android.camera.action.CROP");
		//设置裁剪之后的图片路径文
		//初始化 uri
		Uri imageUri = uri; //返回来的 uri
		Uri outputUri = null; //真实的 uri
		outputUri = Uri.fromFile(mFile);
//			mCutUri = outputUri;

		return handleIntent(intent, imageUri, outputUri);
	}

	private Intent handleIntent(Intent intent, Uri imageUri, Uri outputUri) {

		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop",true);
		// aspectX,aspectY 是宽高的比例，这里设置正方形

		//设置要裁剪的宽高

		if (mIsAvatar) {
			intent.putExtra("aspectX",1);
			intent.putExtra("aspectY",1);
		} else {
			intent.putExtra("aspectX", aspectX);
			intent.putExtra("aspectY", aspectY);
		}
		intent.putExtra("scale",true);
		//如果图片过大，会导致oom，这里设置为false
		intent.putExtra("return-data",false);
		intent.putExtra("noFaceDetection", true);
		//压缩图片
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		if (imageUri != null) {
			intent.setDataAndType(imageUri, "image/*");
		}
		if (outputUri != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		}
		return intent;
	}

	/**
	 * 拍照之后，启动裁剪
	 *
	 * */
	@NonNull
	private Intent CutForCamera(File file) {
		//初始化 uri
		Uri imageUri = null; //返回来的 uri
		Uri outputUri = null; //真实的 uri
		//拍照留下的图片
		Intent intent = new Intent("com.android.camera.action.CROP");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			imageUri = FileProvider.getUriForFile(activity,
					BuildConfig.APPLICATION_ID + ".fileProvider",
					file);
		} else {
			imageUri = Uri.fromFile(file);
		}
		outputUri = Uri.fromFile(mFile);
		//把这个 uri 提供出去，就可以解析成 bitmap了
//			mCutUri = outputUri;

		return handleIntent(intent, imageUri, outputUri);

	}

	public interface IfImagePickhelperCallback {
			void cropSuccess(File file);
	}

}
