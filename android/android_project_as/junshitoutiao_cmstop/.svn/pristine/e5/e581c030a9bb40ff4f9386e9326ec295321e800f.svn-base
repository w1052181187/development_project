package com.cmstop.jstt.views;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.cmstop.jstt.LoginManager;
import com.cmstop.jstt.MyStatusResponseHandler;
import com.cmstop.jstt.beans.data.MChannelItemBean;
import com.cmstop.jstt.data.access.LocalStateServer;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.JUrl;
import com.cmstop.jstt.utils.SPHelper;
import com.cmstop.jstt.utils.UIHelper;
import com.loopj.android.http.RequestParams;
import com.cmstop.jstt.R;

public class FavoriteMergeDialog extends BaseDialogFragment {
	
	public static interface OnFavoriteMergeListener{
		public void onFavoriteMergeSuccess();
		public void onFavoriteMergeFailure();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogDefault);
	}

	@Override
	public BaseFragmentActivity getContext() {
		return (BaseFragmentActivity)getActivity();
	}

	@Override
	public View configContentView() {
		return null;
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {

	}
	
	@SuppressLint("NewApi")
	@Override  
    public Dialog onCreateDialog(Bundle savedInstanceState)  
    {  
        AlertDialog.Builder builder = null;
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
			builder = new AlertDialog.Builder(getActivity(), getTheme());
		}else{
			builder = new AlertDialog.Builder(getActivity());
		}
        builder
        .setMessage(getString(R.string.collect_merge_hint))
        .setPositiveButton("是", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				uploadFavorites();
			}
		}).setNegativeButton("否", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SPHelper.getInst().saveBoolean(SPHelper.KEY_FAVORITE_MERGE_KNOW, true);
				dismiss();
			}
		});
        return builder.create();  
    }
	
	private void uploadFavorites(){
		ArrayList<MChannelItemBean> list = LocalStateServer.getInst(getContext()).getAllFavArticles();
		
		if(Common.isListEmpty(list)){
			dismiss();
			return;
		}
		final Activity context = getContext();
		StringBuilder sb = new StringBuilder();
		int size = list.size();
		for(int i = 0; i < size; i++){
			String aid = list.get(i).getAid();
			if(!TextUtils.isEmpty(aid)){
				sb.append(aid);
				sb.append(",");
			}
		}
		if(sb.length() > 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		if(!Common.hasNet()){
			UIHelper.showToast(context, context.getResources().getString(R.string.intnet_fail));
		}
		RequestParams params = new RequestParams();
		params.add("aid", sb.toString());
		HttpUtil.getClient().get(context, JUrl.SITE + JUrl.URL_COLLECT_FAVORITE_ATRICLES, params, new MyStatusResponseHandler() {
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
//				UIHelper.showToast(context, "文章已合并");

				SPHelper.getInst().saveBoolean(SPHelper.KEY_FAVORITE_MERGE_KNOW, true);
				dismiss();
				((OnFavoriteMergeListener)getContext()).onFavoriteMergeSuccess();
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				if(status == -3){
					// 重复收藏文章或无可用收藏数据！
					onDataSuccess(-3, mod, message, data, obj);					
				}else{
					UIHelper.showToast(context, message);
					((OnFavoriteMergeListener)getContext()).onFavoriteMergeFailure();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse){
				Common.showHttpFailureToast(context);
				((OnFavoriteMergeListener)getContext()).onFavoriteMergeFailure();
			}  
		});
	}
	
	public static boolean checkFavoriteMergeKnowWithDialog(BaseFragmentActivity activity){
		if(LoginManager.getInst().isLogin() && !SPHelper.getInst().getBoolean(SPHelper.KEY_FAVORITE_MERGE_KNOW)){
			List list = LocalStateServer.getInst(activity).getAllFavArticles();
			if(!Common.isListEmpty(list)){
				FavoriteMergeDialog dialog = new FavoriteMergeDialog();
				dialog.showAllowingStateLoss(activity, activity.getSupportFragmentManager(), FavoriteMergeDialog.class.getSimpleName());
				return false;
			}
		}
		return true;
	}
}
