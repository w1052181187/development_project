package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.WriteTenderBookHistoryBean;
import com.bibinet.biunion.project.net.firing.WriteTenderBookFiring;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bibinet on 2017-1-6.
 */
public class WriteTenderHistoryAdapter extends PageActivityAdapter {//代写标书历史记录
    private static final int TYPE_ITEM = 0;  //普通Item View
    public static int Lastposition;
    private final LayoutInflater inflater;
    private Context context;
    private List<WriteTenderBookHistoryBean.ItemBean> historyInfo;
    private WriteTenderBookFiring writeTenderBookFiring;

    public WriteTenderHistoryAdapter(Context context, List<WriteTenderBookHistoryBean.ItemBean> historyInfo, WriteTenderBookFiring writeTenderBookFiring) {
        super(context);
        this.context = context;
        this.historyInfo = historyInfo;
        this.writeTenderBookFiring = writeTenderBookFiring;
        Lastposition = historyInfo.size();
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected int getChildItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    protected int getChildCount() {
        return Common.isListEmpty(historyInfo) ? 0 : historyInfo.size();
    }

    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_historywritebook, parent, false);
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
            ((ItemHolder) holder).historyTenderBook.setText(String.valueOf(historyInfo.get(position).getTenderSelection()));
            ((ItemHolder) holder).historyTenderType.setText(String.valueOf(historyInfo.get(position).getProjectType()));
            ((ItemHolder) holder).tenderType.setText(String.valueOf(historyInfo.get(position).getTenderMode()));
            ((ItemHolder) holder).tenderBookType.setText(String.valueOf(historyInfo.get(position).getTenderType()));
            ((ItemHolder) holder).contactPerson.setText(String.valueOf(historyInfo.get(position).getContact()));
            ((ItemHolder) holder).contactType.setText(String.valueOf(historyInfo.get(position).getCellPhone()));
            ((ItemHolder) holder).contactEmail.setText(String.valueOf(historyInfo.get(position).getEmail()));
            ((ItemHolder) holder).tenderDate.setText(String.valueOf(historyInfo.get(position).getCreateDateStr()));

            if (historyInfo.get(position).getIsEnd()==2) {
                ((ItemHolder) holder).tenderBookHistory.setText("已处理");
                ((ItemHolder) holder).tenderBookHistory.setTextColor(Color.parseColor("#666666"));
            }else {
                ((ItemHolder) holder).tenderBookHistory.setText("待处理");
                ((ItemHolder) holder).tenderBookHistory.setTextColor(Color.parseColor("#666666"));
            }
            //删除历史
            ((ItemHolder) holder).deleteHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = String.valueOf(historyInfo.get(position).getObjectId());
                    writeTenderBookFiring.deleteWriteTenderBook(id);
                }
            });
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.historyTenderBook)
        TextView historyTenderBook;
        @BindView(R.id.historyTenderType)
        TextView historyTenderType;
        @BindView(R.id.tenderType)
        TextView tenderType;
        @BindView(R.id.tenderBookType)
        TextView tenderBookType;
        @BindView(R.id.contactPerson)
        TextView contactPerson;
        @BindView(R.id.contactType)
        TextView contactType;
        @BindView(R.id.contactEmail)
        TextView contactEmail;
        @BindView(R.id.tenderDate)
        TextView tenderDate;
        @BindView(R.id.tenderBookHistory)
        TextView tenderBookHistory;
        @BindView(R.id.delete_history)
        View deleteHistory;
        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
