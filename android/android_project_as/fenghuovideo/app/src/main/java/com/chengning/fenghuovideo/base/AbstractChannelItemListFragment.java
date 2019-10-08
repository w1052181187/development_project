package com.chengning.fenghuovideo.base;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.IIsRecom;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.activity.MainActivity;
import com.chengning.fenghuovideo.activity.VideoDetailActivity;
import com.chengning.fenghuovideo.adapter.ChannelItemRecommendAdapter;
import com.chengning.fenghuovideo.data.access.ChannelItemListDA;
import com.chengning.fenghuovideo.data.access.LocalStateDA;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.BaseChannelItemBean;
import com.chengning.fenghuovideo.data.bean.ChannelBean;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;
import com.chengning.fenghuovideo.nicevideo.FhItemData;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.SPHelper;
import com.chengning.fenghuovideo.widget.HomeNewContentHeader;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.mozilla.javascript.tools.debugger.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChannelItemListFragment extends
			BasePageListFragment<BaseChannelItemBean> {

	public static final String moreChannelTag = "_more";
	private PullToRefreshListView mPull;
	private TwoHeader mTwoHeader;
	private LinearLayout mPullHeader;
	private View mSlideView;

	private OnRecommendHttpListener mListener;
	private ChannelItemListDA mListServer;

	private int refreshCount;
	private boolean mIsHomeRecom = false;
	
	private class TwoHeader{

		private LinearLayout twoHeaderLayout;
		private LinearLayout newContentHeaderLayout;
		private LinearLayout pullHeaderLayout;
		
		TwoHeader(Activity activity){
			twoHeaderLayout = new LinearLayout(activity);
			newContentHeaderLayout = new LinearLayout(activity);
			pullHeaderLayout = new LinearLayout(activity);
			twoHeaderLayout.setOrientation(LinearLayout.VERTICAL);
			twoHeaderLayout.addView(newContentHeaderLayout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			twoHeaderLayout.addView(pullHeaderLayout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}
		
		LinearLayout getLayout(){
			return twoHeaderLayout;
		}
		
		LinearLayout getPullHeader(){
			return pullHeaderLayout;
		}
		
		LinearLayout getNewContentHeader(){
			return newContentHeaderLayout;
		}
	}
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mPull.setRefreshing();
		}
	};

	public boolean isHomeRecom() {
		return mIsHomeRecom;
	}

	public void setHomeRecom(boolean isHomeRecom) {
		this.mIsHomeRecom = isHomeRecom;
		BaseAdapter adapter = getAdapter();
		if (adapter != null && adapter instanceof ChannelItemRecommendAdapter) {
			((ChannelItemRecommendAdapter) adapter)
					.setHomeRecom(this.mIsHomeRecom);
		}
	}

	@Override
	public BaseAdapter buildAdapter(Activity activity,
			List<BaseChannelItemBean> list) {
		ChannelItemRecommendAdapter adapter = new ChannelItemRecommendAdapter(
				activity, list, buildChannel(), mPull);
		adapter.setIsRecom(Common.isTrue(SPHelper.getInst().getInt(
				SPHelper.PREFIX_KEY_CHANNEL_IS_RECOM + buildChannel())));
		adapter.setHomeRecom(isHomeRecom());
		adapter.setRefreshListener(listener);
		return adapter;
	}

	@Override
	public void onListItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView title = (TextView) view
				.findViewById(R.id.item_channel_item_title);
		if (title == null) {
			Log.e(AbstractChannelItemListFragment.class.getSimpleName(), "onListItemClick position: " + position + "title is null!");
			return;
		}
//		Common.handleTextViewReaded(getContext(), view,
//				R.id.item_channel_item_title, true);
		Object bean = title.getTag();
//		if (bean instanceof BaseChannelItemBean) {
//		}
		if(bean instanceof BaseArticlesBean){
			FhItemData itemData = null;
			itemData = new FhItemData(mPull.getRefreshableView(), position, (BaseArticlesBean) bean);
			handleBeanClick(getContext(), view, (BaseChannelItemBean) bean, itemData);
		}
//		else if (bean instanceof NativeResponseDummy) {
//			NativeResponseDummy adBean = (NativeResponseDummy) bean;
//			adBean.handleClick(view);
//		}
	}
	
	public static void handleVodeoDetailItemClick(Activity context, View view, FhItemData itemData){
		TextView title = (TextView) view
				.findViewById(R.id.item_channel_item_title);
		if (title == null) {
			Log.e(AbstractChannelItemListFragment.class.getSimpleName(), "videoArticleRelativeBottom item click title is null!");
			return;
		}
		Object bean = title.getTag();
		if (bean instanceof BaseArticlesBean) {
			VideoDetailActivity.launch(context, (BaseArticlesBean)bean);
		}
		
	}
	
	public static void handleItemClick(Activity context, View view, FhItemData itemData){
		TextView title = (TextView) view
				.findViewById(R.id.item_channel_item_title);
		if (title == null) {
			Log.e(AbstractChannelItemListFragment.class.getSimpleName(), "videoArticleRelativeBottom item click title is null!");
			return;
		}
//		Common.handleTextViewReaded(context, view,
//				R.id.item_channel_item_title, true);
		Object bean = title.getTag();
		if (bean instanceof BaseChannelItemBean) {
			handleBeanClick(context, view, (BaseChannelItemBean) bean, itemData);
		}
//		else if (bean instanceof NativeResponseDummy) {
//			NativeResponseDummy adBean = (NativeResponseDummy) bean;
//			adBean.handleClick(view);
//		}
	}
	
	private class ImageOnClickListener implements OnClickListener {
		
		private FhItemData mItemData;
		
		public ImageOnClickListener(FhItemData itemData){
			this.mItemData = itemData;	
		}

		@Override
		public void onClick(View v) {
//			Common.handleTextViewReaded(getContext(), v,
//					R.id.item_channel_item_title, true);

			ChannelItemBean bean = (ChannelItemBean) v.getTag();
			handleBeanClick(getContext(), v, bean, mItemData);

		}
	}
	
	private static void handleBeanClick(Activity context, View view, BaseChannelItemBean bean, FhItemData itemData) {
		if (bean == null) {
			return;
		}
		if (bean instanceof ChannelItemBean) {
			LocalStateDA.getInst(context).setReadStateRead(
					LocalStateDA.PREFIX_CHANNEL_ITEM,
					((ChannelItemBean) bean).getTid());
			ChannelItemBean itemBean = ((ChannelItemBean) bean);
			String articleType = itemBean.getContent_type();
			int showType = itemBean.getShow_type();

			if (!TextUtils.isEmpty(itemBean.getTz_nav()) && context instanceof MainActivity) {
				((MainActivity) context).onNav(itemBean.getTz_nav());
				return;
			}

			if (TextUtils.equals(articleType, Consts.CONTENT_TYPE_WEB)) {
				// 处理web类型文章点击
				String imgUrl = null;
				if (!TextUtils.isEmpty(itemBean.getImage_list())) {
					imgUrl = itemBean.getImage_list();
				} else if (!Common.isListEmpty(itemBean.getImage_arr())){
					imgUrl = itemBean.getImage_arr().get(0);
				}
//				AdDetailActivity.launch(context, AdDetailActivity.TYPE_ARTICLE, itemBean.getRedirecturl(), imgUrl);
			} else if (TextUtils.equals(articleType,
					Consts.CONTENT_TYPE_VIDEO)) {
				if(context != null && context instanceof MainActivity){
					((MainActivity)context).launchVideoDetailWidget(view, itemData, "");
				}else{
					VideoDetailActivity.launch(context, itemBean);
				}
			} else {
//				ArticleActivity.launch(context, itemBean);
			}

		}
	}

	@Override
	public void configPullToRefreshListView_FootLoad(PullToRefreshListView v) {
		mPull = v;
		mTwoHeader = new TwoHeader(getContext());
		mPullHeader = mTwoHeader.getPullHeader();
		mPull.getRefreshableView().addHeaderView(mTwoHeader.getLayout());
	}

	@Override
	public List<BaseChannelItemBean> onHttpSuccess(Gson gson, String data,
			ChannelBean channelBean, int tarPage) {

		ArrayList<ChannelItemBean> srcList = channelBean.getList();
		ArrayList<ChannelItemBean> slideList = new ArrayList<ChannelItemBean>();
		List<BaseChannelItemBean> list = new ArrayList<BaseChannelItemBean>();

		if (channelBean.getAll_data() != null) {
			if (channelBean.getAll_data().getSlide() != null) {
				slideList = channelBean.getAll_data().getSlide();
			}
			if (channelBean.getAll_data().getRecd_topic() != null) {
				list.addAll(channelBean.getAll_data().getRecd_topic());
			}
		}

		if (tarPage == JUrl.PAGE_START) {
			List<ChannelItemBean> oldList = ChannelItemListDA.getInst(
					getContext()).queryChannelItemList(buildChannel(),
					JUrl.PAGE_START);
			int isRecom = channelBean.getIs_recom();
			if (!Common.isListEmpty(oldList)) {
				int newCount = HomeNewContentHeader.calcuateNewCount(oldList,
						(List) list);
				newCount += HomeNewContentHeader.calcuateNewCount(
						ChannelItemListDA.getInst(getContext())
								.queryChannelItemList(buildChannelSlide(),
										JUrl.PAGE_START), slideList);
				HomeNewContentHeader.handleHttpListData(getContext(),
						mTwoHeader.getNewContentHeader(), getHandler(), newCount, mPull);
//
			}
			String oldMaxid = SPHelper.getInst().getString(
					SPHelper.PREFIX_KEY_CHANNEL_MAXID + buildChannel());
//			 String newMaxid = !Common.isListEmpty(channelBean.getItem()) ?
//			 channelBean.getItem().get(0).getAid() : null;
			String newMaxid = ChannelItemRecommendAdapter
					.getNewMaxIdNoAd((ArrayList) list);
			String maxid = Common.isTrue(isRecom)
					? oldMaxid
					: newMaxid;
			SPHelper.getInst().saveInt(
					SPHelper.PREFIX_KEY_CHANNEL_MAXPAGE + buildChannel(),
					channelBean.getTotal_page());
			SPHelper.getInst().saveInt(
					SPHelper.PREFIX_KEY_CHANNEL_IS_RECOM + buildChannel(),
					isRecom);
			SPHelper.getInst().saveString(
					SPHelper.PREFIX_KEY_CHANNEL_MAXID + buildChannel(), maxid);
			SPHelper.getInst().saveLong(
					SPHelper.PREFIX_TIME_UPDATE_CHANNEL + buildChannel(),
					System.currentTimeMillis());
			
			if ("recommend".equals(buildChannel())) {
				//保存文章最大id（用于桌面红点机制）
				SPHelper.getInst().saveString(SPHelper.BADGE_KEY_BADGE_ID, maxid);
				if (0 == isRecom) {
					refreshCount = 0;
				} else {
					refreshCount++;
				}
			}

			if (getAdapter() instanceof IIsRecom) {
				((IIsRecom) getAdapter()).setIsRecom(Common.isTrue(isRecom));
			}

			// header slide
//			if (!Common.isListEmpty(slideList)) {
				ChannelItemListDA.getInst(getContext()).setChannelAndPage(
						slideList, buildChannelSlide(), JUrl.PAGE_START);
				ChannelItemListDA.getInst(getContext()).clearChannel(
						buildChannelSlide());
				ChannelItemListDA.getInst(getContext()).insertChannelItemList(
						slideList, buildChannelSlide(), JUrl.PAGE_START);
//			}
			if (mListener != null) {
				mListener.onHttpSucess(channelBean);
			}

		}

		if (!Common.isListEmpty(list)) {
			channelItemCache((List) list, tarPage);
		}

		if (srcList != null) {
			// if(!fromMore){
			channelItemCache(srcList, tarPage);
			// }else{
			// channelMoreItemCache(srcList,tarPage);
			// }
			for (BaseChannelItemBean b : srcList) {
				list.add(b);
			}
			return list;
		}

		return list;
	}

	@Override
	public void onHttpFailure() {
		if (mListener != null) {
			mListener.onHttpFailure();
		}
	}
	@Override
	public void onInitNewsDataUI() {
		// header slide
		List<ChannelItemBean> listSlide = ChannelItemListDA.getInst(
				getContext()).queryChannelItemList(buildChannelSlide(),
				JUrl.PAGE_START);
//		setHeader(listSlide);

	}

//	private void setHeader(List<ChannelItemBean> listSlide) {
//		if (!Common.isListEmpty(listSlide)) {
//			if (Common.isTrue(SettingManager.getInst().getNoPicModel())) {
//				mPullHeader.setOrientation(LinearLayout.VERTICAL);
//				mPullHeader.removeAllViews();
//				ChannelItemRecommendAdapter adapter = new ChannelItemRecommendAdapter(
//						getContext(), listSlide, "recommend_slide");
//				for (int i = 0; i < adapter.getCount(); i++) {
//					View v = adapter.getView(i, null, null);
//					v.setTag(listSlide.get(i));
//					v.setOnClickListener(new ImageOnClickListener());
//					mPullHeader.addView(v);
//				}
//			} else {
//				if (mSlideView != null) {
//					mPullHeader.removeView(mSlideView);
//				}
//				// mSlideView = new HeaderChannelItemAdapter(getContext(),
//				// listSlide, model).getView(0, null, null);
//				mSlideView = new AutoPlayBigPicView(getContext(), listSlide,
//						new ImageOnClickListener()).InitbigPic();
//
//				mPullHeader.addView(mSlideView);
//			}
//		} else {
//			mPullHeader.removeAllViews();
//		}
//	}

	public abstract String buildChannelSlide();


	public String appendMaxid(String url, int tarPage) {
		String cidUrl = url;
		String maxid = SPHelper.getInst().getString(
				SPHelper.PREFIX_KEY_CHANNEL_MAXID + buildChannel());
		// TODO(appendMaxid maxid 为空时需要处理)
		// if(TextUtils.isEmpty(maxid)){
		// List<MChannelItemBean> list = getDataList();
		// if(!Common.isListEmpty(list)){
		// maxid = list.get(0).getAid();
		// }
		// }
		if (!TextUtils.isEmpty(maxid)) {
			// 首页时使用newid，其他页使用maxid
			String mUrl = JUrl.appendNewid(url, maxid);
			if ("recommend".equals(buildChannel()) && 3 <= refreshCount) {
				refreshCount = 0;
				mUrl = url;

			}
			cidUrl = tarPage == JUrl.PAGE_START ? mUrl : JUrl.appendMaxid(url,
					maxid);
		}
		return cidUrl;
	}

	public void setOnRecommendHttpListener(OnRecommendHttpListener listener) {
		this.mListener = listener;
	}

	public static interface OnRecommendHttpListener {
		public void onHttpSucess(ChannelBean bean);
		public void onHttpFailure();
	}

	// public class MoreOnClickListenner implements View.OnClickListener{
	//
	// @Override
	// public void onClick(View v) {
	// //get more data
	// if(!Common.hasNet()){
	// getNewsMoreByDB();
	// }else{
	// getMoreByHttp(morePage);
	// }
	//
	// }
	//
	// }
	// private void getMoreByHttp(int page){
	// getListByHttp(page);
	// }
	private void channelItemCache(List<ChannelItemBean> list, int tarPage) {
		mListServer = ChannelItemListDA.getInst(getContext());
		// 缓存除了圈子的item数据
		mListServer.setChannelAndPage(list, buildChannel(), tarPage);
		if (tarPage == JUrl.PAGE_START) {
			mListServer.clearChannel(buildChannel());
		}
		mListServer.insertChannelItemList(list, buildChannel(), tarPage);
	}
}
