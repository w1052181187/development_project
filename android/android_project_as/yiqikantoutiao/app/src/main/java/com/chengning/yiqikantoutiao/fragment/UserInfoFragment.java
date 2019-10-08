package com.chengning.yiqikantoutiao.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

import com.chengning.common.base.BaseFragment;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.activity.MyprofileActivity;
import com.chengning.yiqikantoutiao.data.bean.UserInfoBean;
import com.chengning.yiqikantoutiao.util.Utils;

import java.util.ArrayList;

public class UserInfoFragment extends BaseFragment {

	private static final String TAG = UserInfoFragment.class.getSimpleName();

	static final int INIT_DATA_UI = 1;
	static final int ADD_MORE_DATA_UI = 5;
	static final int TEXT_UPDATED = 6;

	private LayoutInflater mInflater;

	private NestedScrollView mScroll;
	private View mInfoLayout;
	private View mMore;

	private View mView;
	private UserInfoBean mUserBean;
	private boolean isPrepared;

	public UserInfoFragment() {
	}

	public static UserInfoFragment newInstance(UserInfoBean bean) {
		UserInfoFragment fragment = new UserInfoFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", bean);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

		this.mInflater = inflater;
		mView = inflater.inflate(R.layout.fragment_user_profile_me_info,
				container, false);
		isPrepared = true;
		return mView;
	}

	@SuppressLint("NewApi")
	private void lazyLoad(boolean isVisible) {
		if(!isPrepared || !isVisible) {
            return;
        }
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			mScroll.setNestedScrollingEnabled(isVisible);
		}
		
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		lazyLoad(getUserVisibleHint());
	}
	
	@Override
	public void initViews() {

		mScroll = (NestedScrollView) mView
				.findViewById(R.id.scrollbar);
		mInfoLayout = mInflater.inflate(R.layout.header_user_info_detail, null);
		mScroll.addView(mInfoLayout);
		mMore = mInfoLayout.findViewById(R.id.me_info_r5);
	}

	@Override
	public void initDatas() {

		UserInfoBean bean = (UserInfoBean) getArguments().getSerializable(
				"bean");
		setUserData(bean);
	}

	@Override
	public void installListeners() {
		mMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mUserBean) {
					MyprofileActivity.launchByBean(getContext(), mUserBean,
							"more");
				}
			}
		});
	}

	@Override
	public void uninstallListeners() {

	}


	public void setUserData(UserInfoBean bean) {
		sendDataMessage(INIT_DATA_UI, bean);
	}

	public void sendDataMessage(int what, Object obj) {
		if (getContext() != null) {
			Message message = getHandler().obtainMessage(what, obj);
			message.sendToTarget();
		}
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case INIT_DATA_UI:
			boolean isTextUpdated = false;
			UserInfoBean bean = (UserInfoBean) msg.obj;
			if (bean != null && !TextUtils.isEmpty(bean.getUid())) {
				this.mUserBean = bean;

				isTextUpdated = true;
			}
			if (!isTextUpdated) {
			}
			break;
		case ADD_MORE_DATA_UI:
			break;
		case TEXT_UPDATED:
			break;
		default:
			break;
		}
	}

}