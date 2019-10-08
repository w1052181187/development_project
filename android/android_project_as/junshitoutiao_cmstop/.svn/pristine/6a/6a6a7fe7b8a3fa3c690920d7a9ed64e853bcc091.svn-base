package com.cmstop.jstt.fragment.home;

import java.util.List;
import java.util.zip.Inflater;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.chengning.common.base.BaseFragmentActivity;
import com.cmstop.jstt.adapter.MyCommentItemAdapter;
import com.cmstop.jstt.base.BaseListBean;
import com.cmstop.jstt.base.BaseNetListFragment;
import com.cmstop.jstt.beans.data.CommentItemBean;
import com.cmstop.jstt.beans.data.CommentListBean;
import com.cmstop.jstt.utils.JUrl;
import com.google.gson.Gson;
import com.cmstop.jstt.R;
import com.viewpagerindicator.TabPageIndicator;

public class MyCommentFragment extends BaseNetListFragment<CommentItemBean>{

	private static final String KEY_TYPE = "comment_type";
	public static final String COMMENT_TYPE_MY = "my_cmt";
	public static final String COMMENT_TYPE_CMT_MY = "cmt_my";
	private String mCmtType;
	private View titlebar;
	
	public MyCommentFragment(String type) {
		Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        setArguments(bundle);
	}
	
	@Override
	public BaseFragmentActivity buildContext() {
		return (BaseFragmentActivity) getContext();
	}

	@Override
	public String buildUrl() {
		String url = JUrl.SITE;
		if (TextUtils.equals(mCmtType, COMMENT_TYPE_MY)) {
			url = url + JUrl.URL_GET_MY_COMMENT_REPLY_LIST;
		} else if (TextUtils.equals(mCmtType, COMMENT_TYPE_CMT_MY)) {
			url = url + JUrl.URL_GET_MY_COMMENT_REPLY_LIST_MY;
		}
		return url;
	}

	@Override
	public BaseAdapter buildAdapter(FragmentActivity activity, List list) {
//		if (TextUtils.equals(mCmtType, COMMENT_TYPE_MY)) {
//			return new MyCommentItemAdapter(activity, list, mCmtType);
//		} else if (TextUtils.equals(mCmtType, COMMENT_TYPE_CMT_MY)) {
//			return new MyCommentItemAdapter(activity, list,mCmtType);
//		}
		return new MyCommentItemAdapter(activity, list, mCmtType);
		
	}

	@Override
	public String buildMaxId(List list) {
		return ((CommentItemBean)list.get(0)).getAid();
	}

	@Override
	public String configTitle() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String configNoData() {
		if (TextUtils.equals(mCmtType, COMMENT_TYPE_MY)) {
			return "暂时还没有我的评论";
		} else if (TextUtils.equals(mCmtType, COMMENT_TYPE_CMT_MY)) {
			return "暂时还没有评论我的";
		}
		return "";
	}

	@Override
	public View configContentView() {
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my_comment, null);
		titlebar = mView.findViewById(R.id.title_bar_layout);
		return mView;
	}

	@Override
	public void initExtraView() {
		mCmtType = getArguments().getString(KEY_TYPE);
		titlebar.setVisibility(View.GONE);
	}

	@Override
	public void initExtraListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BaseListBean<CommentItemBean> handleHttpSuccess(Gson gson,
			String data) {
		CommentListBean bean = gson.fromJson(data, CommentListBean.class);
		return bean;
	}

}
