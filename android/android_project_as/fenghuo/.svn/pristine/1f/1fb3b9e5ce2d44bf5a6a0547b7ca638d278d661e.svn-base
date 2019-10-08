package com.chengning.fenghuo.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.Consts.CirclePublishType;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.adapter.DiscussImageGridAdapter;
import com.chengning.fenghuo.adapter.EmjoyGridAdapter;
import com.chengning.fenghuo.adapter.EmjoyGridViewPaperAdapter;
import com.chengning.fenghuo.data.bean.GridImageItemBean;
import com.chengning.fenghuo.emotion.weibo.Emotion;
import com.chengning.fenghuo.emotion.weibo.Emotions;
import com.chengning.fenghuo.emotion.weibo.EmotionsDB;
import com.chengning.fenghuo.event.DiscussLinkEvent;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.EmotionHelper;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.DiscussLinkDialog;
import com.chengning.fenghuo.widget.TitleBar;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class DiscussActivity extends BaseFragmentActivity {
	
	public static final int RESULT_CODE_PUBLISH_DISCUSS = 10401;
	public static final String KEY_QUNID = "key_qunid";

	private static final int IMAGE_REQUEST_CODE = 12; // 请求码 本地图片
	private static final int CAMERA_REQUEST_CODE = 14; // 拍照
	private static final int AT_REQUEST_CODE = 0x01;

	private String mFileName = JUrl.FilePathTemp + "/camra.jpg";

	private EditText mTitleEt;
	private View mTitleEtBottomLine;
	private EditText mContentEt;
	private EditText mTempEt;
	private TextView mWordNumLimitHintTv;
	private RelativeLayout mLinkRl;
	private TextView mLinkTv;
	private Button mLinkDelBtn;
	private ImageButton mAtBtn;
	private ImageButton mLinkBtn;
	private ImageButton mEmjoyBtn;
	private RelativeLayout mPhotoPartent;
	private ImageView mPhotoIv;
	private TextView mPhotoNumTv;
	private DiscussActivity mContext;
	private boolean mIsPhotoShowing;
	private boolean mIsEmJoyShowing;
	private ViewPager mEmjoyPager;
	private List<Emotion> mEmjoyItems;
	private List<GridView> mEmjoyPagerItems;
	private EmjoyGridViewPaperAdapter mEmjoyPaperAdapter;
	private List<GridImageItemBean> mImageList;
	private GridView mImageGrid;
	private int mImageId = 0;

	private String[] mContentEtStr = new String[1];
	private DiscussImageGridAdapter mImageGridAdapter;
	private int mImageGridUnitH;
	private LinearLayout mNunLayout;
	private RelativeLayout mEmjoyRl;
	private RelativeLayout mPhotoRl;
	private Button mPreSelectedBt;

//	private int qun_id;
	private TitleBar mTitleBar;
	private CirclePublishType mPublishType;
	
	private DiscussLinkDialog mLinkDialog;
	private DiscussLinkEvent mLinkEvent;
	
	public static void launch(Activity from, CirclePublishType type) {
		Intent intent = new Intent(from, DiscussActivity.class);
		intent.putExtra("type", type);
		from.startActivityForResult(intent, 0);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_discuss);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void processHandlerMessage(Message msg) {

	}

	@Override
	public void initViews() {

		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setRightButton("完成",
				getResources().getColor(R.color.item_title_color_normal));

		mTitleEt = (EditText) findViewById(R.id.discuss_title);
		mTitleEtBottomLine = findViewById(R.id.discuss_title_bottom_line);
		mContentEt = (EditText) this.findViewById(R.id.discuss_content);
		mWordNumLimitHintTv = (TextView) findViewById(R.id.discuss_wordnumlimit_hint);
		mPhotoPartent = (RelativeLayout) this
				.findViewById(R.id.discuss_photo_partent);
		mPhotoIv = (ImageView) this.findViewById(R.id.discuss_photo);
		mPhotoNumTv = (TextView) this.findViewById(R.id.discuss_photo_num);
		mLinkRl = (RelativeLayout) this.findViewById(R.id.discuss_showlink_rl);
		mLinkTv = (TextView) this.findViewById(R.id.discuss_showlink);
		mLinkDelBtn = (Button) this.findViewById(R.id.discuss_showlink_del);
		mAtBtn = (ImageButton) this.findViewById(R.id.discuss_at);
		mLinkBtn = (ImageButton) this.findViewById(R.id.discuss_link);
		mEmjoyBtn = (ImageButton) this.findViewById(R.id.discuss_emjoy);

		mNunLayout = (LinearLayout) this
				.findViewById(R.id.discuss_emjoydotlist);
		mEmjoyRl = (RelativeLayout) this.findViewById(R.id.discuss_emjoy_rl);
		mEmjoyPager = (ViewPager) this.findViewById(R.id.discuss_emjoygrid);
		mPhotoRl = (RelativeLayout) findViewById(R.id.discuss_photo_rl);
		mImageGrid = (GridView) this.findViewById(R.id.discuss_img);
		
	}

	@Override
	public void initDatas() {
		mContext = this;

		mPublishType = (CirclePublishType) getIntent().getSerializableExtra("type");
		if(mPublishType == CirclePublishType.DUANPING){
			mTitleBar.setTitle(mPublishType.getStr());
			mTitleEt.setVisibility(View.GONE);
			mTitleEtBottomLine.setVisibility(View.GONE);
			mWordNumLimitHintTv.setVisibility(View.VISIBLE);
		}else{
			mTitleBar.setTitle(mPublishType.getStr());
			mWordNumLimitHintTv.setVisibility(View.GONE);
		}
		
		mIsEmJoyShowing = false;
		mIsPhotoShowing = false;
		InitEmJoyData();
		mImageList = new ArrayList<GridImageItemBean>();
		mImageGridAdapter = new DiscussImageGridAdapter(this, mImageList,
				mPhotoNumTv);
		mImageGrid.setAdapter(mImageGridAdapter);

		mTempEt = mContentEt;

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.showSoftInput(mContentEt, 0);
			}

		}, 300);
		
		EventBus.getDefault().register(getActivity());
		mLinkEvent = null;
	}

	@Override
	public void installListeners() {

		mContentEt.addTextChangedListener(EmotionHelper.generateTextWatcher(
				mContentEt, mContentEtStr));
		mContentEt.setFilters(new InputFilter[] {
				EmotionHelper.generateEmotionFilter(),
				EmotionHelper.generateHyperlinkFilter() });
		
		mImageGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showImgDialog();
			}
		});
		
		mAtBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPhoto(false);
				showEmjoy(false);
				if (LoginManager.getInst().checkLoginWithNotice(mContext)) {
					mContext.startActivityForResult(new Intent(mContext,
							AtSuggestActivity.class), AT_REQUEST_CODE);
				}

			}
		});
		
		mLinkRl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null == mLinkDialog) {
					mLinkDialog = new DiscussLinkDialog();
				}
				if(mLinkEvent != null){
					mLinkDialog.setContent(mLinkEvent.getmStrLinkTitle(), mLinkEvent.getmStrLinkAddr());
				}
				mLinkDialog.show(getSupportFragmentManager(),
						DiscussLinkDialog.class.getSimpleName());	
			}
		});
		
		mLinkBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null == mLinkDialog) {
					mLinkDialog = new DiscussLinkDialog();
				}
				mLinkDialog.show(getSupportFragmentManager(),
						DiscussLinkDialog.class.getSimpleName());		
			}
		});
		
		mLinkDelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mLinkRl.setVisibility(View.GONE);
				mLinkEvent = null;
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
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		mTitleEt.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mTempEt = mTitleEt;
					mTitleEt.setCursorVisible(true);
				} else {
					showPhoto(false);
					showEmjoy(false);
					mTitleEt.setCursorVisible(false);
				}
			}
		});

		mTitleEt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTempEt = mTitleEt;
				if (mIsPhotoShowing) {
					showPhoto(false);
				}
				if (mIsEmJoyShowing) {
					showEmjoy(false);
				}
			}
		});

		mContentEt.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mTempEt = mContentEt;
					mContentEt.setCursorVisible(true);
					if (mIsPhotoShowing)
						showPhoto(false);
					if (mIsEmJoyShowing)
						showEmjoy(false);
				} else {
					showPhoto(false);
					showEmjoy(false);
					mContentEt.setCursorVisible(false);
				}
			}
		});

		mContentEt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTempEt = mContentEt;
				if (mIsPhotoShowing)
					showPhoto(false);
				if (mIsEmJoyShowing)
					showEmjoy(false);
			}
		});

		mPhotoPartent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mIsPhotoShowing) {
					mTempEt.setCursorVisible(true);
				} else {
					mTempEt.setCursorVisible(false);
				}
				showEmjoy(false);
				showKeyBoard(mIsPhotoShowing);
				showPhoto(!mIsPhotoShowing);
			}
		});

		mEmjoyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPhoto(false);
				showKeyBoard(mIsEmJoyShowing);
				showEmjoy(!mIsEmJoyShowing);
			}
		});

		mTitleBar.setRightButtonOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (LoginManager.getInst().checkLoginWithNotice(mContext)) {
					if (Common.hasNet()) {
						if (!mContentEt.getText().toString().equals("")) {
							Publish();
						} else if (mImageList != null && mImageList.size() > 0) {
							Publish();
						}
					} else {
						UIHelper.showToast(getActivity(), R.string.intnet_fail);
					}
				}
			}
		});
		
	}

	public void onResume() {
		mTempEt.setCursorVisible(true);
		super.onResume();
	}

	public void Publish() {
		UIHelper.addPD(mContext, "发送中...");
		String topictype = "first";
		String content = mContentEtStr[0];
		String from = this.getString(R.string.from_name);

		RequestParams params = new RequestParams();
		params.put("topictype", topictype);
		params.put("roottid", "0");
		params.put("totid", "0");
		params.put("touid", "0");
		params.put("item", "qun");
//		params.put("item_id", qun_id);
		params.put("title", mTitleEt.getText().toString().trim());

		if (from != null) {
			byte[] bs = from.getBytes();
			try {
				from = new String(bs, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			params.put("content", content);
		}
		params.put("from", this.getString(R.string.from_name));
		// 传图片
		if (mImageList != null && mImageList.size() > 0) {
			for (int i = 0; i < mImageList.size(); i++) {
				File f = Common.creatFile(JUrl.FilePathTemp,
						mImageList.get(i).name);
				try {
					params.put("topic" + String.valueOf(i), f, "image/jpeg");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		if (content != null) {
			byte[] bs = content.getBytes();
			try {
				content = new String(bs, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			params.put("content", content);
		}
		
		if(mLinkEvent != null){
			params.put("redirecttitle", mLinkEvent.getmStrLinkTitle());
			params.put("redirecturl", mLinkEvent.getmStrLinkAddr());
		}
		
		if(mPublishType == CirclePublishType.DUANPING){
			params.put("is_special", 0);
		}else{
			params.put("is_special", 1);
		}
		MyJsonHttpResponseHandler ha = new MyJsonHttpResponseHandler() {
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), "发布讨论成功");
				setResult(RESULT_CODE_PUBLISH_DISCUSS);
				UIHelper.removePD();
				int size = mImageList.size();
				if (!Common.isListEmpty(mImageList)) {
					for (int i = 0; i < size; i++) {
						String g = mImageList.get(i).path;
						File f = new File(g);
						if (f.exists()) {
							f.delete();
						}
					}
				}
				finish();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
				UIHelper.removePD();
			};

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				Log.e("taolun", "Push_Comment onFailure" + throwable.toString());
				UIHelper.removePD();
				Utils.handleHttpFailure(mContext, throwable);
			};
		};

		HttpUtil.post(mContext, JUrl.SITE + JUrl.URL_DO_ADD, params, ha);
	}

	@Override
	public void uninstallListeners() {

	}

	@Subscribe
    public void onEventMainThread(DiscussLinkEvent event) {
		mLinkRl.setVisibility(View.VISIBLE);
		SpannableStringBuilder builder = new SpannableStringBuilder("【转载】");
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(mContext
				.getResources().getColor(R.color.black));
		builder.setSpan(colorSpan, 0, builder.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.append(event.getmStrLinkTitle());
		mLinkTv.setText(builder);
		mLinkEvent = event;
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		UIHelper.removePD();
		EventBus.getDefault().unregister(getActivity());
		int size = mImageList.size();
		if (!Common.isListEmpty(mImageList)) {
			for (int i = 0; i < size; i++) {
				String g = mImageList.get(i).path;
				File f = new File(g);
				if (f.exists()) {
					f.delete();
				}
			}
		}

	}

	/**
	 * 回调结果处理
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AT_REQUEST_CODE) {
			if (data != null) {
				String result = data.getExtras().getString("result");
				EmotionHelper.handleInsert(mContentEt, TextUtils.concat("@", result, " ").toString());
				getHandler().postDelayed(new Runnable() {
					@Override
					public void run() {
						((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
								.showSoftInput(mContentEt, 0);
					}
				}, 300);
			}
		} else if (resultCode == 101) {
			if (data != null) {
				String result = data.getExtras().getString("result");
				EmotionHelper.handleInsert(mContentEt, TextUtils.concat("#", result, "#").toString());
			}
		} else {
			switch (requestCode) {
			case CAMERA_REQUEST_CODE:
				// Bitmap photo = BitmapFactory.decodeFile(mFileName);
				Bitmap photo = compressSize(mFileName);
				if (photo != null)
					AddGridImage(photo);
				break;
			case IMAGE_REQUEST_CODE:
				if (data != null && data.getData() != null) {
					try {
						Uri uri = data.getData();
						Bitmap photo2 = getThumbnail(uri, 1600);
						if (photo2 != null)
							AddGridImage(photo2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void showKeyBoard(boolean b) {
		if (b) {
			getHandler().postDelayed(new Runnable() {
				@Override
				public void run() {
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.showSoftInput(mTempEt, 0);
				}
			}, 300);
		} else {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mTempEt.getWindowToken(), 0);
		}
	}

	private void showPhoto(boolean b) {
		if (b == mIsPhotoShowing)
			return;
		if (b) {
			getHandler().postDelayed(new Runnable() {
				@Override
				public void run() {
					mPhotoIv.setBackgroundResource(R.drawable.fabiao_toolbar_img_btn2);
					mPhotoRl.setVisibility(View.VISIBLE);
				}
			}, 300);
		} else {
			mPhotoRl.setVisibility(View.GONE);
			mPhotoIv.setBackgroundResource(R.drawable.fabiao_toolbar_img_btn1);
		}
		mIsPhotoShowing = b;
	}

	private void showEmjoy(boolean b) {
		if (b == mIsEmJoyShowing)
			return;
		if (b) {
			getHandler().postDelayed(new Runnable() {
				@Override
				public void run() {
					mEmjoyBtn.setImageResource(R.drawable.fabiao_toolbar_key_btn);
					mEmjoyRl.setVisibility(View.VISIBLE);
				}
			}, 300);
		} else {
			mEmjoyRl.setVisibility(View.GONE);
			mEmjoyBtn.setImageResource(R.drawable.fabiao_toolbar_emjoy_btn);
		}
		mIsEmJoyShowing = b;
	}

	private void showImgDialog() {
		String[] items = new String[] { "拍照", "选择本地图片" };

		DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:// 拍照
					Intent intentFromCapture = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(mFileName)));
					startActivityForResult(intentFromCapture,
							CAMERA_REQUEST_CODE);
					break;
				case 1:// 选择本地图片
					Intent intentFromGallery = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intentFromGallery,
							IMAGE_REQUEST_CODE);
					break;
				}
			}
		};
		new AlertDialog.Builder(this).setItems(items, click).show();
	}

	public Bitmap getThumbnail(Uri uri, int size) throws FileNotFoundException,
			IOException {
		InputStream input = this.getContentResolver().openInputStream(uri);
		BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
		onlyBoundsOptions.inJustDecodeBounds = true;
		onlyBoundsOptions.inDither = true;// optional
		onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;// optional
		BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
		input.close();
		if ((onlyBoundsOptions.outWidth == -1)
				|| (onlyBoundsOptions.outHeight == -1))
			return null;
		int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight
				: onlyBoundsOptions.outWidth;
		double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;// optional
		input = this.getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
		input.close();
		return bitmap;
	}

	private static int getPowerOfTwoForSampleRatio(double ratio) {
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if (k == 0)
			return 1;
		else
			return k;
	}

	private Bitmap compressSize(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		int max = w;
		float scale = 0.0f;
		if (h > w)
			max = h;
		if (max > 1600) {
			scale = max / 1600;
		}
		newOpts.inSampleSize = (int) scale;
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;// 压缩好比例大小后再进行质量压缩
	}

	private void compressBmpFromBmp(Bitmap image, String name) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		try {
			File file = Common.creatFile(JUrl.FilePathTemp, name);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected String getGridImageName() {
		mImageId++;
		return TextUtils.concat(String.valueOf(mImageId), ".jpg").toString();
	}

	protected void AddGridImage(Bitmap bitmap) {
		GridImageItemBean b = new GridImageItemBean();
		b.bitmap = bitmap;
		b.name = getGridImageName();
		b.path = TextUtils.concat(JUrl.FilePathTemp, "/", b.name).toString();

		compressBmpFromBmp(b.bitmap, b.name);
		mImageList.add(b);
		int row = mImageList.size() / 3;
		if (mImageList.size() % 3 != 0) {
			row++;
		}
		if (row == 1 && mImageList.size() % 3 > 1) {
			mImageGridUnitH = mImageGrid.getHeight();
		}
		if (row > 1) {
			LayoutParams params = mImageGrid.getLayoutParams();
			params.height = mImageGridUnitH * row;
			mImageGrid.setLayoutParams(params);
		}
		mImageGridAdapter.setItems(mImageList);
		mImageGridAdapter.notifyDataSetChanged();
	}

	protected void RemoveGridImage(GridImageItemBean bean) {
		File f = new File(bean.path);
		if (f.exists()) {
			f.delete();
		}
		mImageList.remove(bean);
		mImageGridAdapter.setItems(mImageList);
		mImageGridAdapter.notifyDataSetChanged();
	}

	private final static int EmjoyRowCount = 6;

	private void InitEmJoyData() {
		Emotions emotions = EmotionsDB.getEmotions();
		mEmjoyItems = emotions.getEmotions();
		mEmjoyPagerItems = new ArrayList<GridView>();
		List<List<Emotion>> emjoySpliteItems = Common.splitList(mEmjoyItems,
				EmjoyRowCount * 5);
		for (int i = 0; i < emjoySpliteItems.size(); i++) {
			GridView gv = new GridView(this);
			gv.setAdapter(new EmjoyGridAdapter(this, emjoySpliteItems.get(i), i));
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
					EmotionHelper.handleEmotionInsert(mTempEt, emotion, Consts.EMOTION_SIZE_MIDDLE);
				}
			});
			mEmjoyPagerItems.add(gv);
		}
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.emjoy_tab1);
		for (int i = 0; i < mEmjoyPagerItems.size(); i++) {
			Button bt = new Button(this);
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
		mEmjoyPaperAdapter = new EmjoyGridViewPaperAdapter(this,
				mEmjoyPagerItems);
		mEmjoyPager.setAdapter(mEmjoyPaperAdapter);
	}

	@Override
	public Activity getActivity() {
		return this;
	}
}
