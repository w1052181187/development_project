package com.chengning.fenghuo.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.ReportUserBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.LoadFullListView;
import com.chengning.fenghuo.widget.TitleBar;
import com.loopj.android.http.RequestParams;

public class ChatReportActivity extends BaseActivity {
	
	private static final int SUCCESS_REPORT = 1;

	private static final int SUCCESS_DATA = 2;

	private TitleBar mTitleBar;
	private LoadFullListView mListView;
	private EditText mOtherReason;
	
	private ChatReportAdapter adapter;
	private ArrayList<ReportUserBean> mList;

	protected int pos;
	private String mUid;
	
	public static void launch(Activity from, String uid){
		Intent intent = new Intent(from, ChatReportActivity.class);
		intent.putExtra("uid", uid);
		from.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_chat_report);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Activity getActivity() {
		return ChatReportActivity.this;
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setRightButton("提交", getResources().getColor(R.color.common_blue));
		mListView = (LoadFullListView) findViewById(R.id.chat_report_reason_listview);
		mOtherReason = (EditText) findViewById(R.id.chat_report_reason_other_et);
	}

	@Override
	public void initDatas() {

		mUid = getIntent().getStringExtra("uid");
		
		mList = new ArrayList<ReportUserBean>();
		adapter = new ChatReportAdapter(getActivity(), mList);
		mListView.setAdapter(adapter);
		getReportReson();
	}

	private void getReportReson() {
		HttpUtil.get(JUrl.SITE + JUrl.URL_GET_USER_REPORT_REASON, new MyJsonHttpResponseHandler() {
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Common.showHttpFailureToast(getActivity());
			}
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				getHandler().obtainMessage(SUCCESS_DATA, data).sendToTarget();
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				
			}
		});
	}

	@Override
	public void installListeners() {
		mTitleBar.setRightButtonOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handleReport(pos, mList);
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pos = position;
				handleList(pos);
				mOtherReason.setVisibility("其他".equals(mList.get(pos).getContent()) ? View.VISIBLE : View.GONE);
			}
		});
		
	}

	protected void handleList(int pos) {
		for (ReportUserBean bean : mList) {
			bean.setSel(false);
		}
		mList.get(pos).setSel(true);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 举报
	 * @param pos 
	 */
	protected void handleReport(int pos, ArrayList<ReportUserBean> list) {
		
		RequestParams params = new RequestParams();
    	params.put("touid", mUid);
    	params.put("report_reason", list.get(pos).getReason());
    	if ("其他".equals(list.get(pos).getContent())) {
    		params.put("report_content", mOtherReason.getText().toString());
    	}
    	
		HttpUtil.post(JUrl.SITE + JUrl.URL_DO_REPORT_USER, params, new MyJsonHttpResponseHandler() {
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Common.showHttpFailureToast(getActivity());
			}
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				getHandler().obtainMessage(SUCCESS_REPORT).sendToTarget();
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
			}
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {

		switch (msg.what) {
		case SUCCESS_REPORT:
			finish();
			break;
		case SUCCESS_DATA:
			JSONObject json;
			try {
				json = new JSONObject((String) msg.obj);
				if (null == json) {
					return;
				}
				int size = json.length();
				if(0 == size){
					Common.showHttpFailureToast(getActivity());
					return;
				}
				final String[] keyArray = new String[size];
				int index = 0;
				Iterator iterator = json.keys();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					keyArray[index] = key; 
					index++;
					
				}
				Arrays.sort(keyArray);
				for (int i = 0; i < keyArray.length; i++) {
					String key = keyArray[i];
					String value = json.getString(key);
					ReportUserBean bean = new ReportUserBean();
					bean.setReason(key);
					bean.setContent(value);
					
					mList.add(bean);
				}
				mList.get(0).setSel(true);
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	
	class ChatReportAdapter extends BasePageListAdapter {

		public ChatReportAdapter(Activity activity, List list) {
			super(activity, list);
		}

		@Override
		public int buildLayoutId() {
			return R.layout.item_chat_report;
		}

		@Override
		public void handleLayout(View convertView, int position, Object obj) {
			
			TextView report = BaseViewHolder.get(convertView, R.id.item_chat_report_tv);
			ImageView img = BaseViewHolder.get(convertView, R.id.item_chat_report_checkimg);
			
			ReportUserBean bean = (ReportUserBean) obj;
			report.setText(bean.getContent());
			
			// 6.0手机以上setSelected在ListView内可能不起作用
			// img.setSelected(bean.isSel()); 
			img.setImageResource(bean.isSel() ? R.drawable.list_checked : R.color.transparent);
		}
		
	}

}
