package com.shenyuan.militarynews.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.chengning.common.util.SerializeUtil;
import com.google.gson.Gson;
import com.loopj.android.http.SyncHttpClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.MyJsonHttpResponseHandler;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.adapter.AdChannelItemRecommendAdapter;
import com.shenyuan.militarynews.beans.data.ArticleItemPicBean;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.ArticlesPicBean;
import com.shenyuan.militarynews.beans.data.MChannelBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.beans.data.MChannelNavBean;
import com.shenyuan.militarynews.data.access.ChannelItemListServer;
import com.shenyuan.militarynews.data.access.LocalStateServer;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.SPHelper;

public class OfflineDownloadService extends Service{
	private static final String TAG = OfflineDownloadService.class.getSimpleName();

	private Context mContext;
	
	private OnOfflineDownloadListener mListener;
	private OfflineDownloadState mState = OfflineDownloadState.Init;
	
	private int mProgress;
	
	private Handler mHandler;
	
	private OfflineDownloadTask mTask;
	
	private boolean mIsCanceled = false;
	
	private ArrayList<MChannelNavBean> channelList ;
	@Override
	public void onCreate(){
		super.onCreate();
		mContext = this;
		mHandler = new Handler();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new LocalBinder();
	}
	
	public void setOnOfflineDownloadListener(OnOfflineDownloadListener listener){
		this.mListener = listener;
	}
	
	public OfflineDownloadState getState(){
		return mState;
	}
	
	public int getProgress(){
		return mProgress;
	}
	
	public void setState(OfflineDownloadState state){
		setState(state, null);
	}
	
	public void setState(OfflineDownloadState state, Object obj){
		this.mState = state;
		if(mListener != null){
			mListener.onState(state, obj);
		}
	}
	
	public void startOrResume(){
		if(mTask == null || mTask.isCancelled() || mIsCanceled){
			mIsCanceled = false;
			mTask = new OfflineDownloadTask();
			mTask.start();
		}else{
			mTask.resume();
		}
	}
	
	public void pause(){
		if(mTask != null){
			mTask.pause();
		}
	}
	public void cancel(){
		if(mTask != null){
			mIsCanceled = true;
			mTask.cancel();
		}
	}
	
	private class OfflineDownloadTask extends AsyncTask<String, Integer, String> {

		private volatile boolean isPause = false;
		
		public void start(){
			mProgress = 0;
			isPause = false;
			execute(null, null);
		}

		public void pause(){
			isPause = true;
		}
		
		public void resume(){
			isPause = false;
		}
		
		public void cancel() {
			cancel(true);
			isPause = false;
			mProgress = 0;
			setState(OfflineDownloadState.Init, mProgress);
		}
		
		private boolean privatePause(){
			// 暂停
			while (isPause) {
				// 取消
				if(isCancelled()){
					return true;
				}
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						setState(OfflineDownloadState.Pause, mProgress);
					}
				});
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 判断网络，无网则等待
			while(!Common.hasNet()){
				if(isCancelled()){
					return true;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(isCancelled()){
				return true;
			}
			return false;
		}
		@Override
		protected void onPreExecute(){
			setState(OfflineDownloadState.Init);
		}

		@Override
		protected String doInBackground(String... params) {
			if(privatePause()){
				return null;
			}
			ProgressInt pi = new ProgressInt();
			pi.init();
			pi.publishNext();
			
			//读取频道列表
			ArrayList<ChannelDownloadBean> channelDownloadList = new ArrayList<ChannelDownloadBean>();
			setChannelList();
			pi.setValue(pi.next, 2, channelList.size(), 0);
			for(int i=0;i<channelList.size();i++){

				ChannelDownloadBean dBean = new ChannelDownloadBean();
				
				if(channelList.get(i).getName().equals(Const.CHANNEL_TUIJIAN)){
					// 首页
					dBean.setName(channelList.get(i).getName());
					dBean.setChannel("recommend");
					dBean.setChannelSlide("recommend_slide");
					dBean.setUrl(channelList.get(i).getUrl());
					dBean.setChannelItemList(new ArrayList<MChannelItemBean>());
					dBean.setEmpty(false);
				}else if(channelList.get(i).getShowtype() == Const.CHANNEL_SHOW_TYPE_ZILIAOKU){
					// 资料库
					continue;
				}else{
					// 其他频道
					dBean.setName(channelList.get(i).getName());
					dBean.setChannel("channel_" + channelList.get(i).getName());
					dBean.setChannelSlide(dBean.getChannel() + "_slide");
					dBean.setUrl(channelList.get(i).getUrl());
					dBean.setChannelItemList(new ArrayList<MChannelItemBean>());
					dBean.setEmpty(false);
				}
				
				int page = JUrl.PAGE_START;
				List<MChannelItemBean> channelItemList = ChannelItemListServer.getInst(mContext).queryChannelItemList(dBean.getChannel(), page);
			
				if(Common.isListEmpty(channelItemList)){
					dBean.setEmpty(true);
				}else{
					dBean.setEmpty(false);
					boolean hasNextPage;
					do{
						// slide数据
						List<MChannelItemBean> channelItemSlideList = ChannelItemListServer.getInst(mContext).queryChannelItemList(dBean.getChannelSlide(), page);
						dBean.getChannelItemList().addAll(channelItemList);
						dBean.getChannelItemList().addAll(channelItemSlideList);
						
						// 下一页
						page++;
						channelItemList = ChannelItemListServer.getInst(mContext).queryChannelItemList(dBean.getChannel(), page);
						hasNextPage = !Common.isListEmpty(channelItemList);
					}while(hasNextPage);
				}
				channelDownloadList.add(dBean);
				pi.publishIndexIncrement();
			}
			pi.publishNext();
			if(privatePause()){
				return null;
			}
			//下载频道
			pi.setValue(pi.next, 5, channelDownloadList.size(), 0);
			for(ChannelDownloadBean bean : channelDownloadList){
				if(bean.isEmpty()){
					if(privatePause()){
						return null;
					}
					boolean isSuccess = downloadChannel(bean);
					if(!isSuccess){
						downloadChannel(bean);
					}
					if(privatePause()){
						return null;
					}
				}
				pi.publishIndexIncrement();
			}
			pi.publishNext();
			if(privatePause()){
				return null;
			}
			//下载文章
			int totalSize =0;
			for(ChannelDownloadBean bean : channelDownloadList){
				List<MChannelItemBean> channelItemList = bean.getChannelItemList();
				if(!Common.isListEmpty(channelItemList)){
					totalSize += channelItemList.size();
				}
			}
			pi.setValue(pi.next, 100, totalSize, 0);
			for(ChannelDownloadBean bean : channelDownloadList){
				Log.d(TAG, "download " + bean.getChannel() + " list start");

				List<MChannelItemBean> channelItemList = bean.getChannelItemList();
				if(!Common.isListEmpty(channelItemList)){
					for(MChannelItemBean item : channelItemList){
						if(privatePause()){
							return null;
						}
						downloadMChannelItemImage(item);
						if(Const.CHANNEL_ARTICLE_TUWEN.equals(item.getChannel()) 
								&& LocalStateServer.getInst(mContext).isArticleOfTuWenSaved(LocalStateServer.PREFIX_CHANNEL_ITEM_PIC, item.getAid())){
								
						}else if(LocalStateServer.getInst(mContext).isArticleSaved(LocalStateServer.PREFIX_CHANNEL_ITEM, item.getAid())){
							
						}else{
							downloadArticle(item);
						}
						if(privatePause()){
							return null;
						}
						pi.publishIndexIncrement();
					}
				}
				Log.d(TAG, "download " + bean.getChannel() + " list end");
			}
			pi.publishNext();
			return null;
		}
		@Override
		protected void onPostExecute(String result){
			SPHelper.getInst().saveLong(SPHelper.KEY_OFFLINE_DOWNLOAD_TIME, System.currentTimeMillis());
			mTask = null;
			setState(OfflineDownloadState.Finish);
		}
		
		@Override
		protected void onProgressUpdate(Integer... values){
			OfflineDownloadService.this.mProgress = values[0];
			setState(OfflineDownloadState.Downloading, values[0]);
		}
		
		private void privateDownloadImage(String url){
			if(!Common.isTrue(SettingManager.getInst().getNoPicModel())){
				ImageLoader.getInstance().loadImageSync(url);
			}
		}
		
		private void downloadArticle(final MChannelItemBean bean){
			  final String aid = String.valueOf(bean.getAid());
			  String url = JUrl.SITE + JUrl.URL_GET_ARTICLE_NEW + aid;
			  url = JUrl.appendType(url, bean.getChannel());
			  BasicHeader header = new BasicHeader("Accept-Encoding", "gzip, deflate");
			  Header[] headers = {new BasicHeader("Accept", "*/*"), header,
						new BasicHeader("Accept-Language", "zh-cn")};
				
				SyncHttpClient http = new SyncHttpClient();
				http.setTimeout(5000);
				
//				Log.d(TAG, "downloadArticle url: " + url);
				http.get(mContext, url.toString(), headers, null, new MyJsonHttpResponseHandler() {
					
					@Override
			        public void onFailure(int statusCode, Header[] headers,Throwable throwable,JSONObject errorResponse) { 
//						if(DEBUG)Log.d(TAG, "downloadArticle " + bean.getTid() + " onDataFailure");
			        }
			        @Override 
			        public void onSuccess(JSONObject response) {
			        	try {
				            Gson gson = new Gson();
				            String data = response.optString("data");

				    		if(Const.CHANNEL_ARTICLE_TUWEN.equals(bean.getChannel())){
								ArticlesPicBean aBean = gson.fromJson(data, ArticlesPicBean.class);
								if(aBean == null){
									return;
								}
								aBean.setTid(bean.getAid());
								LocalStateServer.getInst(mContext).setArticle(LocalStateServer.PREFIX_CHANNEL_ITEM_PIC, bean.getAid(), SerializeUtil.serialize(aBean));
							
								downloadArticleOfTuWenImage(aBean);
				    		}else{
				    			ArticlesBean aBean = gson.fromJson(data, ArticlesBean.class);
								if(aBean == null){
									return;
								}
				           		aBean.setTid(bean.getAid());
				           		LocalStateServer.getInst(mContext).setArticle(LocalStateServer.PREFIX_CHANNEL_ITEM, bean.getAid(), SerializeUtil.serialize(aBean));
				           		
				           		downloadArticleImage(aBean);
				    		}
						} catch (Exception e) {
							e.printStackTrace();
						}
			        }
				});
		}
		private void downloadArticleImage(final ArticlesBean bean){
			//下载图片
			if(bean.getImage()!=null){
				privateDownloadImage(bean.getImage());
			}
			if(bean.getPics() !=null && bean.getPics().size() > 0){
				ArrayList<String> picList = bean.getPics();
				for(int i=0;i<picList.size();i++){
					privateDownloadImage(picList.get(i));
				}
			}
		}
		public void downloadArticleOfTuWenImage(final ArticlesPicBean bean){
			//下载图片
			if(bean.getImage()!=null){
				privateDownloadImage(bean.getImage());
			}
			if(bean.getPics() != null){
				for(ArticleItemPicBean b : bean.getPics()){
					privateDownloadImage(b.getUrl());
				}
			}
		}
		private void downloadMChannelItemImage(final MChannelItemBean bean){
			if(bean.getImage()!=null ){
				privateDownloadImage(bean.getImage());
			}
			if(bean.getImage_arr() != null && bean.getImage_arr().size()>0){
				ArrayList<String> picList = bean.getImage_arr();
				for(String imgUrl : picList){
					privateDownloadImage(imgUrl);
				}
			}
		}
		
		private boolean downloadChannel(final ChannelDownloadBean dBean){
			final boolean result[] = new boolean[1];
			result[0] = false;
			
			SyncHttpClient http = new SyncHttpClient();
			http.setTimeout(5000);
			if (!TextUtils.isEmpty(dBean.getUrl())) {
			http.get(JUrl.appendPage(JUrl.SITE + JUrl.API + dBean.getUrl(), JUrl.PAGE_START), new MyJsonHttpResponseHandler(){
				  @Override 
			      public void onSuccess(JSONObject response){
					  try {
						  	Gson gson = new Gson();
						  	String data = response.optString("data");
			           		MChannelBean aBean = gson.fromJson(data, MChannelBean.class);
							if(aBean == null){
								return;
							}
			    			String oldMaxid = null;
//			    			String newMaxid = !Common.isListEmpty(aBean.getItem()) ? aBean.getItem().get(0).getAid() : null;
			    			String newMaxid = AdChannelItemRecommendAdapter.getNewMaxIdNoAd(aBean.getItem());
			    			String maxid = Common.isTrue(aBean.getIs_recom()) ? oldMaxid : newMaxid;
			    			SPHelper.getInst().saveInt(SPHelper.PREFIX_KEY_CHANNEL_MAXPAGE + dBean.getChannel(), aBean.getMaxpage());
			            	SPHelper.getInst().saveInt(SPHelper.PREFIX_KEY_CHANNEL_IS_RECOM + dBean.getChannel(), aBean.getIs_recom());
			            	SPHelper.getInst().saveString(SPHelper.PREFIX_KEY_CHANNEL_MAXID + dBean.getChannel(), maxid);
			            	SPHelper.getInst().saveLong(SPHelper.PREFIX_TIME_UPDATE_CHANNEL + dBean.getChannel(), System.currentTimeMillis());
			            	
			           		ChannelItemListServer.getInst(mContext).setChannelAndPage(aBean.getItem(), dBean.getChannel(), JUrl.PAGE_START);
			           		ChannelItemListServer.getInst(mContext).clearChannel( dBean.getChannel());
			           		ChannelItemListServer.getInst(mContext).insertChannelItemList(aBean.getItem(),  dBean.getChannel(), JUrl.PAGE_START);

			           		ChannelItemListServer.getInst(mContext).setChannelAndPage(aBean.getSlide(),dBean.getChannelSlide(), JUrl.PAGE_START);
			           		ChannelItemListServer.getInst(mContext).clearChannel(dBean.getChannelSlide());
			           		ChannelItemListServer.getInst(mContext).insertChannelItemList(aBean.getSlide(), dBean.getChannelSlide(), JUrl.PAGE_START);

			           		result[0]= true;
			           		
			           		dBean.setChannelItemList(aBean.getItem());
					  } catch (Exception e) {
						  e.printStackTrace();
					  }
				  } 
				  @Override
				  public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
				}
			});
			}			
			return result[0];
		}
		
		private class ProgressInt{
			private float current;
			private float next;
			private float size;
			private float index;
			
			private void init(){
				current = 0;
				next = 0;
				size = 0;
				index = 0;
			}
			
			private void setValue(float current, float next, float size, float index){
				this.current = current;
				this.next = next;
				this.size = size;
				this.index = index;
			}
			
			private int getProgressInt(){
				return (int) (current + (index / size * (next - current)) + 0.5f);
			}
			
			private void publishNext(){
				mProgress = (int)next;
				publishProgress(mProgress);
			}
			
			private void publishIndexIncrement(){
				index++;
				mProgress = getProgressInt();
				publishProgress(mProgress);
			}
		}

		private class ChannelDownloadBean{
			
			private String name;
			private String channel;
			private String channelSlide;
			private String url;
			private boolean isEmpty;
			private List<MChannelItemBean> channelItemList;
			
			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getChannel() {
				return channel;
			}
			
			public void setChannel(String channel) {
				this.channel = channel;
			}
			
			public String getChannelSlide() {
				return channelSlide;
			}
			
			public void setChannelSlide(String channelSlide) {
				this.channelSlide = channelSlide;
			}
			
		
			public boolean isEmpty() {
				return isEmpty;
			}
			
			public void setEmpty(boolean isEmpty) {
				this.isEmpty = isEmpty;
			}

			public List<MChannelItemBean> getChannelItemList() {
				return channelItemList;
			}

			public void setChannelItemList(List<MChannelItemBean> channelItemList) {
				this.channelItemList = channelItemList;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}
			
		}
	
	}


	public void setChannelList(){
		String chanStrOld = SPHelper.getInst().getString(SPHelper.KEY_CHANNEL_LIST);
		if(!TextUtils.isEmpty(chanStrOld)){
			channelList= SerializeUtil.deSerialize(chanStrOld, ArrayList.class);
		}
		
		if(Common.isListEmpty(channelList)){
			channelList = new ArrayList<MChannelNavBean>();
		}
		
		// 固定频道-热点
		MChannelNavBean bean = new MChannelNavBean();
		bean.setName(Const.CHANNEL_REDIAN);
		bean.setUrl(JUrl.SITE + JUrl.URL_GET_CHANNEL_REDIAN);
		channelList.add(bean);
		
	}
	public class LocalBinder extends Binder{
		
		public OfflineDownloadService getService(){
			return OfflineDownloadService.this;
		}
	}
	
	public static enum OfflineDownloadState{
		Init,
		Downloading,
		Pause,
		Finish,
		;
	}
	
	public static interface OnOfflineDownloadListener{
		public void onState(OfflineDownloadState state, Object obj);
	}

}
