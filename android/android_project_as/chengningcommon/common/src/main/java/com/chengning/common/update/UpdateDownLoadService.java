package com.chengning.common.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.chengning.common.R;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;

/**
 * 
<service android:name="com.chengning.common.update.UpdateDownLoadService" >
</service>
 *
 */
public class UpdateDownLoadService extends Service {
	
	private static final String TAG = UpdateDownLoadService.class.getSimpleName();
	
	private static final int MSG_DOWNLOADING = 1;
	private static final int MSG_DOWNLOAD_FINISH = 2;
	private static final int MSG_ERROR = -1;
	
	private static final int DOWNLOAD_SUCCESS = 1;
	private static final int DOWNLOAD_ERROR = -1;
	
	private NetworkInfo info;
	private NotificationManager notificationMrg;
	private PendingIntent contentIntent;
	private ConnectivityManager connectivityManager;
	
	private HashMap<Integer, UpdateDownloadTask> taskMap = new HashMap<Integer, UpdateDownLoadService.UpdateDownloadTask>();
	
	public static void startDownloadApkService(Context context, String url, String title){
		startDownloadApkService(context, url, title, null, null);
	}
	
	public static void startDownloadApkService(Context context, String url, String title, String md5, String target_size){
		Intent intent = new Intent(context, UpdateDownLoadService.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		intent.putExtra("md5", md5);
		intent.putExtra("target_size", target_size);
		context.startService(intent);
	}
	public void onCreate() {
		super.onCreate();
		notificationMrg = (NotificationManager) this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		
		Intent notificationIntent = new Intent(getApplicationContext(), this.getClass());
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
		
		// 注册网络监听接收器  
		IntentFilter mFilter = new IntentFilter();  
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);  
//		mFilter.addAction("android.basic.notification.click.pause");  
//		mFilter.addAction("android.basic.notification.click.cancel");  
//		mFilter.addAction("android.basic.notification.click.restart");  
		registerReceiver(stateReceiver, mFilter);  
		// 注册任务广播接收器  
	}  
      
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String url = intent.getStringExtra("url");
		int id = getStringId(url);
		if(!taskMap.containsKey(id)){
			UpdateDownloadTask task = new UpdateDownloadTask(intent.getStringExtra("url"), intent.getStringExtra("title"),
					intent.getStringExtra("md5"), intent.getStringExtra("target_size"));
			taskMap.put(id, task);
			task.init();
		}
		return START_NOT_STICKY;
	}
  
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(stateReceiver); 
	}
      
//  状态栏视图更新
	private void displayNotificationMessage(int id, String progress, String title) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setOngoing(true);//出现在 “正在运行的”栏目下面  
		builder.setContentTitle(title);
		builder.setContentText("当前进度：" + progress + "% ");
		builder.setContentIntent(contentIntent);
		Notification notification = builder.build();

		notificationMrg.notify(id, notification);
		//setLatestEventInfo只在2.3.3以下才支持，不考虑
//		notification.setLatestEventInfo(getApplicationContext(), title, "当前进度：" + count + "% ", contentIntent);
//      提交一个通知在状态栏中显示。如果拥有相同标签和相同id的通知已经被提交而且没有被移除，该方法会用更新的信息来替换之前的通知。        
//		notificationMrg.notify(flag, notification);
	}
	
	private void dismissNotificationMessage(int id) {        
		notificationMrg.cancel(id);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
      
	// 定义一个Handler，用于处理下载线程与主线程间通讯
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			
			if (!Thread.currentThread().isInterrupted()) {
//                              判断下载线程是否中断
				UpdateDownloadTask task =  (UpdateDownloadTask) msg.obj;
				switch (msg.what) {
				case MSG_DOWNLOADING:
					String progress =  String.valueOf(msg.arg1);
					displayNotificationMessage(task.id, progress, task.title);
					break;
				case MSG_DOWNLOAD_FINISH:
					dismissNotificationMessage(task.id);
					openfile(task.downloadFile);
					taskMap.remove(task.id);
					break;
				case MSG_ERROR:
					dismissNotificationMessage(task.id);
					taskMap.remove(task.id);
					Toast.makeText(getApplication(), "下载异常终止",Toast.LENGTH_SHORT).show();  
					break;
				}
			}
			super.handleMessage(msg);
		}
	};
	
	/**
	 * 打开安装包文件
	 * @param file
	 */
	private void openfile(File file){
		try {
			Uri uri = Uri.fromFile(file);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setDataAndType(uri,"application/vnd.android.package-archive");
			startActivity(intent);
			
			stopSelf();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
      
	private BroadcastReceiver stateReceiver = new BroadcastReceiver() {  
	  
		@Override  
		public void onReceive(Context context, Intent intent) {  
			String action = intent.getAction();  
	    
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				Log.d("mark", "网络状态已经改变");  
				connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
				info = connectivityManager.getActiveNetworkInfo();  
				if (info != null && info.isAvailable()) {  
					String name = info.getTypeName();  
					Log.d("mark", "当前网络名称：" + name);
				} else {  
					Log.d("mark", "没有可用网络,下载服务停止");  
//					while (flag > 0) {  
//						notificationMrg.cancel(flag);  
//						flag -= 1;  
//					}  
//					Toast.makeText(getApplicationContext(), "网络异常，下载服务停止！", Toast.LENGTH_SHORT).show();  
//					onDestroy();  
				}  
			}  
    
//			if (action.equals("android.basic.notification.click.cancel")) {  
//				Bundle bundle = intent.getExtras();  
//				int m = bundle.getInt("flag");  
//				String url = intent.getStringExtra("url");  
//				Log.i("avi", "cancel   clickbroadcast中         接收到的 flag: " + m);  
//				Log.i("avi", "cancel   clickbroadcast中         接收到的 url: "  
//						+ url);  
//				notificationMrg.cancel(m);  
////				if (threadcache.containsKey(url)) {  
////					MyThread onethread = threadcache.get(url);  
////					Log.i("avi", "cancel  clickbroadcast中 thread 线程      url: "  
////							+ onethread.geturl());  
////					onethread.setchanger(false);  
////					Log.i("avi",  
////							"cancel   clickbroadcast中 thread 线程      url: "  
////									+ onethread.getchanger());  
////				}  
//			}  
//    
//			if (action.equals("android.basic.notification.click.pause")) {  
//				Bundle bundle = intent.getExtras();  
//				int m = bundle.getInt("flag");  
//				String url = intent.getStringExtra("url");  
//				Log.i("avi", "pause  clickbroadcast中         接收到的 flag: " + m);  
//				Log.i("avi", "pause  clickbroadcast中         接收到的 url: " + url);  
//			}  
//    
//			if (action.equals("android.basic.notification.click.restart")) {  
//				Bundle bundle = intent.getExtras();  
//				int m = bundle.getInt("flag");  
//				String url = intent.getStringExtra("url");  
//				Log.i("avi", "restart  clickbroadcast中         接收到的 flag: " + m);  
//				Log.i("avi", "restart  clickbroadcast中         接收到的 url: "  
//						+ url);  
//			}  
		}  
	}; 
    	  
	
	private String getUrlFileName(String url) {
		String filename;
		int first = url.lastIndexOf("/") + 1;
		filename = url.substring(first, url.length()).toLowerCase();
		return filename;
	}
	
	private File getDownloadFileDir(){
		File downloadDir = null;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			downloadDir = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS);
		}
		if(downloadDir == null){
			downloadDir = getCacheDir();
		}
		return downloadDir;
	}
	
	private static int getStringId(String str){
		return str != null ? str.hashCode() : 0;
	}
	
	class UpdateDownloadTask {
		
		private Context context;
		
		private int id;
		
		private String url;
		private String title;
		private String md5;
		private String mTargetSize;
		
		private String filename; 
		private File downloadDir;
		private File downloadFile;
		
		private long lastProgressTime;
		private int lastProgress = -1;
		
		public UpdateDownloadTask(String url, String title, String md5, String targetSize){
			this.url = url;
			this.title = title;
			this.md5 = md5;
			this.mTargetSize = targetSize;
		}
		
		public void init(){
			if(!TextUtils.isEmpty(url)){
				id = getStringId(url);
				filename = getUrlFileName(url);
				
				//创建文件
				downloadDir = getDownloadFileDir();
				downloadFile = new File(downloadDir.getPath(), filename);
				
//				notification =  new Notification();
//				notification.icon = R.drawable.ic_launcher;
//				notification.tickerText = title;
//				notification.flags |= Notification.FLAG_ONGOING_EVENT;//出现在 “正在运行的”栏目下面  
		      
//				displayNotificationMessage("0", filename);
				new Thread(new UpdateRunnable(this)).start();
			}
		}
		
		class UpdateRunnable implements Runnable {
			
			private UpdateDownloadTask task;
			
			UpdateRunnable(UpdateDownloadTask task){
				this.task = task;
			}
			
			public void run() {
	            long size = 0; 
	            if(!downloadDir.exists()){
	            	downloadDir.mkdirs();
				}
//	            if(!downloadFile.exists()){
//	            	try {
//						downloadFile.createNewFile();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//	            	size = 0;
//				} else {
//	                size = downloadFile.length();  
//	            }  
	            deleteFileSafely(downloadFile);
	        	try {
					downloadFile.createNewFile();
		            
		            int value = doDownloadTheFile_test(url, downloadFile, size, 3);
		            if (value == DOWNLOAD_SUCCESS) {
		            	Message msg = new Message();  
		                msg.what = MSG_DOWNLOAD_FINISH;
						msg.obj = task;
		                handler.sendMessage(msg);  
		            } else {
		            	Message msg = new Message();  
		                msg.what = MSG_ERROR;
						msg.obj = task;
		                handler.sendMessage(msg);   
		            } 
				} catch (Exception e) {
					e.printStackTrace();

					try {
		            	Message msg = new Message();  
		                msg.what = MSG_ERROR;
						msg.obj = task;
		                handler.sendMessage(msg); 
					} catch (Exception e2) {
						e.printStackTrace();
					}
				}
			}
		}

		/** 
	     * 作用：断点续传下载<BR> 
	     * 作者：peijiangping<BR> 
	     * 日期：2011-6-11 
	     */  
	    @SuppressWarnings({ "unused", "resource" })
		private int doDownloadTheFile_test(String strPath, File file, long size, int retryCount) {
	    	retryCount--;
	    	
	        //file.size()即可得到原来下载文件的大小  
	    	int percent = 0;
	        //下载路径  
	        String url = strPath;  
	        // 设置代理  
	        Header header = null;  
	        HttpResponse response = null;  
	        // 用来获取下载文件的大小  
	        HttpResponse response_test = null;  
	        InputStream is = null;
	        RandomAccessFile fos = null;
	        try {
	            HttpClient client = new DefaultHttpClient();  
	            HttpClient client_test = new DefaultHttpClient();  
	            HttpGet request = new HttpGet(url);  
	            HttpGet request_test = new HttpGet(url);  
	            if (header != null) {  
	                request.addHeader(header);  
	            }  
	            response_test = client_test.execute(request_test);  
	            //获取需要下载文件的大小  
	            long fileSize = response_test.getEntity().getContentLength();  
	            // 验证下载文件的完整性
	            if (fileSize != 0 && fileSize == size) {
	                return 1;  
	            }  
	            //设置下载的数据位置XX字节到XX字节  
	            Header header_size = new BasicHeader("Range", "bytes=" + size + "-"  
	                    + fileSize);  
	            request.addHeader(header_size);  
	            response = client.execute(request);  
	            is = response.getEntity().getContent();  
	            if (is == null) {  
	                throw new RuntimeException("stream is null");  
	            }  
//	            SDCardUtil.createFolder(filePath);  
	            //获取文件对象，开始往文件里面写内容  
	            fos = new RandomAccessFile(file, "rw");  
	            //从文件的size以后的位置开始写入，其实也不用，直接往后写就可以。有时候多线程下载需要用  
	            fos.seek(size);  
	            byte buf[] = new byte[1024 * 8];  
	            long downloadfilesize = 0;
	            do {  
	                int numread = is.read(buf);  
	                if (numread <= 0) {  
	                    break;  
	                }  
	                fos.write(buf, 0, numread);  
	                Message msg = new Message();  
	                downloadfilesize += numread;
	                percent = (int) ((downloadfilesize*100 + size)/fileSize);
					//为了防止频繁的通知导致应用吃紧，百分比增加5才通知一次
	                long currentTime = System.currentTimeMillis();
					boolean needNotifyProgress = (percent == 0) || (percent >= 99) ||
							((percent != lastProgress) && ((currentTime - lastProgressTime) >= 1000));
					if(needNotifyProgress){ 
						lastProgress = percent;
						lastProgressTime = currentTime;
		                msg.what = MSG_DOWNLOADING;
		                msg.arg1 = percent;
						msg.obj = this;
		                handler.sendMessage(msg);// 更新下载进度百分比  
					} 
	                
	            } while (true);  
	            
	            // check size
	            long endSize = file.length();
	            long endTargetSize = 0;
	            try {
	            	if(!TextUtils.isEmpty(mTargetSize)){
	                	endTargetSize = Long.parseLong(mTargetSize);
	            	}
				} catch (Exception e) {
					e.printStackTrace();
				}
	            if(endTargetSize != 0 && endSize != endTargetSize){
	            	Log.e(TAG, "Download file " + file.getPath() + " size not match.");
	            	
	            	if(retryCount <= 0){
	                    deleteFileSafely(file);
	                    return DOWNLOAD_ERROR;
	            	}else{
	                    deleteFileSafely(file);
	                    file.createNewFile();
	                    return doDownloadTheFile_test(strPath, file, 0, retryCount);
	            	}
	            }
	            // check md5
	            String endMd5 = UpdateUtil.getFileMD5(file);
	            if(!TextUtils.isEmpty(md5) && (endMd5 == null || !endMd5.equalsIgnoreCase(md5))){
	            	Log.e(TAG, "Download file " + file.getPath() + " md5 not match.");
	            	
	            	if(retryCount <= 0){
	                    deleteFileSafely(file);
	                    return DOWNLOAD_ERROR;
	            	}else{
	                    deleteFileSafely(file);
	                    file.createNewFile();
	                    return doDownloadTheFile_test(strPath, file, 0, retryCount);
	            	}
	            }
	            
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	            return DOWNLOAD_ERROR;
	        } finally {
				try {
					if(is != null) is.close();
		            if(fos != null)fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	        }
	        return DOWNLOAD_SUCCESS;  
	    } 
	    
	    public boolean deleteFileSafely(File file) {
	        if (file != null) {
	            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
	            File tmp = new File(tmpPath);
	            file.renameTo(tmp);
	            return tmp.delete();
	        }
	        return false;
	    }
	}
	
	
}
