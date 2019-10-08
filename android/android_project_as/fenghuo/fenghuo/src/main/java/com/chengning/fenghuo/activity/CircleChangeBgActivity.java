package com.chengning.fenghuo.activity;


import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.base.util.PermissionManager;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.event.CircleBgChangeEvent;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.ImagePickHelperForCircleBg;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

public class CircleChangeBgActivity extends BaseActivity {

	private TitleBar mTitleBar;
	private TextView mTvFromAbulm;
	private TextView mTvFromCamera;
	
	private ImagePickHelperForCircleBg mImagePickHelper;
	private String mFinalBg = "circlebg.jpg";
	private PermissionManager permissionManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_circle_changebg);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void processHandlerMessage(Message msg) {
		
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.setTitle("更换背景");
		mTitleBar.showDefaultBack();
		
		mTvFromAbulm = (TextView) findViewById(R.id.tv_fromAbulm);
		mTvFromCamera = (TextView) findViewById(R.id.tv_fromCamera);
		
	}

	@Override
	public void initDatas() {
		mImagePickHelper = new ImagePickHelperForCircleBg(getActivity(), mFinalBg);
	}

	@Override
	public void installListeners() {
		permissionManager = new PermissionManager();
		mTvFromAbulm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mImagePickHelper.handleAbulmClick(getActivity(), permissionManager);
			}
		});
		mTvFromCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mImagePickHelper.handleCameraClick(getActivity(), mFinalBg, permissionManager);
			}
		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		permissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
	}

	@Override
	public void uninstallListeners() {
		
	}
	
	private void uploadBg(){
		if (Common.hasNet()) {
			try {
				UIHelper.addPD(getActivity(),
						getResources().getString(R.string.handle_hint));
				File file = Common.creatFile(JUrl.FilePathTemp, mFinalBg);
				RequestParams rp = new RequestParams();
				rp.put("image", file, "image/jpeg");
				HttpUtil.post(getActivity(), JUrl.SITE + JUrl.URL_CIRCLE_EDIT_BG, rp,
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
								UIHelper.removePD();
								if (App.getInst().getUserInfoBean().getUid() != null) {
									getUserInfo(App.getInst().getUserInfoBean()
											.getUid());
								}
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
	
	/**
	 * 回调结果处理
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (mImagePickHelper
				.handleActivityResult(requestCode, resultCode, data)) {
			if(resultCode == RESULT_OK){
				uploadBg();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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
						
						if (null != App.getInst().getUserInfoBean()
								&& bean.getUid().equals(
										App.getInst().getUserInfoBean().getUid())) {
							LoginManager.getInst().saveUserInfo(bean);
							EventBus.getDefault().post(new CircleBgChangeEvent());
							finish();
						}
					}

					@Override
					public void onDataFailure(int status, String mod,
							String message, String data, JSONObject obj) {
						UIHelper.removePD();
						Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
					}
				});
	}
	
	@Override
	public Activity getActivity() {
		return CircleChangeBgActivity.this;
	}

}
