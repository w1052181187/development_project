package com.shenyuan.militarynews.adapter;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.base.util.GlideHelper;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const.ArticleCommentType;
import com.shenyuan.militarynews.Const.LikeAction;
import com.shenyuan.militarynews.LoginManager;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.beans.data.CommentItemBean;
import com.shenyuan.militarynews.beans.data.CommentListBean;
import com.shenyuan.militarynews.beans.data.LoginDbBean;
import com.shenyuan.militarynews.utils.ArticleManagerUtils;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.CommentItemOnClickListener;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.ItemState;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.LikeState;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.MoreReplysListener;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.MoreState;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.Utils;
import com.shenyuan.militarynews.views.EmojiTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleCommentItemAdapter extends BaseTypeListAdapter {
	
	private HashMap<CommentItemBean, ItemState> mStates = new HashMap<CommentItemBean, ItemState>();
	private String mAid;
	private LoginDbBean mUserBean;
	
	public ArticleCommentItemAdapter(Activity activity, List list, String aid) {
		super(activity, list);
		this.mAid = aid;
		if (App.getInst().isLogin()) {
			mUserBean = LoginManager.getInst().getLoginDbBean();
		}
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_article_comment;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		
	}
	
	public ItemState getItemState(CommentItemBean bean){
		if(mStates.containsKey(bean)){
			return mStates.get(bean);
		}else{
			ItemState state = new ItemState();
			mStates.put(bean, state);
			return state;
		}
	}
	
	public static enum ChannelItemType {
		/**
		 * OneSmall，1个小图，默认type，type必须从0开始
		 */
		Common(0, ArticleCommentType.COMMENT_TYPE_COMMON),	// 文章评论普通
		CommonHot(1, ArticleCommentType.COMMENT_TYPE_COMMON_HOT),	// 文章评论热门
		CommonNewest(2, ArticleCommentType.COMMENT_TYPE_COMMON_NEWEST),	// 文章评论最新
		;
		
		private int type;
		private int showType[];
		
		private ChannelItemType(int type, int... showType){
			this.type = type;
			this.showType = showType;
		}
		
		public int getType(){
			return type;
		}
		
		public int[] getShowType(){
			return showType;
		}
		
		public static ChannelItemType parseType(int type){
			ChannelItemType it = ChannelItemType.Common;
			for(ChannelItemType c : values()){
				if(c.type == type){
					it = c;
					break;
				}
			}
			return it;
		}
		
		public static ChannelItemType parseShowType(int showType){
			ChannelItemType it = ChannelItemType.Common;
			for(ChannelItemType c : values()){
				for(int i : c.showType){
					if(i == showType){
						it = c;
						break;
					}
				}
			}
			return it;
		}
	}

	@Override
	public int getItemViewType(int position) {
		CommentItemBean bean = (CommentItemBean) getItem(position);
		return ChannelItemType.parseShowType(bean.getShow_type()).getType();
	}

	@Override
	public int getViewTypeCount() {
		return ChannelItemType.values().length;
	}
	@Override
	public View buildLayoutView(int position, int type) {
		View view = null;
		ChannelItemType ct = ChannelItemType.parseType(type);
		switch (ct) {
		default:
		case Common:
			view = View.inflate(getContext(), R.layout.item_article_comment, null);
			break;
		case CommonHot:
			view = View.inflate(getContext(), R.layout.item_article_comment_hot, null);
			break;
		case CommonNewest:
			view = View.inflate(getContext(), R.layout.item_article_comment_newest, null);
			break;
		}
		return view;
	}

	@Override
	public int buildLayoutId(int position, int type) {
		return -1;
	}

	@Override
	public void handleLayout(View convertView, final int position, Object obj,
			int type) {
		ImageView image = BaseViewHolder.get(convertView, R.id.article_comment_item_image);
		TextView name = BaseViewHolder.get(convertView, R.id.article_comment_item_name);
		TextView city = BaseViewHolder.get(convertView, R.id.article_comment_item_city);
		TextView time = BaseViewHolder.get(convertView,R.id.article_comment_item_time);
		EmojiTextView content = BaseViewHolder.get(convertView, R.id.article_comment_item_content);
		ImageView iVip = BaseViewHolder.get(convertView, R.id.article_comment_item_vip);
		final LinearLayout listLayoutL = BaseViewHolder.get(convertView, R.id.article_comment_item_content_list_layout);
		final LinearLayout listLayout = BaseViewHolder.get(convertView, R.id.article_comment_item_content_list);
		final TextView more = BaseViewHolder.get(convertView, R.id.article_comment_item_list_more);
		final TextView like = BaseViewHolder.get(convertView, R.id.article_comment_item_zan);
		TextView role = BaseViewHolder.get(convertView, R.id.article_comment_item_role);
		
		final CommentItemBean bean = (CommentItemBean) obj;
		if(!TextUtils.isEmpty(bean.getFace())){
			image.setVisibility(View.VISIBLE);
			Utils.setCircleImage(getContext(), bean.getFace(), image);
			
			if (Common.isTrue(SettingManager.getInst().getNightModel())) {
				image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
			}
		}else{
			GlideHelper.getInst().clear(getContext(), image);
			image.setVisibility(View.INVISIBLE);
		}
		if(!TextUtils.isEmpty(bean.getRewards_img())){
			iVip.setVisibility(View.VISIBLE);
			GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getRewards_img(), iVip);
		}else{
			GlideHelper.getInst().clear(getContext(), iVip);
			iVip.setVisibility(View.GONE);
		}
		String username = bean.getUsername();
		name.setText((TextUtils.isEmpty(username) ? "新军事网友" : username));
		city.setText(bean.getIp());
		try {
			time.setText(Common.formatTimeHoursMinutesBefore(Common.formatYYMMDDHHMMSStoLong(bean.getDtime())));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		content.setText(bean.getMsg());
		name.setTag(bean);
		String good = bean.getGood();
		like.setText(TextUtils.isEmpty(good) ? "0" : good);
		like.setTag(bean);
		
		LikeState mLikeState = new ArticleManagerUtils.LikeState(){

			@Override
			public void success(String data) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(data);
					like.setText(jsonObject.optJSONObject("list").optString("data"));
					like.setEnabled(true);
				} catch (JSONException e) {
					like.setEnabled(false);
				}
			}
			
			@Override
			public void init() {
				like.setEnabled(false);
			}
			
			@Override
			public void failure() {
				like.setEnabled(true);
			}
			
		};
		final ArticleManagerUtils mArticleManagerUtils = new ArticleManagerUtils();
		mArticleManagerUtils.setState(mLikeState);
		like.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommentItemBean bean = (CommentItemBean) v.getTag();
				mArticleManagerUtils.doCommentLikeByHttp(getContext(), bean.getId(), LikeAction.GOOD);
			}
		});

		Common.handleRole(role, bean.getHonor());
		boolean isDel = App.getInst().isLogin() && mUserBean != null && TextUtils.equals(bean.getMid(), mUserBean.getUserinfo().getUid());
		convertView.setOnClickListener(new CommentItemOnClickListener(getContext(), bean, mLikeState, !isDel ? null : new Runnable() {
			
			@Override
			public void run() {
				updateList(position);
			}
		}));
		
		// (ArticleCommentItemAdapter listView)
		if (Common.isTrue(SettingManager.getInst().getNightModel())) {
			listLayoutL.getBackground().setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
		}
//		final ArrayList<CommentItemBean> list = bean.getParents_list();
		final ArrayList<CommentItemBean> cList = new ArrayList<CommentItemBean>();
		if(!Common.isListEmpty(bean.getList())){
			cList.addAll(bean.getList());
		}
		
		listLayout.removeAllViews();
		if(Common.isListEmpty(cList)){
			listLayoutL.setVisibility(View.GONE);
		}else{
			listLayoutL.setVisibility(View.VISIBLE);
			
			final int replyCount = bean.getReplys();
			ItemState state = getItemState(bean);
			
			final ArticleCommentItemParentAdapter pAdapter = new ArticleCommentItemParentAdapter(
					getContext(), cList, mAid);
			
			ArticleManagerUtils.addReplyView(0, pAdapter, listLayout, cList, bean);
			
			// 点击更多回复
			ArticleManagerUtils.updateViewMoreState(getItemState(bean), more, replyCount, pAdapter);
			ArticleManagerUtils.updateViewMore(getItemState(bean), more, listLayout);
			more.setOnClickListener(new OnClickListener() {

				private int mTarPage = JUrl.PAGE_START;

				@Override
				public void onClick(View v) {
					ItemState iState = getItemState(bean);
					switch (iState.moreState) {
					case None:
						
						break;
					case Expand:
						iState.moreState = MoreState.Pack;
						ArticleManagerUtils.updateViewMore(getItemState(bean), more, listLayout);
						break;
					case Pack:
						iState.moreState = MoreState.Expand;
						ArticleManagerUtils.updateViewMore(getItemState(bean), more, listLayout);
						break;
					case More:
						ArticleManagerUtils.handleMoreReplys(getContext(), bean, mTarPage, new MoreReplysListener() {

							@Override
							public void moreReplysSuccess(CommentListBean cLBean) {
								cList.addAll(cLBean.getList());
								final int last = listLayout.getChildCount();
								final ArticleCommentItemParentAdapter adapter = new ArticleCommentItemParentAdapter(
										getContext(), cList, mAid);
								ArticleManagerUtils.addReplyView(last, adapter, listLayout, cList, bean);
								ArticleManagerUtils.updateViewMoreState(getItemState(bean), more, replyCount, adapter);
								ArticleManagerUtils.updateViewMore(getItemState(bean), more, listLayout);
								
								if (cLBean.getMaxpage() <= mTarPage) {
									ItemState miState = getItemState(bean);
									miState.moreState = MoreState.Pack;
									ArticleManagerUtils.updateViewMore(getItemState(bean), more, listLayout);
									return;
								} 
								mTarPage++;
							}
						});
						break;
					}
				}
			});
		}
	}
	
	/**
	 * 删除后更新列表
	 * @param position
	 */
	protected void updateList(int position) {
		List list = getList();
		if (list.size() > (position + 1)) {
			int type = ((CommentItemBean)list.get(position)).getShow_type();
			CommentItemBean nextBean = ((CommentItemBean)list.get(position + 1));
			nextBean.setShow_type(type);
		}
		list.remove(position);
		setList(list);
		notifyDataSetChanged();
	}

}
