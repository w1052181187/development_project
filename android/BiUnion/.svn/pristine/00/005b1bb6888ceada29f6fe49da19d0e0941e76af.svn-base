package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.subscribe.PrivateInfoDesignBean;
import com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoDesignActivity;
import com.bibinet.biunion.project.ui.dialog.PrivateInfoDesignSelectDialog;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-27.
 */

public class PrivateInfoDesignTxtAdapter extends RecyclerView.Adapter<PrivateInfoDesignTxtAdapter.PrivateViewHolder> {

    private Context context;

    //数据
    private List<PrivateInfoDesignBean.BaseData> dataList;

    //选择的数据
    private List<PrivateInfoDesignBean.BaseData> selectDataList;

    private PrivateInfoDesignActivity activity;
    public PrivateInfoDesignTxtAdapter(RecyclerView recyclerView, Context context, List<PrivateInfoDesignBean.BaseData> dataList, List<PrivateInfoDesignBean.BaseData> selectDataList) {
        this.context = context;
        this.dataList = dataList;
        this.selectDataList = selectDataList;
        this.activity = (PrivateInfoDesignActivity) context;
        init(recyclerView);
    }

    private void init(RecyclerView recyclerView){
        //设置适应性
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                if (dataList.size() > 0) {
                    View firstChildView = LayoutInflater.from(context).inflate(R.layout.view_private_info_design_txt_tag, null, false);
                    int intw=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int inth=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    firstChildView.measure(intw, inth);
                    int size = dataList.size()/2 + dataList.size()%2;
                    Log.e("height", "-"+size);
                    setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), firstChildView.getMeasuredHeight() * size);
                } else {
                    Log.e("height", "-wu");
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        });
    }

    @Override
    public PrivateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_private_info_design_txt_tag, parent, false);
        PrivateViewHolder privateViewHolder = new PrivateViewHolder(view);
        return privateViewHolder;
    }

    @Override
    public void onBindViewHolder(PrivateViewHolder holder, final int position) {
        //根据数据进行操作
        PrivateInfoDesignBean.BaseData b = dataList.get(position);
        holder.mainTV.setText(b.getName());
        final int index = selectDataList.indexOf(dataList.get(position));
        if(index == -1){
            holder.mainTV.setSelected(false);
        }else{
            holder.mainTV.setSelected(true);
        }

        holder.mainTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index == -1){
                    //保证添加一个 稍后会修改整体逻辑 会有一个值控制
                    selectDataList.clear();
                    selectDataList.add(dataList.get(position));
                }else{
                    selectDataList.remove(dataList.get(position));
                }
                activity.onDataChange();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Common.isListEmpty(dataList) ? 0 : dataList.size();
    }

    class PrivateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.v_private_info_design_txt_tag_main)
        TextView mainTV;

        public PrivateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
