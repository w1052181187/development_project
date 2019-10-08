package com.cmstop.jstt.fragment.home;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengning.common.util.SerializeUtil;
import com.cmstop.jstt.R;
import com.cmstop.jstt.activity.ExclusiveDetailActivity;
import com.cmstop.jstt.beans.data.MChannelBean;
import com.cmstop.jstt.beans.data.MChannelItemBean;
import com.cmstop.jstt.beans.data.MChannelNavBean;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.JUrl;
import com.cmstop.jstt.utils.SPHelper;
import com.cmstop.jstt.utils.Utils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class VideoFragment extends AbstractChannelItemListFragment {
	
	public static final String KEY_BEAN = "bean";

	private LinearLayout mHeader;
	private PullToRefreshListView mListView;
	private MChannelNavBean mBean;
	
	public VideoFragment(){
	}
	
	@SuppressLint("ValidFragment")
	public VideoFragment(MChannelNavBean bean){
		this.mBean = bean;

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BEAN, bean);
        setArguments(bundle);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		if(mBean == null) {
			mBean = (MChannelNavBean) bundle.getSerializable(KEY_BEAN);
		}
        super.onCreate(savedInstanceState); 
    }

	@Override
	public void initDatas(){
		super.initDatas();
//		setHomeRecom(true);
	}

	@Override
	public String buildTAG() {
		return VideoFragment.class.getSimpleName();
	}
	
	@Override
	public String buildChannel() {
		return "exclusive";
	}

	@Override
	public String buildUrl(int tarPage) {
		String url = JUrl.SITE + JUrl.URL_GET_CHANNEL_EXCLUSIVE;
		if (mBean != null && TextUtils.isEmpty(mBean.getUrl())) {
			url = JUrl.SITE + JUrl.API+ mBean.getUrl();
		}
		url =JUrl.appendPage(url, tarPage);
		url = appendMaxid(url, tarPage);
		return url.toString();
	}
	
	@Override
	public List<MChannelItemBean> onHttpSuccess(Gson gson, String data,
			MChannelBean channelBean, int tarPage) {
		if (tarPage == JUrl.PAGE_START) {
			ArrayList<MChannelNavBean> nav = channelBean.getNav();
			String navStr = SerializeUtil.serialize(nav);
			SPHelper.getInst().saveString(SPHelper.KEY_CHANNEL_EXCLUSIVE_NAV, navStr);
		}
		
		return super.onHttpSuccess(gson, data, channelBean, tarPage);
	}
	@Override
	public void configPullToRefreshListView_FootLoad(PullToRefreshListView v) {
		this.mListView = v;
		mHeader = new LinearLayout(getContext());
		mHeader.setOrientation(LinearLayout.VERTICAL);
		mListView.getRefreshableView().addHeaderView(mHeader);
		super.configPullToRefreshListView_FootLoad(v);
	}
	
	@Override
	public void onInitNewsDataUI(){
		ArrayList<MChannelNavBean> nav = (ArrayList<MChannelNavBean>) SerializeUtil.deSerialize(SPHelper.getInst().getString(SPHelper.KEY_CHANNEL_EXCLUSIVE_NAV));
		addHeader(nav);
		super.onInitNewsDataUI();
	}

	private void addHeader(ArrayList<MChannelNavBean> nav) {
		if (!Common.isListEmpty(nav)) {
			mHeader.removeAllViews();
			View header = LayoutInflater.from(getContext()).inflate(R.layout.header_home_video, null);
			setHeader(header, nav);
			mHeader.addView(header);
			
		} else {
			mHeader.removeAllViews();
		}
	}

	private void setHeader(View header, final ArrayList<MChannelNavBean> nav) {
		RecyclerView mRecyclerView = (RecyclerView) header.findViewById(R.id.header_video_nav);  
        //设置布局管理器  
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());  
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);  
        mRecyclerView.setLayoutManager(linearLayoutManager);  
        //设置适配器  
        GalleryAdapter mHeaderAdapter = new GalleryAdapter(getContext(), nav);  
        mRecyclerView.setAdapter(mHeaderAdapter);
        mHeaderAdapter.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClickListener(View view, int position) {
				ExclusiveDetailActivity.launch(getContext(), nav.get(position));
			}
		});
	}
	
	public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {  
		
		private LayoutInflater mInflater;  
		private ArrayList<MChannelNavBean> mDatas;  
		protected OnItemClickListener itemOnListener;

		public GalleryAdapter(Activity context, ArrayList<MChannelNavBean> list) {  
		    mInflater = LayoutInflater.from(context);  
		    mDatas = list;  
		}  

		public class ViewHolder extends RecyclerView.ViewHolder { 
		    public ViewHolder(View arg0)  {  
		        super(arg0);  
		    }  
		
		    ImageView mAvatar;  
		    TextView mName;  
		}  

		@Override  
		public int getItemCount() {
			if (Common.isListEmpty(mDatas)) {
				return 0;
			}
			return mDatas.size();  
		}  

		/** 
		 * 设置值 
		 */  
		@Override  
		public void onBindViewHolder(final ViewHolder viewHolder, final int i){  
			MChannelNavBean bean = mDatas.get(i);
		    Utils.setCircleImage(bean.getImage(), viewHolder.mAvatar);
		    viewHolder.mName.setText(bean.getName());
		    viewHolder.itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					itemOnListener.onItemClickListener(viewHolder.itemView, i);
				}
			});
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
			View view = mInflater.inflate(R.layout.item_header_home_video,  
					arg0, false);  
		    ViewHolder viewHolder = new ViewHolder(view);  
		
		    viewHolder.mAvatar = (ImageView) view.findViewById(R.id.item_header_home_video_avatar);  
		    viewHolder.mName = (TextView) view.findViewById(R.id.item_header_home_video_name);
		    return viewHolder; 
		}  
		
	    /**
	     * 设置点击事件监听器
	     *
	     * @param listener 监听器对象
	     */
	    public void setOnItemClickListener(OnItemClickListener listener) {
	        this.itemOnListener = listener;
	    }

	    
	}
	
	public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }
}  
