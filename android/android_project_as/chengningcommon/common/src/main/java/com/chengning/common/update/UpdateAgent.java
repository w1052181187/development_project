package com.chengning.common.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.chengning.common.base.util.BaseCommon;
import com.chengning.common.base.util.BaseJUrl;
import com.chengning.common.base.util.RetrofitManager;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class UpdateAgent {
	
	public UpdateAgent(){
		
	} 

	public static void update(final Context context, final String appkey){
		update(context, appkey, BaseCommon.getUmengChannel(context), null);
	}
	
	public static void update(final Context context, final String appkey, final String channel, final UpdateListener listener){
//		String url = BaseJUrl.append(UpdateConst.URL_UPDATE, UpdateConst.KEY_APPKEY, appkey);
		String url = new StringBuilder(UpdateConst.URL_UPDATE)
				.append(UpdateConst.KEY_APPKEY).append("=").append(appkey).toString();
		url = BaseJUrl.append(url, UpdateConst.KEY_VERSION_CODE, UpdateUtil.getVersionCode(context));
		url = BaseJUrl.append(url, UpdateConst.KEY_OLD_MD5, UpdateUtil.getApplicationMD5(context));
		url = BaseJUrl.append(url, UpdateConst.KEY_CHANNEL, channel);
		Request request = new Request.Builder()
				.url(url)
				.build();
		OkHttpClient client = RetrofitManager.getInst().getClientBuilder().build();;
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				if(listener != null){
					listener.onUpdateReturned(UpdateStatus.Error, null);
				}
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if(context == null || response == null){
					// null
					if(listener != null){
						listener.onUpdateReturned(UpdateStatus.Error, null);
					}
					return;
				}
				try {
					Gson gson = new Gson();
					String result = response.body().string();
					if (!TextUtils.isEmpty(result)) {
						UpdateBean bean = gson.fromJson(result, UpdateBean.class);
						if("yes".equalsIgnoreCase(bean.getUpdate())){
							// yes
							if(!UpdateUtil.isWifi(context)){
								if(listener != null){
									listener.onUpdateReturned(UpdateStatus.NoneWifi, null);
								}
							}else{
								if(listener != null){
									listener.onUpdateReturned(UpdateStatus.Yes, null);
								}
							}

							Intent intent = new Intent(context, UpdateDialogActivity.class);
							if(!(context instanceof Activity)) {
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							}

							intent.putExtra("bean", bean);
							context.startActivity(intent);
						}else{
							// no
							if(listener != null){
								listener.onUpdateReturned(UpdateStatus.No, null);
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}
}
