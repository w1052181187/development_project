package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.adapter.GradeDescriptionAdapter;
import com.chengning.fenghuovideo.data.bean.GradeBean;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.UIHelper;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

/**
 * @description 等级说明
 * @author wangyungang
 * @date 2015.7.17 14:25
 *
 */
public class MyGradeIntroActivity extends BaseActivity {

	private static final int GRADE_SUCCESS = 1;
	private ListView listView;
	
	private TitleBar mTitleBar;
	private GradeDescriptionAdapter adapter;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_grade_description);
		super.onCreate(paramBundle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case GRADE_SUCCESS:
			adapter = new GradeDescriptionAdapter(getActivity(), (List<GradeBean>) msg.obj);
			listView.setAdapter(adapter);
			break;
		
		default:
			break;
		}
	}

	@Override
	public void initViews() {

		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.setTitle("等级说明");
		mTitleBar.showDefaultBack();
		
		listView = (ListView) findViewById(R.id.directorate_task_help_grade_list);
	}

	@Override
	public void initDatas() {

		getData();
		
	}

	private void getData() {
		HttpUtil.get(JUrl.SITE + JUrl.URL_GET_LEVEL_LIST, new MyJsonHttpResponseHandler() {
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				Gson gson =new Gson();
				List<GradeBean> list = gson.fromJson(data, new TypeToken<List<GradeBean>>(){}.getType());
				Message msg = getHandler().obtainMessage(GRADE_SUCCESS, list);
				msg.sendToTarget();
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
				
			}
		});
		
	}

	@Override
	public void installListeners() {
	}

	@Override
	public void uninstallListeners() {

	}

	public static void launch(Activity context) {
		Intent intent = new Intent(context, MyGradeIntroActivity.class);
		context.startActivity(intent);
	}

	@Override
	public Activity getActivity() {
		return MyGradeIntroActivity.this;
	}

}
