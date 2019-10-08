package com.bibinet.biunion.project.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.PathConfig;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.UUID;

public class MediaLiveUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final ArrayList<String> mList;
    private final RequestOptions circleOptions;
    private final RequestManager glide;
    private Activity activity;

    public MediaLiveUserAdapter(Activity activity, ArrayList<String> list) {
        this.activity = activity;
        this.mList = list;
        circleOptions = new RequestOptions().circleCrop().override(100);
        glide  = Glide.with(activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AvatarViewHolder(LayoutInflater.from(activity).inflate(R.layout.adapter_media_live_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        AvatarViewHolder viewHolder = (AvatarViewHolder) holder;
        if (!TextUtils.isEmpty(mList.get(position))) {
            glide.asBitmap().load(ImageUtils.base64ToBitmap(mList.get(position))).apply(circleOptions).into(viewHolder.avatarImg);
        } else {
            glide.load(R.mipmap.wode_toux).into(viewHolder.avatarImg);
        }

    }

    @Override
    public int getItemCount() {
        return Common.isListEmpty(mList) ? 0 : mList.size();
    }

    private class AvatarViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarImg;

        public AvatarViewHolder(View itemView) {
            super(itemView);
            avatarImg = itemView.findViewById(R.id.ada_media_live_user_avatar);
        }
    }
}
