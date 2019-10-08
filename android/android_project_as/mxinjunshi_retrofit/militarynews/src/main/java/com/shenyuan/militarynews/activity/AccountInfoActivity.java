package com.shenyuan.militarynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseResponseBean;
import com.chengning.common.base.MyRetrofitResponseCallback;
import com.chengning.common.base.util.GlideHelper;
import com.chengning.common.base.util.RetrofitManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.LoginManager;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.beans.data.LoginBean;
import com.shenyuan.militarynews.event.NicknameModifyEvent;
import com.shenyuan.militarynews.event.UserInfoUpdateEvent;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.ImagePickHelper;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.utils.Utils;
import com.shenyuan.militarynews.views.NicknameModifyDialog;
import com.shenyuan.militarynews.views.TitleBar;

import java.io.File;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AccountInfoActivity extends BaseFragmentActivity {
	
	private App mApp;
	private LoginBean mUserInfoBean;
	private ImagePickHelper mImagePickHelper;

	private String mFinalFace = "finalface.jpg";

	private RelativeLayout mUserImgBtn;
	private RelativeLayout mPwdModifyBtn;
	private RelativeLayout mNameBtn;
	private ImageView mUserImg;
	private TextView mNameTv;
	private TitleBar mTitleBar;

	private String mNickname;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_account_info);
		super.onCreate(savedInstanceState);
	}

	public static void launch(Activity from) {
		Intent intent = new Intent(from, AccountInfoActivity.class);
		from.startActivity(intent);
	}

	@Override
	public Activity getActivity() {
		return AccountInfoActivity.this;
	}

	@Override
	public void initViews() {

		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		 mTitleBar.setTitle("账号信息");

		mUserImgBtn = (RelativeLayout) this.findViewById(R.id.infoedit_img_rl);
		mUserImg = (ImageView) this.findViewById(R.id.infoedit_img);
		mNameTv = (TextView) this.findViewById(R.id.infoedit_name_edit);
		mPwdModifyBtn = (RelativeLayout) this
				.findViewById(R.id.infoedit_password_modify_rl);
		mNameBtn = (RelativeLayout) this.findViewById(R.id.infoedit_name_rl);

	}

	@Override
	public void onPause() {

		super.onPause();
	}

	@Override
	public void initDatas() {
		initUserInfo();
		mImagePickHelper = new ImagePickHelper(getActivity(), mFinalFace);
		EventBus.getDefault().register(this);
	}

	/**
	 * 初始化用户信息
	 * 
	 *
	 */
	private void initUserInfo() {
		mApp = App.getInst();

		mUserInfoBean = mApp.getLoginBean();
		setDataForViews();

	}

	public void setDataForViews() {
		if (null != mUserInfoBean) {
			mNickname = mUserInfoBean.getUname();
			if (!TextUtils.isEmpty(mNickname)) {
				mNameTv.setText(mNickname);
			}
			Utils.setCircleImage(getActivity(), mUserInfoBean.getFace(), mUserImg);
			if (Common.isTrue(SettingManager.getInst().getNightModel())) {
				mUserImg.setColorFilter(getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
			}
		}
	}

	@Override
	public void installListeners() {
//		mTitleBar.setBackOnClickListener(new BackOnClickListener() {
//
//			@Override
//			public void onClick() {
//				
//			}
//		});

		mUserImgBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showImgDialog();
			}
		});
		mPwdModifyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (LoginManager.getInst().checkLoginWithNotice(getActivity())) {
					startActivity(new Intent(getActivity(), PwdModifyActivity.class).putExtra(PwdModifyActivity.BOOTTYPE, PwdModifyActivity.MODIFYPWD));
				}
			}
		});
		mNameBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handleNickname(getActivity(), mNameTv.getText().toString());
			}
		});

	}
	
	/**
	 * 修改昵称
	 * @param activity
	 * @param name 
	 */
    protected void handleNickname(Activity activity, String name) {
    	if (TextUtils.isEmpty(name)) {
    		return;
    	}
		NicknameModifyDialog dialog = new NicknameModifyDialog(name);
		dialog.showAllowingStateLoss((BaseFragmentActivity) activity, getSupportFragmentManager(), NicknameModifyDialog.class.getSimpleName());
	}

	@Override
	public void processHandlerMessage(Message msg) {
	}
	

	/***
	 * 图像
	 */
	private void showImgDialog() {
		mImagePickHelper.showPickDialog();
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
			UIHelper.addPD(getActivity(),getResources().getString(R.string.handle_hint));
			File file = Common.creatFile(JUrl.FilePathTemp, mImagePickHelper.getFileName());
			RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),file);
			MultipartBody.Part part = MultipartBody.Part.createFormData("face", file.getName(), requestBody);
			Observable call
					= App.getInst().getApiInterface().post(JUrl.URL_EDIT_FACE, part);
			RetrofitManager.subcribe(call, new MyRetrofitResponseCallback() {
				@Override
				public void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean response) {
					UIHelper.removePD();
					if (data != null) {
						JsonObject object = new JsonParser().parse(data).getAsJsonObject();
						String mFace = object.get("face").toString();
						LoginBean mBean = App.getInst().getLoginBean();
						mBean.setFace(mFace);
						App.getInst().saveLoginBean(mBean);
						mFace = "file://" + JUrl.FilePathTemp + "/" + mImagePickHelper.getFileName();
						Utils.setCircleImage(getActivity(), mFace, mUserImg);
						UserInfoUpdateEvent event = new UserInfoUpdateEvent();
						event.setAvatarPath(mFace);
						EventBus.getDefault().post(event);

					}
				}

				@Override
				public void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response) {
					UIHelper.removePD();
					UIHelper.showToast(getActivity(), message);
				}

				@Override
				public void onError(Throwable t) {
					UIHelper.removePD();
					Common.handleHttpFailure(getActivity(), t);
					super.onError(t);
				}
			});

		} else {
			UIHelper.showToast(getActivity(), R.string.intnet_fail);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

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
		LoginBean bean = App.getInst().getLoginBean();
		bean.setUname(nickname);
		App.getInst().saveLoginBean(bean);
	}
	
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

}
