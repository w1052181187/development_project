package com.shenyuan.militarynews.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chengning.common.base.BaseDialogFragment;
import com.shenyuan.militarynews.R;

public class SettingFontDialog extends BaseDialogFragment {

	private View mView;
	private ListView mListView;
	
	private SettingFontDialogItemClick mListener;
	private String[] strs;
	private ArrayAdapter<String> adapter;
	private View mEmpty;

	public void setData(SettingFontDialogItemClick mListener, String[] strs) {
		this.mListener = mListener;
		this.strs = strs;
	}

	@Override
	public void initData() {
		adapter = new ArrayAdapter<String>(getContext(), R.layout.item_setting_font_dialog, strs);
		mListView.setAdapter(adapter);
	}
	
	@Override
	public void initView() {
		mEmpty = mView.findViewById(R.id.dialog_setting_font_empty);
		mListView = (ListView) mView.findViewById(R.id.dialog_setting_font_listView);
	}
	
	@Override
	public void initListener() {
		
		mEmpty.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mListener.onItemClick(position);
				dismissAllowingStateLoss();
			}
		});
	}

	public static interface SettingFontDialogItemClick{
		void onItemClick(int position);
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_setting_font, null);
		return mView;
	}

}
