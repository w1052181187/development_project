package com.chengning.fenghuovideo.util;

import android.app.Activity;
import android.os.HandlerThread;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.SerializeUtil;
import com.chengning.common.util.ThreadHelper;
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.LoginManager;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.data.access.LocalStateDA;
import com.chengning.fenghuovideo.data.bean.ArticleDigBean;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.widget.CommentReportDialog;
import com.chengning.fenghuovideo.widget.CommentReportDialog.CommentReportConfirmListener;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;

public class ArticleManagerUtils {

	private CollectState mCollectState;
	private HandlerThread mFavHandle;

	public ArticleManagerUtils() {

	}


	public ArticleManagerUtils(CollectState collectState) {
		this.mCollectState = collectState;
	}

	/**
	 *
	 * @param state 收藏状态
	 */

	public void setCollectState(final boolean state) {
		// 改变收藏图标
		if (mCollectState == null) {
			return;
		}
		mCollectState.setState(state);

	}

	/**
	 * 取消收藏
	 */
	public void cancleCollect(final Activity context, final BaseArticlesBean bean){
		if(bean == null){
			return;
		}

		if (App.getInst().isLogin()) {
			if (!Common.hasNet()) {
				Common.showHttpFailureToast(context);
				return;
			}
			cancleCollectByHttp(context, bean);

		} else {
			cancleCollectByDB(context, bean);
		}

	}

	/**
	 * 未登录取消收藏
	 * @param context
	 * @param bean
	 */
	public void cancleCollectByDB(final Activity context, final BaseArticlesBean bean) {
		if (mFavHandle == null) {
			mFavHandle = ThreadHelper.creatThread("my_fav_handle");
		}

		ThreadHelper.handle(mFavHandle, new Runnable() {

			@Override
			public void run() {
				LocalStateDA.getInst(context).deleteFavArticle(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid());
			}
		});
		setCollectState(false);
	}

	/**
	 * 登录取消收藏
	 * @param bean
	 * @param context
	 */
	public void cancleCollectByHttp(final Activity context, final BaseArticlesBean bean) {
		RequestParams params = new RequestParams();
		params.put("tid", bean.getTid());
		HttpUtil.post(context, JUrl.SITE + JUrl.URL_CANCEL_COLLECT, params, new MyJsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {
				Common.showHttpFailureToast(context);
			}

			@Override
			public void onDataSuccess(int status, String mod,
									  String message, String data, JSONObject obj) {
				//TODO 取消成功
				setCollectState(false);
			}

			@Override
			public void onDataFailure(int status, String mod,
									  String message, String data, JSONObject obj) {
				//TODO 取消失败
				UIHelper.showToast(context, message);
			}
		});
	}

	/**
	 * 收藏
	 * @param
	 */
	public void doCollect(final Activity context, final BaseArticlesBean bean){
		if(null == bean){
			return;
		}
		if (App.getInst().isLogin()) {
			if (!Common.hasNet()) {
				Common.showHttpFailureToast(context);
				return;
			}
			doCollectByHttp(context, bean);

		} else {
			doCollectByDB(context, bean);
		}

	}

	/**
	 * 未登录收藏
	 * @param bean
	 * @param context
	 */
	public void doCollectByDB(final Activity context, final BaseArticlesBean bean) {
		if (mFavHandle == null) {
			mFavHandle = ThreadHelper.creatThread("my_fav_handle");
		}

		ThreadHelper.handle(mFavHandle, new Runnable() {

			@Override
			public void run() {
					LocalStateDA.getInst(context).saveFavArticle(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid(), SerializeUtil.serialize(bean));
			}
		});
		setCollectState(true);
	}

	/**
	 * 登录之后收藏
	 * @param bean
	 * @param context
	 */
	public void doCollectByHttp(final Activity context, final BaseArticlesBean bean) {
		RequestParams params = new RequestParams();
		params.put("tid", bean.getTid());
		params.put("is_subscribe",1);

		HttpUtil.post(context, JUrl.SITE + JUrl.URL_DO_COLLECT, params, new MyJsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {
				Common.showHttpFailureToast(context);
			}

			@Override
			public void onDataSuccess(int status, String mod,
									  String message, String data, JSONObject obj) {
				//TODO 收藏成功
				setCollectState(true);
			}

			@Override
			public void onDataFailure(int status, String mod,
									  String message, String data, JSONObject obj) {
				//TODO 收藏失败
				UIHelper.showToast(context, message);
			}
		});
	}

	public void destoryFavHandleThread(){
		ThreadHelper.destory(mFavHandle);
	}

	/**
	 * 删除讨论或者评
	 * @param context
	 * @param tid
	 * @param r
	 */
	public void deleteDynamic(final Activity context, final String tid, final DeleteListener r) {
		RequestParams params = new RequestParams();
		params.put("tid", tid);
		
		HttpUtil.post(context, JUrl.SITE + JUrl.URL_DO_DELETE_DYNAMIC, params, new MyJsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {
				Common.showHttpFailureToast(context);
			}

			@Override
			public void onDataSuccess(int status, String mod,
									  String message, String data, JSONObject obj) {
				UIHelper.showToast(context, message);
				if (r != null) {
					r.deleteSuccess(data);
				}
			}

			@Override
			public void onDataFailure(int status, String mod,
									  String message, String data, JSONObject obj) {
				UIHelper.showToast(context, message);
			}
		});
	}
	
	
	/**
	 * 获取举报内容
	 * @param context
	 * @param tid
	 */
	public void getReport(final BaseFragmentActivity context, final String tid) {
		
		if (!Common.hasNet()) {
         	Common.showHttpFailureToast(context);
         	return;
		} 
		if (LoginManager.getInst().checkLoginWithNotice(context)) {
			
			try {
				JSONObject reason =  new JSONObject(SPHelper.getInst().getString(SPHelper.KEY_REPORT_REASON));
				getReportSuccess(context, tid, reason);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void getReportSuccess(final BaseFragmentActivity mContext, final String tid, final JSONObject json) throws JSONException{
		if (null == json) {
			return;
		}
		int size = json.length();
		if(0 == size){
			Common.showHttpFailureToast(mContext);
			return;
		}
		final String[] keyArray = new String[size];
		final String[] strs = new String[size + 1];
		int index = 0;
		Iterator iterator = json.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			keyArray[index] = key; 
			index++;
		}
		Arrays.sort(keyArray);
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			String value = json.getString(key);
			strs[i] = value;
		}
		strs[size] = "取消";
		
		CommentReportDialog mReportDialog = new CommentReportDialog();
		mReportDialog.setData(strs, new CommentReportConfirmListener() {
			
			@Override
			public void confirm(int pos) {
				RequestParams params = new RequestParams();
		    	params.put("totid", tid);
		    	params.put("report_reason", keyArray[pos]);
		    	String url = JUrl.SITE + JUrl.URL_DO_REPORT;
		    	inform(mContext, url, params);
			}
		});
		mReportDialog.showAllowingStateLoss(mContext, mContext.getSupportFragmentManager(), CommentReportDialog.class.getSimpleName());
	}

	/**
	 * 举报
	 * @param url
	 * @param params
	 */
	public void inform(final Activity mContext, final String url, final RequestParams params) {
		if (!Common.hasNet()) {
         	Common.showHttpFailureToast(mContext);
         	return;
		} 
		HttpUtil.post(url, params, new MyJsonHttpResponseHandler() {
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse){
				Common.showHttpFailureToast(mContext);
			} 
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(mContext, "举报成功");
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(mContext, message);
			}
		});
	
		
	}
	
	/**
	 * 赞
	 * @param mContext
	 * @param bean
	 */
	public void doLike(final Activity mContext, final BaseArticlesBean bean, final DataStateListener state){
		if (null == bean) {
			return;
		}
		if (!Common.hasNet()) {
         	Common.showHttpFailureToast(mContext);
         	return;
		} 
		if (Common.isTrue(bean.getIs_dig())) {
   		 	UIHelper.showToast(mContext, "您已经赞过了");
		} else {
			if (state == null) {
				return;
			}
			state.init();
			String url = JUrl.SITE + JUrl.URL_DO_LIKE;
			RequestParams params = new RequestParams();
			params.put("tid", bean.getTid());
			params.put("touid", bean.getUid());
			HttpUtil.post(mContext, url, params, new MyJsonHttpResponseHandler() {

				@Override
				public void onFailure(int statusCode, Header[] headers,
									  Throwable throwable, JSONObject errorResponse) {
					Common.showHttpFailureToast(mContext);
					state.failure();
				}

				@Override
				public void onDataSuccess(int status, String mod,
										  String message, String data, JSONObject obj) {
					Gson gson = new Gson();
					ArticleDigBean digBean = gson.fromJson(data, ArticleDigBean.class);

//	     			LocalStateDA.getInst(mContext).setLikeStateTrue(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid());

					state.success(String.valueOf(digBean.getDigcounts()));
				}

				@Override
				public void onDataFailure(int status, String mod,
										  String message, String data, JSONObject obj) {
					UIHelper.showToast(mContext, message);
					state.failure();
				}
			});
		}
    }
	/**
	 * 取消赞
	 * @param mContext
	 * @param bean
	 */
	public void cancleLike(final Activity mContext, final BaseArticlesBean bean, final DataStateListener state){
		if (null == bean) {
			return;
		}
		if (!Common.hasNet()) {
         	Common.showHttpFailureToast(mContext);
         	return;
		} 
//		if (Common.isTrue(bean.getIs_dig())) {
//   		 	UIHelper.showToast(mContext, "您已经踩过了");
//		} else {
			if (state == null) {
				return;
			}
			state.init();
			String url = JUrl.SITE + JUrl.URL_CANCLE_LIKE;
			RequestParams params = new RequestParams();
			params.put("tid", bean.getTid());
			params.put("touid", bean.getUid());
			HttpUtil.post(mContext, url, params, new MyJsonHttpResponseHandler() {
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse){
					Common.showHttpFailureToast(mContext);
					state.failure();
				} 
				
				@Override
				public void onDataSuccess(int status, String mod,
						String message, String data, JSONObject obj) {
					Gson gson = new Gson();
					ArticleDigBean digBean =  gson.fromJson(data, ArticleDigBean.class);
					
//	     			LocalStateDA.getInst(mContext).setLikeStateTrue(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid());

					state.success(String.valueOf(digBean.getDigcounts()));
				}

				@Override
				public void onDataFailure(int status, String mod,
						String message, String data, JSONObject obj) {
					UIHelper.showToast(mContext, message);
					state.failure();
				}
			});
//		}
    }

	/**
	 * 不喜欢
	 * @param mContext
	 * @param bean
	 */
	public void doNotLike(final Activity mContext, final BaseArticlesBean bean, final DataStateListener state){
		if (null == bean) {
			return;
		}
		if (!Common.hasNet()) {
			Common.showHttpFailureToast(mContext);
			return;
		}
		if (Common.isTrue(bean.getIs_bad())) {
   		 	UIHelper.showToast(mContext, "您已经踩过了");
		} else {
			state.init();
			String url = JUrl.SITE + JUrl.URL_DONOT_LIKE;
			RequestParams params = new RequestParams();
			params.put("tid", bean.getTid());
			HttpUtil.post(mContext, url, params, new MyJsonHttpResponseHandler() {

				@Override
				public void onFailure(int statusCode, Header[] headers,
									  Throwable throwable, JSONObject errorResponse){
					Common.showHttpFailureToast(mContext);
					state.failure();
				}

				@Override
				public void onDataSuccess(int status, String mod,
										  String message, String data, JSONObject obj) {
					Gson gson = new Gson();
					ArticleDigBean digBean =  gson.fromJson(data, ArticleDigBean.class);

					state.success(String.valueOf(digBean.getBadcounts()));
				}

				@Override
				public void onDataFailure(int status, String mod,
										  String message, String data, JSONObject obj) {
					UIHelper.showToast(mContext, message);
					state.failure();
				}
			});
		}
	}

	public interface CollectState {
		void setState(boolean state);
	}

	public interface DeleteListener {
		void deleteSuccess(String data);
	}

	public interface DataStateListener {
		void init();
		void success(Object... objects);
		void failure();
	}
}
