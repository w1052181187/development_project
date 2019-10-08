package com.bibinet.biunion.project.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.View;

import com.bibinet.biunion.project.bean.subscribe.PrivateInfoDesignBean;

import java.util.List;

/**
 * 单选按钮封装类 单选 可以取消选择
 * Created by 吴昊 on 2017-4-19.
 */

public class PrivateInfoDesignRadioLayout extends LinearLayout {
    private boolean [] isSelect = null;
    private OnRadioLayoutOnClickListener onRadioLayoutOnClickListener;

    public PrivateInfoDesignRadioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private void setSelectItem(int pos){
        if(isSelect == null){
            isSelect = new boolean[getChildCount()];
        }
        int childCount = getChildCount();
        if(pos<0 || pos>=childCount){
            return;
        }
        if(isSelect[pos] == false){
            //没选择
            isSelect[pos] = true;
            setSelectState(this);
            return;
        }
        isSelect[pos] = false;
        //选择
        setSelectState(this);
        return;
    }

    private void setSelectState(ViewGroup v) {
        // TODO Auto-generated method stub
        for(int i=0 ; i<v.getChildCount() ; i++){
            View object = v.getChildAt(i);
            if(isSelect[i]){
                object.setSelected(true);
            }else{
                object.setSelected(false);
            }
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        //套用原来的布局操作
        super.onLayout(changed, l, t, r, b);
        //循环对里面所有的1级子view进行点击事件操作
        for(int i=0 ; i<getChildCount() ; i++){
            View v = getChildAt(i);
            final int finalI = i;
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = finalI;
                    setSelectItem(pos);
                    if(onRadioLayoutOnClickListener!=null){
                        onRadioLayoutOnClickListener.onClickPos(pos);
                    }
                }
            });
        }
    }


    public void setOnRadioLayoutOnClickListener(OnRadioLayoutOnClickListener onRadioLayoutOnClickListener) {
        this.onRadioLayoutOnClickListener = onRadioLayoutOnClickListener;
    }

    public void setInitValue(List<PrivateInfoDesignBean.BaseData> selectTargetList) {
        if(isSelect == null){
            isSelect = new boolean[getChildCount()];
        }

        for(int i=0 ; i<getChildCount() ; i++){
            PrivateInfoDesignBean.BaseData b1 = (PrivateInfoDesignBean.BaseData) getChildAt(i).getTag();
            int index = selectTargetList.indexOf(b1);
            if(index == -1){
                getChildAt(i).setSelected(false);
                isSelect[i] = false;
            }else{
                getChildAt(i).setSelected(true);
                isSelect[i] = true;
            }
        }

    }


    public interface OnRadioLayoutOnClickListener{
        void onClickPos(int pos);
    }
}
