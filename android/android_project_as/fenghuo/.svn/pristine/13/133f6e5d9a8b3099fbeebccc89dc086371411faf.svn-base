package com.chengning.fenghuo.activity;
 
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.google.gson.Gson;

@SuppressLint("ResourceAsColor")
public class MyDigListActivity extends BasePageListActivity<DynamicItemBean> {

	private View mView;

	@Override
	public BaseFragmentActivity buildContext() {
		return MyDigListActivity.this;
	}

	@Override
	public String buildUrl() {
		return JUrl.SITE + JUrl.URL_GET_DIG_ME;
	}

	@Override
	public BaseAdapter buildAdapter(FragmentActivity activity, List list) {
		return new MessagelItemAdapter(activity, list, MessageType.ZAN){
			
			public void configItemView(View convertView, int position, Object obj,
					int type){
				final TextView comment_tv = BaseViewHolder.get(convertView,
						R.id.item_message_comment_tv);
				comment_tv.setVisibility(View.GONE);
			}
		};
	}

	@Override
	public String buildMaxId(List list) {
		return ((DynamicItemBean)list.get(0)).getId();
	}

	@Override
	public String configTitle() {
		return "赞我的";
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
		PushMsgManager.getInstance().getPushUserDataBean().setDig_new(0);
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
//		DynamicItemBean bean = (DynamicItemBean) view.findViewById(R.id.item_message_summary_ll).getTag();
//		if (!Common.isTrue(bean.getIs_subscribe())) {
//			DynamicDetailActivity.launch(getActivity(), bean);
//		} else {
//			ArticleActivity.launch(getActivity(), bean);
//		}
	}

	@Override
	public BaseListBean<DynamicItemBean> handleHttpSuccess(Gson gson, String data) {
		DynamicListBean bean = gson.fromJson(data, DynamicListBean.class);
		if(!Common.isListEmpty(bean.getList())){
			ArrayList<DynamicItemBean> list = new ArrayList<DynamicItemBean>();
			for(DynamicItemBean b : bean.getList()){
				list.add(convertDynamicToDigBean(b));
			}
			bean.setList(list);
		}
		return bean;
	}
	
	public static DynamicItemBean convertDynamicToDigBean(DynamicItemBean bean){
		DynamicItemBean parentBean = new DynamicItemBean();
		parentBean.setTid(bean.getTid());
		parentBean.setUid(bean.getTouid());
		parentBean.setDateline("");
		parentBean.setFace(bean.getToface());
		parentBean.setNickname(bean.getTonickname());
		parentBean.setFrom("");
		parentBean.setContent(bean.getContent());
		parentBean.setImage(bean.getImage());
		parentBean.setImage_list(bean.getImage_list());
		parentBean.setTitle(bean.getTitle());
		parentBean.setIs_subscribe(bean.getIs_subscribe());
		parentBean.setRoot_list(bean.getParent_list());
		parentBean.setContent_type(bean.getContent_type());
		
		ArrayList<String> strs = new ArrayList<String>();
		strs.add("reply".equals(bean.getType()) ? "赞了你" : "赞了你");
		
		DynamicItemBean digBean = new DynamicItemBean();
		digBean.setId(bean.getId());
		digBean.setTid(bean.getTid());
		digBean.setUid(bean.getUid());
		digBean.setDateline(bean.getDateline());
		digBean.setFace(bean.getFace());
		digBean.setRole_name(bean.getRole_name());
		digBean.setNickname(bean.getNickname());
		digBean.setFrom(bean.getFrom());
		digBean.setContent(strs);
		digBean.setImage_list(null);
		digBean.setType(bean.getType());
		digBean.setContent_type(bean.getContent_type());
		
		digBean.setParent_list(parentBean);
		return digBean;
	}
	
}
