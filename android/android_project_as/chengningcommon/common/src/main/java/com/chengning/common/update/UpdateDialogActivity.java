package com.chengning.common.update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.R;
import com.chengning.common.base.BaseActivity;


/**
 * 
<activity
android:name="com.chengning.common.update.UpdateDialogActivity"
android:theme="@android:style/Theme.Translucent.NoTitleBar" >
</activity>
 * 
 */
public class UpdateDialogActivity extends BaseActivity{
	
	private ImageView wifi;
	private TextView content;
	private Button ok;
	private Button cancel;
	private Button ignore;
	private CheckBox check;
	
	private UpdateBean mBean;

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.umeng_update_dialog);
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putSerializable("bean", mBean);
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    	mBean = (UpdateBean) savedInstanceState.getSerializable("bean");
    }

	@Override
	public void initViews() {
//		int i = u.upd.c.a(this).b("umeng_update_content");
//		int j = u.upd.c.a(this).b("umeng_update_wifi_indicator");
//		int k = u.upd.c.a(this).b("umeng_update_id_ok");
//		int l = u.upd.c.a(this).b("umeng_update_id_cancel");
//		int i1 = u.upd.c.a(this).b("umeng_update_id_ignore");
//		int j1 = u.upd.c.a(this).b("umeng_update_id_close");
//		int k1 = u.upd.c.a(this).b("umeng_update_id_check");
		
		wifi = (ImageView) findViewById(R.id.umeng_update_wifi_indicator);
		content = (TextView) findViewById(R.id.umeng_update_content);
		ok = (Button) findViewById(R.id.umeng_update_id_ok);
		cancel = (Button) findViewById(R.id.umeng_update_id_cancel);
		ignore = (Button) findViewById(R.id.umeng_update_id_ignore);
		check = (CheckBox) findViewById(R.id.umeng_update_id_check);
	
		wifi.setVisibility(UpdateUtil.isWifi(getActivity()) ? View.GONE : View.VISIBLE);
		ignore.setVisibility(View.GONE);
		check.setVisibility(View.GONE);
		
	}

	@Override
	public void initDatas() {
		mBean = (UpdateBean) getIntent().getSerializableExtra("bean");

		content.setText(mBean.getUpdate_log());
	}

	@Override
	public void installListeners() {
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// download apk
				String url = mBean.getApk_url();
				String title = UpdateUtil.getApplicationName(getActivity());
				Intent intent = new Intent(getActivity(), UpdateDownLoadService.class);
				intent.putExtra("url", url);
				intent.putExtra("title", title);
				intent.putExtra("md5", mBean.getNew_md5());
				intent.putExtra("target_size", mBean.getTarget_size());
				startService(intent);
				
				finish();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {
		
	}

}
