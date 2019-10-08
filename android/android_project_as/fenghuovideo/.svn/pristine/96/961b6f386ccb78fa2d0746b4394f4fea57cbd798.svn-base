package com.chengning.fenghuovideo.widget;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;
import com.chengning.fenghuovideo.util.Common;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.HashSet;
import java.util.List;

public class HomeNewContentHeader {
	
	private static final long TIME_DELAY_DISMISS = 3000;

	public HomeNewContentHeader() {
	}

	public void show(final Activity activity, final ViewGroup v, final Handler handler, String text, final PullToRefreshListView pull) {
		if(v.getChildCount() == 0){
			View layout = View.inflate(activity, R.layout.header_home_new_content, null);
			v.addView(layout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}

		v.setVisibility(View.VISIBLE);
		final TextView tv = (TextView) v.findViewById(R.id.header_home_new_content_text);
		tv.setText(text);
		tv.setVisibility(View.VISIBLE);
		Runnable dismissRunnable = null;
		Object obj = tv.getTag();
		if(obj == null){
			dismissRunnable = new Runnable() {
				
				@Override
				public void run() {
					if(activity != null){
//						v.setVisibility(View.GONE);
						if (pull.getState() == State.RESET) {
							v.setVisibility(View.GONE);
						} else {
							handler.removeCallbacks(this);
							handler.postDelayed(this, TIME_DELAY_DISMISS);
						}
							
//						pull.scrollBy(0, tv.getHeight());
//						pull.smoothScrollTo(tv.getHeight());
//						if (pull.getState() == State.RESET) {
//							pull.getRefreshableView().smoothScrollBy(-tv.getHeight(), 1000);
//						} else {
//							v.setVisibility(View.GONE);
//						}
					}
				}
			};
			tv.setTag(dismissRunnable);
		}else{
			dismissRunnable = (Runnable) obj;
		}
		handler.removeCallbacks(dismissRunnable);
		handler.postDelayed(dismissRunnable, TIME_DELAY_DISMISS);
	}
	
	public static void handleHttpListData(Activity activity, ViewGroup v,
			Handler handler, int newCount, PullToRefreshListView pull) {
		if(newCount > 0){
			String text =  String.format("烽火军事推荐引擎有%d条更新", newCount);
			new HomeNewContentHeader().show(activity, v, handler, text, pull);
		}else{
			// 首页无新数据时的提示
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
