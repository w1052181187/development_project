package com.bibinet.biunion.project.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.models.MessageModel;
import com.bibinet.biunion.project.ui.activity.MessageDetailActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 吴昊 on 2017-6-16.
 */

public class MessageAdapter extends PageActivityAdapter {


    private static final int TYPE_ITEM = 0;  //普通Item View
    private final LayoutInflater inflater;

    private Activity context;
    private List<MessageModel.ItemsModel> messageModelList;

    public MessageAdapter(Activity context, List<MessageModel.ItemsModel> messageModelList) {
        super(context);
        this.context = context;
        this.messageModelList = messageModelList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.adapter_message, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            MessageHolder itemViewHolder = new MessageHolder(view);
            return itemViewHolder;
        }
        return null;
    }

    @Override
    protected void onChildBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MessageHolder h = (MessageHolder) holder;
        h.titleTV.setText(messageModelList.get(position).getTitle());
        String intro = messageModelList.get(position).getSummary();
        if(intro==null || intro.equals("")){
            h.introTV.setVisibility(View.GONE);
        }else{
            h.introTV.setText(intro);
            h.introTV.setVisibility(View.VISIBLE);
        }
        h.dateTV.setText(messageModelList.get(position).getPublishDate());
        h.mainV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDetailActivity.start(context, messageModelList.get(position).getObjectId());
            }
        });
    }

    @Override
    protected int getChildItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    protected int getChildCount() {
        return Common.isListEmpty(messageModelList) ? 0 : messageModelList.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ada_message_title)
        TextView titleTV;
        @BindView(R.id.ada_message_intro)
        TextView introTV;
        @BindView(R.id.ada_message_date)
        TextView dateTV;
        @BindView(R.id.ada_message)
        View mainV;

        public MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
