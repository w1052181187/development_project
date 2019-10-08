package com.chengning.fenghuo.widget;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.ClipboardManager;
import android.text.TextUtils;

import com.chengning.fenghuo.Consts.ArticleType;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.ArticleManagerUtils.CollectState;
import com.chengning.fenghuo.util.ArticleManagerUtils.DeleteListener;
import com.chengning.fenghuo.util.UIHelper;

public class DynamicCommentClickDialog extends DialogFragment {

	private boolean mIsSelfComment;
	private BaseArticlesBean mCommentBean;
	private DeleteListener mDeleteListener;
	private String mCopyContent;
	private Activity mContext;
	private boolean isOnlyCopy = false;

	public void setData(Activity activity,BaseArticlesBean bean, String copyContent,boolean isSelfComment,DeleteListener listener) {
		setData(activity, bean, copyContent, isSelfComment, listener, false);
	}
	
	public void setData(Activity activity,BaseArticlesBean bean, String copyContent,boolean isSelfComment,DeleteListener listener, boolean isOnlyCopy) {
		
		this.mContext = activity;
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
			items = new String[] {"复制","收藏","举报","删除"};
		} else {
			items = new String[] {"复制","收藏","举报"};
		}
    	DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArticleManagerUtils managerUtils = new ArticleManagerUtils();
				switch(which){
				//复制
				case 0:
					
					if (!TextUtils.isEmpty(mCopyContent)) {
						ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
				        // 将文本内容放到系统剪贴板里。
//						Matcher siteMatcher = Pattern.compile(Consts.SITE_COMPILE_STR).matcher(mCopyContent);
//						
//						while (siteMatcher.find()) {
//							String allValue = siteMatcher.group(0);
//							String value = siteMatcher.group(1);
//							mCopyContent = mCopyContent.replace(allValue, value);
//						}
						cm.setText(mCopyContent);
				        UIHelper.showToast(mContext, R.string.comment_copy_hint);
					}
			        
					dismissAllowingStateLoss();
					break;
				//收藏
				case 1:
					ArticleManagerUtils managerUtilsCollect = new ArticleManagerUtils(new CollectState() {
						
						@Override
						public void setState(boolean state) {
							if (state ) {
								if (mContext == null) {
									return;
								}
								UIHelper.showToast(
										mContext,"圈子" + mContext.getResources().getString(
														R.string.collect_hint));
							}
						}
					});
					managerUtilsCollect.doCollect(mContext, mCommentBean, ArticleType.DYNAMIC);
					dismissAllowingStateLoss();
					break;
				//举报
				case 2:
					managerUtils.getReport(mContext, mCommentBean.getTid());
					dismissAllowingStateLoss();
					break;
				//删除
				case 3:
					managerUtils.deleteDynamic(mContext, mCommentBean.getTid(), mDeleteListener);
					dismissAllowingStateLoss();
					break;
				default :
					break;
				
				}
			}
		};
		
		AlertDialog dialog = new AlertDialog.Builder(mContext).setItems(items,
				click).create();
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}
}
