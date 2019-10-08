package com.chengning.yiqikantoutiao.widget;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.util.Common;

import java.util.HashSet;
import java.util.List;

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
	
//	public static void handleFirstCommentList(Activity activity, View v,
//			Handler handler) {
//		boolean isFirst = SPHelper.getInst().getBoolean(SPHelper.KEY_FIRST_COMMENT_LIST, true);
//		if(isFirst){
//			String text = "单击评论能显示菜单、增加赞，复制";
//			new HomeNewContentPopup().show(activity, v, handler, text);
//			SPHelper.getInst().saveBoolean(SPHelper.KEY_FIRST_COMMENT_LIST, false);
//		}else{
//			// TODO(首页无新数据时的提示)
////			String text = "";
//		}
//	}
	
	public static int calcuateNewCount(List<ChannelItemBean> olds,
			List<ChannelItemBean> news){
		int c = 0;
		HashSet<String> ids = new HashSet<String>();
		if(!Common.isListEmpty(olds)){
			for(ChannelItemBean m : olds){
				ids.add(m.getTid());
			}
		}
		if(!Common.isListEmpty(news)){
			for(ChannelItemBean m : news){
				if(!ids.contains(m.getTid())){
					c++;
				}
			}
		}
		return c;
	}

}
