package com.chengning.yiqikantoutiao.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Browser;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.SimpleFragmentPagerAdapter;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.widget.extend.TabTextViewPageIndicator;
import com.chengning.common.widget.extend.TabTextViewPageIndicator.OnAddTabListener;
import com.chengning.common.widget.extend.TabTextViewPageIndicator.TabView;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.LoginManager;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.data.access.UserinfoOtherServer;
import com.chengning.yiqikantoutiao.data.bean.UserInfoBean;
import com.chengning.yiqikantoutiao.fragment.UserInfoFragment;
import com.chengning.yiqikantoutiao.util.ArticleManagerUtils;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.util.Utils;
import com.chengning.yiqikantoutiao.widget.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class UserInfoActivity extends BaseFragmentActivity {

	private static final int MSG_UI_TOP = 1;
	private static final int MSG_UI_CONTENT = 2;

	private static final int BG_SUCCESS = 3;

	private static final int MSG_UI_FAILURE = 4;

	private FragmentManager mFragmentManager;

	private RelativeLayout mUserTopLayout;
//	private View mBtnLayout;
//	private Button mFocusBtn;
	private ImageView mUserTopBg;
	private ImageView mUserImage;
	private TextView mUserName;
	private TabTextViewPageIndicator mIndicator;
	private ViewPager mPager;
	private SimpleFragmentPagerAdapter mAdapter;

	private UserInfoBean mTargetUserInfoBean;

	protected LayerDrawable bg;

	private TitleBar titleBar;

//	private boolean isFollow;

	private App app;

	private ArrayList<String> strs;

	private ArrayList<Fragment> fragmentsList;
	private int lastNightModel;
	private String mUid;
	private String userName;
	private AppBarLayout mAppBar;
	protected int height;
	private AppBarLayout.OnOffsetChangedListener mOffsetListener;


	public static void launch(Activity from, String nickName) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(
				Consts.BASE_SCHEME + "userinfo://@%s", nickName)));
		intent.putExtra(Browser.EXTRA_APPLICATION_ID, from.getPackageName());
		from.startActivity(intent);
	}

	public static void launchByUid(Activity from, String uid) {
		Intent intent = new Intent(from, UserInfoActivity.class);
		intent.putExtra("uid", uid);
		from.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		this.setContentView(R.layout.activity_user_info);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		mFragmentManager = getSupportFragmentManager();

		titleBar = new TitleBar(getActivity(), true);
		titleBar.setBackText("返回", getResources().getColor(R.color.white),
				getResources().getDrawable(R.drawable.nav_back_white));
		titleBar.setBackTextBackgroundColor(getResources().getColor(
				R.color.transparent));
		titleBar.setBackgroundColor(getResources()
				.getColor(R.color.transparent));

		mAppBar = (AppBarLayout) findViewById(R.id.user_info_appbar);
		mUserTopLayout = (RelativeLayout) findViewById(R.id.user_profile_top);
//		mFocusBtn = (Button) findViewById(R.id.user_profile_focus);
		mUserTopBg = (ImageView) findViewById(R.id.user_profile_top_bg);
		mUserImage = (ImageView) findViewById(R.id.user_profile_user_image);
		mUserName = (TextView) findViewById(R.id.user_profile_user_name_text);

		mIndicator = (TabTextViewPageIndicator) findViewById(R.id.user_profile_indicator);
		mPager = (ViewPager) findViewById(R.id.user_profile_pager);
		
		
	}

	@Override
	public void initDatas() {

		Uri data = getIntent().getData();
		mUid = getIntent().getStringExtra("uid");
		if (data != null) {
			String d = data.toString();
			int index = d.lastIndexOf("/");
			userName = d.substring(index + 1);
			if (userName.indexOf("@") == 0)
				userName = userName.substring(1);
			if (userName.contains(":")) {
				String[] strings = userName.split(":");
				if (0 < strings.length) {
					userName = strings[0];
				}
			}

			initUserInfoByNickname(userName);
		} else if (!TextUtils.isEmpty(mUid)) {
			initUserInfoByUid(mUid);
		} else {
			finish();
			return;
		}

	}

	/**
	 * 初始化用户信息
	 * 
	 * @param userName
	 */
	private void initUserInfoByNickname(String userName) {
		// TODO 判断是否是登录用户
		initData();
		titleBar.setTitle(userName, getResources().getColor(R.color.white));
		if (null != app.getUserInfoBean()
				&& userName.equals(app.getUserInfoBean().getNickname())) {
			mTargetUserInfoBean = app.getUserInfoBean();
		} else {
			mTargetUserInfoBean = UserinfoOtherServer.getInst(getActivity())
					.queryTargetNickname(userName);
		}

		getNewsListByHttpByNickname(userName);

	}

	private void initUserInfoByUid(String uid) {

		initData();
		if (null != app.getUserInfoBean()
				&& uid.equals(app.getUserInfoBean().getUid())) {
			mTargetUserInfoBean = app.getUserInfoBean();
		} else {
			mTargetUserInfoBean = UserinfoOtherServer.getInst(getActivity())
					.queryTargetUid(uid);
		}
		getNewsListByHttp(uid);

	}

	private void initData() {
		app = App.getInst();

		strs = new ArrayList<String>();
		fragmentsList = new ArrayList<Fragment>();
		mAdapter = new SimpleFragmentPagerAdapter(mFragmentManager,
				fragmentsList, strs);

		mPager.setAdapter(mAdapter);
		mPager.setOffscreenPageLimit(0);
		mIndicator.setViewPager(mPager);
		mIndicator.setOnPageChangeListener(pageChangeListener);

		Message msg = getHandler().obtainMessage(MSG_UI_TOP,
				mTargetUserInfoBean);
		msg.sendToTarget();
		lastNightModel = SettingManager.getInst().getNightModel();
	}

	@Override
	public void installListeners() {

		mIndicator.setOnAddTabListener(new OnAddTabListener() {

			@Override
			public TabView onAddTab(Context context) {
				TabView tabView = mIndicator.new TabView(getActivity());
				if (!Common.isTrue(SettingManager.getInst().getNightModel())) {
					tabView.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.news_tab_indicator_white));
				} else {
					tabView.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.news_tab_indicator_night));
				}

				return tabView;
			}
		});


//		mFocusBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				changeFollow(mTargetUserInfoBean);
//			}
//		});
		
		final int minHeight = getResources().getDimensionPixelSize(R.dimen.titile_bar_height);
		mOffsetListener = new AppBarLayout.OnOffsetChangedListener() {
			
			@Override
			public void onOffsetChanged(AppBarLayout arg0, int arg1) {
				
				height = mUserTopLayout.getMeasuredHeight() - minHeight + arg1;
				if (height == 0) {
					setTitleBar(true);
				} else {
					setTitleBar(false);
				}
			}
		};
		mAppBar.addOnOffsetChangedListener(mOffsetListener);
		
	}

	@Override
	public void uninstallListeners() {

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public void onResume() {
		super.onResume();
		if (SettingManager.getInst().getNightModel() != lastNightModel) {
			finish();
			if (!TextUtils.isEmpty(userName)) {
				launch(getActivity(), userName);
			} else if (!TextUtils.isEmpty(mUid)) {
				launchByUid(getActivity(), mUid);
			}
			
		}
	}

	public void onPause() {
		super.onPause();
	}

	@SuppressLint("NewApi")
	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case MSG_UI_TOP:

			mTargetUserInfoBean = (UserInfoBean) msg.obj;
			if (null != mTargetUserInfoBean) {
				handleTopData();
			}

			break;
		case MSG_UI_CONTENT:
			mTargetUserInfoBean = (UserInfoBean) msg.obj;
			if (null != mTargetUserInfoBean) {
				handleContent();
			}
			break;
		case BG_SUCCESS:
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				mUserTopLayout.setBackground(bg);
			} else {
				mUserTopLayout.setBackgroundDrawable(bg);
			}
			bg = null;
			break;
		case MSG_UI_FAILURE:
			break;
		default:
			break;
		}
	}

	private void handleContent() {
		handleTopData();
		addFragments();
		if (app.isLogin()
				&& app.getUserInfoBean().getUid()
						.equals(mTargetUserInfoBean.getUid())) {
			LoginManager.getInst().saveUserInfo(mTargetUserInfoBean);
		} else {
			if (UserinfoOtherServer.getInst(getActivity()).queryTargetUid(
					mTargetUserInfoBean.getUid()) == null) {
				UserinfoOtherServer.getInst(getActivity()).insertOne(
						mTargetUserInfoBean);
			} else {
				UserinfoOtherServer.getInst(getActivity()).updateTargetUid(
						mTargetUserInfoBean);
			}
		}
	}

	private void handleTopData() {
		titleBar.setTitle(mTargetUserInfoBean.getNickname(), getResources()
				.getColor(R.color.white));
		setBackground(mUserTopLayout, mTargetUserInfoBean);
		Utils.setCircleImage(mTargetUserInfoBean.getFace(), mUserImage);

		mUserName.setText(mTargetUserInfoBean.getNickname());


		// TODO 关注
//		handleBottomFollowVisibility(mTargetUserInfoBean);
	}

	/**
	 * 处理发送消息和关注是否可见
	 * 
	 * @param uBean
	 */
//	private void handleBottomFollowVisibility(UserInfoBean uBean) {
//		if (app.isLogin()
//				&& app.getUserInfoBean().getNickname()
//						.equals(uBean.getNickname())) {
////			mBottomLayout.setVisibility(View.GONE);
//			mBtnLayout.setVisibility(View.GONE);
//			return;
//		}
//		mBtnLayout.setVisibility(View.VISIBLE);
////		mBottomLayout.setVisibility(View.GONE);
//		isFollow = Common.isTrue(mTargetUserInfoBean.getIs_follow());
//		handleFollowState(isFollow);
//	}

	/**
	 * 设置用户头像模糊背景
	 * 
	 * @param layout
	 * @param bean
	 */
	private void setBackground(final RelativeLayout layout, UserInfoBean bean) {
		ImageLoader.getInstance().displayImage(bean.getProfile_image(), mUserTopBg);
		
//		ImageLoader.getInstance().loadImage(bean.getFace(),
//				new SimpleImageLoadingListener() {
//
//					@Override
//					public void onLoadingComplete(String imageUri, View view,
//							final Bitmap loadedImage) {
//						mUserTopLayout.getViewTreeObserver()
//								.addOnPreDrawListener(new OnPreDrawListener() {
//
//									private boolean hasMeasured;
//
//									@Override
//									public boolean onPreDraw() {
//										if (hasMeasured == false) {
//											DisplayUtil.getInst().init(
//													getActivity());
//											int with = DisplayUtil.getInst()
//													.getScreenWidth();
//											int height = mUserTopLayout
//													.getMeasuredHeight();
//											Bitmap bmp = ImageUtils.bigBitmap(
//													loadedImage, with, height);
//											bg = Utils.blur(getActivity(), bmp,
//													mUserTopLayout);
//											bmp.recycle();
//											getHandler().obtainMessage(
//													BG_SUCCESS, bg)
//													.sendToTarget();
//											hasMeasured = true;
//										}
//										return true;
//									}
//								});
//
//						super.onLoadingComplete(imageUri, view, loadedImage);
//					}
//
//				});
	}

	/**
	 * 从json 获得数据
	 * 
	 * @param userName
	 */
	private void getNewsListByHttpByNickname(String userName) {
		String url = JUrl.SITE + JUrl.Get_USER_INFO_BY_NICKNAME
				+ URLEncoder.encode(userName);
		HttpUtil.get(getActivity(), url, null, new MyJsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Common.handleHttpFailure(getActivity(), throwable);
				getHandler().obtainMessage(MSG_UI_FAILURE).sendToTarget();
				UIHelper.removePD();
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				Gson gson = new Gson();
				UserInfoBean bean = gson.fromJson(data, UserInfoBean.class);

				Message msg = getHandler().obtainMessage(MSG_UI_CONTENT, bean);
				msg.sendToTarget();

				UIHelper.removePD();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {

				UIHelper.removePD();

				new AlertDialog.Builder(getActivity())
						.setTitle("注意")
						.setMessage("抱歉，" + message)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}
								}).setCancelable(false).create().show();
			};
		});
	}

	/**
	 * 从json 获得数据
	 */
	private void getNewsListByHttp(String uid) {
		String url = JUrl.SITE + JUrl.Get_USER_INFO + uid;
		HttpUtil.get(getActivity(), url, null, new MyJsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Common.handleHttpFailure(getActivity(), throwable);
				getHandler().obtainMessage(MSG_UI_FAILURE).sendToTarget();
				UIHelper.removePD();
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				Gson gson = new Gson();
				UserInfoBean bean = gson.fromJson(data, UserInfoBean.class);

				Message msg = getHandler().obtainMessage(MSG_UI_CONTENT, bean);
				msg.sendToTarget();
				UIHelper.removePD();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {

				UIHelper.removePD();

				new AlertDialog.Builder(getActivity())
						.setTitle("注意")
						.setMessage("抱歉，" + message)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}
								}).setCancelable(false).create().show();
			};
		});
	}

	void addFragments() {
		strs.clear();
		strs.add("资料");

		UserInfoFragment aFragment = UserInfoFragment.newInstance(mTargetUserInfoBean);
		fragmentsList.clear();
		fragmentsList.add(aFragment);
		mAdapter.notifyDataSetChanged();
		mIndicator.notifyDataSetChanged();
	}
//	private void changeFollow(UserInfoBean bean) {
//
//		ArticleManagerUtils.followUser(getActivity(), bean.getUid(),
//				new Runnable() {
//
//					@Override
//					public void run() {
//						isFollow = !isFollow;
//						handleFollowState(isFollow);
//					}
//				});
//	}

	/**
	 * 处理关注状态
	 * 
	 * @param isFollow
	 */
//	protected void handleFollowState(boolean isFollow) {
////		mFocus.setText(isFollow ? getString(R.string.str_userinfo_already_focused)
////				: getString(R.string.str_userinfo_not_focused));
//		mFocusBtn.setText(isFollow ? getString(R.string.str_userinfo_already_focused)
//				: getString(R.string.str_userinfo_not_focused));
//	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			if (arg0 == 0) {
				fragmentsList.get(arg0).setUserVisibleHint(true);
			} else {
				fragmentsList.get(0).setUserVisibleHint(false);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};


	/**
	 * 设置titleBar
	 * 
	 * @param b
	 */
	protected void setTitleBar(boolean b) {
		if (mTargetUserInfoBean == null) {
			return;
		}
		if (b) {
			titleBar.setBackgroundColor(Common.isTrue(SettingManager.getInst()
					.getNightModel()) ? getResources().getDrawable(
					R.drawable.nav_bg_night) : getResources().getDrawable(
					R.drawable.nav_bg));
			titleBar.setBackText("返回",
					getResources().getColor(R.color.title_bar_title_text),
					getResources().getDrawable(R.drawable.nav_back_white));
			titleBar.setTitle(
					mTargetUserInfoBean.getNickname(),
					Common.isTrue(SettingManager.getInst().getNightModel()) ? getResources()
							.getColor(R.color.night_text_color)
							: getResources().getColor(
									R.color.title_bar_title_text));
		} else {
			titleBar.setBackgroundColor(getResources().getColor(
					R.color.transparent));
			titleBar.setBackText("返回", getResources().getColor(R.color.white),
					getResources().getDrawable(R.drawable.nav_back_white));
			titleBar.setTitle(mTargetUserInfoBean.getNickname(), getResources()
					.getColor(R.color.white));
		}
	}

	@Override
	public Activity getActivity() {
		return UserInfoActivity.this;
	}

}
