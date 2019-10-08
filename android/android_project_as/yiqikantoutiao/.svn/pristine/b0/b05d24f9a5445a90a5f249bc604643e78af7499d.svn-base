package com.chengning.yiqikantoutiao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseViewHolder;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.data.bean.ChanBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class DragAdapter extends BaseAdapter {
	/** TAG*/
	private final static String TAG = "DragAdapter";
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	private Context context;
	/** 控制的postion */
	private int holdPosition;
	/** 是否改变 */
	private boolean isChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的频道列表） */
	public List<ChanBean> channelList;
	/** TextView 频道内容 */
	private TextView item_text;
	/** 要删除的position */
	public int remove_position = -1;
	
	private boolean isEditMode = false;
	private OnClickListener deleteClick;
	private OnChangeListener changeListener;

	public DragAdapter(Context context, List<ChanBean> channelList) {
		this.context = context;
		this.channelList = channelList;
	}
	
	public void setIsEditMode(boolean isEditMode){
		this.isEditMode = isEditMode;
	}
	
	public void setDeleteOnClickListener(OnClickListener listener){
		this.deleteClick = listener;
	}
	
	public void setOnChangeListener(OnChangeListener listener){
		this.changeListener = listener;
	}
	
	@Override
	public int getCount() {
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ChanBean getItem(int position) {
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_channel_tab, null);

		ImageView delete = BaseViewHolder.get(convertView, R.id.item_channel_tab_delete);
		TextView item_text = BaseViewHolder.get(convertView, R.id.item_channel_tab_name);
		
		ChanBean bean = (ChanBean) getItem(position);
		delete.setOnClickListener(deleteClick);
		delete.setTag(position);

		item_text.setText(bean.getName());
		item_text.setTag(bean);
		
		if (position == 0) {
			delete.setVisibility(View.INVISIBLE);
//			item_text.setBackgroundResource(R.color.common_gray);
//			item_text.setTextColor(context.getResources().getColor(R.color.white));
		} else {
			delete.setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
//			item_text.setBackgroundResource(R.color.transparent);
//			item_text.setTextColor(context.getResources().getColor(R.color.channel_tab_text));
		}
		
		if (isChanged && (position == holdPosition) && !isItemShow) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (position == -1 + channelList.size())) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
		}
		if(remove_position == position){
			item_text.setText("");
		}
		return convertView;
	}

	/** 添加频道列表 */
	public void addItem(ChanBean channel) {
		channelList.add(channel);
		notifyDataSetChanged();
	}

	/** 拖动变更频道排序 */
	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		ChanBean dragItem = getItem(dragPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		if (changeListener != null) {
			changeListener.onChange(dragPostion, dropPostion);
		}
		notifyDataSetChanged();
	}
	
	/** 获取频道列表 */
	public List<ChanBean> getChannnelLst() {
		return channelList;
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		remove();
		notifyDataSetChanged();
	}

	/** 删除频道列表 */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		notifyDataSetChanged();
	}
	
	/** 设置频道列表 */
	public void setListDate(List<ChanBean> list) {
		channelList = list;
	}
	
	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}
	
	public static interface OnChangeListener{
		public void onChange(int dragPosition, int dropPosition);
	}
}