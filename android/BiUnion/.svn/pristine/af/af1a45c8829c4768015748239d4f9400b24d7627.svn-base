package com.bibinet.biunion.project.ui.expand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.dao.MediaMusicData;
import com.bibinet.biunion.project.models.MediaMusicDataModel;

/**
 * Created by bibinet on 2017-1-6.
 */
public abstract class PageActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //初始状态
    public static final int LOADING_INIT = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //可以下拉加载更多
    public static final int LOAD_ACCESS = 2;
    //没有数据时
    public static final int LOAD_NODATA=3;
    private Context context;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;
    private int TYPE_FOOTER = 1;

    public PageActivityAdapter(Context context) {
        this.context = context;
    }

    public int getCurrentState(){
        return load_more_status;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(context).inflate(R.layout.adapter_bottom_load, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return onChildCreateViewHolder(parent, viewType);
    }

    protected abstract RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                case LOADING_INIT:
                    footViewHolder.textshow.setVisibility(View.GONE);
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    break;
                case LOADING_MORE:
                    footViewHolder.textshow.setText("正在加载...");
                    footViewHolder.textshow.setVisibility(View.VISIBLE);
                    footViewHolder.progressBar.setVisibility(View.VISIBLE);
                    break;
                case LOAD_NODATA:
                    footViewHolder.textshow.setText("暂无更多数据");
                    footViewHolder.textshow.setVisibility(View.GONE);
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    break;
                case LOAD_ACCESS:
                    footViewHolder.textshow.setText("正在加载...");
                    footViewHolder.textshow.setVisibility(View.VISIBLE);
                    footViewHolder.progressBar.setVisibility(View.VISIBLE);
                    break;
            }
        }else{
            onChildBindViewHolder(holder, position);
        }
    }

    protected abstract void onChildBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.e("parent", "parent");
        int count =  getChildCount();
        return count > 0 ? count + 1 : 0;
    }

    @Override
    public final int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        return getChildItemViewType(position);
    }

    protected abstract int getChildItemViewType(int position);

    protected abstract int getChildCount();

    private class FootViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public TextView textshow;

        public FootViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadMore);
            textshow = (TextView) itemView.findViewById(R.id.textshow);
        }
    }
}
