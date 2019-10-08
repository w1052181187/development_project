package com.bibinet.biunion.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Method;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 多个item类型的适配器封装类
 * Created by 吴昊 on 2017-4-28.
 */

public abstract class MultipleBaseAdapter extends BaseAdapter {

    /**
     * @author 吴昊
     * @time 2017-4-28 15:43
     * 强制重写该方法 得到当前position位置的item类型
     * @param position 位置
     * @return
     */
    @Override
    public abstract int getItemViewType(int position);

    /**
     * @author 吴昊
     * @time 2017-4-28 15:43
     * 根据得到的类型数组长度返回类型个数
     */
    @Override
    public final int getViewTypeCount() {
        return getItemViewTypeId().length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public final View getView(int i, View view, ViewGroup viewGroup) {
        int currentType = getItemViewType(i);
        for(int typeI=0 ; typeI<getItemViewTypeId().length ; typeI++){
            //类型一致时执行 选一执行
            if(currentType==getItemViewTypeId()[typeI]){
                Object o;
                if(view==null){
                    view = LayoutInflater.from(getContext()).inflate(getLayoutId()[typeI], viewGroup, false);
                    o = initView(currentType, view);
                    view.setTag(o);
                }else{
                    o = view.getTag();
                }
                initValue(i, currentType, o);
                break;
            }
        }
        return view;
    }

    /**
     * @author 吴昊
     * @time 2017-4-28 15:45
     * 得到Context对象
     */
    protected abstract Context getContext();

    /**
     * @author 吴昊
     * @time 2017-4-28 15:46
     * 初始化值
     * @param position 位置
     * @param currentType 当前类型
     * @param holder holder对象
     */
    protected abstract void initValue(int position, int currentType, Object holder);

    /**
     * @author 吴昊
     * @time 2017-4-28 15:47
     * 初始化界面
     * @param currentType 当前类型
     * @param view item的view
     * @return holder对象
     */
    protected abstract Object initView(int currentType, View view);
    /**
     * @author 吴昊
     * @time 2017-4-28 15:47
     * 得到布局id数组
     */
    protected abstract int [] getLayoutId();

    /**
     * @author 吴昊
     * @time 2017-4-28 15:48
     * 得到item类型的数组
     */
    public abstract int [] getItemViewTypeId();
}
