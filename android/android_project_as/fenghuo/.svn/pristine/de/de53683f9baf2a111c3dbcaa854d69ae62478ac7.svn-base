package com.chengning.fenghuo.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.widget.MultiStateView;
import com.chengning.common.widget.MultiStateView.ViewState;
import com.chengning.fenghuo.LoadStateManager;
import com.chengning.fenghuo.LoadStateManager.LoadState;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.DirectorateTaskBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.TitleBar;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

/**
 * @description 任务详情活动
 * @author wangyungang
 * @date 2015.7.15 14:56
 *
 */
public class DirectorateTaskDetailActivity extends BaseActivity {

	private static final  int DATA_SUCCESS = 0;
	private static final int HTTP_FAIL = 1;
	
	private static final String TASK_EVERYDAY_LOGIN = "login";
//	private static final int TASK_EVERYDAY_SIGN = 2;
//	private static final int TASK_POST_DISCUSS = 2;
	private static final String TASK_SHARE_DISCUSS = "topic";
//	private static final int TASK_FORWARD_DISCUSS = 4;
	private static final String  TASK_CMT_DISCUSS = "reply";
	
	/**
	 * 任务类型：1：每日登录 2：每日签到 3：发布圈子 4：分享圈子 5：转发圈子 6：评论圈子
	 */
//	private int actionType;
	private TitleBar mTitleBar;
	
	private LinearLayout mLayout;
	private TextView mCountTv;
	private TextView mPointTv;
//	private TextView mMoneyTv;
	private String action;
	private DirectorateTaskBean bean;
	private LoadStateManager mLoadStateManager;
	private TextView mCompelete;
	private MultiStateView mMultiStateView;


	@Override
	public void onCreate(Bundle paramBundle) {
		
		Common.setTheme(getActivity());
		
		if (null != paramBundle) {
			action = paramBundle.getString("action");
			bean = (DirectorateTaskBean) paramBundle.getSerializable("bean");
		} else {
			action = getIntent().getStringExtra("action");
		}
		
		if (TextUtils.equals(action, TASK_EVERYDAY_LOGIN)) {
			setContentView(R.layout.activity_login_everyday);
		} else if (TextUtils.equals(action, TASK_SHARE_DISCUSS)) {
			setContentView(R.layout.activity_share_cmt);
		} else if (TextUtils.equals(action, TASK_CMT_DISCUSS)) {
			setContentView(R.layout.activity_comment_task_desc);
		} else {
			setContentView(R.layout.activity_login_everyday);
		}
		
		super.onCreate(paramBundle);
	}

	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("action", action);
        savedInstanceState.putSerializable("bean", bean);
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
		action = savedInstanceState.getString("action");
		bean = (DirectorateTaskBean) savedInstanceState.getSerializable("bean");
    }
	
	@Override
	public void processHandlerMessage(Message msg) {

		switch (msg.what) {
		case DATA_SUCCESS:
			
			bean = (DirectorateTaskBean) msg.obj;
			String str = "";
			if (1 == bean.getCycletype()) {
				str = "次/日";
			}
			if (0 == bean.getRewardnum()) {
				
				mLayout.setVisibility(View.GONE);
				
			} else {
				StringBuffer rewardNum = new StringBuffer();
				rewardNum.append(bean.getRewardnum()).append(str);
				mCountTv.setText(rewardNum.toString());
			}
			
			if (TextUtils.equals("完成", bean.getComplete())) {
				mCompelete.setText("今天的任务已完成");
			}
			
			mPointTv.setText(TextUtils.concat("+",bean.getExtcredits1()));
//			mMoneyTv.setText("+" + bean.getExtcredits2());
			
			mLoadStateManager.setState(LoadState.Success);
			
			break;
		case HTTP_FAIL :
			
			mLoadStateManager.setState(LoadState.Failure);
			
			break;

		default:
			break;
		}
	}

	@Override
	public void initViews() {

		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.setTitle("任务说明");
		mTitleBar.showDefaultBack();
		
		mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
		mLayout = (LinearLayout) mMultiStateView.findViewById(R.id.directorate_task_detail_count_ll);
		mCountTv = (TextView) mMultiStateView.findViewById(R.id.directorate_task_detail_count);
		mPointTv = (TextView) mMultiStateView.findViewById(R.id.directorate_task_detail_point);
		mCompelete = (TextView) mMultiStateView.findViewById(R.id.directorate_task_detail_complete);
//		mMoneyTv = (TextView) findViewById(R.id.directorate_task_detail_money);
	}

	@Override
	public void initDatas() {
		
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {
			
			@Override
			public void OnStateChange(LoadState state, Object obj) {
				switch (state) {
				case Init:
					mMultiStateView.setViewState(ViewState.LOADING);
					break;
				case Success:
					mMultiStateView.setViewState(ViewState.CONTENT);
					break;
				case Failure:
					mMultiStateView.setViewState(ViewState.ERROR);
					break;
				default:
					break;
				}
			}
		});
		
		mLoadStateManager.setState(LoadState.Init);
		
		if (null != bean) {
			getHandler().obtainMessage(DATA_SUCCESS, bean).sendToTarget();
		} else {
			action = getIntent().getStringExtra("action");
			getTaskDetail(getActivity(), action);
		}
		
	}

	/**
	 * 获取任务详情
	 * @param context
	 * @param action
	 */
	private void getTaskDetail(final Activity context, String action) {
		RequestParams params = new RequestParams();
		params.put("action", action);
		HttpUtil.get(JUrl.SITE + JUrl.DIRECTORATE_TASK_DETAIL, params, new MyJsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				
				Common.handleHttpFailure(context, throwable);
				getHandler().obtainMessage(HTTP_FAIL).sendToTarget();
			}

			@Override
	         public void onFinish() {
	         }

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.removePD();
				Gson gson = new Gson();
                DirectorateTaskBean bean = gson.fromJson(data.toString(), DirectorateTaskBean.class);
				//list = SetDbName(list);
                getHandler().obtainMessage(DATA_SUCCESS, bean).sendToTarget();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
			}
			
		});
	}

	@Override
	public void installListeners() {

		mMultiStateView.setRefreshOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				getTaskDetail(getActivity(), action);
			}
		});
		
	}

	@Override
	public void uninstallListeners() {

	}

	public static void launch(Activity context,String action){
		Intent intent = new Intent(context, DirectorateTaskDetailActivity.class);
		intent.putExtra("action", action);
		context.startActivity(intent);
	}

	@Override
	public Activity getActivity() {
		return DirectorateTaskDetailActivity.this;
	}
}
