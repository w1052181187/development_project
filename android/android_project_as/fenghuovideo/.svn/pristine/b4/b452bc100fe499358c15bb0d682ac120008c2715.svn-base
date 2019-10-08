package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.SimpleFragmentPagerAdapter;
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.base.IScrollCallBack;
import com.chengning.fenghuovideo.data.bean.UserInfoBean;
import com.chengning.fenghuovideo.event.CollectEditStateEvent;
import com.chengning.fenghuovideo.fragment.MyFavoriteNewsFragment;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.widget.MyTabPageIndicator;
import com.chengning.fenghuovideo.widget.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MyFavoriteActivity extends BaseFragmentActivity {

	private static final String TAG = MyFavoriteActivity.class.getSimpleName();
	private FragmentManager mFragmentManager;
	
	private MyTabPageIndicator mIndicator;
	private ViewPager mViewPager;
	private SimpleFragmentPagerAdapter mAdapter;
	private int mPageItemIndex;

	private MyFavoriteNewsFragment newsFragment;
	private int lastNightModel;
	private TitleBar mTitleBar;
	private TextView mTitleRightBtn;
	private HashMap<Integer, EditState> mTagMap;
	protected boolean isEditFlag;

	private IScrollCallBack mScrollCallBack;
	private ImageView mTopBtn;
	
	public static void launch(Activity from){
		Intent intent = new Intent(from, MyFavoriteActivity.class);
		from.startActivity(intent);
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		Common.setTheme(getActivity());
		this.setContentView(R.layout.activity_my_favorite_content);
		super.onCreate(savedInstanceState);
	}
	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void initViews() {
		mFragmentManager = getSupportFragmentManager();
		mIndicator = (MyTabPageIndicator) findViewById(R.id.my_favorite_indicator);
		mViewPager = (ViewPager) findViewById(R.id.my_favorite_pager);
		mTitleBar = new TitleBar(getActivity(), true);
		mTopBtn = (ImageView) findViewById(R.id.topbtn);
		
	}

	enum EditState{
		Delete("编辑"),
		Compelete("完成"),
		;
		private String str;
		private EditState(String str){
			this.str = str;
		}
		public String getStr(){
			return str;
		}
	}
	
	@Override
	public void initDatas() {
		mTitleBar.setTitle("我的收藏");
		mTitleBar.showDefaultBack();
		mTitleBar.setRightButton(EditState.Delete.getStr(), getResources().getColor(R.color.item_title_color_normal));
		mTitleRightBtn = (TextView) mTitleBar.getRightButton();
		mTagMap = new HashMap<Integer, EditState>();
		mTagMap.put(0, EditState.Delete);
		mTagMap.put(1, EditState.Delete);

		lastNightModel = SettingManager.getInst().getNightModel();

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

		addFragment();
		EventBus.getDefault().register(this);
	}

	@Override
	public void installListeners() {
		mTitleBar.setRightButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (EditState.Delete.equals((mTagMap.get(mPageItemIndex)))) {
					mTagMap.put(mPageItemIndex, EditState.Compelete);
					mTitleRightBtn.setText(EditState.Compelete.getStr());
					isEditFlag = true;

				} else if (EditState.Compelete.equals(mTagMap.get(mPageItemIndex))) {
					mTagMap.put(mPageItemIndex, EditState.Delete);
					mTitleRightBtn.setText(EditState.Delete.getStr());
					isEditFlag = false;
				}

				newsFragment.notifyStateChange(isEditFlag);

			}
		});

		mTopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				forceTop();
				mTopBtn.setVisibility(View.GONE);
			}
		});
	}

	private void forceTop() {
		if (newsFragment == null) {
			return;
		}
		newsFragment.forceTop();
	}

	@Override
	public void processHandlerMessage(Message msg) {
		
	}
	
	@Subscribe
    public void onEventMainThread(CollectEditStateEvent event) {
		toggleRightButton(event.isHasData());
    }
	
	/**
	 * 显示或者隐藏
	 * @param b
	 */
	private void toggleRightButton(boolean b) {
		if (b) {
			mTitleBar.showRightButton();
		} else {
			mTitleBar.hideRightButton();
		}
	}
	private void addFragment(){
		ArrayList<String> strs = new ArrayList<String>();
		strs.add("新闻");

		newsFragment = new MyFavoriteNewsFragment();
		newsFragment.setmScrollCallback(mScrollCallBack);
		
		ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(newsFragment);
		
		mAdapter = new SimpleFragmentPagerAdapter(mFragmentManager,
				fragmentsList,strs);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOffscreenPageLimit(1);
		mViewPager.setCurrentItem(mPageItemIndex);
		mIndicator.setViewPager(mViewPager);
		mIndicator.setOnPageChangeListener(pageChangeListener);
		mIndicator.setVisibility(View.GONE);
	}
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			mPageItemIndex = arg0;
			mTitleRightBtn.setText(mTagMap.get(mPageItemIndex).getStr());
			newsFragment.setUserVisibleHint(true);
			newsFragment.dealScrollY();

		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};
	
	@Override
	public void onResume() {
		if (SettingManager.getInst().getNightModel() != lastNightModel) {
			finish();
			launch(getActivity());
		}
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}
