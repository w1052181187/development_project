package com.bibinet.biunion.project.ui.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.adapter.MoreItemAdapter;
import com.bibinet.biunion.project.animations.AnimationManager;
import com.bibinet.biunion.project.bean.KeyValueBean;
import com.bibinet.biunion.project.bean.TypeFilterBean;
import com.bibinet.biunion.project.ui.custom.TypeFilterLayout;
import com.bibinet.biunion.project.utils.DensityUtil;
import com.bibinet.biunion.project.utils.PublicPopWindowUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by Wh on 2017-12-4.
 */

public class TypeFilterManager implements PublicPopWindowUtils.onPopDismissListener, MoreItemAdapter.ProjectTextClickListioner, TypeFilterLayout.OnRadioLayoutOnClickListener {
    public final int TYPE_POS0 = 0;
    public final int TYPE_POS1 = 1;
    public final int TYPE_POS2 = 2;
    private final int CLICK_TYPE_POS0 = 0;
    private final int CLICK_TYPE_POS1 = 2;
    private final int CLICK_TYPE_POS2 = 4;
    @BindViews({R.id.v_type_filter_pos0, R.id.v_type_filter_pos1, R.id.v_type_filter_pos2})
    TextView [] posTV;
    @BindView(R.id.v_type_filter_radio)
    TypeFilterLayout radioLayout;
    private View view;
    private PublicPopWindowUtils publicPopWindowUtils;
    private Context context;
    private List<TypeFilterBean> typeFilterBeanList;
    private OnSelectBackListener onSelectBackListener;

    public TypeFilterManager(Context context, View typeFilterView, List<TypeFilterBean> typeFilterBeanList, OnSelectBackListener onSelectBackListener){
        view = typeFilterView;
        this.context = context;
        this.typeFilterBeanList = typeFilterBeanList;
        publicPopWindowUtils = new PublicPopWindowUtils(context, this);
        this.onSelectBackListener = onSelectBackListener;
        ButterKnife.bind(this, view);
    }

    public void init(){
        radioLayout.setOnRadioLayoutOnClickListener(this);
        setTitle();
    }

    private void setTitle(){
        for(int i=0 ; i<typeFilterBeanList.size() ; i++){
            posTV[i].setText(typeFilterBeanList.get(i).getTitle());
        }
    }

    //开始进行查询
    private void initPopView(TypeFilterBean typeFilterBean, final TextView textView) {
        publicPopWindowUtils.showPopWindow(R.layout.item_popwindow);
        View view = publicPopWindowUtils.getPopview();
        view.findViewById(R.id.pop_background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicPopWindowUtils.dissMisPopWindow();
            }
        });
        View background = view.findViewById(R.id.background);
        RecyclerView popViewRecyclerView = (RecyclerView) view.findViewById(R.id.popRecyclerView);
        MoreItemAdapter adapter = new MoreItemAdapter(typeFilterBean, context);
        popViewRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        popViewRecyclerView.setAdapter(adapter);
        int height =  DensityUtil.dip2px(251);
        int count = typeFilterBean.getDataList().size();
        if(count < 7){
            height = 35 * count + (count-1);
            height = DensityUtil.dip2px(height);
        }
        popViewRecyclerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
        adapter.setOnclickListioner(this);
        publicPopWindowUtils.showPopWindow(textView);
        AnimationManager.topToBottom(popViewRecyclerView, DensityUtil.dip2px(251), 200);
        AnimationManager.hideToShow(background, 0, 1f, 200);
    }

    @Override
    public void onDismiss() {
        radioLayout.setSelectItem(-1);
    }

    @Override
    public void onClickPos(int pos) {
        TypeFilterBean typeFilterBean = null;
        int tag = -1;
        switch (pos){
            case CLICK_TYPE_POS0:
                typeFilterBean = typeFilterBeanList.get(TYPE_POS0);
                tag = TYPE_POS0;
                break;
            case CLICK_TYPE_POS1:
                typeFilterBean = typeFilterBeanList.get(TYPE_POS1);
                tag = TYPE_POS1;
                break;
            case CLICK_TYPE_POS2:
                typeFilterBean = typeFilterBeanList.get(TYPE_POS2);
                tag = TYPE_POS2;
                break;
        }
        initPopView(typeFilterBean, posTV[tag]);
    }

    @Override
    public void onProjectTextClickLitioner(KeyValueBean bean) {
        publicPopWindowUtils.dissMisPopWindow();
        setTitle();
        if(onSelectBackListener!=null){
            String key0 = typeFilterBeanList.get(TYPE_POS0).getKey();
            String key1 = typeFilterBeanList.get(TYPE_POS1).getKey();
            String key2 = typeFilterBeanList.get(TYPE_POS2).getKey();
            onSelectBackListener.select(key0, key1, key2);
        }
    }

    public interface OnSelectBackListener{
        void select(String key0, String key1, String key2);
    }

}
