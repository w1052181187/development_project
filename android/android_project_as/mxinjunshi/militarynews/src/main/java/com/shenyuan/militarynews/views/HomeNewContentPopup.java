package com.shenyuan.militarynews.views;

import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.SPHelper;

public class HomeNewContentPopup {
	
	private static final long TIME_DELAY_DISMISS = 3000;

	public HomeNewContentPopup() {
	}

	public void show(final Activity activity, View v, Handler handler, String text) {
		final TextView tv = (TextView) v.findViewById(R.id.popup_home_new_content_text);
		tv.setText(text);
		tv.setVisibility(View.VISIBLE);
		final long tagTime = System.currentTimeMillis();
		tv.setTag(tagTime);
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(activity != null){
					// 同一textview有可能连续设置多次，只处理最新的
					if(tagTime == (Long)tv.getTag()){
						tv.setVisibility(View.GONE);
					}
				}
			}
		}, TIME_DELAY_DISMISS);
	}

	public static void handleHttpListData(Activity activity, View v,
			Handler handler, int newCount) {
		if(newCount > 0){
			String text = "小编为您推荐了" + newCount + "条新内容";
			new HomeNewContentPopup().show(activity, v, handler, text);
		}else{
			// TODO(首页无新数据时的提示)
//			String text = "";
		}
	}
	
	public static void handleFirstCommentList(Activity activity, View v,
			Handler handler) {
		boolean isFirst = SPHelper.getInst().getBoolean(SPHelper.KEY_FIRST_COMMENT_LIST, true);
		if(isFirst){
			CommentFirstHintDialog dialog = new CommentFirstHintDialog();
			dialog.showAllowingStateLoss((BaseFragmentActivity) activity,((FragmentActivity)activity).getSupportFragmentManager(), CommentFirstHintDialog.class.getSimpleName());
			SPHelper.getInst().saveBoolean(SPHelper.KEY_FIRST_COMMENT_LIST, false);
		}else{
			// TODO(首页无新数据时的提示)
//			String text = "";
		}
	}
	
	public static int calcuateNewCount(List<MChannelItemBean> olds,
			List<MChannelItemBean> news){
		int c = 0;
		HashSet<String> ids = new HashSet<String>();
		if(!Common.isListEmpty(olds)){
			for(MChannelItemBean m : olds){
				ids.add(m.getAid());
			}
		}
		if(!Common.isListEmpty(news)){
			for(MChannelItemBean m : news){
				if(!ids.contains(m.getAid())){
					c++;
				}
			}
		}
		return c;
	}

}
