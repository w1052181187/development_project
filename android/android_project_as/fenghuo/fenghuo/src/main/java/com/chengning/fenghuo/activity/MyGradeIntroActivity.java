package com.chengning.fenghuo.activity;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.adapter.DirectorateTaskHelpGradeAdapter;
import com.chengning.fenghuo.data.bean.MilitaryGradeBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @description 指挥部任务帮助活动
 * @author wangyungang
 * @date 2015.7.17 14:25
 *
 */
public class MyGradeIntroActivity extends BaseActivity {

	private static final int GRADE_SUCCESS = 1;
	private ListView listView;
	
	private TitleBar mTitleBar;
	private DirectorateTaskHelpGradeAdapter adapter;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_directorate_task_help);
		super.onCreate(paramBundle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case GRADE_SUCCESS:
			adapter = new DirectorateTaskHelpGradeAdapter(getActivity(), (List<MilitaryGradeBean>) msg.obj);
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
				List<MilitaryGradeBean> list = gson.fromJson(data, new TypeToken<List<MilitaryGradeBean>>(){}.getType());
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
