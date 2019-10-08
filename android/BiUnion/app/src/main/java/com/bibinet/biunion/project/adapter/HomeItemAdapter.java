package com.bibinet.biunion.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.models.InformationItemsModel;
import com.bibinet.biunion.project.ui.activity.InformationDetailActivity;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bibinet on 2017-6-27.
 */

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeItemHolder> {

    private final int layoutId = R.layout.adapter_home_item;
    private Context context;
    //数据
    private List<InformationItemsModel> dataList;
    private Activity activity;
    private int maxShowCount;

    private boolean isShowProjectType;

    //针对多选 将选择列表传入
    //多选需要设置最大值
    public HomeItemAdapter(Context context, RecyclerView recyclerView, List<InformationItemsModel> dataList, int maxShowCount, boolean isShowProjectType) {
        this.context = context;
        this.dataList = dataList;
        this.maxShowCount = maxShowCount;
        this.activity = (Activity) context;
        this.isShowProjectType = isShowProjectType;
        init(recyclerView);
    }

    private void init(RecyclerView recyclerView) {
        //设置适应性
        recyclerView.setLayoutManager(new LinearLayoutManager(context) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                try {
                    View firstChildView = LayoutInflater.from(context).inflate(layoutId, null, false);
                    int width = View.MeasureSpec.makeMeasureSpec(0,
                            View.MeasureSpec.UNSPECIFIED);
                    int height = View.MeasureSpec.makeMeasureSpec(0,
                            View.MeasureSpec.UNSPECIFIED);
                    firstChildView.measure(width, height);
//                    view.getMeasuredWidth(); // 获取宽度
//                    view.getMeasuredHeight(); // 获取高度
                    setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), DensityUtil.dip2px(62) * maxShowCount);
                } catch (Exception e) {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        });
    }

    @Override
    public HomeItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        HomeItemHolder homeItemHolder = new HomeItemHolder(view);
        return homeItemHolder;
    }

    @Override
    public void onBindViewHolder(HomeItemHolder holder, final int position) {
        String location = dataList.get(position).getProjectLocation();
        if (location == null || location.equals("")) {
            holder.projectLoaction.setVisibility(View.GONE);
        } else {
            holder.projectLoaction.setVisibility(View.VISIBLE);
            holder.projectLoaction.setText(location);
        }
        String time = dataList.get(position).getProjectTime();
        holder.projectTime.setText(time);
        holder.companyName.setText(dataList.get(position).getProjectName());
        if (isShowProjectType) {
            holder.typeV.setVisibility(View.VISIBLE);
            if (dataList.get(position).getProjectType().equals("A")) {
                holder.projectTypeImage.setImageResource(R.mipmap.shouye_gongcheng);
            } else if (dataList.get(position).getProjectType().equals("B")) {
                holder.projectTypeImage.setImageResource(R.mipmap.shouye_huowu);
            } else {
                holder.projectTypeImage.setImageResource(R.mipmap.shouye_fuw);
            }
        } else {
            holder.typeV.setVisibility(View.GONE);
        }
//        if(position == 0){
//            holder.topLine.setVisibility(View.VISIBLE);
//        }else{
//            holder.topLine.setVisibility(View.GONE);
//        }
        holder.topLine.setVisibility(View.GONE);
        if (position == maxShowCount - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationDetailActivity.start(activity, dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return Common.isListEmpty(dataList) ? 0 : dataList.size();
    }


    class HomeItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ada_home_item_type)
        View typeV;
        @BindView(R.id.top_line)
        View topLine;
        @BindView(R.id.line)
        View line;
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

        public HomeItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
