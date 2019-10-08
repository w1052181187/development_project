package com.chengning.fenghuovideo.fragment;

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
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.LoginManager;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.activity.LoginActivity;
import com.chengning.fenghuovideo.activity.MainActivity;
import com.chengning.fenghuovideo.activity.MyFavoriteActivity;
import com.chengning.fenghuovideo.activity.MyprofileActivity;
import com.chengning.fenghuovideo.activity.SettingActivity;
import com.chengning.fenghuovideo.activity.WodeActivity;
import com.chengning.fenghuovideo.data.access.UserinfoOtherServer;
import com.chengning.fenghuovideo.data.bean.UserInfoBean;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.Utils;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.chengning.fenghuovideo.wxapi.AuthTencentActivity;
import com.chengning.fenghuovideo.wxapi.AuthWeiboActivity;
import com.chengning.fenghuovideo.wxapi.WXEntryActivity;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFragment extends BaseFragment {
	
	private static final String TAG = MyFragment.class.getSimpleName();
	private static final int MSG_UI_TOP = 1;

	private View mView;
	private PullToRefreshScrollView mPullScroll;
	private RelativeLayout mTopInfoLayout;
	private View mLoginLayout;
	private View mNologinLayout;
	private ImageView mUserImage;
	private TextView mUserName;
	private TextView mUserDesc;
	private TextView mCollectTv;
	private TextView mNightTv;
	private RelativeLayout mSettingLayout;
	
	private UserInfoBean mUserInfoBean;

	private View mPhoneLoginImg;
	private View mWxLoginImg;
	private View mQQLoginImg;
	private View mWbLoginImg;
	private View mMoreLogin;


	private boolean mNightState;
	private int nightMode;
	private TitleBar mTitleBar;

	@Override
	public void initViews() {

		mTitleBar = new TitleBar(getActivity(), mView);
		mTitleBar.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		mTitleBar.setBackText("");
		mTitleBar.setBackText("", getResources().getColor(R.color.transparent),
				getResources().getDrawable(R.drawable.fv_back));
		mTitleBar.setBackTextBackgroundColor(getResources().getColor(R.color.transparent));

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

		mCollectTv = (TextView) mView
				.findViewById(R.id.me_common_collect_tv);
		mNightTv = (TextView) mView
				.findViewById(R.id.me_common_night_tv);
		mSettingLayout = (RelativeLayout) mView
				.findViewById(R.id.me_common_setting_rl);

		mPhoneLoginImg = mView.findViewById(R.id.my_login_phone_img);
		mWxLoginImg = mView.findViewById(R.id.my_login_wx_img);
		mQQLoginImg = mView.findViewById(R.id.my_login_qq_img);
		mWbLoginImg = mView.findViewById(R.id.my_login_sina_img);
		mMoreLogin = mView.findViewById(R.id.my_login_more_tv);

	}

	@Override
	public void initDatas() {

		nightMode = SettingManager.getInst().getNightModel();
		mNightState = Common.isTrue(nightMode);
		updateNightView(mNightState);
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
				WodeActivity.launch(getContext());
			}
		});

		mSettingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), SettingActivity.class);
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


	}

	/**
	 * 设置白天黑夜模式
	 * @param isChecked
	 */
	private void setDayAndNightModel(final boolean isChecked) {
		App.getInst().setNightModelChange(true);
		SettingManager.getInst().saveNightModel(isChecked ? Common.TRUE : Common.FALSE);
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
				Utils.handleDefaultAvatar(bean.getFace(), mUserImage);

				mUserName.setText(bean.getNickname());
				mUserDesc.setText(!TextUtils.isEmpty(bean.getAboutme()) ? bean
								.getAboutme() : "暂无简介");

				LoginManager.getInst().saveUserInfo(bean);
			} else {
				mLoginLayout.setVisibility(View.GONE);
				mNologinLayout.setVisibility(View.VISIBLE);

			}
			mPullScroll.onRefreshComplete();
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
							getHandler().obtainMessage(MSG_UI_TOP, bean)
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

}
