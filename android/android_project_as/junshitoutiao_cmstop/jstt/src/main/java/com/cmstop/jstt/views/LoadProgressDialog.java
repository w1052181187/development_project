/**************************************************************************************
* [Project]
*       MyProgressDialog
* [Package]
*       com.lxd.widgets
* [FileName]
*       CustomProgressDialog.java
* [Copyright]
*       Copyright 2012 LXD All Rights Reserved.
* [History]
*       Version          Date              Author                        Record
*--------------------------------------------------------------------------------------
*       1.0.0           2012-4-27         lxd (rohsuton@gmail.com)        Create
**************************************************************************************/
	
package com.cmstop.jstt.views;

import com.cmstop.jstt.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;


/********************************************************************
 * [Summary]
 *       TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要
 * [Remarks]
 *       TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 *******************************************************************/

public class LoadProgressDialog extends Dialog {
	private Context context = null;
	private static LoadProgressDialog customProgressDialog = null;
	
	private LoadProgressDialog(Context context){
		super(context);
		this.context = context;
	}
	
	public LoadProgressDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public static LoadProgressDialog createDialog(Context context){
		customProgressDialog = new LoadProgressDialog(context); 
		Window window = customProgressDialog.getWindow();    
		window.requestFeature(Window.FEATURE_NO_TITLE);
		window.setBackgroundDrawableResource(R.color.transparent);
		customProgressDialog.setContentView(R.layout.dialog_progress);
		return customProgressDialog;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	
    	if (customProgressDialog == null){
    		return;
    	}
    }
 
    /**
     * 
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public LoadProgressDialog setTitile(String strTitle){
    	return customProgressDialog;
    }
    
    /**
     * 
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public LoadProgressDialog setMessage(String strMessage){
    	TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
    	
    	if (tvMsg != null){
    		tvMsg.setText(strMessage);
    	}
    	
    	return customProgressDialog;
    }
}
