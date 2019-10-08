package com.chengning.fenghuo.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.chengning.fenghuo.Consts.UserVip;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.R;

public class AtSuggestExpandableListAdapter extends BaseExpandableListAdapter  implements
		SectionIndexer {

	private static final String SECTION_RECENT = "*";
	private static final String SECTION_RECENT_CHINESE = "最近联系人";
	private static final String SECTIONS = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String mSections = "";

	private List<UserInfoBean> mItemList;
	private List<UserInfoBean> mRecentList;
	private LayoutInflater mInflater;
	private OnClickListener mClick;
	private boolean mHasHeader;
	
	private Context mContext;

	private List<String> mGroups;
	private List<List<UserInfoBean>> mChilds;
	private List<Integer> mGroupPositions;

	public AtSuggestExpandableListAdapter(Context context, List<UserInfoBean> list,
			OnClickListener click, boolean hasHeader, List<UserInfoBean> recentList) {
		this.mInflater = LayoutInflater.from(context);
		mItemList = list;
		mContext = context;
		mClick = click;
		mHasHeader = hasHeader;
		mRecentList = recentList;
		
		updateData(list);
	}
	
	public void updateData(List<UserInfoBean> list){
		mItemList = list;
		mGroups = new ArrayList<String>();
		mChilds = new ArrayList<List<UserInfoBean>>();
		mGroupPositions = new ArrayList<Integer>();

		if(mItemList.size() == 0){
			return;
		}
		// 转换拼音
		for(UserInfoBean bean : mItemList){
			if(!TextUtils.isEmpty(bean.getNickname_pinyin())){
				continue;
			}
			String nickname = bean.getNickname();
			if(!TextUtils.isEmpty(nickname)){
				String pinyin = pinyin(nickname);
				bean.setNickname_pinyin(pinyin != null ? pinyin.toUpperCase() : nickname.toUpperCase());
			}
		}
		
		// 排序
		Collections.sort(list, new UserInfoBeanComparator());
		
		// 初始分组名
		char[] sections = SECTIONS.toCharArray();
		int sectionsLength = sections.length;
		int sectionsIndex = 0;
		
		// 分组：遍历一遍，分配group对应的child
		int dataSize = mItemList.size();
		int dataIndex = 0;
		int preDataIndex = 0;
		for(int i = dataIndex; i < dataSize; i++){
			char firstChar = mItemList.get(i).getNickname_pinyin().charAt(0);
			for(int j = sectionsIndex + 1; j < sectionsLength; j++){
				if(sections[j] == firstChar){
					if((i - dataIndex) == 0){
						sectionsIndex = j;
						continue;
					}else{
						mGroups.add(String.valueOf(sections[sectionsIndex]));
						mChilds.add(mItemList.subList(dataIndex, i));
						preDataIndex = dataIndex;
						dataIndex = i;
						sectionsIndex = j;
					}
					break;
				}
			}
			if(i == (dataSize - 1)){
				mGroups.add(String.valueOf(sections[sectionsIndex]));
				mChilds.add(mItemList.subList(dataIndex, dataSize));
				preDataIndex = dataIndex;
				dataIndex = i;
			}
		}
		if(dataIndex < (dataSize - 1)){
			if(mGroups.size() == 0){
				mGroups.add(String.valueOf(sections[0]));
				mChilds.add(new ArrayList<UserInfoBean>());
			}
			int childsSize = mChilds.size() - 1;
			mChilds.remove(childsSize);
			mChilds.add(mItemList.subList(preDataIndex, dataSize));
		}
		
		// 最近联系人
		if(mRecentList != null && mRecentList.size() > 0){
			mGroups.add(0, SECTION_RECENT_CHINESE);
			mChilds.add(0, mRecentList);
		}
		
		// 计算group位置
		int groupsSize = mGroups.size();
		for(int i = 0; i < groupsSize; i++){
			if(i == 0){
				mGroupPositions.add(0);
			}else{
				int position = mGroupPositions.get(i - 1) + mChilds.get(i - 1).size() + 1;
				mGroupPositions.add(position);
			}
		}

		// 最终分组名
		StringBuilder stringBuilder = new StringBuilder();
		for(String str : mGroups){
			if(SECTION_RECENT_CHINESE.equals(str)){
				stringBuilder.append(SECTION_RECENT);
			}else{
				stringBuilder.append(str);
			}
		}
		mSections = stringBuilder.toString();
	}
	
	public void updateBySearchKey(String key){
		key = key.toUpperCase();
		List<UserInfoBean> tempChilds = new ArrayList<UserInfoBean>();
		for (UserInfoBean b : mItemList) {
			if(b.getNickname_pinyin().contains(key)){
				tempChilds.add(b);				
			}else if(b.getNickname().contains(key)){
				tempChilds.add(b);
			}
		}
		mGroups.removeAll(mGroups);
		mChilds.removeAll(mChilds);
		mGroups.add(0, "所有联系人");
		mChilds.add(0, tempChilds);
		
		// 最终分组名
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("所有联系人");
		mSections = stringBuilder.toString();
		
	}

	@Override
	public int getPositionForSection(int section) {
		return mGroupPositions.get(section);
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++)
			sections[i] = String.valueOf(mSections.charAt(i));
		return sections;
	}

	/**
	 * 字符的拼音，多音字就得到第一个拼音。不是汉字，就return null。
	 */
	public String pinyin(char c) {
		String[] pinyins = null;
		try {
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			pinyins = PinyinHelper.toHanyuPinyinStringArray(c, format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pinyins == null) {
			return null;
		}
		return pinyins[0];
	}
	
	public String pinyin(String str) {
		String pinyin = null;
		try {
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			pinyin = PinyinHelper.toHanyuPinyinString(str, format, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pinyin == null) {
			return null;
		}
		return pinyin;
	}

	private class UserInfoBeanComparator implements Comparator<UserInfoBean> {

		public int compare(UserInfoBean u1, UserInfoBean u2) {
			String o1 = u1.getNickname_pinyin();
			String o2 = u2.getNickname_pinyin();
			
			for (int i = 0; i < o1.length() && i < o2.length(); i++) {

				int codePoint1 = o1.charAt(i);
				int codePoint2 = o2.charAt(i);

				if (Character.isSupplementaryCodePoint(codePoint1)
						|| Character.isSupplementaryCodePoint(codePoint2)) {
					i++;
				}

				if (codePoint1 != codePoint2) {
					if (Character.isSupplementaryCodePoint(codePoint1)
							|| Character.isSupplementaryCodePoint(codePoint2)) {
						return codePoint1 - codePoint2;
					}

					String pinyin1 = pinyin((char) codePoint1);
					String pinyin2 = pinyin((char) codePoint2);

					if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
						if (!pinyin1.equals(pinyin2)) {
							return pinyin1.compareTo(pinyin2);
						}
					} else {
						return codePoint1 - codePoint2;
					}
				}
			}
			return o1.length() - o2.length();
		}
	}

	@Override
	public int getGroupCount() {
		return mGroups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChilds.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mChilds.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_at_suggest_group, null);
		}
		TextView title = (TextView) convertView.findViewById(R.id.title);
		title.setText((String)getGroup(groupPosition));
		return convertView;
	}

	public class ChildViewHolder {
		ImageView image;
		ImageView userVipImage;
		TextView name;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_at_suggest, null);
			holder = new ChildViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.user_image);
			holder.userVipImage = (ImageView) convertView.findViewById(R.id.user_vip_image);
			holder.name = (TextView) convertView.findViewById(R.id.user_name);

			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}

		UserInfoBean bean = (UserInfoBean) getChild(groupPosition, childPosition);
		if (bean != null) {
			Utils.showFace(bean.getFace(), holder.image);
			holder.name.setText(bean.getNickname());
			UserVip.showVip(bean.getVip_pic(), holder.userVipImage);

			holder.name.setTag(bean);
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
