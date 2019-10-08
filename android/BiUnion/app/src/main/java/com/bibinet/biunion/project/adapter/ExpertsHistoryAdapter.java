package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.ExpertsAskAnswerResultBean;
import com.bibinet.biunion.project.ui.activity.ExpertAskResultActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bibinet on 2017-1-6.
 */
public class ExpertsHistoryAdapter extends PageActivityAdapter {//专家回答历史记录
    private static final int TYPE_ITEM_EXPERTS = 0;  //普通Item View
    private final LayoutInflater inflater;
    private Context context;
    private List<ExpertsAskAnswerResultBean.ItemsBean> historyInfo;
    private onDeleteDataListioner deleteDataListioner;

    public ExpertsHistoryAdapter(Context context, List<ExpertsAskAnswerResultBean.ItemsBean> historyInfo) {
        super(context);
        this.context = context;
        this.historyInfo = historyInfo;
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected int getChildItemViewType(int position) {
        return TYPE_ITEM_EXPERTS;
    }

    @Override
    protected int getChildCount() {
        return Common.isListEmpty(historyInfo) ? 0 : historyInfo.size();
    }

    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_EXPERTS) {
            View view = inflater.inflate(R.layout.item_expertsanserhistory, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            ItemHolder itemViewHolder = new ItemHolder(view);
            return itemViewHolder;
        }
        return null;
    }

    @Override
    protected void onChildBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).answerTitle.setText(historyInfo.get(position).getTitle());
            ((ItemHolder) holder).content.setText(historyInfo.get(position).getContent());
            ((ItemHolder) holder).createTime.setText(String.valueOf(historyInfo.get(position).getCreateDateStr()));
            if (!TextUtils.isEmpty(historyInfo.get(position).getexpertName())) {
                ((ItemHolder) holder).answerExpertsV.setVisibility(View.VISIBLE);
                ((ItemHolder) holder).answerExperts.setText(historyInfo.get(position).getexpertName());
                if (historyInfo.get(position).getIsSolved() == 0) {
                    ((ItemHolder) holder).answerState.setText("专家待处理");
                } else {
                    ((ItemHolder) holder).answerState.setText("专家已处理");
                }
            } else {
                ((ItemHolder) holder).answerExpertsV.setVisibility(View.GONE);
                if (historyInfo.get(position).getIsSolved() == 0) {
                    ((ItemHolder) holder).answerState.setText("待处理");
                } else {
                    ((ItemHolder) holder).answerState.setText("已处理");
                }
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (historyInfo.get(position).getIsSolved() == 1) {
                        Intent intent = new Intent(context, ExpertAskResultActivity.class);
                        intent.putExtra("userobjectId", String.valueOf(historyInfo.get(position).getObjectId()));
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "专家正在处理中，请稍后。。。", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            ((ItemHolder) holder).deleteAskImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteDataListioner != null) {
                        deleteDataListioner.onDelete(position);
                    }
                }
            });
        }
    }

    public void setOnDeletListioner(onDeleteDataListioner onDeletListioner) {
        this.deleteDataListioner = onDeletListioner;
    }

    public interface onDeleteDataListioner {
        void onDelete(int postion);
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.answerTitle)
        TextView answerTitle;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.createTime)
        TextView createTime;
        @BindView(R.id.answerExperts)
        TextView answerExperts;

        @BindView(R.id.answerExperts_view)
        View answerExpertsV;

        @BindView(R.id.deleteAskImage)
        ImageView deleteAskImage;
        @BindView(R.id.answerState)
        TextView answerState;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
