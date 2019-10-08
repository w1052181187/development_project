package com.chengning.fenghuo.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.data.bean.SettingBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.TitleBar;
import com.loopj.android.http.RequestParams;

public class SetCommentPermissionActivity extends BaseActivity {
	private static final String TAG = SetCommentPermissionActivity.class
			.getSimpleName();

	private static final int VALUE_ALL = 0;
	private static final int VALUE_FOCUS = 1;

	private static final int ARG_SUCCESS = 1;
	private static final int ARG_FAILURE = 2;

	private static final int MSG_ALL = 1;
	private static final int MSG_FOCUS = 2;

	private TitleBar mTitleBar;

	private ImageView mAllImage;
	private ImageView mFocusImage;

	private View mAllLayout;
	private View mFocusLayout;

	private boolean mIsAll;
	private boolean mIsFocus;

	private SetCommentPermissionActivity mContext;
	private SettingBean mUserInfoBean;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(this);
		setContentView(R.layout.activity_set_comment_permission);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setTitle("评论权限");
		mTitleBar.setBackText("设置");
		mAllImage = (ImageView) findViewById(R.id.set_comment_permission_all_checkimg);
		mFocusImage = (ImageView) findViewById(R.id.set_comment_permission_focus_checkimg);

		mAllLayout = findViewById(R.id.set_comment_permission_all_rl);
		mFocusLayout = findViewById(R.id.set_comment_permission_focus_rl);
	}

	@Override
	public void initDatas() {
		mContext = this;
		mUserInfoBean = SettingManager.getInst().getSettingBean();

		readUserConfig();
	}

	public void changeShow(View view, boolean on) {
		view.setVisibility(on ? View.VISIBLE : View.INVISIBLE);
	}

	public void changeTwoShow(boolean isAll) {
		mIsAll = isAll;
		mIsFocus = !mIsAll;

		changeShow(mAllImage, mIsAll);
		changeShow(mFocusImage, mIsFocus);
	}

	private void readUserConfig() {
		mIsAll = mUserInfoBean.getConfig_reply_access() == VALUE_ALL;

		changeTwoShow(mIsAll);
	}

	public void doCommentConfigHttpRequestPost(int what, int reply_access) {
		RequestParams params = new RequestParams();
		params.put("reply_access", reply_access);

		params.put("devicetype", "2");
		params.put("devicetoken", AVInstallation.getCurrentInstallation()
				.getInstallationId());
		params.put("objectid", AVInstallation.getCurrentInstallation()
				.getObjectId());

		httpRequestPost(what, JUrl.SITE + JUrl.URL_DO_COMMENT_PERMISSION_CONFIG, params);
	}

	public void httpRequestPost(final int what, String url, RequestParams params) {
		UIHelper.addPD(mContext, "处理中...");
		HttpUtil.post(mContext, url, params, new MyJsonHttpResponseHandler() {

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				Log.i(TAG, "HttpUtil onSuccess");
				UIHelper.removePD();
				sendMsg(what, data, ARG_SUCCESS, 0);
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				Log.e(TAG, "HttpUtil onFailure");

				UIHelper.removePD();
				sendMsg(what, null, ARG_FAILURE, 0);
				if (!Common.hasNet()) {
					UIHelper.showToast(mContext, "操作失败，请检查网络");
				} else {
					UIHelper.showToast(mContext, "操作失败，服务器故障");
				}

			};
		});
	}

	public void sendMsg(int what, Object obj, int arg1, int arg2) {
		if (mContext == null || getHandler() == null) {
			return;
		}
		Message msg = getHandler().obtainMessage(what);
		msg.obj = obj;
		msg.arg1 = arg1;
		msg.arg2 = arg2;
		msg.sendToTarget();
	}

	@Override
	public void processHandlerMessage(Message msg) {
		int what = msg.what;
		int arg1 = msg.arg1;
		if (arg1 == ARG_SUCCESS) {
			mUserInfoBean.setConfig_reply_access(mIsAll ? VALUE_ALL
					: VALUE_FOCUS);
			SettingManager.getInst().saveSetting(mUserInfoBean);
		} else {
			switch (what) {
			case MSG_ALL:
				changeTwoShow(!mIsAll);
				break;
			case MSG_FOCUS:
				changeTwoShow(!mIsAll);
				break;
			}
		}
	}

	@Override
	public void installListeners() {
		
		mAllLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!LoginManager.getInst().checkLoginWithNotice(getActivity())) {
					return;
				}
				
				if (!mIsAll) {
					changeTwoShow(true);
					doCommentConfigHttpRequestPost(MSG_ALL, VALUE_ALL);
				}
			}
		});
		mFocusLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!LoginManager.getInst().checkLoginWithNotice(getActivity())) {
					return;
				}
				
				if (mIsAll) {
					changeTwoShow(false);
					doCommentConfigHttpRequestPost(MSG_FOCUS, VALUE_FOCUS);
				}
			}
		});
	}

	@Override
	public void uninstallListeners() {

	}

	public static String getCommentPermissionDetailText(SettingBean bean) {
		String replyAccess = null;
		if (null != bean) {
			replyAccess = bean.getConfig_reply_access() == VALUE_ALL ? "所有人"
					: "我关注的人";
		} else {
			replyAccess = "";
		}
		return replyAccess;
	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
