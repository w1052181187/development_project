package com.shenyuan.militarynews.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.ad.AdDataDummy.NativeResponseDummy;
import com.shenyuan.militarynews.adapter.AdChannelItemRecommendAdapter;
import com.shenyuan.militarynews.base.BaseListBean;
import com.shenyuan.militarynews.base.BasePageListActivity;
import com.shenyuan.militarynews.base.IScrollCallBack;
import com.shenyuan.militarynews.beans.data.ExclusiveListBean;
import com.shenyuan.militarynews.beans.data.ExclusiveListBean.ExclusiveListHeaderBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.beans.data.MChannelNavBean;
import com.shenyuan.militarynews.data.access.LocalStateServer;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.Utils;

public class ExclusiveDetailActivity extends BasePageListActivity<MChannelItemBean>{
	
	private static final String TAG = ExclusiveDetailActivity.class.getSimpleName();
	
	protected static final int DATA_RED_COMMENT = 10003;
	
	private View mView;

	private MChannelNavBean mBean;

	private boolean isFirstPage;

	private LinearLayout mHeader;

	private DisplayImageOptions mOptions;
	
	private IScrollCallBack mScrollCallBack;
	private Button mTopBtn;

	public static void launch(Activity from, MChannelNavBean bean){
		Intent intent = new Intent(from, ExclusiveDetailActivity.class);
		intent.putExtra("bean", bean);
		from.startActivity(intent);
	}

	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putSerializable("bean", mBean); 
    	
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mBean = (MChannelNavBean) savedInstanceState.getSerializable("bean");
    }
    

	@Override
	public BaseFragmentActivity buildContext() {
		return ExclusiveDetailActivity.this;
	}

	@Override
	public String buildUrl() {
		String url = JUrl.SITE + JUrl.API + mBean.getUrl();
		return url;
	}

	@Override
	public BaseAdapter buildAdapter(FragmentActivity activity, List list) {
		AdChannelItemRecommendAdapter adapter = new AdChannelItemRecommendAdapter(activity, list, null);
		adapter.setDividerExclusive(true);
		return adapter;
	}

	@Override
	public String buildMaxId(List list) {
		MChannelItemBean firstBean = (MChannelItemBean) list.get(0);
		return firstBean.getAid();
	}

	@Override
	public String configTitle() {
		return mBean.getName();
	}

	@Override
	public String configNoData() {
		return "暂时还没有数据";
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_exclusive_detail, null, false);
		return mView;
	}

	@Override
	public void initExtraView() {
		mHeader = new LinearLayout(getActivity());
		mHeader.setOrientation(LinearLayout.VERTICAL);
		
		//设置监听（取消点击的背景）
		mHeader.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		getPullListView().getRefreshableView().addHeaderView(mHeader);
		mTopBtn = (Button) mView.findViewById(R.id.topbtn);
	}

	@Override
	public void initExtraListener() {
		mTopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				forceTop();
				mTopBtn.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void handleItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		View tagView = view.findViewById(R.id.item_channel_item_content_layout);
		if(tagView == null){
			//屏蔽头部点击事件..
			return;
		}
		Object tag = tagView.getTag();
		if(tag instanceof MChannelItemBean){
			Common.handleTextViewReaded(getActivity(), view, R.id.item_channel_item_title_one_small, true);
			Common.handleTextViewReaded(getActivity(), view, R.id.item_channel_item_title_one_big_two_small, true);
			Common.handleTextViewReaded(getActivity(), view, R.id.item_channel_item_title_one_big, true);
			Common.handleTextViewReaded(getActivity(), view, R.id.item_channel_item_title_three_small, true);
			Common.handleTextViewReaded(getActivity(), view, R.id.item_channel_item_title_two_big_1, true);
			Common.handleTextViewReaded(getActivity(), view, R.id.item_channel_item_title_two_big_2, true);
			
			MChannelItemBean bean = (MChannelItemBean) tag;
			handleBeanClick(bean, position);
		}else if(tag instanceof NativeResponseDummy){
			NativeResponseDummy adBean = (NativeResponseDummy) tag;
			adBean.handleClick(view);
		}
	}
	public void handleBeanClick(MChannelItemBean bean, int position){
		if(bean == null){
			return;
		}
		LocalStateServer.getInst(getActivity()).setReadStateRead(LocalStateServer.PREFIX_CHANNEL_ITEM, bean.getAid());
		Utils.handleBeanClick(getActivity(), bean);
	}

	@Override
	public BaseListBean<MChannelItemBean> handleHttpSuccess(Gson gson,
			String data) {
		ExclusiveListBean bean = gson.fromJson(data, ExclusiveListBean.class);
		if (isFirstPage) {
			addHeader(bean);
		}
		
		return bean;
	}
	
	private void addHeader(ExclusiveListBean bean) {
		ExclusiveListHeaderBean headerBean = bean.getChan_info();
		if (headerBean != null) {
			mHeader.removeAllViews();
			View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_exclusive_detail, null);
			setHeader(header, headerBean);
			mHeader.addView(header);
			
		} else {
			mHeader.removeAllViews();
		}
	}
	
	private void setHeader(View header, ExclusiveListHeaderBean headerBean) {
		ImageView face = (ImageView) header.findViewById(R.id.header_exclusive_detail_face);
		TextView info = (TextView) header.findViewById(R.id.header_exclusive_detail_info_content);
		TextView time = (TextView) header.findViewById(R.id.header_exclusive_detail_time);
		ImageLoader.getInstance().displayImage(headerBean.getImage(), face, mOptions);
		info.setText(headerBean.getDescription());
		time.setText("更新至：	" + headerBean.getPubTime() + "期");
	}

	@Override
	public void getDataByHttp(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
		super.getDataByHttp(isFirstPage);
	}

	@Override
	public void initExtraData() {
		mBean = (MChannelNavBean) getIntent().getSerializableExtra("bean");
		
		mOptions = new DisplayImageOptions.Builder() 
		.showImageOnLoading(R.drawable.loading)
		.showImageForEmptyUri(R.drawable.loading)
		.showImageOnFail(R.drawable.loading)
		.resetViewBeforeLoading(true)
		.cacheInMemory(true)  
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.build();
		
		mScrollCallBack = new IScrollCallBack() {
			@Override
			public void show() {
				mTopBtn.setVisibility(View.VISIBLE);
			}
			@Override
			public void hidden() {
				mTopBtn.setVisibility(View.GONE);
			}
		};
		setmScrollCallback(mScrollCallBack);
	}
	
}
