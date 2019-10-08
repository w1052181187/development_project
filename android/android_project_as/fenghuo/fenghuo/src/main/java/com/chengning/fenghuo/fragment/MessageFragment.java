package com.chengning.fenghuo.fragment;
 
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.chengning.common.base.BaseFragment;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.PushMsgManager;
import com.chengning.fenghuo.PushMsgManager.PushMsgEventListener;
import com.chengning.fenghuo.PushMsgManager.PushMsgUserDataBean;
import com.chengning.fenghuo.PushMsgManager.SimplePushMsgEventListener;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.activity.AtMeListActivity;
import com.chengning.fenghuo.activity.ChatListActivity;
import com.chengning.fenghuo.activity.CommentAboutMeListActivity;
import com.chengning.fenghuo.activity.MyDigListActivity;
import com.chengning.fenghuo.activity.MyFansListActivity;
import com.chengning.fenghuo.adapter.MessageEntryListAdapter;
import com.chengning.fenghuo.data.access.ChatConversationDA;
import com.chengning.fenghuo.data.bean.ChatConversationBean;
import com.chengning.fenghuo.data.bean.MessageEntryBean;
import com.chengning.fenghuo.widget.PullToRefreshListView_FootLoad;
import com.chengning.fenghuo.widget.TitleBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

@SuppressLint("ResourceAsColor")
public class MessageFragment extends BaseFragment {
	
	private static final String TAG = MessageFragment.class.getSimpleName();
	
	public static final int CONTEXT_MENU_STICK = 1001;
	public static final int CONTEXT_MENU_DELETE = 1002;
	
	private static final int INIT_NEWSDATA_UI = 1;
	private static final int DELETE_MESSAGE = 2;
	 
	private Activity mContext;

	private PullToRefreshListView_FootLoad mPullListView;
	
	private View mAtMe;
	private View mComment;
	private View mLike;
	private View mFans;
	private View mChat;
	private View mAtMeRight;
	private View mCommentRight;
	private View mLikeRight;
	private View mFansRight;
	private View mChatRight;
	private TextView mRedDotAtMe;
	private TextView mRedDotComment;
	private TextView mRedDotLike;
	private TextView mRedDotFans;
	private TextView mRedDotChat;

	private View mView; 
	private OnClickListener mOnMenuClickListener;

	private View mLastSelectedView;

	private List<MessageEntryBean> mDataList;
	private MessageEntryListAdapter mAdapter;
	
	private boolean mIsCreate;
	private boolean mIsDBInit;
	
	private PushMsgEventListener pushAtMeListener;
	private PushMsgEventListener pushCommentListener;
	private PushMsgEventListener pushLikeListener;
	private PushMsgEventListener pushFansListener;
	private PushMsgEventListener pushPMListener;

	public void setOnMenuClickListener(OnClickListener onClickListener){
		this.mOnMenuClickListener = onClickListener;
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	    mView = inflater.inflate(R.layout.fragment_message, container, false);
	    return mView ;
	}
   
	@Override
	public void initViews() {  
		mContext = getContext();
		
		TitleBar titleBar = new TitleBar(getContext(), mView);
		titleBar.setTitle("我的消息");
		titleBar.showDefaultBack();
		
		mPullListView = (PullToRefreshListView_FootLoad) mView.findViewById(R.id.list);
		mPullListView.getRefreshableView().addHeaderView(View.inflate(mContext, R.layout.header_fragment_message, null));
		// 禁止滑动
		mPullListView.setMode(Mode.DISABLED);
		
		mAtMe = mView.findViewById(R.id.message_at_rl);
		mComment = mView.findViewById(R.id.message_comment_rl);
		mLike = mView.findViewById(R.id.message_like_rl);
		mFans = mView.findViewById(R.id.message_fans_rl);
		mChat = mView.findViewById(R.id.message_chat_rl);
		mAtMeRight = mView.findViewById(R.id.message_at_detail_imageview);
		mCommentRight = mView.findViewById(R.id.message_comment_detail_imageview);
		mLikeRight = mView.findViewById(R.id.message_like_detail_imageview);
		mFansRight = mView.findViewById(R.id.message_fans_detail_imageview);
		mChatRight = mView.findViewById(R.id.message_chat_detail_imageview);
		mRedDotAtMe = (TextView) mView.findViewById(R.id.message_at_red);
		mRedDotComment = (TextView) mView.findViewById(R.id.message_comment_red);
		mRedDotLike = (TextView) mView.findViewById(R.id.message_like_red);
		mRedDotFans = (TextView) mView.findViewById(R.id.message_fans_red);
		mRedDotChat = (TextView) mView.findViewById(R.id.message_chat_red);
	}
	
	@Override
	public void initDatas() {
		mContext = this.getContext();
		mIsCreate = true;
		mDataList = new ArrayList<MessageEntryBean>();

		showEmpty();
//		getHandler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				mPullListView.setRefreshing();
//			}
//		}, Consts.TIME_WAIT_REFRESH);

	}
	 
	@Override
	public void installListeners() {
		mAtMe.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (LoginManager.getInst().checkLoginWithNotice(getActivity())) {
					startActivity(new Intent(getContext(), AtMeListActivity.class));
				}
			}
		});
		mComment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (LoginManager.getInst().checkLoginWithNotice(getActivity())) {
					startActivity(new Intent(getContext(), CommentAboutMeListActivity.class));
				}
			}
		});
		mLike.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (LoginManager.getInst().checkLoginWithNotice(getActivity())) {
					startActivity(new Intent(getContext(), MyDigListActivity.class));
				}
			}
		});
		mFans.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (LoginManager.getInst().checkLoginWithNotice(getActivity())) {
					startActivity(new Intent(getContext(), MyFansListActivity.class));
				}
			}
		});
		mChat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getContext(), ChatListActivity.class));
			}
		});
		mPullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				MessageEntryBean bean = (MessageEntryBean) view.findViewById(R.id.message_entry_title).getTag();
//				ChatActivity.launch(mContext, bean, ChatActivity.FROM_MY_MESSAGE_LIST);
			}
		});
		mPullListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true)); 
		mPullListView.setOnRefreshListener(new OnRefreshListener(){  
			@Override
			public void onRefresh(PullToRefreshBase refreshView) { 
				getHandler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mPullListView.onRefreshComplete();
					}
				}, 600);
			}
		});
		
		pushAtMeListener = new SimplePushMsgEventListener(mRedDotAtMe, mAtMeRight){

			@Override
			public long configValue(int type, PushMsgUserDataBean bean) {
				return bean.getAt_new();
			}
		};
		pushCommentListener = new SimplePushMsgEventListener(mRedDotComment, mCommentRight){

			@Override
			public long configValue(int type, PushMsgUserDataBean bean) {
				return bean.getComment_new();
			}
		};
		pushLikeListener = new SimplePushMsgEventListener(mRedDotLike, mLikeRight){

			@Override
			public long configValue(int type, PushMsgUserDataBean bean) {
				return bean.getDig_new();
			}
		};
		pushFansListener = new SimplePushMsgEventListener(mRedDotFans, mFansRight){

			@Override
			public long configValue(int type, PushMsgUserDataBean bean) {
				return bean.getFans_new();
			}
		};
		pushPMListener = new SimplePushMsgEventListener(mRedDotChat, mChatRight){
			
			@Override
			public long configValue(int type, PushMsgUserDataBean bean) {
				return bean.getNewpm();
			}
		};
	}
	
	@Override
	public void onStart() {
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_AT_NEW, pushAtMeListener);
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_COMMENT_NEW, pushCommentListener);
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_DIG_NEW, pushLikeListener);
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_FANS_NEW, pushFansListener);
		PushMsgManager.getInstance().addListener(PushMsgManager.TYPE_PM_NEW, pushPMListener);
//		if(App.getInst().isMessageChange() && !mIsCreate){
//			getHandler().postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//					mPullListView.setRefreshing();
//				}
//			}, Consts.TIME_WAIT_REFRESH);
//		}
		mIsCreate = false;
		super.onStart(); 
	}
	
	
	@Override
	public void onResume() {
		List<ChatConversationBean> list = ChatConversationDA.getInst(getActivity()).queryAllConByUid(App.getInst().getUserInfoBean().getUid());
		
		int pmCount = 0;
		for (ChatConversationBean bean : list) {
			pmCount += bean.getMsg_no_readed_num();
		}
		PushMsgManager.getInstance().getPushUserDataBean().setNewpm(pmCount);
		super.onResume();
	}
	
	
	@Override
	public void onStop() {
		super.onStop(); 
		PushMsgManager.getInstance().removeListener(PushMsgManager.TYPE_AT_NEW, pushAtMeListener);
		PushMsgManager.getInstance().removeListener(PushMsgManager.TYPE_COMMENT_NEW, pushCommentListener);
		PushMsgManager.getInstance().removeListener(PushMsgManager.TYPE_DIG_NEW, pushLikeListener);
		PushMsgManager.getInstance().removeListener(PushMsgManager.TYPE_FANS_NEW, pushFansListener);
		PushMsgManager.getInstance().removeListener(PushMsgManager.TYPE_PM_NEW, pushPMListener);
	}
	
	public void forceRefresh(){
		if(mPullListView != null && !mPullListView.isRefreshing()){
			mPullListView.setRefreshing();
		}
	}
	
	public void sendListMessage(int what, List list){
		int listSize = 0;
		if(list != null){
			listSize = list.size();
		}
		if(getContext() != null){
			Message message = getHandler().obtainMessage(what, list);
			message.arg1 = listSize;
		    message.sendToTarget();  
		}
	}
	
	private void showEmpty(){
		mAdapter = new MessageEntryListAdapter(mContext, mDataList, null, null);
		mPullListView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		mPullListView.setFootLoadFull();
//		Common.setFootLoadVisibility(mPullListView, View.GONE);
		getHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mPullListView.onRefreshComplete();
			}
		}, Consts.TIME_WAIT_REFRESH);
	}

	@Override
	public void processHandlerMessage(Message msg) {
		
	}
	
}
