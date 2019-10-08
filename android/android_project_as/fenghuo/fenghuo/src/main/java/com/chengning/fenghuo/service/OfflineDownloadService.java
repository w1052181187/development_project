package com.chengning.fenghuo.service;

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
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.adapter.ChannelItemRecommendAdapter;
import com.chengning.fenghuo.data.access.ChannelItemListDA;
import com.chengning.fenghuo.data.access.ChannelListDA;
import com.chengning.fenghuo.data.access.LocalStateDA;
import com.chengning.fenghuo.data.bean.ArticleItemPicBean;
import com.chengning.fenghuo.data.bean.ArticlesBean;
import com.chengning.fenghuo.data.bean.ArticlesPicBean;
import com.chengning.fenghuo.data.bean.ChanBean;
import com.chengning.fenghuo.data.bean.ChannelBean;
import com.chengning.fenghuo.data.bean.ChannelItemBean;
import com.chengning.fenghuo.data.bean.SubscribeContentItemBean;
import com.chengning.fenghuo.fragment.ColumnFragment;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.SPHelper;
import com.google.gson.Gson;
import com.loopj.android.http.SyncHttpClient;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OfflineDownloadService extends Service {
	
	private static final String TAG = OfflineDownloadService.class.getSimpleName();
	
	private static final boolean DEBUG = Consts.DEBUG;
	
	private Context mContext;
	
	private OnOfflineDownloadListener mListener;
	private OfflineDownloadState mState = OfflineDownloadState.Init;
	
	private int mProgress;
	
	private Handler mHandler;
	
	private OfflineDownloadTask mTask;

	private boolean mIsCanceled = false;
	
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
		if(mTask == null || mTask.isCancelled() || mIsCanceled ){
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
			
			// 读取频道列表
			if(DEBUG)Log.d(TAG, "OfflineDownloadTask 读取频道列表");
			
			List<ChanBean> chanList = ChannelListDA.getInst(mContext).queryMyChannelList();
			ArrayList<ChannelDownloadBean> channelDownloadList = new ArrayList<ChannelDownloadBean>();
			if(Common.isListEmpty(chanList)){
				mProgress = 100;
				publishProgress(mProgress);
				return null;
			}
			
			pi.setValue(pi.next, 2, chanList.size(), 0);
			for(ChanBean bean : chanList){
				final ChannelDownloadBean dBean = new ChannelDownloadBean();
				if(bean.getLocal_my_index() == 1){
					// 首页
					dBean.setChannel("recommend");
					dBean.setChannelSlide("recommend_slide");
					dBean.setBean(bean);
					dBean.setChannelItemList(new ArrayList<ChannelItemBean>());
					dBean.setEmpty(false);
				}else{
					// 其他频道
					dBean.setChannel("channel_" + bean.getId());
					dBean.setChannelSlide("channel_" + bean.getId() + "_slide");
					dBean.setBean(bean);
					dBean.setChannelItemList(new ArrayList<ChannelItemBean>());
					dBean.setEmpty(false);
				}
				
				int page = JUrl.PAGE_START;
				List<ChannelItemBean> channelItemList = ChannelItemListDA.getInst(mContext).queryChannelItemList(dBean.getChannel(), page);
				if(Common.isListEmpty(channelItemList)){
					dBean.setEmpty(true);
				}else{
					dBean.setEmpty(false);
					
					boolean hasNextPage;
					do {
						// slide数据
						List<ChannelItemBean> channelItemSlideList = ChannelItemListDA.getInst(mContext).queryChannelItemList(dBean.getChannelSlide(), page);
						dBean.getChannelItemList().addAll(channelItemList);
						dBean.getChannelItemList().addAll(channelItemSlideList);
						// 下一页
						page++;
						channelItemList = ChannelItemListDA.getInst(mContext).queryChannelItemList(dBean.getChannel(), page);
						hasNextPage = !Common.isListEmpty(channelItemList);
					} while (hasNextPage);
				}
				channelDownloadList.add(dBean);

				pi.publishIndexIncrement();
			}
			
			downLoadDujiaChannel(pi, channelDownloadList);
			
			pi.publishNext();
			
			if(privatePause()){
				return null;
			}
			
			// 下载频道
			if(DEBUG)Log.d(TAG, "OfflineDownloadTask 下载频道");
			
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
			
			// 下载文章
			if(DEBUG)Log.d(TAG, "OfflineDownloadTask 下载文章");
			
			int totalSize = 0;
			for(ChannelDownloadBean bean : channelDownloadList){
				List<ChannelItemBean> channelItemList = bean.getChannelItemList();
				if(!Common.isListEmpty(channelItemList)){
					totalSize += channelItemList.size();
				}
			}
			pi.setValue(pi.next, 100, totalSize, 0);
			for(ChannelDownloadBean bean : channelDownloadList){
				List<ChannelItemBean> channelItemList = bean.getChannelItemList();
				if(!Common.isListEmpty(channelItemList)){
					for(ChannelItemBean item : channelItemList){
						if(privatePause()){
							return null;
						}
						if(!TextUtils.isEmpty(item.getImage_list())){
							downloadChannelItemImage(item);
						}else if(!Common.isListEmpty(item.getImage_arr())){
							downloadChannelItemImages(item);
						}
						if(Consts.CONTENT_TYPE_TUWEN.equals(item.getContent_type()) 
								&& LocalStateDA.getInst(mContext).isArticleOfTuWenSaved(LocalStateDA.PREFIX_CHANNEL_ITEM_PIC, item.getTid())){
								
						}else if(LocalStateDA.getInst(mContext).isArticleSaved(LocalStateDA.PREFIX_CHANNEL_ITEM, item.getTid())){
							
						}else{
							downloadArticle(item);
						}
						
						if(privatePause()){
							return null;
						}
						pi.publishIndexIncrement();
					}
				}
			}
			pi.publishNext();
			if(DEBUG)Log.d(TAG, "OfflineDownloadTask 全部完成");
			
			return null;
		}
		
		/**
		 * 缓存原创频道
		 * @param pi 
		 */
		private void downLoadDujiaChannel(
				ProgressInt pi, ArrayList<ChannelDownloadBean> channelDownloadList) {
			final ChannelDownloadBean dBean = new ChannelDownloadBean();
			ChanBean bean = new ChanBean();
			bean.setUrl("topic_1.php?code=recdtopicnew&act=dujia");
			
			// 原创频道
			dBean.setChannel("tab_video");
			dBean.setChannelSlide("tab_video" + "_slide");
			dBean.setBean(bean);
			dBean.setChannelItemList(new ArrayList<ChannelItemBean>());
			dBean.setEmpty(false);
			
			int page = JUrl.PAGE_START;
			List<ChannelItemBean> channelItemList = ChannelItemListDA.getInst(mContext).queryChannelItemList(dBean.getChannel(), page);
			if(Common.isListEmpty(channelItemList)){
				dBean.setEmpty(true);
			}else{
				dBean.setEmpty(false);
				
				boolean hasNextPage;
				do {
					// slide数据
					List<ChannelItemBean> channelItemSlideList = ChannelItemListDA.getInst(mContext).queryChannelItemList(dBean.getChannelSlide(), page);
					dBean.getChannelItemList().addAll(channelItemList);
					dBean.getChannelItemList().addAll(channelItemSlideList);
					// 下一页
					page++;
					channelItemList = ChannelItemListDA.getInst(mContext).queryChannelItemList(dBean.getChannel(), page);
					hasNextPage = !Common.isListEmpty(channelItemList);
				} while (hasNextPage);
			}
			channelDownloadList.add(dBean);

			pi.publishIndexIncrement();
		}

		@Override
		protected void onProgressUpdate(Integer... values){
			OfflineDownloadService.this.mProgress = values[0];
			setState(OfflineDownloadState.Downloading, values[0]);
		}

		@Override
		protected void onPostExecute(String result){
			// TODO(清除缓存时也需要清除sp数据)
			SPHelper.getInst().saveLong(SPHelper.KEY_OFFLINE_DOWNLOAD_TIME, System.currentTimeMillis());
			mTask = null;
			setState(OfflineDownloadState.Finish);
		}
		
		private boolean downloadChannel(final ChannelDownloadBean dBean){
			if(DEBUG)Log.d(TAG, "downloadChannel " + dBean.getChannel() + " start");
			
			final boolean result[] = new boolean[1];
			result[0] = false;
			SyncHttpClient http = new SyncHttpClient();
			http.setTimeout(5000);
			http.get(JUrl.appendPage(JUrl.SITE + dBean.getBean().getUrl(), JUrl.PAGE_START),
					new MyJsonHttpResponseHandler() {
				
				@Override
		        public void onFailure(int statusCode, Header[] headers,Throwable throwable,JSONObject errorResponse) {
					if(DEBUG)Log.d(TAG, "downloadChannel " + dBean.getChannel() + " onFailure");
		        };
				
				@Override
				public void onDataSuccess(int status, String mod, String message,
						String data, JSONObject obj) {
					if(DEBUG)Log.d(TAG, "downloadChannel " + dBean.getChannel() + " onDataSuccess");
					
		            Gson gson = new Gson();
					ChannelBean mChannelBean = gson.fromJson(data, ChannelBean.class);
//					List<ChannelItemBean> srcList = mChannelBean.getList();
					List<ChannelItemBean> topiclist = new ArrayList<ChannelItemBean>();
					List<ChannelItemBean> list = new ArrayList<ChannelItemBean>();
					if (mChannelBean.getAll_data() != null) {
						if (mChannelBean.getAll_data().getSlide() != null) {
							topiclist = mChannelBean.getAll_data().getSlide();
						}
						if (mChannelBean.getAll_data().getRecd_topic() != null) {
							list = mChannelBean.getAll_data().getRecd_topic();
						}						
					}
					List<ChannelItemBean> srcList = mChannelBean.getList();
					if (srcList != null) {
						for (ChannelItemBean b : srcList) {
							if (b.getIs_slide() == Common.TRUE) {
								topiclist.add(b);
							} else {
								list.add(b);
							}
						}
					} else {
						srcList = new ArrayList<ChannelItemBean>();
					}
					if(topiclist != null && list != null){
						srcList.addAll(topiclist);
						srcList.addAll(list);
						String oldMaxid = null;
//		    			String newMaxid = !Common.isListEmpty(aBean.getItem()) ? aBean.getItem().get(0).getAid() : null;
		    			String newMaxid = ChannelItemRecommendAdapter.getNewMaxIdNoAd(list);
		    			String maxid = Common.isTrue(mChannelBean.getIs_recom()) ? oldMaxid : newMaxid;
		    			SPHelper.getInst().saveInt(SPHelper.PREFIX_KEY_CHANNEL_IS_RECOM + dBean.getChannel(), mChannelBean.getIs_recom());
		            	SPHelper.getInst().saveString(SPHelper.PREFIX_KEY_CHANNEL_MAXID + dBean.getChannel(), maxid);
		            	SPHelper.getInst().saveLong(SPHelper.PREFIX_TIME_UPDATE_CHANNEL + dBean.getChannel(), System.currentTimeMillis());
		            	
		            	ChannelItemListDA.getInst(mContext).setChannelAndPage(list, dBean.getChannel(), JUrl.PAGE_START);
		            	ChannelItemListDA.getInst(mContext).clearChannel(dBean.getChannel());
		            	ChannelItemListDA.getInst(mContext).insertChannelItemList(list, dBean.getChannel(), JUrl.PAGE_START);
		            	
		            	SPHelper.getInst().saveInt(SPHelper.PREFIX_KEY_CHANNEL_MAXPAGE + dBean.getChannel(), mChannelBean.getTotal_page());
		            	
						ChannelItemListDA.getInst(mContext).setChannelAndPage(topiclist, dBean.getChannelSlide(), JUrl.PAGE_START);
						ChannelItemListDA.getInst(mContext).clearChannel(dBean.getChannelSlide());
						ChannelItemListDA.getInst(mContext).insertChannelItemList(topiclist, dBean.getChannelSlide(), JUrl.PAGE_START);
						
						dBean.setChannelItemList(srcList);
						result[0] = true;
					}
					if(!Common.isListEmpty(mChannelBean.getColumnlist())){
						ChannelItemListDA.getInst(mContext).setChannelAndPage(mChannelBean.getColumnlist(),
								ColumnFragment.KEY_CHANNEL_SLIDE, JUrl.PAGE_START);
						ChannelItemListDA.getInst(mContext).clearChannel(
								ColumnFragment.KEY_CHANNEL_SLIDE);
						ChannelItemListDA.getInst(mContext).insertChannelItemList(
								mChannelBean.getColumnlist(), ColumnFragment.KEY_CHANNEL_SLIDE, JUrl.PAGE_START);
						downloadColumuSlideImage(mChannelBean.getColumnlist());
					}
				}
				
				@Override
				public void onDataFailure(int status, String mod, String message,
						String data, JSONObject obj) {
					if(DEBUG)Log.d(TAG, "downloadChannel " + dBean.getChannel() + " onDataFailure");
				}
			});
			return result[0];
		}
		
		private void downloadChannelItemImage(final ChannelItemBean bean){
			// 下载图片
			String img = bean.getImage_list();
			if(!TextUtils.isEmpty(img)){
				privateDownloadImage(img);
			}
		}
		private void downloadChannelItemImages(final ChannelItemBean bean){
			List<String> imgs = bean.getImage_arr();
			if(!Common.isListEmpty(imgs)){
				for(String img : imgs){
					privateDownloadImage(img);
				}
			}
		}
		private void downloadColumuSlideImage(final ArrayList<ChannelItemBean> list){
			if(!Common.isListEmpty(list)){
				for(ChannelItemBean bean : list){
					if(!TextUtils.isEmpty(bean.getImages())){
						privateDownloadImage(bean.getImages());
					}
				}
			}
		}
		private void downloadArticle(final ChannelItemBean bean){
			if(DEBUG)Log.d(TAG, "downloadArticle " + bean.getTid() + " start");
			
	        final String aid = String.valueOf(bean.getTid());
			String url = JUrl.SITE + JUrl.URL_GET_ARTICLES_SUBCSRIBE + aid;
			BasicHeader header = new BasicHeader("Accept-Encoding", "gzip, deflate");
			Header[] headers = {new BasicHeader("Accept", "*/*"), header,
					new BasicHeader("Accept-Language", "zh-cn")};
			
			SyncHttpClient http = new SyncHttpClient();
			http.setTimeout(5000);
			http.get(mContext, url, headers, null, new MyJsonHttpResponseHandler() {
				
				@Override
		        public void onFailure(int statusCode, Header[] headers,Throwable throwable,JSONObject errorResponse) { 
					if(DEBUG)Log.d(TAG, "downloadArticle " + bean.getTid() + " onFailure");
		        };
				
				@Override
				public void onDataSuccess(int status, String mod, String message,
						String data, JSONObject obj) {
					if(DEBUG)Log.d(TAG, "downloadArticle " + bean.getTid() + " onDataSuccess");
					
		            Gson gson = new Gson();
		            if(Consts.CONTENT_TYPE_TUWEN.equals(bean.getContent_type())){
						ArticlesPicBean aBean = gson.fromJson(data, ArticlesPicBean.class);
						if(aBean == null){
							return;
						}
						LocalStateDA.getInst(mContext).setArticle(LocalStateDA.PREFIX_CHANNEL_ITEM_PIC, bean.getTid(), SerializeUtil.serialize(aBean));
					
						downloadArticleOfTuWenImage(aBean);
		    		}else{
		    			ArticlesBean aBean = gson.fromJson(data, ArticlesBean.class);
						if(aBean == null){
							return;
						}
		           		LocalStateDA.getInst(mContext).setArticle(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid(), SerializeUtil.serialize(aBean));
		           		
		           		downloadArticleImage(aBean);
		    		}
	           		
				}
				
				@Override
				public void onDataFailure(int status, String mod, String message,
						String data, JSONObject obj) {
					if(DEBUG)Log.d(TAG, "downloadArticle " + bean.getTid() + " onDataFailure");
				}
			});
		}
		
		public void downloadArticleOfTuWenImage(final ArticlesPicBean bean){
			//下载图片
			if(bean.getImage()!=null){
				privateDownloadImage(bean.getImage());
			}
			if(bean.getContent() != null){
				for(ArticleItemPicBean b : bean.getContent()){
					privateDownloadImage(b.getSrc());
				}
			}
		}
		
		private void downloadArticleImage(final ArticlesBean bean){
			// 下载图片
			ArrayList<SubscribeContentItemBean> content = bean.getContent();
			if(!Common.isListEmpty(content)){
				for(SubscribeContentItemBean item : content){
					if("img".equals(item.getType()) && !TextUtils.isEmpty(item.getSrc())){
						privateDownloadImage(item.getSrc());
					}
				}
			}
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
			
			String channel;
			String channelSlide;
			ChanBean bean;
			boolean isEmpty;
			List<ChannelItemBean> channelItemList;
			
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
			
			public ChanBean getBean() {
				return bean;
			}
			
			public void setBean(ChanBean bean) {
				this.bean = bean;
			}
			
			public boolean isEmpty() {
				return isEmpty;
			}
			
			public void setEmpty(boolean isEmpty) {
				this.isEmpty = isEmpty;
			}

			public List<ChannelItemBean> getChannelItemList() {
				return channelItemList;
			}

			public void setChannelItemList(List<ChannelItemBean> channelItemList) {
				this.channelItemList = channelItemList;
			}
			
		}
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

	public void privateDownloadImage(String url) {
		if(!Common.isTrue(SettingManager.getInst().getNoPicModel())){
			ImageLoader.getInstance().loadImageSync(url);
		}
	}

}
