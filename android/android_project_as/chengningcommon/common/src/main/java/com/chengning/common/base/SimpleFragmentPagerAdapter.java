package com.chengning.common.base;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
	private FragmentManager fm;
	private ArrayList<Fragment> fragmentsList;
	private List<String> strs;
    private int mChildCount;

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

	public SimpleFragmentPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> fragments, List<String> strs) {
		super(fm);
		this.fm = fm;
		this.fragmentsList = fragments;
		this.strs = strs;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (strs == null) {
			return "";
		} else {
			return strs.get(position).toUpperCase();
		}
	}

	@Override
	public int getCount() {
		if (fragmentsList == null) {
			return 0;
		}
		return fragmentsList.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		if (fragmentsList == null) {
			return null;
		}
		return fragmentsList.get(arg0);
	}

    @Override
    public int getItemPosition(Object object) {
        if ( mChildCount > 0) {
            // 这里利用判断执行若干次不缓存，刷新
            mChildCount --;
            // 返回这个是itemPOSITION_NONE
            return POSITION_NONE;
        }
        // 这个则是POSITION_UNCHANGED
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		try {
			super.destroyItem(container, position, object);
		} catch (Exception e) {
			e.printStackTrace();

			try {
				Fragment fragment = (Fragment) object;
				FragmentTransaction ft = fm.beginTransaction();
				ft.remove(fragment);
				ft.commitAllowingStateLoss();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public ArrayList<Fragment> getFragmentsList() {
		return fragmentsList;
	}

}
