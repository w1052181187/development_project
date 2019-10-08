package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.subscribe.PrivateInfoDesignBean;
import com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoDesignActivity;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-27.
 */

public class PrivateAeraAdapter extends RecyclerView.Adapter<PrivateAeraAdapter.PrivateViewHolder> {

    private Context context;

    private List<PrivateInfoDesignBean.BaseData> dataList;

    private List<PrivateInfoDesignBean.BaseData> selectTargetList;

    private PrivateInfoDesignActivity activity;
    public PrivateAeraAdapter(Context context, List<PrivateInfoDesignBean.BaseData> dataList, List<PrivateInfoDesignBean.BaseData> selectTargetList) {
        this.context = context;
        this.dataList = dataList;
        this.selectTargetList = selectTargetList;
        this.activity = (PrivateInfoDesignActivity) context;
    }

    @Override
    public PrivateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_area, parent, false);
        PrivateViewHolder privateViewHolder = new PrivateViewHolder(view);
        return privateViewHolder;
    }

    @Override
    public void onBindViewHolder(PrivateViewHolder holder, final int position) {
        holder.areaTextview.setText(dataList.get(position).getName());
        final int index = selectTargetList.indexOf(dataList.get(position));
        if (index == -1) {
            holder.areaTextview.setSelected(false);
        } else {
            holder.areaTextview.setSelected(true);
        }
        holder.areaTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == -1){
                    if(selectTargetList.size()>=5){
                        ToastUtils.showShort(context.getResources().getString(R.string.string_private_info_design_max_hint));
                        return;
                    }
                    selectTargetList.add(dataList.get(position));
                }else{
                    selectTargetList.remove(dataList.get(position));
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
        @BindView(R.id.areaTextview)
        TextView areaTextview;

        public PrivateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
