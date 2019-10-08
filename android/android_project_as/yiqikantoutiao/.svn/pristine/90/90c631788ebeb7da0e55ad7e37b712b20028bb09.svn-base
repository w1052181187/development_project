package com.chengning.yiqikantoutiao.widget;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.adapter.ChannelTabAdapter;
import com.chengning.yiqikantoutiao.adapter.DragAdapter;
import com.chengning.yiqikantoutiao.adapter.DragAdapter.OnChangeListener;
import com.chengning.yiqikantoutiao.data.access.ChannelListDA;
import com.chengning.yiqikantoutiao.data.bean.ChanBean;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.SPHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class HomeEditChannelList {
	
	private Activity mActivity;
	
	private View mLayout;
	private TextView mDelete;
	private View mArrow;
	private DragGrid mMyGrid;
	private GridView mOtherGrid;
	
	private DragAdapter mDragAdapter;
	private ChannelTabAdapter mOtherAdapter;
	
	private boolean mIsEdit = false;
	private boolean mIsChanged = false;
	
	private OnChannelListChangeListener mListener;
	
	private List<ChanBean> mOtherList;

	protected int mListSize;
	protected int oListSize;
	
	public HomeEditChannelList(Activity activity, OnChannelListChangeListener listener){
		this.mActivity = activity;
		this.mListener = listener;
		mLayout = activity.findViewById(R.id.home_edit_channel_list);
		
		mDelete = (TextView) mLayout.findViewById(R.id.home_edit_channel_list_delete);
		mArrow = mLayout.findViewById(R.id.home_edit_channel_list_arrow);
		mMyGrid = (DragGrid) mLayout.findViewById(R.id.home_edit_channel_list_my_grid);
		mOtherGrid = (GridView) mLayout.findViewById(R.id.home_edit_channel_list_other_grid);
	}
	
	private List<ChanBean> handleOtherList(List<ChanBean> allList, List<ChanBean> mList, List<ChanBean> oList){
		if(oList == null){
			oList = new ArrayList<ChanBean>();
		}else{
			oList.clear();
		}
		
		HashSet<String> mSet = new HashSet<String>();
		for(ChanBean b : mList){
			mSet.add(b.getId());
		}
		for(ChanBean b : allList){
			if(!mSet.contains(b.getId())){
				oList.add(b);
			}
		}
		if(oList.size() > 0 && oList.get(0).getLocal_other_index() == -1){
			Collections.sort(oList, new Comparator<ChanBean>() {

				@Override
				public int compare(ChanBean lhs, ChanBean rhs) {
					int r = lhs.getLocal_other_index() - rhs.getLocal_other_index();
					if(r == 0){
						r = lhs.getId().compareTo(rhs.getId());
					}
					return 0;
				}
			});
		}
		return oList;
	}
	
	public void init(){
		mIsChanged = false;
		final List<ChanBean> mList = ChannelListDA.getInst(mActivity).queryMyChannelList();
		final List<ChanBean> allList = ChannelListDA.getInst(mActivity).queryOtherChannelList();
		mOtherList = handleOtherList(allList, mList, mOtherList);
		mDragAdapter = new DragAdapter(mActivity, mList);
		mDragAdapter.setDeleteOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIsChanged = true;
				int position = (Integer) v.getTag();
				ChanBean bean = mDragAdapter.getItem(position);
				
				if(!Common.isListEmpty(mOtherList)){
					bean.setLocal_my_index(-1);
				}else{
					bean.setLocal_my_index(-1);
				}
				ChannelListDA.getInst(mActivity).updateTarget(bean);
				
				mList.remove(bean);
				mOtherList = handleOtherList(allList, mList, mOtherList);
				mDragAdapter.notifyDataSetChanged();
				mOtherAdapter.notifyDataSetChanged();
			}
		});
		mDragAdapter.setOnChangeListener(new OnChangeListener() {
			
			@Override
			public void onChange(int dragPosition, int dropPosition) {
				mIsChanged = true;
				int mIndex = 1;
				for(ChanBean b : mList){
					b.setLocal_my_index(mIndex);
					mIndex++;
				}
				saveUserNavListTimeLocal(System.currentTimeMillis(),mList);
				ChannelListDA.getInst(mActivity).updateList(mList);
				
			}
		});
		mMyGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				dismiss();
				mListener.onClick(position);
			}
		});
		mMyGrid.setAdapter(mDragAdapter);
		
		mOtherAdapter = new ChannelTabAdapter(mActivity, mOtherList);
		mOtherGrid.setAdapter(mOtherAdapter);
		mOtherGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				mIsChanged = true;
				ChanBean bean = (ChanBean) mOtherAdapter.getItem(position);
				if(!Common.isListEmpty(mList)){
					bean.setLocal_my_index(mList.get(mList.size() - 1).getLocal_my_index() + 1);
				}else{
					bean.setLocal_my_index(1);
				}
				ChannelListDA.getInst(mActivity).updateTarget(bean);
				
				mList.add(bean);
				mOtherList = handleOtherList(allList, mList, mOtherList);
				mDragAdapter.notifyDataSetChanged();
				mOtherAdapter.notifyDataSetChanged();
				saveUserNavListTimeLocal(System.currentTimeMillis(),mList);
			}
		});
		
		mArrow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		mDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIsEdit = !mIsEdit;
				mDelete.setText(mIsEdit ? "完成" : "排序删除");
				
				mDragAdapter.setIsEditMode(mIsEdit);
				mDragAdapter.notifyDataSetChanged();
				if(!mIsEdit){
					// TODO(操作中也需要保存)
					List<ChanBean> mList = mDragAdapter.getChannnelLst();
					mListSize = mList.size();
					for(int i = 0; i < mListSize; i++){
						ChanBean b = mList.get(i);
						b.setLocal_my_index(i + 1);
					}
					List<ChanBean> oList = mOtherAdapter.getList();
					oListSize = oList.size();
					for(int i = 0; i < oListSize; i++){
						ChanBean b = oList.get(i);
						b.setLocal_my_index(-1);
					}
					ArrayList<ChanBean> list = new ArrayList<ChanBean>();
					list.addAll(mList);
					list.addAll(oList);
					saveUserNavListTimeLocal(System.currentTimeMillis(),mList);
					ChannelListDA.getInst(mActivity).updateList(list);
				}
			}
		});
	}
	
	public void show(){
		mLayout.setVisibility(View.VISIBLE);
	}
	
	public void dismiss(){
		mLayout.setVisibility(View.GONE);
		if(mIsEdit){
			mIsEdit = false;
			mDelete.setText("排序删除");
			
			mDragAdapter.setIsEditMode(mIsEdit);
			mDragAdapter.notifyDataSetChanged();
		}
		if(mIsChanged){
			mListener.onChange(mDragAdapter.getChannnelLst());
		}
	}
	
	public static void saveUserNavListTimeLocal(long time, List<ChanBean> mList){
		SPHelper.getInst().saveLong(SPHelper.KEY_USER_NAV_LIST_TIME_LOCAL, time);
		SPHelper.getInst().saveString(SPHelper.KEY_USER_ID_LOCAL,  App.getInst().getUserInfoBean().getUid());
		if (!App.getInst().isLogin()) {
			SPHelper.getInst().saveString(SPHelper.KEY_USER_NAV_LOCAL, "");
		}else {
			StringBuffer myLocalNav = new StringBuffer();
			int size = mList.size();
			for (int i = 1; i < size;i++){
				myLocalNav.append(mList.get(i).getId());
				if (size - 1 != i) {
					myLocalNav.append(",");
				}
			} 
			SPHelper.getInst().saveString(SPHelper.KEY_USER_NAV_LOCAL, myLocalNav.toString());
		}
		
	}
	
	public boolean isShowing(){
		return mLayout.getVisibility() == View.VISIBLE;
	}
	
	public static interface OnChannelListChangeListener{
		public void onChange();
		public void onChange(List mList);
		public void onClick(int position);
	}
	
}
