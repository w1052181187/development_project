package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.bean.subscribe.PrivateInfoDesignBean;
import com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoDesignActivity;
import com.bibinet.biunion.project.ui.dialog.PrivateInfoDesignSelectDialog;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-27.
 */

public class PrivateInfoDesignMultiselectAdapter extends RecyclerView.Adapter<PrivateInfoDesignMultiselectAdapter.PrivateViewHolder> implements PrivateInfoDesignSelectDialog.OnPrivateInfoDesignSelectListener {

    private Context context;
    private OnSelectListener listener;

    //数据菜单列表
    private List<PrivateInfoDesignBean.BaseData> menuList;
    private List<PrivateInfoDesignBean.BaseData> mList;
    //数据
    private List<PrivateInfoDesignBean.BaseData> dataList;
    private boolean isDeleteState = false;
    private Button deleteBtn;

    private PrivateInfoDesignActivity activity;
    private int maxSelectCount;

    public PrivateInfoDesignMultiselectAdapter(RecyclerView recyclerView, Context context, List<PrivateInfoDesignBean.BaseData> menuList, List<PrivateInfoDesignBean.BaseData> dataList) {
        this.context = context;
        this.menuList = menuList;
        //用户选择的数据 单选 无所谓 创建一个
        this.dataList = dataList;
        this.activity = (PrivateInfoDesignActivity) context;
        init(recyclerView);
    }
    //针对多选 将选择列表传入
    //多选需要设置最大值
    public PrivateInfoDesignMultiselectAdapter(RecyclerView recyclerView, Context context, List<PrivateInfoDesignBean.BaseData> menuList, List<PrivateInfoDesignBean.BaseData> dataList, int maxSelectCount) {
        this.context = context;
        this.menuList = menuList;
        //多选传入
        this.dataList = dataList;
        this.maxSelectCount = maxSelectCount;
        this.activity = (PrivateInfoDesignActivity) context;
        init(recyclerView);
    }

    private void init(RecyclerView recyclerView){
        //准备另一个数据 没有第一项
        mList = new ArrayList<PrivateInfoDesignBean.BaseData>();
        for(int i=1 ; i<menuList.size() ; i++){
            mList.add(menuList.get(i));
        }

        //默认添加一条 菜单的第一条 默认的全部或者整体
        //如果该菜单有值 不用添加
        if(dataList.size()<=0){
            dataList.add(menuList.get(0));
        }

        //设置适应性
        recyclerView.setLayoutManager(new LinearLayoutManager(context) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                if (dataList.size() > 0) {
                    View firstChildView = LayoutInflater.from(context).inflate(R.layout.view_private_info_design_popup_tag, null, false);
                    int intw=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int inth=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    firstChildView.measure(intw, inth);
                    setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), firstChildView.getMeasuredHeight()* dataList.size());
                } else {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        });
    }

    @Override
    public PrivateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_private_info_design_popup_tag, parent, false);
        PrivateViewHolder privateViewHolder = new PrivateViewHolder(view);
        return privateViewHolder;
    }

    @Override
    public void onBindViewHolder(PrivateViewHolder holder, final int position) {
        //根据数据进行操作
        PrivateInfoDesignBean.BaseData b = dataList.get(position);
        //设置展示字体的内容和颜色 值
        holder.windowTxt.setText(b.getName());
        holder.windowTxt.setTextColor(Color.parseColor("#333333"));
        holder.windowTxt.setTag(position);

        //第一条不能删除
        if(isDeleteState){
            holder.deleteV.setVisibility(View.VISIBLE);
        }else{
            holder.deleteV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return Common.isListEmpty(dataList) ? 0 : dataList.size();
    }

    public void addItem() {
        if(getItemCount()>=maxSelectCount){
            ToastUtils.showShort(context.getResources().getString(R.string.string_private_info_design_max_hint));
            return;
        }
        //新增 弹窗选择
        new PrivateInfoDesignSelectDialog(context, mList, this).show();
    }

    //添加选择 回调
    @Override
    public void onSelect(int pos) {
        String id = dataList.get(0).getId();
        if(id.equals("")){
            dataList.remove(0);
            dataList.add(0, mList.get(pos));
        }else{
            //排重 检测是否有 有添加 没有提示重复
            boolean result = check(mList, pos);
            if(!result){
                dataList.add(mList.get(pos));
                checkDeleteState(deleteBtn);
            }
        }
        activity.onDataChange();
        notifyDataSetChanged();
    }

    private boolean check(List<PrivateInfoDesignBean.BaseData> menuList, int pos) {
        PrivateInfoDesignBean.BaseData cb = menuList.get(pos);
        for(int i=0 ; i<dataList.size() ; i++){
            PrivateInfoDesignBean.BaseData b = dataList.get(i);
            if(b.equals(cb)){
                ToastUtils.showShort("已选择该项");
                return true;
            }
        }
        return false;
    }

    //判断删除状态
    private void checkDeleteState(Button button){
        if(button==null){
            return;
        }
        //判断是否能删除
        if(dataList.size()==1){
            button.setSelected(false);
            button.setEnabled(false);
            button.setText("删除");
            isDeleteState = false;
        }else{
            button.setSelected(true);
            button.setEnabled(true);
        }
    }

    //删除 启动删除状态
    public void deleteItem(Button button) {
        //删除
        if(isDeleteState){
            isDeleteState = false;
            button.setText("删除");
        }else{
            isDeleteState = true;
            button.setText("完成");
        }
        activity.onDataChange();
        notifyDataSetChanged();
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
        if(dataList.size()<=1){
            //初始化这个按钮
            this.deleteBtn.setEnabled(false);
            this.deleteBtn.setSelected(false);
            this.deleteBtn.setText("删除");
        }else{
            //初始化这个按钮
            this.deleteBtn.setEnabled(true);
            this.deleteBtn.setSelected(true);
            this.deleteBtn.setText("删除");
        }

    }

    public void setListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSelectListener {
        void onSelectPos(int pos);
    }

    class PrivateViewHolder extends RecyclerView.ViewHolder implements PrivateInfoDesignSelectDialog.OnPrivateInfoDesignSelectListener {
        @BindView(R.id.v_private_info_design_popup_tag_main_txt)
        TextView windowTxt;

        @BindView(R.id.v_private_info_design_popup_tag_delete)
        View deleteV;

        public PrivateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //删除标签
        @OnClick(R.id.v_private_info_design_popup_tag_delete)
        void delete(){
            int p = (int) windowTxt.getTag();
            dataList.remove(p);
            checkDeleteState(deleteBtn);
            activity.onDataChange();
            notifyDataSetChanged();
        }

        @OnClick(R.id.v_private_info_design_popup_tag_main)
        void select() {
            int p = (int) windowTxt.getTag();
            //弹窗选择
            if(p==0 && dataList.size()==1){
                new PrivateInfoDesignSelectDialog(context, menuList, this).show();
            }else{
                new PrivateInfoDesignSelectDialog(context, mList, this).show();
            }
            activity.onDataChange();
        }

        //二次点击修改
        @Override
        public void onSelect(int pos) {
            int p = (int) windowTxt.getTag();
            boolean result;
            if(p==0 && dataList.size()==1){
                result = check(menuList, pos);
            }else{
                result = check(mList, pos);
            }
            //排重
            if(!result){
                //将选择的结果 给到那个值 如果位置为-1 证明没选择 给默认的空字符串
                if(listener!=null){
                    listener.onSelectPos(pos);
                }
                if(p==0 && dataList.size()==1){
                    dataList.remove(p);
                    dataList.add(p, menuList.get(pos));
                }else{
                    dataList.remove(p);
                    dataList.add(p, mList.get(pos));
                }
                notifyDataSetChanged();
            }
            activity.onDataChange();
        }
    }
}
