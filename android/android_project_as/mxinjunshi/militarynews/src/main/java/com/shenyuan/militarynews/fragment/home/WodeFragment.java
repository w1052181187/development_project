package com.shenyuan.militarynews.fragment.home;

import org.apache.http.Header;
import de.greenrobot.event.EventBus;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.util.HttpUtil;
import com.google.gson.Gson;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const.PointActionType;
import com.shenyuan.militarynews.MyStatusResponseHandler;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.activity.AccountInfoActivity;
import com.shenyuan.militarynews.activity.DirectorateActivity;
import com.shenyuan.militarynews.activity.HomeActivity;
import com.shenyuan.militarynews.activity.LoginActivity;
import com.shenyuan.militarynews.activity.MyCommentActivity;
import com.shenyuan.militarynews.activity.MyFavoriteActivity;
import com.shenyuan.militarynews.activity.RankIntroductionActivity;
import com.shenyuan.militarynews.activity.SettingActivity;
import com.shenyuan.militarynews.beans.data.DirectoratePointBean;
import com.shenyuan.militarynews.beans.data.LoginBean;
import com.shenyuan.militarynews.event.ShopUrlLoadEvent;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.SPHelper;
import com.shenyuan.militarynews.utils.TaskUpdateUtil;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.utils.Utils;
import com.shenyuan.militarynews.views.TitleBar;
import com.umeng.analytics.MobclickAgent;


public class WodeFragment extends BaseFragment{

//	public static final String LOGIN_FLAG = "loginSuccess";
	public static final int TASK_SUCCESS = 1;
	public static final String TASK_DATA = "taskData";
	
	private View mView;
	
	private TitleBar mTitleBar;
	private ImageView mLoginHead;
	private TextView mLoginName;
	private RelativeLayout mNoLogin;
	private RelativeLayout mLogin;
	private RelativeLayout mHeadQuart;
	private RelativeLayout mMyFav;
	private RelativeLayout mSetting;
	private RelativeLayout mRankShow;
	private Button mLogoutBtn;
	
	private ImageView mUserHead;
	private TextView mUserName;
	private TextView mUserLevel;
	private TextView mUserRank;
	private TextView mUserScore;
	
	private RelativeLayout mMyComment;
	private View mMyCommentLine;
	private RelativeLayout mMyOrder;
	private View mMyOrderLine;
	private boolean mLastLoginState;

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), mView);
		mTitleBar.hideBack();
		mTitleBar.setLeftButton(null);
		mTitleBar.setTitle(getResources().getString(R.string.my), getResources().getColor(R.color.white));
		mTitleBar.setBackgroundColor(getResources().getColor(R.color.ac_bg_color));
		
		mNoLogin = (RelativeLayout) mView.findViewById(R.id.ac_rl_nologin);
		mLoginHead = (ImageView) mView.findViewById(R.id.ac_login_iv);
		mLoginName = (TextView) mView.findViewById(R.id.ac_name_tv);
		
		mLogin = (RelativeLayout) mView.findViewById(R.id.ac_rl_login);
		mUserHead = (ImageView) mView.findViewById(R.id.ac_iv_head);
		mUserName = (TextView) mView.findViewById(R.id.ac_tv_name);
		mUserLevel = (TextView) mView.findViewById(R.id.ac_tv_level);
		mUserRank = (TextView) mView.findViewById(R.id.ac_tv_rank);
		mUserScore = (TextView) mView.findViewById(R.id.ac_tv_score);
		
		mHeadQuart = (RelativeLayout) mView.findViewById(R.id.ac_rl_headquart);
		mMyFav = (RelativeLayout) mView.findViewById(R.id.ac_rl_fav);
		mMyComment = (RelativeLayout) mView.findViewById(R.id.ac_rl_comment);
		mMyCommentLine =  mView.findViewById(R.id.ac_line_comment);
		mMyOrder = (RelativeLayout) mView.findViewById(R.id.ac_rl_order);
		mMyOrderLine =  mView.findViewById(R.id.ac_line_order);
		mSetting = (RelativeLayout) mView.findViewById(R.id.ac_rl_setting);
		mLogoutBtn  = (Button) mView.findViewById(R.id.ac_btn_logout);
		mRankShow = (RelativeLayout) mView.findViewById(R.id.rank_show_rl);
	}

	@Override
	public void initDatas() {
		mLastLoginState = App.getInst().isLogin();
	}

	@Override
	public void installListeners() {
		mLoginHead.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
		});
		mUserHead.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AccountInfoActivity.launch(getContext());
				
			}
		});
		mUserName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AccountInfoActivity.launch(getContext());
			}
		});
		mHeadQuart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				if(App.getInst().isLogin()){
					DirectorateActivity.launch(getActivity());
				}else{
					startActivity(new Intent(getActivity(), LoginActivity.class));
				}
			}
		});
		mMyFav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyFavoriteActivity.launch(getActivity());
			}
		});
		mSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SettingActivity.class));
			}
		});
		mLogoutBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!Common.hasNet()){
					logout();
				}else{
					UIHelper.addPD(getActivity(), "正在退出");
					HttpUtil.get(JUrl.SITE + JUrl.URL_EXIT, new MyStatusResponseHandler() {
						@Override
						public void onDataSuccess(int status, String mod, String message,
								String data, JSONObject obj) {
							UIHelper.removePD();
							logout();
						}
						
						@Override
						public void onDataFailure(int status, String mod, String message,
								String data, JSONObject obj) {
							UIHelper.removePD();
							logout();
						}
	
						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							super.onFailure(statusCode, headers, throwable, errorResponse);
							UIHelper.removePD();
							logout();
						}
					});
				}
			}
		});
		mRankShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), RankIntroductionActivity.class));
				
			}
		});
		mMyComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyCommentActivity.launch(getActivity(), false);
			}
		});
		mMyOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HomeActivity.setChangeView(HomeActivity.SHOP_FRAGMENT);
				EventBus.getDefault().postSticky(new ShopUrlLoadEvent(SPHelper.getInst().getString(SPHelper.KEY_ORDER_URL)));
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (!getUserVisibleHint()){
			return;
		}
		
		getHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				updateView();
				if(App.getInst().isLogin()){
					getUserInfoByHttp();
				}
			}
		}, 20);
	}

	private void updateView(){
		
		if(App.getInst().isLogin()){
			LoginBean mBean = App.getInst().getLoginBean();
			mNoLogin.setVisibility(View.INVISIBLE);
			mLogin.setVisibility(View.VISIBLE);
			mMyComment.setVisibility(View.VISIBLE);
			mMyCommentLine.setVisibility(View.VISIBLE);
			mMyOrder.setVisibility(View.VISIBLE);
			mMyOrderLine.setVisibility(View.VISIBLE);
			Utils.setCircleImage(mBean.getFace(), mUserHead);
			mUserName.setText(mBean.getUname());
			mUserLevel.setText(mBean.getIcon());
			mUserRank.setText(mBean.getHonor());
			mUserScore.setText(mBean.getScores());
			mLogoutBtn.setVisibility(View.VISIBLE);

			//每日登录手动登录积分信息
			if(!Common.isListEmpty(App.getInst().getLoginBean().getCredits_rule())){
				DirectoratePointBean pointBean = TaskUpdateUtil.convertLoginToPoint(App.getInst().getLoginBean());
				try {
					if(pointBean != null){
						TaskUpdateUtil.showHints(getActivity(), pointBean, PointActionType.LOGIN);		
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
			//每日登录cookie登录积分信息
			if(!Common.isListEmpty(App.getInst().getLoginBean().getCredits_rule())){
				initEveryLogin();
				return;
			}
		}else{
			mNoLogin.setVisibility(View.VISIBLE);
			mLogin.setVisibility(View.INVISIBLE);
			mLogoutBtn.setVisibility(View.INVISIBLE);
			mMyComment.setVisibility(View.GONE);
			mMyCommentLine.setVisibility(View.GONE);
			mMyOrder.setVisibility(View.GONE);
			mMyOrderLine.setVisibility(View.GONE);
		}
		
		boolean mCurLoginState = App.getInst().isLogin();
		if (mCurLoginState != mLastLoginState) {
			mLastLoginState = mCurLoginState;
//			EventBus.getDefault().post(new LoginStateChangeEvent());
		}
		
	}
	
	private void logout(){
		EventBus.getDefault().postSticky(new ShopUrlLoadEvent(SPHelper.getInst().getString(SPHelper.KEY_MALL_LOGOUT_URL)));
		MobclickAgent.onEvent(getActivity(), "user_logout");
		App.getInst().clearLoginBean();
		updateView();
	}
	
	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case TASK_SUCCESS:
			if(App.getInst().isLogin()){
				LoginBean mBean = App.getInst().getLoginBean();
				if(mBean.getCredits_rule()!=null){
					DirectoratePointBean pointBean = TaskUpdateUtil.convertLoginToPoint(mBean);
					try {
						if(pointBean != null){
							TaskUpdateUtil.showHints(getActivity(), pointBean, PointActionType.LOGIN);		
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			break;

		default:
			break;
		}
		
	}
	
//	/***
//	 * 图像
//	 */
//	private void showImgDialog() {
//		mImagePickHelper.setModifyNickname(true);
//		mImagePickHelper.setNickname(mUserName.getText().toString().trim());
//		mImagePickHelper.showPickDialog();
//	}
	
	private void getUserInfoByHttp(){
		HttpUtil.get(JUrl.SITE + JUrl.URL_GET_USERINFO, new MyStatusResponseHandler(){
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				Gson gson = new Gson();
				LoginBean mBean = gson.fromJson(data, LoginBean.class);
				if(mBean != null){
					App.getInst().saveLoginBean(mBean);
				}
                updateView();
			}
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				Common.handleHttpFailure(getActivity(), throwable);
			}
		});
	}
	
	private void initEveryLogin(){
		if (Common.hasNet()) {
			//TODO cookie登录请求每日登录
			HttpUtil.get(JUrl.SITE + JUrl.URL_GET_LOGIN_EVERYDAY,new MyStatusResponseHandler(){
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					Common.handleHttpFailure(getActivity(), throwable);
//					getHandler().obtainMessage(HTTP_FAIL).sendToTarget();
				}
				@Override
		        public void onFinish() {
		        }
				@Override
				public void onDataSuccess(int status, String mod, String message,
						String data, JSONObject obj) {
//					UIHelper.removePD();
					Gson gson = new Gson();
					LoginBean mBean = gson.fromJson(data, LoginBean.class);
					if(mBean != null){
						App.getInst().saveLoginBean(mBean);
					}
	                getHandler().obtainMessage(TASK_SUCCESS, mBean).sendToTarget();
				}

				@Override
				public void onDataFailure(int status, String mod, String message,
						String data, JSONObject obj) {
				}
				
			});
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_wode, null);
		return mView;
	}
	
	
}
