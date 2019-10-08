package com.cmstop.jstt.utils;

import java.io.File;
import java.io.FileNotFoundException;

import com.cmstop.jstt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.view.WindowManager.LayoutParams;

public class ImagePickHelper {
	   	public static final int PHOTO_REQUEST_TAKEPHOTO = 3111;// 拍照
	   	public static final int PHOTO_REQUEST_GALLERY = 3112;// 从相册中选择
	   	public static final int PHOTO_REQUEST_CROP = 3113;// 裁剪
	   	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";//temp file
	   	Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap
	    
	    private Activity activity;
	    private String preFileName;
	    private String fileName;
	    
	    public ImagePickHelper(final Activity activity,final String fileName){
	    	this.activity = activity;
	        this.fileName = fileName;
	        delete(JUrl.FilePathTemp);
	    }
	    
	    public ImagePickHelper() {
		}

		public void showPickDialog(){
	    	fileName = System.currentTimeMillis() + ".jpg";
//	    	if(preFileName != null && !preFileName.equals(""))
//	    		deletePreFile();
	    	ContextThemeWrapper contextThemeWrapper = 
	    		    new ContextThemeWrapper(activity, R.style.dialog_list_style);
	    	
	    	String[] items = new String[] {activity.getResources().getString(R.string.myProfile_edit_activity_takephoto),
	    			activity.getResources().getString(R.string.myProfile_edit_activity_localphoto)};
	    	DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(which){
					//拍照
					case 0:
						handleCameraClick(activity, fileName);
						break;
					//从本地选择图片
					case 1:
						handleAbulmClick(activity);
						break;
					default :
						break;
					
					}
				}
			};
			new AlertDialog.Builder(contextThemeWrapper).setItems(items,
				click).show();
	    }
	    
	    /**
	     * 处理相册点击
	     * @param activity
	     */
	    public void handleAbulmClick(Activity activity) {
	    	Intent intentFromGallery = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			activity.startActivityForResult(intentFromGallery,
					PHOTO_REQUEST_GALLERY);
		}

		/**
	     * 处理点击相机
	     * @param activity
	     */
		public void handleCameraClick(Activity activity, String fileName) {
			Intent intentFromCapture = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(Common.creatFile(JUrl.FilePathTemp,fileName)));
			activity.startActivityForResult(intentFromCapture,
					PHOTO_REQUEST_TAKEPHOTO);
		}

	private void deleteFile() {
		File file = Common.creatFile(JUrl.FilePathTemp,fileName);
		if(file.exists() && file.isFile()){
			file.delete();
		}
	}
		
	private void deletePreFile() {
		File file = Common.creatFile(JUrl.FilePathTemp, preFileName);
		if(file.exists() && file.isFile()){
			file.delete();
	    }
	}
	
	private void delete(String path){
		File dir = new File(path);
		if (!dir.exists()) {
			return;
		}
		delete(dir);
	}
	
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

	public boolean handleActivityResult(int requestCode, int resultCode, Intent data){
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			File file = Common.creatFile(JUrl.FilePathTemp,fileName);
			try {
				if(file.exists() && file.isFile()){
		            startPhotoCrop(Uri.fromFile(file), 200);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case PHOTO_REQUEST_GALLERY:
			if (data != null && data.getData() != null) {
	            startPhotoCrop(data.getData(), 200);
			}
			break;
		case PHOTO_REQUEST_CROP:
//		        if (data != null && data.getExtras() != null) {
//		            Bitmap face = data.getExtras().getParcelable("data");
//		            if(face != null){
//						ImageUtils.compressBmpFromBmp(face, Common.creatFile(JUrl.FilePathTemp,fileName));
//						return true;
//		            }
//		        }
			if (imageUri != null) {
				Bitmap bitmap = decodeUriAsBitmap(imageUri);// decode bitmap
				if (bitmap != null) {
					ImageUtils.compressBmpFromBmp(bitmap,
							Common.creatFile(JUrl.FilePathTemp, fileName));
					return true;
				}
			}
			break;
		}
		return false;
	}
	
	private void startPhotoCrop(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, PHOTO_REQUEST_CROP);
    }
	
	private Bitmap decodeUriAsBitmap(Uri uri){
	    Bitmap bitmap = null;
	    try {
	        bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    }
	    return bitmap;
	}
	public String getFileName(){
		return fileName;
	}
}
