package com.chengning.fenghuovideo.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.widget.wheel.ArrayWheelAdapter;
import com.chengning.common.widget.wheel.WheelView;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.UIHelper;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class ChosePushTimeDialog extends AlertDialog{
	
	private static final String TAG = ChosePushTimeDialog.class.getSimpleName();
	
	public static final String[] ARRAY_START_TIME = new String[]{
		"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", 
		"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", 
		"16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", 
	};
	public static final String[] ARRAY_END_TIME = new String[]{
		"01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", 
		"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", 
		"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00", 
	};
	
	private Activity mContext;
	
	private String mInitStartTime;
	private String mInitEndTime;
	private String mStartTime;
	private String mEndTime;
	
	private ChosePushTimeListener mListener;

	private WheelView mStartTimeWheel;

	private WheelView mEndTimeWheel;

	public static void chosePushTime(Activity context, ChosePushTimeListener listener, String startTime, String endTime){
		ChosePushTimeDialog dialog = new ChosePushTimeDialog(context);
		dialog.mContext = context;
		dialog.mInitStartTime = startTime;
		dialog.mInitEndTime = endTime;
		dialog.mListener = listener;
		
		if("00:00".equals(dialog.mInitEndTime)){
			dialog.mInitEndTime = "24:00";
		}
		
		dialog.show();
	}
	
	protected ChosePushTimeDialog(Context context) {
		super(context);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_chose_push_time);
		mStartTimeWheel = (WheelView) findViewById(R.id.dialog_chose_push_time_start_time_wheel);
		mEndTimeWheel = (WheelView) findViewById(R.id.dialog_chose_push_time_end_time_wheel);
		
		mStartTimeWheel.setVisibleItems(5);
		mEndTimeWheel.setVisibleItems(5);
		
		mStartTimeWheel.setAdapter(new ArrayWheelAdapter<String>(ARRAY_START_TIME));
		mEndTimeWheel.setAdapter(new ArrayWheelAdapter<String>(ARRAY_END_TIME));
		
		mStartTimeWheel.setCurrentItem(getTimeIndex(mInitStartTime, ARRAY_START_TIME));
		mEndTimeWheel.setCurrentItem(getTimeIndex(mInitEndTime, ARRAY_END_TIME));

		setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				mListener.onCancel();
			}
		});
		findViewById(R.id.dialog_chose_push_time_cancel).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				mListener.onCancel();
			}
		});
		findViewById(R.id.dialog_chose_push_time_confirm).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				confirm();
			}
		});
	}
	
	private int getTimeIndex(String time, String[] strs){
		String time2 = time != null ? ("0" + time) : "";
		int index = 0;
		for(int i = 0; i < strs.length; i++){
			if(strs[i].equals(time)){
				index = i;
				break;
			}
			if(strs[i].equals(time2)){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void confirm(){
		httpPost();
		
	}
	
	public static interface ChosePushTimeListener{
		public void onCancel();
		public void onConfirm(String startTime, String endTime);
	}

	public void httpPost()
	{  

		mStartTime = ARRAY_START_TIME[mStartTimeWheel.getCurrentItem()];
		mEndTime = ARRAY_END_TIME[mEndTimeWheel.getCurrentItem()];
		
		if(!TextUtils.isEmpty(mStartTime) && mStartTime.equals(mInitStartTime) 
				&& !TextUtils.isEmpty(mEndTime) && mEndTime.equals(mInitEndTime)){
			dismiss();
			return;
		}
		
		UIHelper.addPD(mContext, "处理中...");
		
		RequestParams params = new RequestParams();
		params.put("start_time", mStartTime);
		params.put("end_time", mEndTime);

		params.put("devicetype", "2");
		params.put("devicetoken", AVInstallation.getCurrentInstallation().getInstallationId());
		params.put("objectid", AVInstallation.getCurrentInstallation().getObjectId());
		 
		HttpUtil.post(mContext, JUrl.SITE + JUrl.URL_DO_PUSH_CONFIG_TIME, params, new MyJsonHttpResponseHandler() {
	       	    
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.removePD();
	             if(isShowing()){
		             dismiss();
	             }
	     		 mListener.onConfirm(mStartTime, mEndTime);
				
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) { 
	             UIHelper.removePD(); 
	             UIHelper.showToast(mContext, "操作失败");
			}
		});
 
	}
	
}
