package com.shenyuan.militarynews.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-10-10
 * Time: 上午9:25
 */
public class FragmentTabViewAdapter{
    private List<Fragment> fragments; // 一个tab页面对应一个Fragment
    private View[] mViews; // 用于切换tab
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int fragmentContentId; // Activity中所要被替换的区域的id
    private int currentTab; // 当前Tab页面索引

    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能

    public FragmentTabViewAdapter(FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId, View... views) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        this.mViews = views;
        
        for(int i = 0; i < mViews.length; i++){
        	View v = mViews[i];
        	v.setTag(i);
        	v.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int index = (Integer)v.getTag();
					onCheckedChanged(index);
				}
			});
        }
    }
     
    public void onCheckedChanged(int index) {
		for(View v : mViews){
			v.setSelected(false);
		}
		mViews[index].setSelected(true);
    	
        Fragment fragment = fragments.get(index);
        FragmentTransaction ft = obtainFragmentTransaction(index);

        getCurrentFragment().onPause(); // 暂停当前tab 

        if(fragment.isAdded()){
            fragment.onResume(); // 启动目标tab的onResume()
        }else{
            ft.add(fragmentContentId, fragment);
        }
        showTab(index); // 显示目标tab
        ft.commitAllowingStateLoss();

        // 如果设置了切换tab额外功能功能接口
        if(null != onRgsExtraCheckedChangedListener){
            onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(index);
        }

    }

    /**
     * 切换tab
     * @param idx
     */
    private void showTab(int idx){
        for(int i = 0; i < fragments.size(); i++){
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);

            if(idx == i){
                ft.show(fragment);
            }else{
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
        currentTab = idx; // 更新目标tab为当前tab
    }

    /**
     * 获取一个带动画的FragmentTransaction
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index){
        FragmentTransaction ft = fragmentManager.beginTransaction(); 
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment(){
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }
 
    public static class OnRgsExtraCheckedChangedListener{
        public void OnRgsExtraCheckedChanged(int index){

        }
    }

}
