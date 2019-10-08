package com.chengning.fenghuovideo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.chengning.common.widget.NestRadioGroup;

import java.util.List;

public class FragmentTabAdapterExtend implements NestRadioGroup.OnCheckedChangeListener{
    private List<Fragment> fragments; // 一个tab页面对应一个Fragment
    private NestRadioGroup rgs; // 用于切换tab
    private FragmentActivity fragmentActivity; // Fragment所属的Activity
    private int fragmentContentId; // Activity中所要被替换的区域的id
    private int checkedBtnId;
    private int currentTab; // 当前Tab页面索引

    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能

    public FragmentTabAdapterExtend(FragmentActivity fragmentActivity, List<Fragment> fragments, int fragmentContentId, NestRadioGroup rgs) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentActivity = fragmentActivity;
        this.fragmentContentId = fragmentContentId;

        // 默认显示第一页
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId, fragments.get(0));
        ft.commitAllowingStateLoss(); 
        rgs.setOnCheckedChangeListener(this); 
    }
    
    public int GetCheckBtnId()
    {
    	return checkedBtnId;
    }
     

    @Override
    public void onCheckedChanged(NestRadioGroup radioGroup, int checkedId) {
    	checkedBtnId = rgs.getCheckedRadioButtonId();
        for(int i = 0; i < rgs.getChildCount(); i++){
        	boolean isMatched = false;
        	View v = rgs.getChildAt(i);
        	
        	if(rgs.getChildAt(i).getId() == checkedId){
				isMatched = true;
        	}else if(v instanceof RelativeLayout){
        		RelativeLayout rv = (RelativeLayout)v;
        		for(int j = 0; j < rv.getChildCount(); j++){
        			if(rv.getChildAt(j).getId() == checkedId){
        				isMatched = true;
        				break;
        			}
        		}
        	}
        	try {
        		if(isMatched){
                    Fragment fragment = fragments.get(i);
                    FragmentTransaction ft = obtainFragmentTransaction(i);

                    getCurrentFragment().onPause(); // 暂停当前tab 

                    if(fragment.isAdded()){ 
                        fragment.onResume(); // 启动目标tab的onResume()
                    }else{
                        ft.add(fragmentContentId, fragment);
                    }
                    showTab(i); // 显示目标tab
                    ft.commitAllowingStateLoss();

                    // 如果设置了切换tab额外功能功能接口
                    if(null != onRgsExtraCheckedChangedListener){
                        onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                    }
                    break;
                }
        	} catch(Exception e) {
        		
        	}
            
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
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction(); 
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
        public void OnRgsExtraCheckedChanged(NestRadioGroup radioGroup, int checkedId, int index){

        }
    }

}
