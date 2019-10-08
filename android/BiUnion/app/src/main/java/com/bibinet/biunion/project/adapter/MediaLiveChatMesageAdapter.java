package com.bibinet.biunion.project.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.models.MediaLiveMsgItemModel;
import com.bibinet.biunion.project.utils.Common;

import java.util.ArrayList;

public class MediaLiveChatMesageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final ArrayList<MediaLiveMsgItemModel> mList;
    private Activity activity;

    public MediaLiveChatMesageAdapter(Activity activity, ArrayList<MediaLiveMsgItemModel> list) {
        this.activity = activity;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(activity).inflate(R.layout.adapter_media_live_chat_msg, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        if (position == 0) {
            messageViewHolder.usernameTv.setText(TextUtils.isEmpty(mList.get(position).getUserName()) ?  "" : mList.get(position).getUserName());
        } else {
            messageViewHolder.usernameTv.setText(TextUtils.isEmpty(mList.get(position).getUserName()) ?  "" : mList.get(position).getUserName() + ": ");
        }

        messageViewHolder.contentTv.setText(TextUtils.isEmpty(mList.get(position).getContent()) ?  "" : mList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return Common.isListEmpty(mList) ? 0 : mList.size();
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTv;
        public TextView contentTv;

        public MessageViewHolder(View itemView) {
            super(itemView);
            usernameTv = itemView.findViewById(R.id.ada_media_live_chat_msg_username);
            contentTv = itemView.findViewById(R.id.ada_media_live_chat_msg_content);
        }
    }
}
