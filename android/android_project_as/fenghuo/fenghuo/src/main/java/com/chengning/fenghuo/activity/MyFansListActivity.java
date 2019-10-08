package com.chengning.fenghuo.activity;
 
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.PushMsgManager;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.adapter.MyFansListAdapter;
import com.chengning.fenghuo.base.BaseListBean;
import com.chengning.fenghuo.base.BasePageListActivity;
import com.chengning.fenghuo.data.bean.FansListBean;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.JUrl;
import com.google.gson.Gson;

@SuppressLint("ResourceAsColor")
public class MyFansListActivity extends BasePageListActivity<UserInfoBean> {

	private View mView;

	@Override
	public BaseFragmentActivity buildContext() {
		return MyFansListActivity.this;
	}

	@Override
	public String buildUrl() {
		return JUrl.SITE + JUrl.URL_GET_MILITARY_FANS_FANS;
	}

	@Override
	public BaseAdapter buildAdapter(FragmentActivity activity, List list) {
		return new MyFansListAdapter(activity, list, new FollowOnClickListener());
	}

	@Override
	public String buildMaxId(List list) {
		return ((UserInfoBean)list.get(0)).getUid();
	}

	@Override
	public String configTitle() {
		return "我的粉丝";
	}

	@Override
	public String configNoData() {
		return "暂无粉丝";
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_base_list, null, false);
		return mView;
	}

	@Override
	public void initExtraView() {
		PushMsgManager.getInstance().getPushUserDataBean().setFans_new(0);
	}

	@Override
	public void initExtraData() {

	}

	@Override
	public void initExtraListener() {
		
	}

	@Override
	public void handleItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		UserInfoBean u = (UserInfoBean) view.findViewById(R.id.follow_fans_user_name).getTag();
		UserInfoActivity.launch(getActivity(), u.getNickname());
	}

	@Override
	public BaseListBean<UserInfoBean> handleHttpSuccess(Gson gson, String data) {
		FansListBean bean = gson.fromJson(data, FansListBean.class);
		return bean;
	}
	
	private class FollowOnClickListener implements OnClickListener { 
		@Override
		public void onClick(View v) {
			if(LoginManager.getInst().checkLoginWithNotice(getActivity())) {
				UserInfoBean bean = (UserInfoBean) v.getTag();
                Follow(bean.getUid(), v);
			}
		} 
	}
	
	public void Follow(String uid, final View v) {
		ArticleManagerUtils.followUser(getActivity(), uid, new Runnable() {
			
			@Override
			public void run() {
				v.setSelected(!v.isSelected());
				if (v instanceof TextView) {
					((TextView)v).setText(!v.isSelected() ? getString(R.string.str_userinfo_already_focused)
							: getString(R.string.str_columnist_not_focused));
				}
			}
		});
	}
}
