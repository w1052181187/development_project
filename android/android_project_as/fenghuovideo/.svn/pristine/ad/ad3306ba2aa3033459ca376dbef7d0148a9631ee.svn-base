package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.LoginManager;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.data.bean.PushConfigBean;
import com.chengning.fenghuovideo.data.bean.SettingBean;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.UIHelper;
import com.chengning.fenghuovideo.widget.ChosePushTimeDialog;
import com.chengning.fenghuovideo.widget.ChosePushTimeDialog.ChosePushTimeListener;
import com.chengning.fenghuovideo.widget.SwitchButton;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class SetNotificationActivity extends BaseActivity {
	private static final String TAG = SetNotificationActivity.class
			.getSimpleName();

	private static final int ARG_SUCCESS = 1;
	private static final int ARG_FAILURE = 2;

	private static final int MSG_TIMDE_DETAIL = 0;
	private static final int MSG_NOTIFICATION = 1;
	private static final int MSG_AT_ME = 2;
	private static final int MSG_COMMENT = 3;
	private static final int MSG_DIG = 4;
	private static final int MSG_STRANGERS = 5;
	private static final int MSG_FANS = 6;
	private static final int MSG_GOODNESS = 7;

	private TitleBar mTitleBar;

	private TextView mTimeDetail;
	private View mTimeDetailLayout;
	private SwitchButton mNotificationSwitch;

	private SwitchButton mGoodnessSwitch;

	private View mLayout3;

	private String mStartTime;
	private String mEndTime;
	private boolean mIsNotificationOn;

	private boolean mIsGoodnessOn;

	private boolean mIsIgnoreChange;

	private SetNotificationActivity mContext;
	private SettingBean mSettingBean;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(this);
		setContentView(R.layout.activity_set_notification);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setTitle("通知设置");
		mTitleBar.setBackText("设置");

		mTimeDetail = (TextView) findViewById(R.id.setting_notification_receive_time_detail_txt);
		mTimeDetailLayout = findViewById(R.id.setting_notification_receive_time_rl);
		mNotificationSwitch = (SwitchButton) findViewById(R.id.setting_notification_switch_switch);

		mGoodnessSwitch = (SwitchButton) findViewById(R.id.setting_notification_goodness_switch);

		mLayout3 = findViewById(R.id.setting_notification_3);
	}

	@Override
	public void initDatas() {
		mContext = this;
		mSettingBean = SettingManager.getInst().getSettingBean();
		if (App.getInst().isLogin()) {
			getUserConfig();
		}
		readUserConfig();
	}

	public void showDetail(boolean show) {
		int visibility = show ? View.VISIBLE : View.GONE;
		mLayout3.setVisibility(visibility);
	}

	public void changeSwitch(SwitchButton btn, boolean on) {
		mIsIgnoreChange = true;
		btn.setChecked(!on);
		mIsIgnoreChange = false;
	}

	private void readUserConfig() {

		mStartTime = mSettingBean.getConfig_push_start_time();
		mEndTime = mSettingBean.getConfig_push_end_time();
		mIsNotificationOn = Common.isTrue(mSettingBean.getConfig_push_enable());
		mIsGoodnessOn = Common.isTrue(mSettingBean.getIs_push());

		showDetail(mIsNotificationOn);

		if (!TextUtils.isEmpty(mStartTime) && !TextUtils.isEmpty(mEndTime)) {
			mTimeDetail.setText(TextUtils.concat(mStartTime, "-", mEndTime));
		}
		changeSwitch(mNotificationSwitch, mIsNotificationOn);
		changeSwitch(mGoodnessSwitch, mIsGoodnessOn);
	}

	public void doPushConfigHttpRequestPost(int what, String param, int val) {
		RequestParams params = new RequestParams();
		params.put("param", param);
		params.put("val", val);

		params.put("devicetype", "2");
		params.put("devicetoken", AVInstallation.getCurrentInstallation()
				.getInstallationId());
		params.put("objectid", AVInstallation.getCurrentInstallation()
				.getObjectId());

		httpRequestPost(what, JUrl.SITE + JUrl.URL_DO_PUSH_CONFIG, params);
	}

	public void httpRequestPost(final int what, String url, RequestParams params) {
		UIHelper.addPD(mContext, "处理中...");
		HttpUtil.post(mContext, url, params, new MyJsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				UIHelper.removePD();
				Common.showHttpFailureToast(getActivity());
				sendMsg(what, null, ARG_FAILURE);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.removePD();
				sendMsg(what, data, ARG_SUCCESS);
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.removePD();
				sendMsg(what, null, ARG_FAILURE);
				UIHelper.showToast(getActivity(), message);

			};
		});
	}

	/**
	 * 获取用户配置
	 */
	public void getUserConfig() {
		RequestParams params = new RequestParams();
		params.put("devicetoken", AVInstallation.getCurrentInstallation()
				.getInstallationId());
		HttpUtil.post(mContext, JUrl.SITE + JUrl.URL_GET_PUSH_CONFIG, params,
				new MyJsonHttpResponseHandler() {

					@Override
					public void onDataSuccess(int status, String mod,
							String message, String data, JSONObject obj) {
						Gson gson = new Gson();
						PushConfigBean configBean = gson.fromJson(data,
								PushConfigBean.class);
						mSettingBean.setConfig_push_enable(configBean
								.getEnable());
						mSettingBean.setConfig_push_start_time(configBean
								.getStart_time());
						mSettingBean.setConfig_push_end_time(configBean
								.getEnd_time());

						mSettingBean.setIs_push(configBean.getIs_splendid());

						SettingManager.getInst().saveSetting(mSettingBean);
						readUserConfig();

					}

					@Override
					public void onDataFailure(int status, String mod,
							String message, String data, JSONObject obj) {

					};
				});
	}

	public void sendMsg(int what, Object obj, int arg1) {
		if (mContext == null || getHandler() == null) {
			return;
		}
		Message msg = getHandler().obtainMessage(what);
		msg.obj = obj;
		msg.arg1 = arg1;
		msg.sendToTarget();
	}

	@Override
	public void processHandlerMessage(Message msg) {
		int what = msg.what;
		int arg1 = msg.arg1;
		if (arg1 == ARG_SUCCESS) {
			mSettingBean.setConfig_push_enable(mIsNotificationOn ? Common.TRUE
					: Common.FALSE);
			mSettingBean.setIs_push(mIsGoodnessOn ? Common.TRUE : Common.FALSE);
			mSettingBean.setConfig_push_start_time(mStartTime);
			mSettingBean.setConfig_push_end_time(mEndTime);

			SettingManager.getInst().saveSetting(mSettingBean);
		} else {
			switch (what) {
			case MSG_NOTIFICATION:
				mIsNotificationOn = !mIsNotificationOn;
				changeSwitch(mNotificationSwitch, mIsNotificationOn);
				break;
			case MSG_GOODNESS:
				mIsGoodnessOn = !mIsGoodnessOn;
				changeSwitch(mGoodnessSwitch, mIsGoodnessOn);
				break;
			}
		}
	}

	@Override
	public void installListeners() {
		mTimeDetailLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChosePushTimeDialog.chosePushTime(
						mContext,
						new ChosePushTimeListener() {

							@Override
							public void onConfirm(String startTime,
									String endTime) {
								mStartTime = startTime;
								mEndTime = endTime;
								mTimeDetail
										.setText(mStartTime + "-" + mEndTime);

								mSettingBean
										.setConfig_push_start_time(startTime);
								mSettingBean.setConfig_push_end_time(endTime);

								SettingManager.getInst().saveSetting(
										mSettingBean);
							}

							@Override
							public void onCancel() {
							}
						}, mSettingBean.getConfig_push_start_time(),
						mSettingBean.getConfig_push_end_time());
			}
		});
		mNotificationSwitch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (mIsIgnoreChange) {
							return;
						}
						mIsNotificationOn = !isChecked;

						showDetail(mIsNotificationOn);

						doPushConfigHttpRequestPost(MSG_NOTIFICATION, "enable",
								mIsNotificationOn ? Common.TRUE : Common.FALSE);

					}
				});
		mGoodnessSwitch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (mIsIgnoreChange) {
							return;
						}
						mIsGoodnessOn = !isChecked;
						doPushConfigHttpRequestPost(MSG_GOODNESS,
								"is_splendid", mIsGoodnessOn ? Common.TRUE
										: Common.FALSE);
					}
				});
	}

	@Override
	public void uninstallListeners() {

	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
