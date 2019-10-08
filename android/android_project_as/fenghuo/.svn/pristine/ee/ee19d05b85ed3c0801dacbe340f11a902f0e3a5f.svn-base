package com.chengning.fenghuo.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.PushMsgManager;
import com.chengning.fenghuo.PushMsgManager.PushMsgEventListener;
import com.chengning.fenghuo.PushMsgManager.PushMsgUserDataBean;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.activity.LoginActivity;
import com.chengning.fenghuo.activity.MainActivity;
import com.chengning.fenghuo.activity.MessageActivity;
import com.chengning.fenghuo.activity.MyFavoriteActivity;
import com.chengning.fenghuo.activity.MyprofileActivity;
import com.chengning.fenghuo.activity.SettingActivity;
import com.chengning.fenghuo.activity.TaskWebActivity;
import com.chengning.fenghuo.data.access.UserinfoOtherServer;
import com.chengning.fenghuo.data.bean.UserInfoAdBean;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.SPHelper;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.wxapi.AuthTencentActivity;
import com.chengning.fenghuo.wxapi.AuthWeiboActivity;
import com.chengning.fenghuo.wxapi.WXEntryActivity;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFragment extends BaseFragment {
	
	private static final String TAG = MyFragment.class.getSimpleName();
	private static final int MSG_UI_TOP = 1;
	private static final int MSG_UI_TOP_AD = 2 ;

	private View mView;
	private PullToRefreshScrollView mPullScroll;
	private RelativeLayout mTopInfoLayout;
	private View mLoginLayout;
	private View mNologinLayout;
	private ImageView mUserImage;
	private TextView mUserName;
	private TextView mUserDesc;
//	private LinearLayout mTopCountLayout;
//	private RelativeLayout mTopCountDynamicLayout;
//	private RelativeLayout mTopCountFocusLayout;
//	private RelativeLayout mTopCountFansLayout;
//	private TextView mTopCountDynamicCountText;
//	private TextView mTopCountFocusCountText;
//	private TextView mTopCountFansCountText;
//	private RelativeLayout mDirectorateLayout;
	private TextView mCollectTv;
	private TextView mNightTv;
	private RelativeLayout mSettingLayout;
	
	private UserInfoBean mUserInfoBean;
	private RelativeLayout mMessageLayout;
	private View mContactRed;

	private View mPhoneLoginImg;
	private View mWxLoginImg;
	private View mQQLoginImg;
	private View mWbLoginImg;
	private View mMoreLogin;

	private TextView mMyIncomeGold;
	private TextView mMyIncomeMoney;
	private View mTaskSystem;
	private View mExchangecenter;
	private View mExchangecenterLine;

	private boolean mNightState;
	private MyFragment mCurFragment;
    private TextView mMyIncomeWithdraw;
	private View mMyIncomeGoldRl;
	private View mMyIncomeMoneyRl;
	private View mMyIncome;
    private View mInviteRl;
    private TextView mInviteTitle;
    private TextView mInviteDesc;
	private int nightMode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Common.setTheme(getActivity());
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		mPullScroll = (PullToRefreshScrollView) mView
				.findViewById(R.id.scrollbar);
		mPullScroll.getRefreshableView().setFillViewport(true);
		mPullScroll.setMode(Mode.DISABLED);

		mLoginLayout = mView.findViewById(R.id.my_login_rl);
		mNologinLayout = mView.findViewById(R.id.my_nologin_ll);

		mTopInfoLayout = (RelativeLayout) mView
				.findViewById(R.id.me_top_info_rl);
		mUserImage = (ImageView) mView
				.findViewById(R.id.me_top_info_user_image);
		mUserName = (TextView) mView.findViewById(R.id.me_top_info_name);
		mUserDesc = (TextView) mView.findViewById(R.id.me_top_info_desc);

//		mTopCountLayout = (LinearLayout) mView
//				.findViewById(R.id.me_top_count_ll);
//		mTopCountDynamicLayout = (RelativeLayout) mView
//				.findViewById(R.id.me_top_count_dynamic_rl);
//		mTopCountFocusLayout = (RelativeLayout) mView
//				.findViewById(R.id.me_top_count_focus_rl);
//		mTopCountFansLayout = (RelativeLayout) mView
//				.findViewById(R.id.me_top_count_fans_rl);
//		mTopCountDynamicCountText = (TextView) mView
//				.findViewById(R.id.me_top_count_dynamic_count_text);
//		mTopCountFocusCountText = (TextView) mView
//				.findViewById(R.id.me_top_count_focus_count_text);
//		mTopCountFansCountText = (TextView) mView
//				.findViewById(R.id.me_top_count_fans_count_text);

//		mDirectorateLayout = (RelativeLayout) mView
//				.findViewById(R.id.me_common_directorate_rl);
		mCollectTv = (TextView) mView
				.findViewById(R.id.me_common_collect_tv);
		mNightTv = (TextView) mView
				.findViewById(R.id.me_common_night_tv);
		mMessageLayout = (RelativeLayout) mView
				.findViewById(R.id.me_common_message_rl);
		mSettingLayout = (RelativeLayout) mView
				.findViewById(R.id.me_common_setting_rl);
		
		mContactRed = mView.findViewById(R.id.me_common_message_red);

		mPhoneLoginImg = mView.findViewById(R.id.my_login_phone_img);
		mWxLoginImg = mView.findViewById(R.id.my_login_wx_img);
		mQQLoginImg = mView.findViewById(R.id.my_login_qq_img);
		mWbLoginImg = mView.findViewById(R.id.my_login_sina_img);
		mMoreLogin = mView.findViewById(R.id.my_login_more_tv);

		mMyIncome = mView.findViewById(R.id.me_common_my_income_ll);
		mMyIncomeGold = (TextView) mView.findViewById(R.id.me_common_income_gold_tv);
		mMyIncomeGoldRl = mView.findViewById(R.id.me_common_my_income_gold_rl);
		mMyIncomeMoney = (TextView) mView.findViewById(R.id.me_common_income_money_tv);
		mMyIncomeMoneyRl = mView.findViewById(R.id.me_common_my_income_money_rl);
		mMyIncomeWithdraw = (TextView) mView.findViewById(R.id.me_common_income_money_withdraw_tv);

		mTaskSystem = mView.findViewById(R.id.me_common_task_system_rl);
		mExchangecenter = mView.findViewById(R.id.me_common_exchangecenter_ll);
		mExchangecenterLine = mView.findViewById(R.id.me_common_exchangecenter_line);

		mInviteRl = mView.findViewById(R.id.me_common_invite_friends_rl);
		mInviteTitle = (TextView)mView.findViewById(R.id.me_common_invite_friends_title);
		mInviteDesc = (TextView)mView.findViewById(R.id.me_common_invite_friends_desc);
	}

	@Override
	public void initDatas() {

		nightMode = SettingManager.getInst().getNightModel();
		mNightState = Common.isTrue(nightMode);
		updateNightView(mNightState);
//
//		getHandler().postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				getHandler().obtainMessage(MSG_UI_TOP, mUserInfoBean)
//						.sendToTarget();
//				// 刷新数据
//				if (App.getInst().isLogin()) {
//					getUserInfo();
//				}
//			}
//
//		}, Common.TIME_WAIT_REFRESH);
		mCurFragment = this;
	}

	@Override
	public void installListeners() {
		mTopInfoLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!App.getInst().isLogin()) {
					Intent intent = new Intent(getContext(), LoginActivity.class);
					startActivity(intent);
				} else {
					MyprofileActivity.launchByBean(getContext(),
							mUserInfoBean, "more");
				}

			}
		});
		
//		mTopCountDynamicLayout.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (LoginManager.getInst().checkLoginWithNotice(getContext())) {
//					Intent intent = new Intent(getContext(), MyDynamicListActivity.class);
//					startActivity(intent);
//				}
//			}
//		});
//
//		mTopCountFocusLayout.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (LoginManager.getInst().checkLoginWithNotice(getContext())) {
//					Intent intent = new Intent(getContext(), MyFollowListActivity.class);
//					startActivity(intent);
//				}
//			}
//		});
//
//		mTopCountFansLayout.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (LoginManager.getInst().checkLoginWithNotice(getContext())) {
//					Intent intent = new Intent(getContext(), MyFansListActivity.class);
//					startActivity(intent);
//				}
//			}
//		});
		
//		mDirectorateLayout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (App.getInst().isLogin()) {
//					Intent intent = new Intent(getContext(),
//							DirectorateActivity.class);
//					startActivity(intent);
//				} else {
//					LoginManager.noticeNotLaunched(getContext());
//				}
//			}
//		});
		mCollectTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyFavoriteActivity.launch(getContext());
			}
		});

		mNightTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mNightState = !mNightState;
				setDayAndNightModel(mNightState);
				getContext().finish();
				MainActivity.launch(getContext(),3);
			}
		});

		mSettingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), SettingActivity.class);
				startActivity(intent);
			}
		});
		mMessageLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), MessageActivity.class);
				startActivity(intent);
			}
		});

		mPhoneLoginImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.launch(getContext());
			}
		});
		mWxLoginImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("cmd", "login");
				intent.putExtra("action", JUrl.Action_Login);
				intent.setClass(getContext(), WXEntryActivity.class);
				getContext().startActivity(intent);
			}
		});

		mQQLoginImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("cmd", "login");
				intent.putExtra("action", JUrl.Action_Login);
				intent.setClass(getContext(), AuthTencentActivity.class);
				getContext().startActivityForResult(intent, 1);
			}
		});

		mWbLoginImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("cmd", "login");
				intent.putExtra("action", JUrl.Action_Login);
				intent.setClass(getContext(), AuthWeiboActivity.class);
				getContext().startActivityForResult(intent, 1);
			}
		});

		mMoreLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.launch(getContext());
			}
		});

		mMyIncomeGoldRl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			handleIncomeClick(0);
			}
		});
		mMyIncomeMoneyRl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleIncomeClick(1);
			}
		});
		mTaskSystem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = SPHelper.getInst().getString(SPHelper.KEY_MY_TASK_URL);
				url = url + "?viewtype=" + nightMode;
				TaskWebActivity.launch(getActivity(),url);
			}
		});

		mExchangecenter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = SPHelper.getInst().getString(SPHelper.KEY_MY_EXCHANGE_URL);
				url = url + "?viewtype=" + nightMode;
				TaskWebActivity.launch(getActivity(),url);
			}
		});

        mMyIncomeWithdraw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
				String url = SPHelper.getInst().getString(SPHelper.KEY_MY_EXCHANGE_URL);
				url = url + "?viewtype=" + nightMode;
				TaskWebActivity.launch(getActivity(),url);
            }
        });

        mInviteRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = SPHelper.getInst().getString(SPHelper.KEY_MY_PRENTICE_URL);
				url = url + "?viewtype=" + nightMode;
                TaskWebActivity.launch(getActivity(),url);
            }
        });
	}

	private void handleIncomeClick(int type) {
		String url = SPHelper.getInst().getString(SPHelper.KEY_MY_INCOME_URL);
		url = url + "?type=" + type;
		url = url + "&viewtype=" + nightMode;
		TaskWebActivity.launch(getActivity(),url);
	}

	/**
	 * 设置白天黑夜模式
	 * @param isChecked
	 */
	private void setDayAndNightModel(final boolean isChecked) {
		// TODO 设置白天黑夜模式
		App.getInst().setNightModelChange(true);
		SettingManager.getInst().saveNightModel(isChecked ? Common.TRUE : Common.FALSE);
//		updateNightView(isChecked);
	}

	private void updateNightView(boolean isChecked) {

		mNightTv.setText(isChecked ? "日间" : "夜间");
		Drawable drawable = getResources().getDrawable(isChecked ? R.drawable.wode_rijian : R.drawable.wode_yejian_icon);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		mNightTv.setCompoundDrawables(null, drawable, null, null);
	}

	@Override
	public void processHandlerMessage(Message msg) {
		int what = msg.what;
		switch (what) {
		case MSG_UI_TOP:
			UserInfoBean bean = (UserInfoBean) msg.obj;
			mUserInfoBean = bean;
			if (App.getInst().isLogin()) {
				mLoginLayout.setVisibility(View.VISIBLE);
				mNologinLayout.setVisibility(View.GONE);
				mMyIncome.setVisibility(View.VISIBLE);
				mExchangecenter.setVisibility(View.VISIBLE);
				mExchangecenterLine.setVisibility(View.VISIBLE);
				Utils.showFace(getContext(), bean, mUserImage, true);
//				mUserName.setVisibility(View.VISIBLE);
//				mUserDesc.setVisibility(View.VISIBLE);

				mUserName.setText(bean.getNickname());
				mUserDesc.setText(!TextUtils.isEmpty(bean.getAboutme()) ? bean
								.getAboutme() : "暂无简介");
//				mDivider.setVisibility(View.VISIBLE);
//				mTopCountLayout.setVisibility(View.VISIBLE);
//				mTopCountDynamicCountText.setText("" + bean.getTopic_count());
//				mTopCountFocusCountText.setText("" + bean.getFollow_count());
//				mTopCountFansCountText.setText("" + bean.getFans_count());

				mMyIncomeGold.setText("金币:" + bean.getExtcredits2());
				mMyIncomeMoney.setText("零钱:" + bean.getExtcredits3());

				LoginManager.getInst().saveUserInfo(bean);
			} else {
				mLoginLayout.setVisibility(View.GONE);
				mNologinLayout.setVisibility(View.VISIBLE);
				mMyIncome.setVisibility(View.GONE);
				mExchangecenter.setVisibility(View.GONE);
				mExchangecenterLine.setVisibility(View.GONE);

			}
			mPullScroll.onRefreshComplete();
			mInviteRl.setVisibility(App.getInst().isLogin() ? View.VISIBLE : View.GONE);
			break;
		case MSG_UI_TOP_AD:
			UserInfoAdBean adBean = (UserInfoAdBean) msg.obj;
			if (adBean == null) {
				return;
			}
			mInviteTitle.setText(adBean.getAd_title());
			mInviteDesc.setText(adBean.getAd_tips());
			break;
		default:
			break;
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Common.setTheme(getContext());
		mView = inflater.inflate(R.layout.fragment_me, container, false);
		return mView;
	}

	private void getUserInfo() {
		if (Common.hasNet()) {
			final String uid = mUserInfoBean.getUid();
			HttpUtil.get(getContext(), JUrl.SITE + JUrl.Get_USER_INFO + uid, null,
					new MyJsonHttpResponseHandler() {

						@Override
						public void onDataSuccess(int status, String mod,
								String message, String data, JSONObject obj) {
							Gson gson = new Gson();
							UserInfoBean bean = gson.fromJson(data,
									UserInfoBean.class);

							if (UserinfoOtherServer.getInst(getContext())
									.queryTargetUid(uid) == null) {
								UserinfoOtherServer.getInst(getContext())
										.insertOne(bean);
							} else {
								UserinfoOtherServer.getInst(getContext())
										.updateTargetUid(bean);
							}
							JSONObject adObject = null;
							UserInfoAdBean adBean = new UserInfoAdBean();
							try {
								adObject = new JSONObject(data);
								adBean.setAd_title(adObject.optString("ad_title"));
								adBean.setAd_tips(adObject.optString("ad_tips"));
							} catch (JSONException e) {
								e.printStackTrace();
							}
							getHandler().obtainMessage(MSG_UI_TOP, bean)
									.sendToTarget();
							getHandler().obtainMessage(MSG_UI_TOP_AD, adBean)
									.sendToTarget();



						}

						@Override
						public void onDataFailure(int status, String mod,
								String message, String data, JSONObject obj) {
							mPullScroll.onRefreshComplete();
						}
					});
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mUserInfoBean = App.getInst().getUserInfoBean();
		getHandler().obtainMessage(MSG_UI_TOP, mUserInfoBean)
				.sendToTarget();
		getHandler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// 刷新数据
				if (App.getInst().isLogin()) {
					getUserInfo();
				}
			}

		}, Common.TIME_WAIT_REFRESH);
		
	}

	
	private PushMsgEventListener mPushMsgEventListener = new PushMsgEventListener() {

		@Override
		public int onEvent(int type, PushMsgUserDataBean bean) {
			long count = bean.getAt_new() + bean.getComment_new()
					+ bean.getDig_new() + bean.getFans_new() + bean.getNewpm();
			mContactRed.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
			return 0;
		}
	};
	
	@Override
	public void onStart() {
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_AT_NEW,
				mPushMsgEventListener);
		PushMsgManager.getInstance().addListener(
				PushMsgManager.TYPE_COMMENT_NEW, mPushMsgEventListener);
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_DIG_NEW,
				mPushMsgEventListener);
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_FANS_NEW,
				mPushMsgEventListener);
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_PM_NEW,
				mPushMsgEventListener);
		super.onStart();
	};
	
	@Override
	public void onPause() {
		
		PushMsgManager.getInstance().removeListener(PushMsgManager.TYPE_AT_NEW,
				mPushMsgEventListener);
		PushMsgManager.getInstance().removeListener(
				PushMsgManager.TYPE_COMMENT_NEW, mPushMsgEventListener);
		PushMsgManager.getInstance().removeListener(
				PushMsgManager.TYPE_DIG_NEW, mPushMsgEventListener);
		PushMsgManager.getInstance().removeListener(
				PushMsgManager.TYPE_FANS_NEW, mPushMsgEventListener);
		PushMsgManager.getInstance().removeListener(PushMsgManager.TYPE_PM_NEW,
				mPushMsgEventListener);
		super.onPause();
	}

}
