package com.bibinet.biunion.project.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.interf.RxjavaLoadBitmapInterf;
import com.bibinet.biunion.project.models.MediaLiveRecordItemModel;
import com.bibinet.biunion.project.ui.activity.MediaRecordDetailActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.DateFormatUtils;
import com.bibinet.biunion.project.utils.DensityUtil;
import com.bibinet.biunion.project.utils.ImageUtils;
import com.bibinet.biunion.project.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MediaLiveRecordListAdapter extends PageActivityAdapter{

    private final RequestOptions circleOptions;
    private final RequestOptions options;
    private final RequestManager glide;
    private Activity activity;
    private List<MediaLiveRecordItemModel> mDataList;
    int imageLayoutWidth;

    public MediaLiveRecordListAdapter(Activity activity, List<MediaLiveRecordItemModel> dataList) {
        super(activity);
        this.activity = activity;
        this.mDataList = dataList;
        int gapWidth = activity.getResources().getDimensionPixelSize(R.dimen.common_left_right_margin_space_live);
        if (getChildCount() > 1) {
            imageLayoutWidth = (DensityUtil .getScreenWidth(activity) - gapWidth * 4) / 2;
        } else{
            imageLayoutWidth = DensityUtil.getScreenWidth(activity) - gapWidth * 2;
        }
        circleOptions = new RequestOptions().override(100).circleCrop();
//                .signature(new Key() {
//                    @Override
//                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
//                        messageDigest.digest();
//                    }
//                })
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true);
        options = new RequestOptions()
                .override(imageLayoutWidth,imageLayoutWidth * 3 / 2)
                .centerCrop();
//                .signature(new Key() {
//                    @Override
//                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
//                        messageDigest.digest();
//                    }
//                })
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true);
        glide = Glide.with(activity);
    }

    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(activity).inflate(R.layout.adapter_media_live_list_item, parent, false));
    }

    @Override
    protected void onChildBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        MediaLiveRecordItemModel model = mDataList.get(position);
        updateSize(itemViewHolder.coverImg, imageLayoutWidth);

        try {
            if (!TextUtils.isEmpty(model.getLiveCover())) {
                ImageUtils.handleBitmapByRxjava(model.getLiveCover(), new RxjavaLoadBitmapInterf() {
                    @Override
                    public void success(Bitmap bitmap) {
                        glide.asBitmap().load(bitmap).apply(options).into(itemViewHolder.coverImg);
                    }
                });
            }

            if (!TextUtils.isEmpty(model.getUserLogo())) {
                ImageUtils.handleBitmapByRxjava(model.getUserLogo(), new RxjavaLoadBitmapInterf() {
                    @Override
                    public void success(Bitmap bitmap) {
                        glide.asBitmap().load(bitmap).apply(circleOptions).into(itemViewHolder.avatarImg);
                    }
                });

            } else {
                glide.load(R.mipmap.wode_toux).into(itemViewHolder.avatarImg);
            }
        } catch (Exception e){

        }

        itemViewHolder.titleTv.setText(mDataList.get(position).getLiveName());
        itemViewHolder.timeTv.setText(DateFormatUtils.format(mDataList.get(position).getLiveStartTime(), "yyyy-MM-dd"));
        itemViewHolder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mDataList.get(position).getObjectId())) {
                    ToastUtils.showShort("播放地址为空，无法正常播放！");
                    return;
                }
                MediaRecordDetailActivity.launch(activity, mDataList.get(position).getLiveName(), mDataList.get(position).getObjectId());

            }
        });

    }



    private void updateSize(ImageView v, int imageWidth){
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        lp.width = imageWidth;
        lp.height = imageWidth * 3 / 2;
        v.setLayoutParams(lp);
    }

    @Override
    protected int getChildItemViewType(int position) {
        return 0;
    }

    @Override
    public int getChildCount() {
        return Common.isListEmpty(mDataList) ? 0 : mDataList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        public View mainView;
        public ImageView coverImg;
        public ImageView avatarImg;
        public TextView titleTv;
        public TextView timeTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mainView = itemView.findViewById(R.id.ada_media_live_item_view);
            coverImg = itemView.findViewById(R.id.ada_media_live_item_cover);
            avatarImg = itemView.findViewById(R.id.ada_media_live_item_avatar);
            titleTv = itemView.findViewById(R.id.ada_media_live_item_title);
            timeTv = itemView.findViewById(R.id.ada_media_live_item_time);
        }
    }


}
