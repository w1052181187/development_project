package com.bibinet.biunion.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.models.InformationItemsModel;
import com.bibinet.biunion.project.models.PrivateInfoResultModel;
import com.bibinet.biunion.project.ui.activity.InformationDetailActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bibinet on 2017-1-6.
 */
public class PrivateInfoShowAdapter extends PageActivityAdapter {
    private static final int TYPE_ITEM = 0;  //普通Item View
    private final LayoutInflater inflater;
    private Activity context;
    private List<InformationItemsModel> socailInfos;


    public PrivateInfoShowAdapter(Activity context, List<InformationItemsModel> socailInfos) {
        super(context);
        this.context = context;
        this.socailInfos = socailInfos;
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected RecyclerView.ViewHolder onChildCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.adapter_private_info_show, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            PrivateInfoShowItemHolder itemViewHolder = new PrivateInfoShowItemHolder(view);
            return itemViewHolder;
        }
        return null;
    }


    @Override
    protected void onChildBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PrivateInfoShowItemHolder) {
            PrivateInfoShowItemHolder h = (PrivateInfoShowItemHolder) holder;
            String location = socailInfos.get(position).getProjectLocation();
            if(location==null || location.equals("")){
                h.projectLoaction.setVisibility(View.GONE);
            }else{
                h.projectLoaction.setVisibility(View.VISIBLE);
                h.projectLoaction.setText(location);
            }
            String time = socailInfos.get(position).getProjectTime();
            h.projectTime.setText(time);
            h.companyName.setText(socailInfos.get(position).getProjectName());
            if (socailInfos.get(position).getProjectType().equals("A")) {
                h.projectTypeImage.setImageResource(R.mipmap.shouye_gongcheng);
            } else if (socailInfos.get(position).getProjectType().equals("B")) {
                h.projectTypeImage.setImageResource(R.mipmap.shouye_huowu);
            } else {
                h.projectTypeImage.setImageResource(R.mipmap.shouye_fuw);
            }

            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InformationDetailActivity.start(context, socailInfos.get(position));
                }
            });
        }
    }

    @Override
    protected int getChildItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    protected int getChildCount() {
        return Common.isListEmpty(socailInfos) ? 0 : socailInfos.size();
    }


    class PrivateInfoShowItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.projectTypeImage)
        ImageView projectTypeImage;
        @BindView(R.id.projectTypeMain)
        RelativeLayout projectTypeMain;
        @BindView(R.id.companyName)
        TextView companyName;
        @BindView(R.id.projectLoaction)
        TextView projectLoaction;
        @BindView(R.id.projectIsTop)
        ImageView projectIsTop;
        @BindView(R.id.projectTime)
        TextView projectTime;

        public PrivateInfoShowItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
