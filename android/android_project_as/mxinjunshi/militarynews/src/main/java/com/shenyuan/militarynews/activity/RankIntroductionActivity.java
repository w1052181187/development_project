package com.shenyuan.militarynews.activity;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenyuan.militarynews.MyStatusResponseHandler;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.adapter.RankIntroductionAdapter;
import com.shenyuan.militarynews.beans.data.RankBean;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.chengning.common.util.StatusBarUtil;;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.views.TitleBar;


public class RankIntroductionActivity extends BaseActivity {

	private static final int GRADE_SUCCESS = 1;
	private ListView listView;
	
	private TitleBar mTitleBar;
	private RankIntroductionAdapter adapter;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_directorate_task_help);
		if(Common.isTrue(SettingManager.getInst().getNightModel())){  
			StatusBarUtil.setBar(this, getResources().getColor(R.color.night_bg_color), false);
        }else{  
        	StatusBarUtil.setBar(this, getResources().getColor(R.color.normalstate_bg), true);
        }
		super.onCreate(paramBundle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case GRADE_SUCCESS:
			adapter = new RankIntroductionAdapter(getActivity(), (List<RankBean>) msg.obj);
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
		//TODO 等待接口接入
		HttpUtil.get(JUrl.SITE + JUrl.URL_GET_LEVEL_LIST, new MyStatusResponseHandler() {		
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				Gson gson =new Gson();
				List<RankBean> list = gson.fromJson(data, new TypeToken<List<RankBean>>(){}.getType());
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
		Intent intent = new Intent(context, RankIntroductionActivity.class);
		context.startActivity(intent);
	}

	@Override
	public Activity getActivity() {
		return RankIntroductionActivity.this;
	}

}
