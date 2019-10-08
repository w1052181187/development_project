package com.chengning.fenghuo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.ThreadHelper;
import com.chengning.common.widget.MultiStateView;
import com.chengning.common.widget.MultiStateView.ViewState;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.LoadStateManager;
import com.chengning.fenghuo.LoadStateManager.LoadState;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.PushMsgManager;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.data.access.ChatConversationDA;
import com.chengning.fenghuo.data.access.UserinfoOtherServer;
import com.chengning.fenghuo.data.bean.ChatConversationBean;
import com.chengning.fenghuo.data.bean.ChatMessageBean;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.util.ChatClientManager;
import com.chengning.fenghuo.util.ChatClientManager.MessageListener;
import com.chengning.fenghuo.util.ChatClientManager.QueryListener;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.NotificationUtils;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.DynamicTextView;
import com.chengning.fenghuo.widget.PullToRefreshListView_FootLoad;
import com.chengning.fenghuo.widget.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.RequestParams;

public class ChatListActivity extends BaseFragmentActivity {

	protected static final int DATA_SUCCESS = 0;
	protected static final int DATA_FAILTURE = 1;
	protected static final int DELETE_SUCCESS = 2;
	private PullToRefreshListView_FootLoad mListView;
	private TitleBar mTitleBar;
	private ChatListListAdapter adapter;
	private ArrayList<ChatConversationBean> mList;

	private MessageListener mListener;
	private ChatClientManager mClientManager;
	private LoadStateManager mLoadStateManager;
	private UserInfoBean mUserBean;

	private TextView mTitleRightBtn;
	protected boolean isEditFlag;
	private NotificationManager manager;
	protected boolean isUpdateUserInfo;
	private BroadcastReceiver receiver;
	private Vibrator vibrator;
	private String mMemberId;
	private MultiStateView mMultiStateView;
	private HandlerThread mConThread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_chat_list);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		handleIsOnline();
		IntentFilter filter = new IntentFilter();
		filter.addAction(getPackageName() + "ACTION_RECEIVE_MSG");
		registerReceiver(receiver, filter);
		
		if (null != mList) {
			for (ChatConversationBean mConversation : mList) {
				NotificationUtils.addTag(mConversation.getCon_id());
			}
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		unregisterReceiver(receiver);
		if (null != mList) {
			for (ChatConversationBean mConversation : mList) {
				NotificationUtils.removeTag(mConversation.getCon_id());
			}
		}
		super.onPause();
	}

	@Override
	public Activity getActivity() {
		return ChatListActivity.this;
	}

	@Override
	public void initViews() {

		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setRightButton(EditState.Delete.getStr(), getResources()
				.getColor(R.color.common_blue));
		mTitleRightBtn = (TextView) mTitleBar.getRightButton();
		mTitleRightBtn.setTag(EditState.Delete);
		
		mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
		mListView = (PullToRefreshListView_FootLoad) mMultiStateView.findViewById(R.id.chat_list_listview);
		mMultiStateView.setEmptyHintColorResource(!Common.isTrue(SettingManager.getInst().getNightModel()) 
				? R.color.article_time : R.color.night_text_color);
	}

	enum EditState {
		Delete("编辑"), Compelete("完成"), ;
		private String str;

		private EditState(String str) {
			this.str = str;
		}

		public String getStr() {
			return str;
		}
	}

	@Override
	public void initDatas() {
		if (!LoginManager.getInst().checkLoginWithNotice(getActivity())) {
			finish();
			return;
		}
		mClientManager = ChatClientManager.getInst();

		mUserBean = App.getInst().getUserInfoBean();
		initLoadState();
		initListener();
		mTitleBar.setTitle("聊天列表");
		isEditFlag = false;
		mList = new ArrayList<ChatConversationBean>();
		adapter = new ChatListListAdapter(getActivity(), mList,
				new DeleteListener(), isEditFlag);
		mListView.setAdapter(adapter);

		manager = (NotificationManager) getActivity().getSystemService(
				Context.NOTIFICATION_SERVICE);

		isUpdateUserInfo = false;
		
		vibrator = (Vibrator) getActivity().getSystemService(
				Service.VIBRATOR_SERVICE);

		PushMsgManager.getInstance().getPushUserDataBean().setNewpm(0);

	}

	/**
	 * 处理聊天是否下线
	 */
	private void handleIsOnline() {
		if (null == mClientManager.getmClient()) {
			mClientManager.handleOfflineHint(getActivity());
			finish();
			return;
		}
		// mClientManager.getmClient().getClientStatus(
		// new AVIMClientStatusCallback() {
		//
		// @Override
		// public void done(AVIMClientStatus status) {
		// if (null == status) {
		// // mClientManager.close(true);
		// mClientManager.handleOfflineHint(getActivity());
		// finish();
		// } else {
		refreshData();
		// }
		// }
		// });
	}

	private void initListener() {
		mListener = new MessageListener() {

			@Override
			public void sendSuccess(AVIMTypedMessage msg) {

			}

			// @Override
			// public void querySuccess(final List<AVIMConversation>
			// conversations) {
			//
			// mList.clear();
			// mList.addAll(conversations);
			//
			// updateList();
			// // getHandler().obtainMessage(DATA_SUCCESS,
			// mList).sendToTarget();
			// getHandler().postDelayed(new Runnable() {
			//
			// @Override
			// public void run() {
			// updateUserinfo(conversations);
			// }
			// }, 2000);
			//
			// }

			@Override
			public void handleFailure(AVIMException e) {
				// switch (e.getCode()) {
				// // case 0:
				// // mClientManager.close(true);
				// // finish();
				// // break;
				// case 119:
				// mClientManager.close(true);
				// finish();
				// break;
				//
				// default:
				// getHandler().obtainMessage(DATA_FAILTURE).sendToTarget();
				// break;
				// }
				getHandler().obtainMessage(DATA_FAILTURE).sendToTarget();
			}

			@Override
			public void fetchSuccess(List<ChatMessageBean> list) {

			}

			@Override
			public void fetchHistorySuccess(List<ChatMessageBean> secondPage) {

			}

			@Override
			public void querySuccess(
					final List<ChatConversationBean> conversations) {
				mList.clear();
				mList.addAll(conversations);

				if (!isUpdateUserInfo && !Common.isListEmpty(mList)) {
//					getHandler().postDelayed(new Runnable() {
//
//						@Override
//						public void run() {
							updateUserinfo(conversations);
//						}
//					}, 500);
				} else {
					updateList();
				}

			}

		};
		
		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if ((getPackageName() + "ACTION_RECEIVE_MSG").equals(intent
						.getAction())) {
					AVIMTypedMessage message = (AVIMTypedMessage) intent
							.getParcelableExtra("msg");
					refreshData();
					vibrator.vibrate(500);
					
				}

			}
		};
	}

	/**
	 * 更新列表
	 */
	protected void updateList() {
		if (mList.isEmpty()) {
			getHandler().obtainMessage(DATA_SUCCESS, mList).sendToTarget();
			return;
		}
		
		final ArrayList<ChatConversationBean> beans = new ArrayList<ChatConversationBean>();
		for (final ChatConversationBean bean : mList) {

			mClientManager.queryConversationByMem(bean.getCon_members(), new QueryListener() {
				
				@Override
				public void sussess() {
					mMemberId = "";
					ArrayList<String> members = bean.getCon_members();
					if (null != members) {
						mMemberId = members.get(0);
						if (App.getInst().getUserInfoBean().getUid().equals(mMemberId)) {
							if (members.size() > 1) {
								mMemberId = members.get(1);
							} else {
								mMemberId = "";
							}

						}
					}

					AVIMConversation conversation = mClientManager.getConversation();
					if (!TextUtils.isEmpty(mMemberId)) {
						UserInfoBean infoBean = UserinfoOtherServer.getInst(
								getActivity()).queryTargetUid(mMemberId);

						String cAttr = mClientManager.creatConversationAttr(mUserBean);
						String tAttr = "";
						if (null != infoBean) {
							tAttr = mClientManager.creatConversationAttr(infoBean);
							bean.setCon_face(infoBean.getFace());
							bean.setCon_name(infoBean.getNickname());
							bean.setRole_name(infoBean.getRole_name());
						}
						String mCreater = bean.getCreator();
						if (null != mCreater) {
							if (mCreater.equals(mUserBean.getUid())) {
								conversation.setAttribute("creat_bean", cAttr);
								if (null != infoBean) {
									conversation.setAttribute("target_bean", tAttr);
								}

							} else {
								if (null != infoBean) {
									conversation.setAttribute("creat_bean", tAttr);
								}
								conversation.setAttribute("target_bean", cAttr);
							}
							mClientManager.updateConversation(conversation);
						}
					}
					getHandler().obtainMessage(DATA_SUCCESS, mList).sendToTarget();
				}
				
				@Override
				public void failure() {
					beans.add(bean);
					ChatConversationDA.getInst(getActivity()).delete(bean);
					mList.removeAll(beans);
					getHandler().obtainMessage(DATA_SUCCESS, mList).sendToTarget();
				}
			}, mListener);
			
		}
		
	}

	/**
	 * 更新用户信息
	 * 
	 * @param conversations
	 */
	protected void updateUserinfo(List<ChatConversationBean> conversations) {
		StringBuffer uids = new StringBuffer();
		String userId = App.getInst().getUserInfoBean().getUid();
		int size = conversations.size();
		ArrayList<String> members = null;
		for (int i = 0; i < size; i++) {
			members = conversations.get(i).getCon_members();
			for (String uid : members){
				if (!TextUtils.equals(userId, uid)) {
					uids.append(uid);
				}
				
			}
			
			if (!(size - 1 == i)) {
				uids.append(",");
			}
		}
		
		RequestParams params = new RequestParams();
		params.put("uid", uids);
		HttpUtil.get(JUrl.SITE + JUrl.GET_USER_INFO_BY_IDS, params,
				new MyJsonHttpResponseHandler() {
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				updateList();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onDataSuccess(int status, String mod,
					String message, String data, JSONObject obj) {
				Gson gson = new Gson();
				ArrayList<UserInfoBean> list = gson.fromJson(data,
						new TypeToken<ArrayList<UserInfoBean>>() {
						}.getType());
				UserinfoOtherServer.getInst(getActivity()).insertList(
						list);
				updateList();
				isUpdateUserInfo = true;
			}

			@Override
			public void onDataFailure(int status, String mod,
					String message, String data, JSONObject obj) {
				updateList();
				isUpdateUserInfo = true;
			}
		});
	}

	/**
	 * 初始化加载状态
	 */
	private void initLoadState() {
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager
				.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {

					@Override
					public void OnStateChange(LoadState state, Object obj) {
						switch (state) {
						case Init:
							mMultiStateView.setViewState(ViewState.LOADING);
							mTitleBar.hideRightButton();
							break;
						case Success:
							mMultiStateView.setViewState(ViewState.CONTENT);
							mTitleBar.showRightButton();
							break;
						case NoData:
							mMultiStateView.setViewState(ViewState.EMPTY);
							mTitleBar.hideRightButton();
							break;
						case Failure:
							mMultiStateView.setViewState(ViewState.ERROR);
							break;
						default:
							break;
						}
					}
				});
		mLoadStateManager.setState(LoadState.Init);
	}

	@Override
	public void installListeners() {

		mTitleBar.setRightButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (EditState.Delete.equals(mTitleRightBtn.getTag())) {
					mTitleRightBtn.setText(EditState.Compelete.getStr());
					mTitleRightBtn.setTag(EditState.Compelete);
					// mTitleRightBtn.setTextColor(getResources().getColor(
					// R.color.common_blue));
					isEditFlag = true;

				} else if (EditState.Compelete.equals(mTitleRightBtn.getTag())) {
					mTitleRightBtn.setText(EditState.Delete.getStr());
					mTitleRightBtn.setTag(EditState.Delete);
					// mTitleRightBtn.setTextColor(getResources().getColor(
					// R.color.common_blue));
					isEditFlag = false;
				}
				adapter.setDelFlag(isEditFlag);
				adapter.notifyDataSetChanged();
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), ChatActivity.class);
				intent.putExtra(Consts.CHAT_ENTRY, Consts.ChatAction.LIST);
				intent.putExtra(Consts.CONVERSATION_ID, mList.get(position - 1)
						.getCon_id());
				startActivity(intent);
			}
		});

		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				refreshData();
			}
		});

		mMultiStateView.setRefreshOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				refreshData();
			}
		});

	}

	/**
	 * 刷新数据
	 */
	protected void refreshData() {
		if (null != mClientManager.getmClient()) {
			if (mConThread == null) {
				mConThread = ThreadHelper.creatThread("my_con");
			}
			ThreadHelper.handle(mConThread, new Runnable() {
				
				@Override
				public void run() {
					mClientManager.queryConversations(getActivity(), null, null,
							mListener);
				}
			});
			
			manager.cancelAll();
		}
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case DATA_SUCCESS:
			List list = (List) msg.obj;
			adapter.notifyDataSetChanged();
			handleRefreshComplete();
			if (list.isEmpty()) {
				mLoadStateManager.setState(LoadState.NoData);
				mTitleRightBtn.setVisibility(View.GONE);
			} else {
				mLoadStateManager.setState(LoadState.Success);
				mTitleRightBtn.setVisibility(View.VISIBLE);
			}

			break;
		case DATA_FAILTURE:
			if (Common.isListEmpty(mList)) {
				mLoadStateManager.setState(LoadState.Failure);
			}

			handleRefreshComplete();
			break;
		case DELETE_SUCCESS:
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

	private void handleRefreshComplete() {
		mListView.setFootLoadFull();
		mListView.onRefreshComplete();
	}

	/**
	 * 删除对话
	 * 
	 * @param pos
	 */
	private void deleteCov(final int pos) {

		if (mConThread == null) {
			mConThread = ThreadHelper.creatThread("my_con");
		}
		ThreadHelper.handle(mConThread, new Runnable() {
			
			@Override
			public void run() {
				ChatConversationDA.getInst(getActivity()).delete(mList.get(pos));
				mList.remove(pos);
				getHandler().obtainMessage(DELETE_SUCCESS).sendToTarget();
			}
		});
	}
	
	@Override
	public void onDestroy() {
		ThreadHelper.destory(mConThread);
		getHandler().removeMessages(DATA_SUCCESS);
		getHandler().removeMessages(DATA_FAILTURE);
		getHandler().removeMessages(DELETE_SUCCESS);
		super.onDestroy();
	}

	/**
	 * 删除收藏监听类
	 * 
	 */
	public class DeleteListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int pos = (Integer) v.findViewById(R.id.message_entry_delete)
					.getTag();
			deleteCov(pos);
		}

	}

	class ChatListListAdapter extends BasePageListAdapter {

		private boolean isDeleteFlag;
		private OnClickListener listener;
		private Gson gson;

		public ChatListListAdapter(Activity activity, List list,
				OnClickListener deleteListener, boolean isDeleteFlag) {
			super(activity, list);
			this.isDeleteFlag = isDeleteFlag;
			this.listener = deleteListener;
			gson = new Gson();
		}

		@Override
		public int buildLayoutId() {
			return R.layout.item_message_entry;
		}

		@Override
		public int getCount() {
			if (getList().size() > 20) {
				return 20;
			}
			return super.getCount();
		}

		@Override
		public void handleLayout(View convertView, int position, Object obj) {
			ChatConversationBean conversation = (ChatConversationBean) obj;
			ImageView image = BaseViewHolder.get(convertView,
					R.id.message_entry_image);
			final DynamicTextView message = BaseViewHolder.get(convertView,
					R.id.message_entry_abstract);
			TextView time = BaseViewHolder.get(convertView,
					R.id.message_entry_time);
			TextView title = BaseViewHolder.get(convertView,
					R.id.message_entry_title);
			ImageView delete = BaseViewHolder.get(convertView,
					R.id.message_entry_delete);
			TextView red = BaseViewHolder.get(convertView,
					R.id.message_entry_red);

			ChatMessageBean messageBean = conversation.getNewest_msg();
			String content = "";
			if (null != messageBean) {
				switch (messageBean.getMsg_type()) {
				case AVIMMessageType.TEXT_MESSAGE_TYPE:
					content = messageBean.getContent();
					break;
				case AVIMMessageType.IMAGE_MESSAGE_TYPE:
					content = "[图片]";
					break;

				default:
					content = "";
					break;
				}
			} 
			
			int noReadNum = conversation.getMsg_no_readed_num();
			if ( noReadNum > 0) {
				message.setContent("[" + noReadNum
						+ "]" + content, ImageSpan.ALIGN_BOTTOM);
				red.setVisibility(View.VISIBLE);
				red.setText(String.valueOf(noReadNum));
			} else {
				red.setVisibility(View.GONE);
				message.setContent(content, ImageSpan.ALIGN_BOTTOM);
			}

			time.setText(Common.dateCompareNow(conversation.getUpdate_time()));
			String url = null == conversation ? "" : conversation.getCon_face();
			Utils.showFace(url, image);
			if (Common.isTrue(SettingManager.getInst().getNightModel())) {
				image.setColorFilter(getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
			}
			title.setText(null == conversation ? "" : conversation
					.getCon_name());

			Common.handleUserNameDisplay(getContext(), conversation, title);
			delete.setOnClickListener(listener);
			delete.setTag(position);
			delete.setVisibility(isDeleteFlag ? View.VISIBLE : View.GONE);
		}

		public void setDelFlag(boolean isEditFlag) {
			isDeleteFlag = isEditFlag;
		}

	}

}
