package com.bibinet.biunion.project.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/6/11.
 */

public class ViewCheckUtils {
    /**
     * 初始化时调用
     * @param confirmView
     * @param inputView
     */
    public static void inputConfirm(final View confirmView, final EditText... inputView){
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isAccess = true;
                for(int i=0 ; i<inputView.length ; i++){
                    String txt = inputView[i].getText().toString();
                    if(txt.equals("")){
                        isAccess = false;
                    }
                }
                if(isAccess){
                    confirmView.setSelected(true);
                    confirmView.setEnabled(true);
                }else{
                    confirmView.setSelected(false);
                    confirmView.setEnabled(false);
                }
            }
        };
        tw.afterTextChanged(null);
        for(int i=0 ; i<inputView.length ; i++){
            inputView[i].addTextChangedListener(tw);
        }
    }

    public static void getCodeCheck(final View getCodeCheck, final EditText phone){
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(phone.getText().toString().length()==11){
                    getCodeCheck.setSelected(true);
                    getCodeCheck.setEnabled(true);
                }else{
                    getCodeCheck.setSelected(false);
                    getCodeCheck.setEnabled(false);
                }
            }
        };
        tw.afterTextChanged(null);
        phone.addTextChangedListener(tw);
    }

    public static void showPassword(final View passwordV, final EditText passwordET){
        passwordV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectIndex = passwordET.getSelectionEnd();
                if (passwordV.isSelected()) {
                    passwordV.setSelected(false);
                    passwordET.setInputType(129);
                } else {
                    passwordV.setSelected(true);
                    passwordET.setInputType(145);
                }
                passwordET.setSelection(selectIndex);
            }
        });
    }

    public static void setIsCanEdit(View view, boolean isCanEdit){
        view.setEnabled(isCanEdit);
        if(view instanceof ViewGroup){
            ViewGroup vg = (ViewGroup)view;
            for(int i=0 ; i<vg.getChildCount() ; i++){
                setIsCanEdit(vg.getChildAt(i), isCanEdit);
            }
        }else{
            view.setEnabled(isCanEdit);
        }
    }
}
