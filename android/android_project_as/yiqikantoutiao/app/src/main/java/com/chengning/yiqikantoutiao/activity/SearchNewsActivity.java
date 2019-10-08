package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.PageHelper;
import com.chengning.common.util.SerializeUtil;
import com.chengning.common.widget.MultiStateView;
import com.chengning.common.widget.MultiStateView.ViewState;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.LoadStateManager;
import com.chengning.yiqikantoutiao.LoadStateManager.LoadState;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.adapter.SearchNewsAdapter;
import com.chengning.yiqikantoutiao.data.access.LocalStateDA;
import com.chengning.yiqikantoutiao.data.bean.BaseChannelItemBean;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.data.bean.SearchNewsListBean;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.SPHelper;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.widget.NoScrollGridView;
import com.chengning.yiqikantoutiao.widget.PullToRefreshListView_FootLoad;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchNewsActivity extends BaseActivity {

	
	private static final int DATA_SUCCESS = 10000;
	
	private static final int MAX_HISTORY = 20;
	
	private EditText mEditText;
	private Button mBtnClear;
	private View mCancel;
	private TextView mCancelTv;
	private PullToRefreshListView_FootLoad mPullListView;  
	private MultiStateView mMultiStateView;

	private View mSearchHistoryLayout;
	private NoScrollGridView mHistoryGridView;
	private TextView mSearchHistoryClear;

	private View mHotKeywordsLayout;
	private NoScrollGridView mHotKeywordsGridView;
	
	private ArrayList<String> mHistoryList;
	private SearchNewsAdapter mAdapter;
	private List<ChannelItemBean> mList;
	private LoadStateManager mLoadStateManager;
	private PageHelper mPage;
	private HistoryAdapter mHistoryAdapter;
	
	private String mStrSearchKeyWord;
	private String mStrSearchHistory;
	private String mHotKeywords;
	private View mSearchHistoryScroll;
	private ArrayList<String> mHotKeywordsList;
	private HistoryAdapter mHotKeywordsAdapter;
	private boolean mHistoryDelete = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(this);
		setContentView(R.layout.activity_searchnews);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		
		mEditText = (EditText) findViewById(R.id.search_edittext);
		mBtnClear = (Button) findViewById(R.id.title_bar_search_clear);
		mCancel = findViewById(R.id.title_bar_cancel);
		mCancelTv = (TextView) findViewById(R.id.title_bar_cancel_text);
		mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
		mMultiStateView.setVisibility(View.INVISIBLE);
		mPullListView = (PullToRefreshListView_FootLoad) findViewById(R.id.list);
		mPullListView.setMode(Mode.DISABLED);
		
		mSearchHistoryScroll= findViewById(R.id.search_history_scroll);
		mSearchHistoryLayout= findViewById(R.id.search_history_ll);
		mHistoryGridView = (NoScrollGridView) findViewById(R.id.history_list);
		mSearchHistoryClear = (TextView) findViewById(R.id.search_history_clear);

		mHotKeywordsLayout= findViewById(R.id.search_hot_keywords_ll);
		mHotKeywordsGridView = (NoScrollGridView) findViewById(R.id.hot_keywords_list);
	}

	@Override
	public void initDatas() {
		mPage = new PageHelper();
		mList = new ArrayList<ChannelItemBean>();
		initLoadState();
		mAdapter= new SearchNewsAdapter(getActivity(), mList, null);
		mPullListView.setAdapter(mAdapter);

		//历史记录
		mHistoryList = new ArrayList<String>();
		mHistoryAdapter = new HistoryAdapter(mHistoryList);
		mHistoryGridView.setAdapter(mHistoryAdapter);
		mHistoryAdapter.setDeleteListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int pos = (int) v.getTag();
				mHistoryList.remove(pos);
				dealHistory();
			}
		});

		//热门搜索
		mHotKeywordsList = new ArrayList<String>();
		mHotKeywordsAdapter = new HistoryAdapter(mHotKeywordsList);
		mHotKeywordsGridView.setAdapter(mHotKeywordsAdapter);
		
		mStrSearchHistory = SPHelper.getInst().getString(SPHelper.SEARCH_NEWS_HISTORY);
		mHotKeywords = SPHelper.getInst().getString(SPHelper.SEARCH_NEWS_KEYWORDS);

		handleHistory(mStrSearchHistory);
		handleHotKeywordsSearch(mHotKeywords);


		dealHistory();
		
		getHandler().postDelayed(new Runnable() {

			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(mEditText, 0);
			}
		}, 300);
		
	}

	private void handleHotKeywordsSearch(String mStrKeywords) {
		if (TextUtils.isEmpty(mStrKeywords)) {
			return;
		};
		mHotKeywordsList.clear();
		mHotKeywordsList.addAll((ArrayList) SerializeUtil.deSerialize(mStrKeywords));
	}

	private void handleHistory(String mStrSearchHistory) {
		if (TextUtils.isEmpty(mStrSearchHistory)) {
			return;
		}
		String[] historyKeywords = mStrSearchHistory.split(",");
		mHistoryList.clear();
		for(String t : historyKeywords){
			mHistoryList.add(t);
		}
//		mHistoryAdapter.notifyDataSetChanged();
	}

	private void dealHistory(){
		mMultiStateView.setVisibility(View.GONE);
		boolean isHistoryEmpy = Common.isListEmpty(mHistoryList);
		boolean isHotKeywordsEmpy = Common.isListEmpty(mHotKeywordsList);
		if(isHistoryEmpy && isHotKeywordsEmpy){
			mSearchHistoryScroll.setVisibility(View.GONE);

		} else  {
			mSearchHistoryScroll.setVisibility(View.VISIBLE);
			if (!isHistoryEmpy) {
				mSearchHistoryLayout.setVisibility(View.VISIBLE);
				mHistoryAdapter.notifyDataSetChanged();
			} else {
				mSearchHistoryLayout.setVisibility(View.GONE);
			}
			if (!isHotKeywordsEmpy && !mHistoryDelete) {
				mHotKeywordsLayout.setVisibility(View.VISIBLE);
				mHotKeywordsAdapter.notifyDataSetChanged();
			} else {
				mHotKeywordsLayout.setVisibility(View.GONE);
			}

		}

	}

	@Override
	public void installListeners() {
		mBtnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEditText.setText("");
				mSearchHistoryLayout.setVisibility(View.VISIBLE);
				dealHistory();
				mMultiStateView.setVisibility(View.GONE);
			}
		});
		mCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mPullListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener(){
			@Override
			public void onLastItemVisible() {
				if(mPage.hasNextPage()){
					mPullListView.setFootLoading();
					getDataByHttp();
				}
			}
		}); 
		mPullListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
				View tagView = view.findViewById(R.id.item_channel_item_content_layout);
				Object tag = tagView.getTag();
				if(tag instanceof ChannelItemBean){
					Common.handleTextViewReaded(getActivity(), view, R.id.item_channel_item_title, true);
					
					ChannelItemBean bean = (ChannelItemBean) tag;
					handleBeanClick(bean);
				}
			}
		});
		mMultiStateView.setRefreshOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDataByHttpInit();
			}
		});
		mHistoryGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				mStrSearchKeyWord = mHistoryList.get(position);
				mHistoryList.remove(position);
				mHistoryList.add(0, mStrSearchKeyWord);
				getDataByHttpInit();
			}
		});

		mHotKeywordsGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				mStrSearchKeyWord = mHotKeywordsList.get(position);
				int historyPos = 0;
				boolean isHas = false;
				for (String str : mHistoryList) {
					if (TextUtils.equals(str,mStrSearchKeyWord)) {
						historyPos = mHistoryList.indexOf(str);
						isHas = true;
					}
				}
				if (isHas) {
					mHistoryList.remove(historyPos);
				}
				mHistoryList.add(0, mStrSearchKeyWord);
				getDataByHttpInit();
			}
		});

		mSearchHistoryClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mHistoryDelete = !mHistoryDelete;
				mHistoryAdapter.setIsDelete(mHistoryDelete);
				mHistoryAdapter.notifyDataSetChanged();
				Drawable drawable = getResources().getDrawable(R.drawable.searchnews_delete);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
				mSearchHistoryClear.setCompoundDrawables(mHistoryDelete ? null : drawable, null, null, null);
				mSearchHistoryClear.setText(mHistoryDelete ? "完成" : "");
				mHotKeywordsLayout.setVisibility(mHistoryDelete ? View.GONE : View.VISIBLE);

			}
		});

		mEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String str = mEditText.getText().toString().trim();
				if (TextUtils.isEmpty(str)) {
					if (mBtnClear.getVisibility() != View.GONE) {
						mBtnClear.setVisibility(View.GONE);
					}
				} else {
					if (mBtnClear.getVisibility() != View.VISIBLE) {
						mBtnClear.setVisibility(View.VISIBLE);
					}
				}
			}
		});
		mEditText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					mStrSearchKeyWord = mEditText.getText().toString().trim();
					if (TextUtils.isEmpty(mStrSearchKeyWord)) {
						return true;
					}

					int index = mHistoryList.indexOf(mStrSearchKeyWord);
					if (index != -1) {
						mHistoryList.remove(index);
						mHistoryList.add(0, mStrSearchKeyWord);
					} else {
						mHistoryList.add(0, mStrSearchKeyWord);
					}

					getDataByHttpInit();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case DATA_SUCCESS:
			mList.clear();
			if (!Common.isListEmpty(mPage.getDataList())) {
				mList.addAll(mPage.getDataList());
				mPage.setMaxid(mList.get(0).getTid());
				mAdapter.notifyDataSetChanged();
			}
			handleRefreshSuccess();
			mLoadStateManager.setState(Common.isListEmpty(mList) ? LoadState.NoData : LoadState.Success);
			break;
		default:
			break;
		}
	}
	
	private void getDataByHttpInit(){
		if(TextUtils.isEmpty(mStrSearchKeyWord)){
			return;
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		mEditText.setText(mStrSearchKeyWord);
		mEditText.setSelection(mStrSearchKeyWord.length());

		mMultiStateView.setVisibility(View.VISIBLE);
		mSearchHistoryScroll.setVisibility(View.GONE);
		mCancelTv.setText("返回");
		mPage.setCurrentPage(0);
		mPage.setMaxid("");
		mPage.clear();
		getDataByHttp();
	}
	
	private void getDataByHttp(){
		if(mPage.isCurrentPageFirst()){
			mLoadStateManager.setState(LoadState.Init);
		}
		
		if(mPage.isRequestRunning()){
			return;
		}

		String keycode = "";
		try {
			keycode = URLEncoder.encode(mStrSearchKeyWord, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			UIHelper.showToast(getActivity(), "搜索字不合法");
			return;
		}
		
		String url = JUrl.SITE + JUrl.URL_GET_SEARCH;
		url = mPage.isCurrentPageFirst() ? mPage.appendFirstPage(url) : mPage.appendNextPageAndMaxid(url);
		mPage.setRequestStart(url.toString());
		url = JUrl.append(url, "keywords", keycode);
		HttpUtil.get(url, new MyJsonHttpResponseHandler() {
			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				Gson gson = new Gson();
				SearchNewsListBean bean = gson.fromJson(data, SearchNewsListBean.class);
				if(mPage.isCurrentPageFirst()){
					mAdapter.setKeywords(handleKeyWords(bean.getKeywords()));
				}
				int currentPage = (null == bean ? 1 : mPage.getCurrentPage() + 1);
				mPage.setCurrentPage(currentPage);
				mPage.setPageData(currentPage, (null == bean ? null : bean.getList()));
				mPage.setMaxPage((null == bean ? 0 : bean.getTotal_page()));
				getHandler().obtainMessage(DATA_SUCCESS).sendToTarget();
			}
			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
				handleRefreshSuccess();
				if(status == -2){
					mLoadStateManager.setState(LoadState.NoData);
				}else{
					UIHelper.showToast(getActivity(), message);
					if(mPage.isCurrentPageFirst()){
						mLoadStateManager.setState(LoadState.Failure);
					}
				}
			}
			@Override
	        public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject error) {
				handleRefreshSuccess();
				Common.handleHttpFailure(getActivity(), throwable);
				if(mPage.isCurrentPageFirst()){
					mLoadStateManager.setState(LoadState.Failure);
				}
			}
		});
	}

	private void initLoadState() {
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {
			@Override
			public void OnStateChange(LoadState state, Object obj) {
				switch (state) {
				case Init:
					mMultiStateView.setViewState(ViewState.LOADING);
					break;
				case Success:
					mMultiStateView.setViewState(ViewState.CONTENT);
					break;
				case NoData:
					mMultiStateView.setViewState(ViewState.EMPTY);
					break;
				case Failure:
					mMultiStateView.setViewState(ViewState.ERROR);
					break;
				}
			}
		});
		mLoadStateManager.setState(LoadState.Init);
	}
	

	private void handleBeanClick(BaseChannelItemBean bean) {
		if (bean == null) {
			return;
		}
		if (bean instanceof ChannelItemBean) {
			LocalStateDA.getInst(getActivity()).setReadStateRead(
					LocalStateDA.PREFIX_CHANNEL_ITEM,
					((ChannelItemBean) bean).getTid());
			ChannelItemBean itemBean = ((ChannelItemBean) bean);
			String articleType = itemBean.getContent_type();
			int showType = itemBean.getShow_type();

			if (!TextUtils.isEmpty(itemBean.getTz_nav()) && getActivity() instanceof MainActivity) {
				((MainActivity) getActivity()).onNav(itemBean.getTz_nav());
				return;
			}
			if(articleType.equals(Consts.CONTENT_TYPE_TUWEN)){
				PhotoPageActivity.launch(getActivity(), itemBean);
				return;
			}
			if (TextUtils.equals(articleType, Consts.CONTENT_TYPE_WEB)) {
				// 处理web类型文章点击
				String imgUrl = null;
				if (!TextUtils.isEmpty(itemBean.getImage_list())) {
					imgUrl = itemBean.getImage_list();
				} else if (!Common.isListEmpty(itemBean.getImage_arr())){
					imgUrl = itemBean.getImage_arr().get(0);
				}
				AdDetailActivity.launch(getActivity(), AdDetailActivity.TYPE_ARTICLE, itemBean.getRedirecturl(), imgUrl);
			} else if (TextUtils.equals(articleType,
					Consts.CONTENT_TYPE_VIDEO)) {
				VideoDetailActivity.launch(getActivity(), itemBean);
			} else {
				ArticleActivity.launch(getActivity(), itemBean);
			}

		}
	}
	
	private void handleRefreshSuccess() {
		mPullListView.setFootLoadFull();
		mPage.setRequestEnd();
	}
	
	private String[] handleKeyWords(ArrayList<String> list){
		if(Common.isListEmpty(list)){
			return new String[]{};
		}
		return list.toArray(new String[]{});
	}
	
	@Override
	public Activity getActivity() {
		return this;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		SPHelper.getInst().saveString(SPHelper.SEARCH_NEWS_HISTORY, listToString(mHistoryList));
	}
	
	private String listToString(List<String> list){
		if(list==null){
	      return "";
	   }
	   StringBuilder result = new StringBuilder();
	   boolean first = true;
	   int i = 0;
	   for(String string :list) {
	      if(first) {
	         first=false;
	      }else{
	         result.append(",");
	      }
	      result.append(string);
	      i++;
	      if(i >= MAX_HISTORY){
	    	  break;
	      }
	   }
	   return result.toString();
	}
	
	class HistoryAdapter extends BaseAdapter {

		private ArrayList<String> mList;
		private boolean isDelete;
		private OnClickListener mDeleteListener;

		public HistoryAdapter(ArrayList<String> mList) {
			this.mList = mList;
		}

		public void setIsDelete(boolean isDelete) {
			this.isDelete = isDelete;
		}

		void setDeleteListener(OnClickListener listener) {
			this.mDeleteListener = listener;
		}

		@Override
		public int getCount() {
			if(mList.size() > MAX_HISTORY)
				return MAX_HISTORY;
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = getLayoutInflater().inflate(R.layout.item_search_history, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.item_title);

			title.setText(mList.get(position));
			ImageView delete = (ImageView) convertView.findViewById(R.id.item_delete);
			delete.setTag(position);

			if (isDelete) {
				delete.setVisibility(View.VISIBLE);
				delete.setOnClickListener(mDeleteListener);
			} else {
				delete.setVisibility(View.GONE);
			}

			return convertView;
		}
	}

}
