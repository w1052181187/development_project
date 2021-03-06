package com.chengning.fenghuo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.adapter.AtSuggestExpandableListAdapter;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.woozzu.android.widget.IndexableExpandableListView;

@SuppressLint("ResourceAsColor")
public class AtSuggestActivity extends BaseFragmentActivity {

	private static final String TAG = AtSuggestActivity.class.getSimpleName();

	private static final int INIT_NEWSDATA_UI = 1;

	private EditText mInputEditText;
	private IndexableExpandableListView mPullListView;
	private TextView mTipsText;

	private List<UserInfoBean> mDataList;
	private List<UserInfoBean> mRecentList;
	private AtSuggestExpandableListAdapter mAdapter;

	private Activity mContext;

	private TitleBar titleBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		this.setContentView(R.layout.activity_at_suggest);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		mContext = this;
		titleBar = new TitleBar(mContext, true);
		titleBar.setTitle("联系人");
		titleBar.showDefaultBack();
		titleBar.setRightButton("更新",
				getResources().getColor(R.color.home_bottom_txt_sel));

		mInputEditText = (EditText) findViewById(R.id.search_inputedit);
		mTipsText = (TextView) findViewById(R.id.tips_txt);
		mPullListView = (IndexableExpandableListView) findViewById(R.id.list);
	}

	@Override
	public void initDatas() {
		mDataList = new ArrayList<UserInfoBean>();

		// IndexScroller
		mPullListView.setFastScrollEnabled(true);

		showEmpty();

		// if(!App.getInst().isFollowChange()){
		// if(App.getInstance().getFollowList() == null
		// || App.getInstance().getFollowList().size() == 0){
		// App.getInstance().setFollowList(ContactsServer.getInst(mContext).queryUserInfoAll());
		// }
		// }
		// mRecentList =
		// RecentContactsServer.getInst(mContext).queryRecentTenUserInfo();
		// getHandler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// // mPullListView.setRefreshing();
		// if(App.getInstance().isFollowChange()
		// || App.getInstance().getFollowList() == null
		// || App.getInstance().getFollowList().size() == 0)
		// {
		// List<UserInfoBean> list =
		// ContactsServer.getInst(mContext).queryUserInfoAll();
		// if(list == null || list.size() == 0 ||
		// App.getInstance().isFollowChange()){
		// UIHelper.addPD(mContext, "加载中...");
		// getNewsListByHttp();
		// }else{
		// App.getInstance().setFollowList(list);
		// sendListMessage(INIT_NEWSDATA_UI, list);
		// }
		// }else{
		// sendListMessage(INIT_NEWSDATA_UI, App.getInst().getFollowList());
		// }
		// }
		// }, Consts.TIME_WAIT_REFRESH);

	}

	@Override
	public void installListeners() {
		titleBar.setRightButtonOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.addPD(mContext, "加载中...");
				getNewsListByHttp();
			}
		});
		// mInput.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // AtSearchLocalActivity.launch(mContext);
		// }
		// });
		mInputEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String key = mInputEditText.getText().toString().trim();
				if (key.equals("")) {
					mAdapter.updateData(mDataList);
					mPullListView.setIndexScrollerIsVisible(true);
				} else {
					mAdapter.updateBySearchKey(key);
					mPullListView.setIndexScrollerIsVisible(false);
				}
				mAdapter.notifyDataSetChanged();
				for (int i = 0; i < mAdapter.getGroupCount(); i++) {
					mPullListView.expandGroup(i);
				}
			}
		});
		mPullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UserInfoBean u = (UserInfoBean) view.findViewById(
						R.id.user_name).getTag();
				setResultAtUser(u);
			}
		});
		mPullListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader
				.getInstance(), false, true));
		mPullListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return true;
			}
		});
		mPullListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				UserInfoBean u = (UserInfoBean) v.findViewById(R.id.user_name)
						.getTag();
				u.setLasttime(String.valueOf(System.currentTimeMillis()));
				setResultAtUser(u);
				return true;
			}
		});
		getNewsListByHttp();
	}

	public void setResultAtUser(UserInfoBean bean) {
		setResultAtUser(bean.getNickname());
	}

	public void setResultAtUser(String nickname) {
		String result = nickname;
		if (nickname != null) {
			result = nickname;
		}

		Intent intent = new Intent();
		// 把返回数据存入Intent
		intent.putExtra("result", result);
		// 设置返回数据
		mContext.setResult(11, intent);
		// 关闭Activity
		mContext.finish();
	}

	@Override
	public void uninstallListeners() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 11) {
			if (data != null) {
				String result = data.getExtras().getString("result");
				setResultAtUser(result);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void sendListMessage(int what, List list) {
		int listSize = 0;
		if (list != null) {
			listSize = list.size();
		}
		Message message = getHandler().obtainMessage(what, list);
		message.arg1 = listSize;
		message.sendToTarget();
	}

	private void showEmpty() {
		mAdapter = new AtSuggestExpandableListAdapter(mContext, mDataList,
				null, false, mRecentList);
		mPullListView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	private void showTips(boolean show) {
		if (show) {
			String str = null;
			str = "还没有关注";
			mTipsText.setText(str);
			mTipsText.setVisibility(View.VISIBLE);
		} else {
			mTipsText.setVisibility(View.GONE);
		}
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case INIT_NEWSDATA_UI:
			List<UserInfoBean> list = (List<UserInfoBean>) msg.obj;
			int listSize = msg.arg1;
			if (list != null && list.size() > 0) {
				mDataList.clear();
				mDataList.addAll(list);
				mAdapter = new AtSuggestExpandableListAdapter(mContext,
						mDataList, null, false, mRecentList);
				mPullListView.setAdapter(mAdapter);
				mPullListView.setIndexScrollerAlwaysVisible(false);
				mAdapter.notifyDataSetChanged();
				for (int i = 0; i < mAdapter.getGroupCount(); i++) {
					mPullListView.expandGroup(i);
				}
			} else {
				showTips(true);
			}
			UIHelper.removePD();
			break;
		}
	}

	private void getNewsListByHttp() {
		String url = JUrl.SITE + JUrl.URL_GET_MILITART_FANS;
		HttpUtil.get(mContext, url, null, new MyJsonHttpResponseHandler() {
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				Gson gson = new Gson();
				List<UserInfoBean> list = gson.fromJson(data,
						new TypeToken<List<UserInfoBean>>() {
						}.getType());
				// App.getInst().setFollowList(list);
				// App.getInst().setFollowChange(false);
				// ContactsServer.getInst(mContext).deleteUserInfoAll();
				// ContactsServer.getInst(mContext).insertUserInfoList(list);
				sendListMessage(INIT_NEWSDATA_UI, list);
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.removePD();
				UIHelper.showToast(mContext, message);
				showTips(true);
			};

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				Common.handleHttpFailure(getActivity(), throwable);
			};
		});
	}

	@Override
	public Activity getActivity() {
		return this;
	}
}
