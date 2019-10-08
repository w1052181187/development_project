/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chengning.yiqikantoutiao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengning.yiqikantoutiao.R;
import com.viewpagerindicator.TabPageIndicator;

public class MyTabPageIndicator extends TabPageIndicator {
    private static final int[] LL = new int[] {
        R.attr.textNormalSize,
        R.attr.textSelectedSize,
    };
    
    private static final int MyTabPageIndicator_textNormalSize = 0;
    private static final int MyTabPageIndicator_textSelectedSize = 1;
    
    private int mTextSize;
    private int mTextSelectedSize;

    public MyTabPageIndicator(Context context) {
        this(context, null);
    }

    public MyTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, LL, R.attr.vpiTabPageIndicatorStyle, 0);
        mTextSize = a.getDimensionPixelOffset(MyTabPageIndicator_textNormalSize, 0);
        mTextSelectedSize = a.getDimensionPixelOffset(MyTabPageIndicator_textSelectedSize, 0);
        a.recycle();
    }
    
    @Override
    public void notifyDataSetChanged() {
    	super.notifyDataSetChanged();
    }
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);

    	LinearLayout mTabLayout = (LinearLayout) getChildAt(0);
        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final TextView child = (TextView) mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            child.setSelected(isSelected);
            if (isSelected) {
            	child.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSelectedSize);
            }else{
            	child.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            }
        }
    }
}
