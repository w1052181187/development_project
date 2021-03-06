package com.chengning.fenghuo.adapter;


import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.GridImageItemBean;

public class DiscussImageGridAdapter extends BaseAdapter { 
	private Context ctx;
	private LayoutInflater mInflater;
	private List<GridImageItemBean> mItems; 
	private TextView mPhotoNumTv;

	public DiscussImageGridAdapter() {
		// TODO Auto-generated constructor stub
	}

	public DiscussImageGridAdapter(Context context,List<GridImageItemBean> list, TextView numTv) {
		this.ctx = context; 
		mInflater = LayoutInflater.from(context);
		mItems = list; 
		mPhotoNumTv = numTv;
	}
	
	public void setItems(List<GridImageItemBean> list)
	{
		mItems = list; 
		updateNumTv();
	}

	@Override
	public int getCount() {
		if(mItems.size() == 6)
			return mItems.size();
		return mItems.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	} 

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
	    if (convertView == null)
	    { 
			convertView = mInflater.inflate(R.layout.item_discuss_grid, null); 
	    	viewHolder = new ViewHolder();
			viewHolder.img = (ImageView) convertView.findViewById(R.id.item_discuss_grid_image);
            viewHolder.close = (ImageButton) convertView.findViewById(R.id.item_discuss_grid_close);
			convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
        viewHolder.close.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View v) {
				GridImageItemBean bean = (GridImageItemBean) v.getTag();
				File f = new File(bean.path);
				if(f.exists())
				{
					f.delete();
				}   
				mItems.remove(bean);
				updateNumTv();
				notifyDataSetChanged();
			} 
        });  
        
		if(mItems.size() == position){
			viewHolder.close.setVisibility(View.GONE);
			viewHolder.img.setImageResource(R.drawable.add_img_icon);
		}else{
			GridImageItemBean entity = mItems.get(position); 
		    viewHolder.img.setImageBitmap(entity.bitmap); 
		    viewHolder.img.setTag(entity.path);
		    viewHolder.close.setTag(entity);
		    viewHolder.close.setVisibility(View.VISIBLE);
		}

	    convertView.setTag(viewHolder); 
	    return convertView;
	}
	
	private void updateNumTv(){
		if(mItems.size() == 0){
			mPhotoNumTv.setVisibility(View.GONE);
		}else{
			mPhotoNumTv.setVisibility(View.VISIBLE);
			mPhotoNumTv.setText(mItems.size() + "");
		}
	}
	
	static class ViewHolder { 
        public ImageView img;
        public ImageButton close;
    }

}
