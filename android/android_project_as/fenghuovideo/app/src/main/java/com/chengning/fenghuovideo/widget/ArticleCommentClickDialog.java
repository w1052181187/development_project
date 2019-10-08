package com.chengning.fenghuovideo.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.util.ArticleManagerUtils;
import com.chengning.fenghuovideo.util.ArticleManagerUtils.DeleteListener;
import com.chengning.fenghuovideo.util.UIHelper;

public class ArticleCommentClickDialog extends BaseDialogFragment {

	private boolean mIsSelfComment;
	private BaseArticlesBean mCommentBean;
	private DeleteListener mDeleteListener;
	private String mCopyContent;
	private boolean isOnlyCopy = false;

	public void setData(BaseArticlesBean bean, String copyContent,boolean isSelfComment,DeleteListener listener) {
		setData(bean, copyContent, isSelfComment, listener, false);
	}
	
	public void setData(BaseArticlesBean bean, String copyContent,boolean isSelfComment,DeleteListener listener, boolean isOnlyCopy) {
		this.mCommentBean = bean;
		this.mCopyContent = copyContent;
		this.mIsSelfComment = isSelfComment;
		this.mDeleteListener = listener;
		this.isOnlyCopy = isOnlyCopy;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String[] items = null;
		
		if (isOnlyCopy){
			items = new String[] {"复制"};
		} else if (mIsSelfComment) {
			items = new String[] {"复制","举报","删除"};
		} else {
			items = new String[] {"复制","举报"};
		}
    	DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArticleManagerUtils managerUtils = new ArticleManagerUtils();
				switch(which){
				//复制
				case 0:
					
					if (!TextUtils.isEmpty(mCopyContent)) {
						ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
				        // 将文本内容放到系统剪贴板里。
//						Matcher siteMatcher = Pattern.compile(Consts.SITE_COMPILE_STR).matcher(mCopyContent);
//						
//						while (siteMatcher.find()) {
//							String allValue = siteMatcher.group(0);
//							String value = siteMatcher.group(1);
//							mCopyContent = mCopyContent.replace(allValue, value);
//						}
						cm.setText(mCopyContent);
				        UIHelper.showToast(getContext(), R.string.comment_copy_hint);
					}
			        
					dismissAllowingStateLoss();
					break;
				//举报
				case 1:
					managerUtils.getReport((BaseFragmentActivity) getContext(), mCommentBean.getTid());
					dismissAllowingStateLoss();
					break;
				//删除
				case 2:
					managerUtils.deleteDynamic(getContext(), mCommentBean.getTid(), mDeleteListener);
					dismissAllowingStateLoss();
					break;
				default :
					break;
				
				}
			}
		};
		
		AlertDialog dialog = new AlertDialog.Builder(getContext()).setItems(items,
				click).create();
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	@Override
	public View configContentView() {
		return null;
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {

	}
}
