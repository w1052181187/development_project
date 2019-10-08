package com.shenyuan.militarynews.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.chengning.common.app.ActivityInfo.ActivityState;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.IBaseActivity;
import com.chengning.common.util.PageHelper;
import com.google.gson.Gson;
import com.shenyuan.militarynews.Const.ArticleCommentType;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.adapter.ArticleCommentItemAdapter;
import com.shenyuan.militarynews.base.BaseListBean;
import com.shenyuan.militarynews.base.BasePageListActivity;
import com.shenyuan.militarynews.base.IScrollCallBack;
import com.shenyuan.militarynews.beans.data.CommentItemBean;
import com.shenyuan.militarynews.beans.data.CommentListBean;
import com.shenyuan.militarynews.utils.CommentCheckUtil;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.UmengShare;
import com.shenyuan.militarynews.views.CommentInputDialog;
import com.shenyuan.militarynews.views.CommentReplyInputDialog;
import com.shenyuan.militarynews.views.HomeNewContentPopup;
import com.shenyuan.militarynews.views.TitleBar;
import com.umeng.analytics.MobclickAgent;

public class ArticleCommentActivity extends BasePageListActivity<CommentItemBean>{
	
	private static final String TAG = ArticleCommentActivity.class.getSimpleName();
	
	protected static final int DATA_RED_COMMENT = 10003;
	
	private TitleBar mTitleBar;
	private View mInput;
	
	private String mId;
	
	private View mView;

	private BaseAdapter mAdapter;

	private boolean isFirstPage;
	
	private IScrollCallBack mScrollCallBack;
	private Button mTopBtn;

	public static void launch(Activity from, String id){

		Intent intent = new Intent(from, ArticleCommentActivity.class);
		intent.putExtra("id", id);
		from.startActivity(intent);
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putString("id", mId); 
    	
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mId = savedInstanceState.getString("id");
    }
    
    @Override
    public void onStop(){
    	CommentCheckUtil.onActivityStop();
    	super.onStop();
    }

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg0) {
		case CommentCheckUtil.REQUEST_CODE_LOGIN_COMMENT:
			Runnable skipRunnable = new Runnable() {
				
				@Override
				public void run() {
					if(getActivityInfo().getActivityState() != ActivityState.Killed){
						CommentInputDialog dialog = new CommentInputDialog();
						dialog.setAid(mId);
						dialog.showAllowingStateLoss((BaseFragmentActivity)getActivity(), getActivity().getSupportFragmentManager(),
							CommentInputDialog.class.getSimpleName());
					}
				}
			};
			CommentCheckUtil.onActivityResult(arg0, arg1, arg2, getHandler(), skipRunnable);
			break;
		case CommentCheckUtil.REQUEST_CODE_LOGIN_COMMENT_CHILD:
			try {
				final CommentItemBean bean = (CommentItemBean) arg2.getExtras().getSerializable("bean");
				CommentCheckUtil.onActivityResult(arg0, arg1, arg2, getHandler(), new Runnable() {
					
					@Override
					public void run() {
						if(getActivityInfo().getActivityState() != ActivityState.Killed){
							CommentReplyInputDialog dialog = new CommentReplyInputDialog();
							dialog.setData(bean);
							dialog.showAllowingStateLoss((BaseFragmentActivity)getActivity(), (getActivity()).getSupportFragmentManager(),
								CommentReplyInputDialog.class.getSimpleName());
						}
					
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}
	
	@Override
	public BaseFragmentActivity buildContext() {
		return ArticleCommentActivity.this;
	}

	@Override
	public String buildUrl() {
		String url = JUrl.SITE + JUrl.URL_GET_COMMENT_REPLY_LIST + mId;
//		url = JUrl.appendNum(url, 20);
		return url;
	}

	@Override
	public BaseAdapter buildAdapter(FragmentActivity activity, List list) {
		return new ArticleCommentItemAdapter(activity, list, mId);
	}

	@Override
	public String buildMaxId(List list) {
		String maxId = "";
		for (CommentItemBean bean : (ArrayList<CommentItemBean>)list) {
			if (bean.getShow_type() == ArticleCommentType.COMMENT_TYPE_COMMON_NEWEST) {
				maxId = bean.getAid();
			}
		}
		return maxId;
	}

	@Override
	public String configTitle() {
		return "全部评论";
	}

	@Override
	public String configNoData() {
		return "暂时还没有评论";
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_article_comment, null, false);
		return mView;
	}

	@Override
	public void initExtraView() {
		mInput = mView.findViewById(R.id.article_comment_input);
		mTopBtn = (Button) mView.findViewById(R.id.topbtn);
	}

	@Override
	public void initExtraListener() {
		mInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (getActivityInfo().getActivityState() == ActivityState.SaveInstanceStated){
					return;
				}
//				if(CommentCheckUtil.checkLoginNotNeededOfComment(getActivity())){
					CommentInputDialog dialog = new CommentInputDialog();
					dialog.setAid(mId);
					dialog.showAllowingStateLoss((BaseFragmentActivity) getActivity(), getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
//				}
			}
		});
		
		mTopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				forceTop();
				mTopBtn.setVisibility(View.GONE);
			}
		});
		
	}

	@Override
	public void handleItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	@Override
	public BaseListBean<CommentItemBean> handleHttpSuccess(Gson gson,
			String data) {
		CommentListBean bean = gson.fromJson(data, CommentListBean.class);
		getHandler().obtainMessage(DATA_RED_COMMENT, bean.getHot_list()).sendToTarget();
		setTitleBar(bean);
		
		if (isFirstPage) {
			ArrayList<CommentItemBean> list = bean.getList();
			if (!Common.isListEmpty(list)) {
				CommentItemBean firstBean = (CommentItemBean) list.get(0);
				firstBean.setShow_type(ArticleCommentType.COMMENT_TYPE_COMMON_NEWEST);
				list.set(0, firstBean);
			}
			ArrayList<CommentItemBean> hotList = bean.getHot_list();
			if (!Common.isListEmpty(hotList)) {
				CommentItemBean hotFirstBean = (CommentItemBean) hotList.get(0);
				hotFirstBean.setShow_type(ArticleCommentType.COMMENT_TYPE_COMMON_HOT);
				list.addAll(0,hotList);
			}
			
			bean.setList(list);
		}
		return bean;
	}
	
	
	@Override
	public void getDataByHttp(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
		super.getDataByHttp(isFirstPage);
	}
	
	private void setTitleBar(CommentListBean bean) {
		mTitleBar.setTitle(new StringBuilder("全部评论(").append(bean.getCount()).append(")").toString());
	}

	@Override
	public void initExtraData() {
		CommentCheckUtil.onActivityCreate();
		mTitleBar = getTitleBar();
		mId = getIntent().getStringExtra("id");
		HomeNewContentPopup.handleFirstCommentList(getActivity(), mView, getHandler());
		mScrollCallBack = new IScrollCallBack() {
			@Override
			public void show() {
				mTopBtn.setVisibility(View.VISIBLE);
			}
			@Override
			public void hidden() {
				mTopBtn.setVisibility(View.GONE);
			}
		};
		setmScrollCallback(mScrollCallBack);
	}

	
}
