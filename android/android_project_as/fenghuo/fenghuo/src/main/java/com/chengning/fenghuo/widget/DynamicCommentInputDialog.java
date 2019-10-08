package com.chengning.fenghuo.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.activity.AtSuggestActivity;
import com.chengning.fenghuo.adapter.EmjoyGridAdapter;
import com.chengning.fenghuo.adapter.EmjoyGridViewPaperAdapter;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.data.bean.CommentItemBean;
import com.chengning.fenghuo.data.bean.CommentSuccessBean;
import com.chengning.fenghuo.emotion.weibo.Emotion;
import com.chengning.fenghuo.emotion.weibo.Emotions;
import com.chengning.fenghuo.emotion.weibo.EmotionsDB;
import com.chengning.fenghuo.event.CommentSuccessEvent;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.EmotionHelper;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.util.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

public class DynamicCommentInputDialog extends BaseDialogFragment {

	private final static int EmJoyChange = 4;
	private static final int AT_REQUEST_CODE = 0x01;

	private View mView;
	private View mEmpty;
	private View mPublish;
	private EditText mInput;
	private ImageButton mAtBtn;
	private ImageButton mEmjoyBtn;
	private ImageButton mAlbumBtn;
	private boolean mIsEmJoy;
	private ViewPager mEmjoyPager;
	private List<Emotion> mEmjoyItems;
	private List<GridView> mEmjoyPagerItems;
	private EmjoyGridViewPaperAdapter mEmjoyPaperAdapter;
	private LinearLayout mNunLayout;
	private RelativeLayout mEmjoyRl;
	private Button mPreSelectedBt;

	private String[] mContentEtStr = new String[1];

	private Handler mHandler;
	private BaseArticlesBean bean;

	@Override
	public void initListener() {
		mEmpty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		mPublish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPublish.setEnabled(false);
				publish();
			}
		});

		mAtBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (LoginManager.getInst().checkLoginWithNotice(getContext())) {
					startActivityForResult(new Intent(getContext(),
							AtSuggestActivity.class), AT_REQUEST_CODE);
					;
				}

			}
		});

		mEmjoyPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {

				if (mPreSelectedBt != null) {
					mPreSelectedBt.setBackgroundResource(R.drawable.emjoy_tab1);
				}

				Button currentBt = (Button) mNunLayout.getChildAt(position);
				currentBt.setBackgroundResource(R.drawable.emjoy_tab2);
				mPreSelectedBt = currentBt;

				// Log.i("INFO", "current item:"+position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		mInput.setOnTouchListener(new OnTouchListener() {
			boolean ismove = false;
			int x = 0;
			int y = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x = 0;
					y = 0;
					x = (int) event.getX();
					y = (int) event.getY();
					break;
				case MotionEvent.ACTION_UP:
					if (!ismove) {
						mIsEmJoy = false;
						ShowKeyBoardOrEmjoy(mIsEmJoy);
					}
					ismove = false;
					break;
				case MotionEvent.ACTION_MOVE:
					int a = (int) Math.abs(event.getX() - x);
					int b = (int) Math.abs(event.getY() - y);
					if (a > 20 || b > 20) {
						ismove = true;
					}
					break;
				}
				return false;
			}
		});

		mEmjoyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsEmJoy) {
					mIsEmJoy = false;
				} else {
					mIsEmJoy = true;
				}
				ShowKeyBoardOrEmjoy(mIsEmJoy);
			}
		});
	}

	@Override
	public void initView() {
		mEmpty = mView.findViewById(R.id.dynamic_comment_input_empty);
		mPublish = mView.findViewById(R.id.dynamic_cmt_bottom_send);
		mInput = (EditText) mView.findViewById(R.id.dynamic_cmt_bottom_input);
		mAtBtn = (ImageButton) mView.findViewById(R.id.dynamic_cmt_bottom_at);
		mEmjoyBtn = (ImageButton) mView
				.findViewById(R.id.dynamic_cmt_bottom_emjoy);

		mNunLayout = (LinearLayout) mView
				.findViewById(R.id.dynamic_cmt_bottom_emjoydotlist);
		mEmjoyRl = (RelativeLayout) mView
				.findViewById(R.id.dynamic_cmt_bottom_emjoy_rl);
		mEmjoyPager = (ViewPager) mView
				.findViewById(R.id.dynamic_cmt_bottom_emjoygrid);
	}

	@Override
	public void initData() {
		// 内容编辑
		mInput.addTextChangedListener(EmotionHelper.generateTextWatcher(mInput,
				mContentEtStr));
		// 更换表情
		mInput.setFilters(new InputFilter[] {
				EmotionHelper.generateEmotionFilter(),
				EmotionHelper.generateHyperlinkFilter() });
		mIsEmJoy = false;
		InitEmJoyData();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				((InputMethodManager) getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.showSoftInput(mInput, 0);
			}

		}, 300); // 在一秒后打开

		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case EmJoyChange:
					boolean a = (Boolean) msg.obj;
					VisiableEmjoy(a);
					break;
				}
				super.handleMessage(msg);
			}

		};

		bean = (BaseArticlesBean) getArguments().getSerializable("bean");
		if (null != bean) {
			mInput.setHint("回复 " + bean.getNickname());
		}

	}

	public void setBean(BaseArticlesBean bean) {
		Bundle args = new Bundle();
		args.putSerializable("bean", bean);
		setArguments(args);
	}

	/**
	 * 发表
	 */
	public void publish() {
		String input = mInput.getText().toString();
		// check input
		if (TextUtils.isEmpty(input) || input.length() < 2) {
			UIHelper.showToast(getContext(), "请至少输入两个字符");
			mPublish.setEnabled(true);
		} else {
			// publish
			if (!Common.hasNet()) {
				Common.showHttpFailureToast(getContext());
				mPublish.setEnabled(true);
				return;
			}

			String url = JUrl.SITE + JUrl.URL_DO_COMMENT_PUBLISH;
			// url = App.getInst().isLogin() ? JUrl.URL_DO_COMMENT_PUBLISH :
			// JUrl.URL_DO_ANON_COMMENT_PUBLISH;
			RequestParams params = new RequestParams();

			params.put("content", handleContent(bean, input));
			params.put("topictype", "reply");
			params.put("totid", bean.getTid());

			// if (null == mRootTid) {
			// mRootTid = bean.getTid();
			// }
			params.put("roottid", bean.getRoottid());
//			boolean is_reply = !App.getInst().getUserInfoBean().getUid().equals(bean.getUid()) && Common.isTrue(bean.getIsReplyClicked());
			if (Common.isTrue(bean.getIsReplyClicked())) {
				params.put("is_reply", 1);
			}
			params.put("touid", bean.getUid());
			params.put("from", bean.getFrom());
			HttpUtil.post(url, params, new MyJsonHttpResponseHandler() {

				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject error) {

					Common.showHttpFailureToast(getContext());
					mPublish.setEnabled(true);
				};

				@Override
				public void onDataSuccess(int status, String mod,
						String message, String data, JSONObject obj) {
					UIHelper.showToast(getContext(), message);
					dismissAllowingStateLoss();
					mPublish.setEnabled(true);
					Gson gson = new Gson();
					CommentSuccessBean sBean = gson.fromJson(data, CommentSuccessBean.class);
					CommentSuccessEvent mEvent = new CommentSuccessEvent(sBean.getList());
					EventBus.getDefault().post(mEvent);
					if(mDialogCommentListener != null && sBean.getList() != null)
						mDialogCommentListener.onCommentSuccess(sBean.getList());
					try {
						Utils.showHints((FragmentActivity)getContext(), data);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onDataFailure(int status, String mod,
						String message, String data, JSONObject obj) {

					UIHelper.showToast(getContext(), message);
					mPublish.setEnabled(true);
				}
			});
		}
	}

	private String handleContent(BaseArticlesBean bean, String input) {
		// if (!App.getInst().getUserInfoBean().getUid().equals(bean.getUid()))
		// {
		// StringBuffer buffer = new StringBuffer();
		// buffer.append("回复 @").append(bean.getNickname()).append(" ").append(input);
		// return buffer.toString();
		// }
		return input;
	}

	final static int EmjoyRowCount = 6;

	/**
	 * 初始化表情数据
	 */
	protected void InitEmJoyData() {
		Emotions emotions = EmotionsDB.getEmotions();
		mEmjoyItems = emotions.getEmotions();
		mEmjoyPagerItems = new ArrayList<GridView>();
		List<List<Emotion>> emjoySpliteItems = Common.splitList(mEmjoyItems,
				EmjoyRowCount * 5);
		for (int i = 0; i < emjoySpliteItems.size(); i++) {
			GridView gv = new GridView(getContext());
			gv.setAdapter(new EmjoyGridAdapter(getContext(), emjoySpliteItems
					.get(i), i));
			gv.setGravity(Gravity.CENTER);
			gv.setClickable(true);
			gv.setFocusable(true);
			gv.setSelector(R.color.transparent);
			gv.setNumColumns(6);
			gv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					EmjoyGridAdapter adapter = (EmjoyGridAdapter) parent
							.getAdapter();
					Emotion emotion = (Emotion) adapter.getItem(position);
					EmotionHelper.handleEmotionInsert(mInput, emotion, Consts.EMOTION_SIZE_MIDDLE);
				}
			});
			mEmjoyPagerItems.add(gv);
		}
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.emjoy_tab1);
		for (int i = 0; i < mEmjoyPagerItems.size(); i++) {
			Button bt = new Button(getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					bitmap.getWidth(), bitmap.getHeight());
			params.setMargins(5, 0, 5, 0);
			bt.setLayoutParams(params);
			bt.setBackgroundResource(R.drawable.emjoy_tab1);
			mNunLayout.addView(bt);
		}
		Button currentBt = (Button) mNunLayout.getChildAt(0);
		currentBt.setBackgroundResource(R.drawable.emjoy_tab2);
		mPreSelectedBt = currentBt;
		mEmjoyPaperAdapter = new EmjoyGridViewPaperAdapter(getContext(),
				mEmjoyPagerItems);
		mEmjoyPager.setAdapter(mEmjoyPaperAdapter);

	}

	public static final int Show_KeyBoard = 11;
	public static final int Show_Emjoy = 12;

	/**
	 * 显示键盘或者表情
	 * 
	 * @param s
	 */
	public void ShowKeyBoardOrEmjoy(boolean s) {
		mEmjoyBtn.setClickable(false);
		if (s) {
			((InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.showSoftInput(mInput, 0);
			InputMethodManager imm = (InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					InputMethodManager imm = (InputMethodManager) getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
					Message message = mHandler.obtainMessage(EmJoyChange,
							mIsEmJoy);
					message.sendToTarget();

				}
			}, 300);
		} else {
			mEmjoyRl.setVisibility(View.GONE);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Message message = mHandler.obtainMessage(EmJoyChange,
							mIsEmJoy);
					message.sendToTarget();
				}
			}, 250);
		}

	}

	/**
	 * 表情可见
	 * 
	 * @param s
	 */
	public void VisiableEmjoy(boolean s) {
		mEmjoyBtn.setClickable(true);
		if (s) {
			mEmjoyBtn.setImageResource(R.drawable.fabiao_toolbar_key_btn);
			mEmjoyRl.setVisibility(View.VISIBLE);
		} else {
			mEmjoyBtn.setImageResource(R.drawable.fabiao_toolbar_emjoy_btn);
			((InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.showSoftInput(mInput, 0);
		}
	}

	public void onResume() {
		super.onResume();
		ShowKeyBoardOrEmjoy(this.mIsEmJoy);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case AT_REQUEST_CODE:
			if (resultCode == 11 && data != null) {
				String result = data.getExtras().getString("result");
				EmotionHelper.handleInsert(mInput, "@" + result + " ");
			}
			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private DialogCommentListener mDialogCommentListener;
	public void setCommentCallback(DialogCommentListener l){
		this.mDialogCommentListener = l;
	}
	
	public interface DialogCommentListener {
		void onCommentSuccess(CommentItemBean iBean);
		void onCommentFail();
	}
	
	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dynamic_comment_input, null);
		return mView;
	}
}
