package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.KeyValueBean;
import com.bibinet.biunion.project.bean.TypeFilterBean;
import com.bibinet.biunion.project.utils.Common;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bibinet on 2017-6-15.
 */

public class MoreItemAdapter extends RecyclerView.Adapter<MoreItemAdapter.MoreViewHolder> {
    private TypeFilterBean datas;
    private List<KeyValueBean> dataList;
    private Context context;
    private ProjectTextClickListioner textClickListioner;
    public MoreItemAdapter(TypeFilterBean datas, Context context) {
        this.datas = datas;
        this.dataList = datas.getDataList();
        this.context = context;
    }

    @Override
    public MoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_poptextview, parent, false);
        MoreViewHolder moreViewHolder = new MoreViewHolder(view);
        return moreViewHolder;
    }

    @Override
    public void onBindViewHolder(MoreViewHolder holder, final int position) {
        holder.projectText.setText(datas.getDataList().get(position).getValue());
        holder.projectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.setKey(dataList.get(position).getKey());
                datas.setTitle(dataList.get(position).getValue());
                textClickListioner.onProjectTextClickLitioner(null);
            }
        });
    }
    public void setOnclickListioner(ProjectTextClickListioner projectTextClickListioner){
        this.textClickListioner=projectTextClickListioner;
    }
    @Override
    public int getItemCount() {
        return Common.isListEmpty(dataList) ? 0 : dataList.size();
    }

   public interface ProjectTextClickListioner{
      void onProjectTextClickLitioner(KeyValueBean keyValueBean);
    }

    class MoreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.projectText)
        TextView projectText;
        public MoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
