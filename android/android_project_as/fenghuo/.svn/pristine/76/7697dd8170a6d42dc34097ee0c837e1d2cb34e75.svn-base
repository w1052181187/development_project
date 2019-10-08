package com.chengning.fenghuo.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.IForceListenRefresh;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.base.IForceListenRefreshExtend;
import com.chengning.fenghuo.base.IScrollCallBack;
import com.chengning.fenghuo.widget.TitleBar;

public class VideoFragment extends BaseFragment implements IForceListenRefreshExtend {
	
	
	private View mView;
	private TitleBar mTitleBar;
	private VideoChanelFragment videoChanelFragment;
	private ImageView mTopBtn;
	private IScrollCallBack mScrollCallBack;

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getContext(), mView);
		mTitleBar.setTitle(getString(R.string.tab_video));
		mTopBtn = (ImageView) mView.findViewById(R.id.topbtn);
	}
	

	@Override
	public void initDatas() {

		mScrollCallBack = new IScrollCallBack() {
			@Override
			public void show() {
				mTopBtn.setVisibility(View.VISIBLE);
			}
			@Override
			public void hidden() {
				mTopBtn.setVisibility(View.GONE);
			}
		};

		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		videoChanelFragment= new VideoChanelFragment();
		videoChanelFragment.setmScrollCallback(mScrollCallBack);
		transaction.replace(R.id.fragment_video_content, videoChanelFragment);
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void installListeners() {

		mTopBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				forceTop();
				mTopBtn.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_video, container, false);
		return mView;
	}
	
	/**
	 * 刷新视频
	 */
	public void refreshVideo() {
		if (videoChanelFragment == null) {
			return;
		} else if (videoChanelFragment instanceof IForceListenRefreshExtend &&
				!(((IForceListenRefreshExtend) videoChanelFragment).getRefreshState() == RefreshState.Refreshing)) {
			setRefreshState(((IForceListenRefreshExtend)videoChanelFragment).getRefreshState());
			((IForceListenRefreshExtend) videoChanelFragment).forceRefresh();
			mTopBtn.setVisibility(View.GONE);
		}
	}

	private RefreshState mRefreshState = RefreshState.RefreshComplete;
	private OnRefreshStateListener mListener;
	private OnRefreshStateListener mControlListener = new OnRefreshStateListener() {
		
		@Override
		public void onStart() {
			if(mListener != null){
				mListener.onStart();
			}
		}
		
		@Override
		public void onFinish() {
			if(mListener != null){
				mListener.onFinish();
			}
		}
	};
	
	private void setRefreshState(RefreshState state){
		this.mRefreshState = state;
		if(mControlListener != null){
			switch (mRefreshState) {
			case Refreshing:
				mControlListener.onStart();
				break;
			case RefreshComplete:
				mControlListener.onFinish();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void forceRefresh() {
		refreshVideo();
	}


	@Override
	public void forceCheckRefresh() {
		if (videoChanelFragment != null && videoChanelFragment instanceof IForceListenRefreshExtend) {
			((IForceListenRefreshExtend) videoChanelFragment).forceCheckRefresh();
		}
	}


	@Override
	public RefreshState getRefreshState() {
		return mRefreshState;
	}

	@Override
	public void setOnRefreshStateListener(OnRefreshStateListener listener) {
		this.mListener = listener;
	}

	@Override
	public void forceSetPageSelected(boolean isSelected) {

	}

	@Override
	public void forceTop() {
		if(videoChanelFragment != null && videoChanelFragment instanceof IForceListenRefreshExtend
				&& !(((IForceListenRefreshExtend)videoChanelFragment).getRefreshState() == RefreshState.Refreshing)){
			((IForceListenRefreshExtend)videoChanelFragment).forceTop();
		}
	}
}
