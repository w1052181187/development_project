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
import com.chengning.common.base.BaseViewHolder;
import com.chengning.fenghuo.Consts.MessageType;
import com.chengning.fenghuo.PushMsgManager;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.adapter.MessagelItemAdapter;
import com.chengning.fenghuo.base.BaseListBean;
import com.chengning.fenghuo.base.BasePageListActivity;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.data.bean.DynamicListBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.widget.CommentAboutMePopup;
import com.chengning.fenghuo.widget.CommentAboutMePopup.OnDataTypeChangeListener;
import com.chengning.fenghuo.widget.CommentAboutMePopup.PopupDataType;
import com.google.gson.Gson;

@SuppressLint("ResourceAsColor")
public class CommentAboutMeListActivity extends BasePageListActivity<DynamicItemBean> {

	private View mView;
	
	private PopupDataType mDataType = CommentAboutMePopup.DEFAULT_DATA_TYPE;
	private CommentAboutMePopup mPopup;

	@Override
	public BaseFragmentActivity buildContext() {
		return CommentAboutMeListActivity.this;
	}

	@Override
	public String buildUrl() {
		// type 1评论我的 2我的评论 不传值所有评论
		String url = JUrl.SITE + JUrl.URL_GET_MY_COMMENT;
		switch (mDataType) {
		default:
		case All:
			return url;
		case AboutMe:
			return JUrl.append(url, "type", "1");
		case MyComment:
			return JUrl.append(url, "type", "2");
		}
	}

	@Override
	public BaseAdapter buildAdapter(FragmentActivity activity, List list) {
		return new MessagelItemAdapter(activity, list, MessageType.COMMENT){
			public void configItemView(View convertView, int position, Object obj,
					int type){
				final TextView comment_tv = BaseViewHolder.get(convertView,
						R.id.item_message_comment_tv);
				// TODO(me)
			}
		};
	}

	@Override
	public String buildMaxId(List list) {
		return ((DynamicItemBean)list.get(0)).getTid();
	}

	@Override
	public String configTitle() {
		return "";
	}

	@Override
	public String configNoData() {
		return "暂无数据";
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_base_list, null, false);
		return mView;
	}

	@Override
	public void initExtraView() {
		mPopup = new CommentAboutMePopup(getActivity(), new OnDataTypeChangeListener() {
			
			@Override
			public void onChange(PopupDataType type) {
				CommentAboutMeListActivity.this.mDataType = type;
				getTitleBar().setTitle(mDataType.getStr());
				getPullListView().setRefreshing();
			}
		});
		PushMsgManager.getInstance().getPushUserDataBean().setComment_new(0);
	}

	@Override
	public void initExtraData() {

	}

	@Override
	public void initDatas(){
		super.initDatas();
		
		getTitleBar().setTitle(mDataType.getStr());
		getTitleBar().getTitle().setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.comment_about_me_title_drawable_padding));
		getTitleBar().setTitle(R.drawable.arrow_down_2);
	}

	@Override
	public void initExtraListener() {
		getTitleBar().setTitleOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPopup.show(v);
			}
		});
	}

	@Override
	public void handleItemClick(AdapterView<?> parent, View view, int position,
			long id) {		
//		DynamicItemBean bean = (DynamicItemBean) view.findViewById(R.id.item_message_content).getTag();
//		if (!Common.isTrue(bean.getIs_subscribe())) {
//			DynamicDetailActivity.launch(getActivity(), bean);
//		} else {
//			ArticleActivity.launch(getActivity(), bean);
//		}
		
	}

	@Override
	public BaseListBean<DynamicItemBean> handleHttpSuccess(Gson gson, String data) {
		DynamicListBean bean = gson.fromJson(data, DynamicListBean.class);
		return bean;
	}
	
}
