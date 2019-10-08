package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.util.PermissionManager;
import com.chengning.common.util.HttpUtil;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.LoginManager;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.data.access.UserinfoOtherServer;
import com.chengning.yiqikantoutiao.data.bean.UserInfoBean;
import com.chengning.yiqikantoutiao.event.NicknameModifyEvent;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.ImagePickHelper;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.util.Utils;
import com.chengning.yiqikantoutiao.widget.NicknameModifyDialog;
import com.chengning.yiqikantoutiao.widget.TitleBar;
import com.chengning.yiqikantoutiao.widget.TitleBar.BackOnClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MyprofileActivity extends BaseFragmentActivity {
	private static final int GetInfo_Sucess = 4; // 保存完成
	private App mApp;
	private UserInfoBean mUserInfoBean;
	private ImagePickHelper mImagePickHelper;

	private int Savecount = 0;
	private String mFinalFace = "finalface.jpg";

	private RelativeLayout mUserImgBtn;
	private RelativeLayout mNameBtn;
	private ImageView mUserImg;
	private TextView logoutButton;
	private TextView mNameTv;
	private TitleBar mTitleBar;
	private ImageView mImgBoult;
	private ImageView mNameBoult;
	private String uid;
	private PermissionManager permissionManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_my_profile);
		super.onCreate(savedInstanceState);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Savecount = 1;
		}
		return super.onKeyDown(keyCode, event);
	}

	public static void launchByUid(Activity from, String uid, String action) {
		Intent intent = new Intent(from, MyprofileActivity.class);
		intent.putExtra("uid", uid);
		intent.putExtra("action", action);
		from.startActivity(intent);
	}

	public static void launchByBean(Activity from, UserInfoBean bean,
                                    String action) {
		Intent intent = new Intent(from, MyprofileActivity.class);
		intent.putExtra("bean", bean);
		intent.putExtra("action", action);
		from.startActivity(intent);
	}

	@Override
	public Activity getActivity() {
		return MyprofileActivity.this;
	}

	@Override
	public void initViews() {

		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		// mTitleBar.setTitle("我的资料");

		mUserImgBtn = (RelativeLayout) this.findViewById(R.id.infoedit_img_rl);
		mUserImg = (ImageView) this.findViewById(R.id.infoedit_img);
		logoutButton = (TextView) this.findViewById(R.id.infoedit_logout);
		mNameTv = (TextView) this.findViewById(R.id.infoedit_name_edit);
		mNameBtn = (RelativeLayout) this.findViewById(R.id.infoedit_name_rl);
		mImgBoult = (ImageView) findViewById(R.id.infoedit_img_boult);
		mNameBoult = (ImageView) findViewById(R.id.infoedit_name_boult);
	}

	@Override
	public void initDatas() {

		String mAction = getIntent().getStringExtra("action");
		EventBus.getDefault().register(getActivity());
		if (TextUtils.isEmpty(mAction)) {
			finish();
			return;
		}

		initUserInfo(mAction);
		mImagePickHelper = new ImagePickHelper(getActivity(), mFinalFace);
	}

	/**
	 * 初始化用户信息
	 * 
	 * @param action
	 */
	private void initUserInfo(String action) {
		mApp = App.getInst();

		if ("more".equals(action)) {
			mUserInfoBean = (UserInfoBean) getIntent().getSerializableExtra(
					"bean");
			if (mUserInfoBean == null) {
				uid = getIntent().getStringExtra("uid");
			} else {
				uid = mUserInfoBean.getUid();
			}
		} else if ("direct".equals(action)) {
			uid = App.getInst().getUserInfoBean().getUid();
		}

		if (!TextUtils.isEmpty(uid)){
			handleInfo(uid);
			if (Common.hasNet()) {
				getUserInfo(uid);
			}
			setDataForViews();
		} else {
			finish();
		}

	}

	private void handleInfo(String uid) {

		if (mApp.isLogin() && mApp.getUserInfoBean().getUid().equals(uid)) {

			mUserInfoBean = mApp.getUserInfoBean();
			handleInfoIsEditable(true);
		} else {
			mUserInfoBean = UserinfoOtherServer.getInst(getActivity())
					.queryTargetUid(uid);
			handleInfoIsEditable(false);
		}
	}

	/**
	 * 处理资料是否可修改
	 * 
	 * @param isEditable
	 */
	private void handleInfoIsEditable(boolean isEditable) {
		mTitleBar.setTitle(isEditable ? "我的资料" : "资料");
		mUserImgBtn.setEnabled(isEditable ? true : false);
		mNameBtn.setEnabled(isEditable ? true : false);

		logoutButton.setVisibility(isEditable ? View.VISIBLE : View.GONE);
		mImgBoult.setVisibility(isEditable ? View.VISIBLE : View.GONE);
		mNameBoult.setVisibility(isEditable ? View.VISIBLE : View.GONE);

	}

	public void setDataForViews() {
		if (null != mUserInfoBean) {
			if (!TextUtils.isEmpty(mUserInfoBean.getNickname())) {
				mNameTv.setText(mUserInfoBean.getNickname());
			}
			Utils.setCircleImage(mUserInfoBean.getFace_original(), mUserImg);
			if (Common.isTrue(SettingManager.getInst().getNightModel())) {
				mUserImg.setColorFilter(getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
			}
		}
	}

	@Override
	public void installListeners() {
		mTitleBar.setBackOnClickListener(new BackOnClickListener() {

			@Override
			public void onClick() {
				Savecount = 1;
			}
		});

		logoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginManager.getInst().logout();
				getActivity().finish();
			}
		});
		mUserImgBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showImgDialog();
			}
		});
		mNameBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handleNickname(mNameTv.getText().toString());
			}
		});

	}

	/**
	 * 修改昵称
	 *
	 * @param name
	 */
	protected void handleNickname(String name) {
		if (TextUtils.isEmpty(name)) {
			return;
		}
		NicknameModifyDialog dialog = new NicknameModifyDialog();
		dialog.setData(name);
		dialog.show(getSupportFragmentManager(), NicknameModifyDialog.class.getSimpleName());
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case GetInfo_Sucess:
			mUserInfoBean = (UserInfoBean) msg.obj;
			if (null != mApp.getUserInfoBean()
					&& mUserInfoBean.getUid().equals(
							mApp.getUserInfoBean().getUid())) {
				LoginManager.getInst().saveUserInfo(mUserInfoBean);
			}

			setDataForViews();
			break;
		default:

			break;
		}
	}

	public void getUserInfo(String uid) {
		HttpUtil.get(getActivity(), JUrl.SITE + JUrl.Get_USER_INFO + uid, null,
				new MyJsonHttpResponseHandler() {

					@Override
					public void onDataSuccess(int status, String mod,
                                              String message, String data, JSONObject obj) {
						Gson gson = new Gson();
						UserInfoBean bean = gson.fromJson(data,
								new TypeToken<UserInfoBean>() {
								}.getType());
						getHandler().obtainMessage(GetInfo_Sucess, bean)
								.sendToTarget();
					}

					@Override
					public void onDataFailure(int status, String mod,
                                              String message, String data, JSONObject obj) {
						UIHelper.removePD();
						Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
					}
				});
	}

	/***
	 * 图像
	 */
	private void showImgDialog() {
		permissionManager = new PermissionManager();
		mImagePickHelper.showPickDialog(permissionManager);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		permissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
	}

	/**
	 * 回调结果处理
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (mImagePickHelper
				.handleActivityResult(requestCode, resultCode, data)) {
			EditFace();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void EditFace() {
		if (Common.hasNet()) {
			try {
				UIHelper.addPD(getActivity(),
						getResources().getString(R.string.handle_hint));
				File file = Common.creatFile(JUrl.FilePathTemp, mFinalFace);
				RequestParams rp = new RequestParams();
				rp.put("face", file, "image/jpeg");
				HttpUtil.post(getActivity(), JUrl.SITE + JUrl.Edit_Face, rp,
						new MyJsonHttpResponseHandler() {
							public void onFailure(int statusCode,
									Header[] headers, Throwable throwable,
									JSONObject errorResponse) {
								Common.handleHttpFailure(getActivity(), throwable);
							};

							@Override
							public void onFinish() {
								UIHelper.removePD();
							}

							@Override
							public void onDataSuccess(int status, String mod,
                                                      String message, String data, JSONObject obj) {
								App.getInst().setUserAvatarChange(true);
								if (App.getInst().getUserInfoBean().getUid() != null) {
									getUserInfo(App.getInst().getUserInfoBean()
											.getUid());
								}
								UIHelper.removePD();
							}

							@Override
							public void onDataFailure(int status, String mod,
                                                      String message, String data, JSONObject obj) {
								UIHelper.showToast(getActivity(), message);
								UIHelper.removePD();
							};

						});
			} catch (FileNotFoundException e) {
				UIHelper.removePD();
				e.printStackTrace();
			}
		} else {

			UIHelper.showToast(getActivity(), R.string.intnet_fail);
		}
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(getActivity());
		super.onDestroy();
	}

	@Subscribe
	public void onEventMainThread(NicknameModifyEvent event) {
		updateNickname(event.getNickname());
	}

	/**
	 * 更新昵称
	 * @param nickname
	 */
	private void updateNickname(String nickname) {
		if (TextUtils.isEmpty(nickname)) {
			return;
		}
		mNameTv.setText(nickname);
		mUserInfoBean.setNickname(nickname);
		getHandler().obtainMessage(GetInfo_Sucess, mUserInfoBean)
				.sendToTarget();
	}

}
