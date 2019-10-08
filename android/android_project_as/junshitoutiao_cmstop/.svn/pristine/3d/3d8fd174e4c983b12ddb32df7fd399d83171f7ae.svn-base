package com.cmstop.jstt.views;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HttpUtil;
import com.cmstop.jstt.Const;
import com.cmstop.jstt.MyStatusResponseHandler;
import com.cmstop.jstt.R;
import com.cmstop.jstt.beans.data.RewardNumBean;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.JUrl;
import com.cmstop.jstt.utils.UIHelper;
import com.cmstop.jstt.wxapi.WXPayEntryActivity;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ArticleRewardDialog extends BaseDialogFragment{
	
	private View mReward;
	private Button mBtnClose;
	private LinearLayout mPriceLl;
	private Button mBtnPay;
	
	private String mArticleTid;
	private int mCurrentIndex;
	private List<RadioButton> mRadioList;
	
	private List<RewardNumBean> mRewardNumList;
	
	@Override
	public void initView() {
		mBtnClose = (Button) mReward.findViewById(R.id.reward_dialog_close);
		mPriceLl = (LinearLayout) mReward.findViewById(R.id.reward_dialog_price_ll);
		mBtnPay = (Button) mReward.findViewById(R.id.reward_btn_pay);
	}
	
	@Override
	public void initListener() {
		mBtnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		
		mBtnPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pay();
			}
		});

	}

	@Override
	public void initData() {
		
		mRadioList = new ArrayList<RadioButton>();
		
		if(mRewardNumList == null){
			mRewardNumList = new ArrayList<RewardNumBean>();
		}
		
		int maxNum = mRewardNumList.size();
		
		boolean isOdd = false;
		int row = 0;
		if(maxNum%2 == 0){
			row = maxNum / 2;
		}else{
			row = maxNum /2 + 1;
			isOdd = true;
		}
		
		for(int i = 0; i<row; i++){
			RewardNumBean bean1 = mRewardNumList.get(i*2);
			RewardNumBean bean2 = mRewardNumList.get(i*2 + 1);
			View view = LayoutInflater.from(getContext()).inflate(R.layout.item_reward_dialog_price, null);
			RadioButton mRadioIndex1 = (RadioButton) view.findViewById(R.id.reward_btn_index1);
			RadioButton mRadioIndex2 = (RadioButton) view.findViewById(R.id.reward_btn_index2);
			mRadioIndex1.setTag(i*2);
			mRadioIndex1.setText(doTextSize(bean1.getPrice()+""));
			mRadioList.add(mRadioIndex1);
			if(i == row - 1 && isOdd){
				mRadioIndex2.setVisibility(View.INVISIBLE);
			}else{
				mRadioIndex2.setTag(i*2 + 1);
				mRadioIndex2.setText(doTextSize(bean2.getPrice()+""));
				mRadioList.add(mRadioIndex2);
			}
			mPriceLl.addView(view);
		}
		
		for (final RadioButton item : mRadioList) {
			item.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						updateRadioUI((Integer)(item.getTag()));
					}
				}
			});
		}
		
		if(maxNum != 0){
			mCurrentIndex = 0;
			mRadioList.get(mCurrentIndex).setChecked(true);
		}
		
		api = WXAPIFactory.createWXAPI(getContext(), Const.WX_APP_ID, false);
    	if (api.isWXAppInstalled()) {//判断是否安装微信客户端  
    		api.registerApp(Const.WX_APP_ID);
    	} else {
    		UIHelper.showToast(getContext(), getResources().getString(R.string.no_wx_no_pay));
    		mBtnPay.setEnabled(false);
    	}

    	DisplayUtil.getInst().init(getContext());
	}

	@Override
	public BaseFragmentActivity getContext() {
		return (BaseFragmentActivity)getActivity();
	}

	@Override
	public View configContentView() {
		mReward = LayoutInflater.from(getContext()).inflate(R.layout.dialog_article_reward, null);
		return mReward;
	}
	
	private void updateRadioUI(int index){
		mCurrentIndex = index;
		for (RadioButton mRadio : mRadioList) {
			mRadio.setChecked(false);
		}
		mRadioList.get(index).setChecked(true);
	}
	
	private Spannable doTextSize(String num){
		Spannable strSpan = new SpannableString(num + "元");
		int index = strSpan.toString().indexOf("元");
		strSpan.setSpan(new AbsoluteSizeSpan(DisplayUtil.getInst().dip2px(20)), 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		strSpan.setSpan(new AbsoluteSizeSpan(DisplayUtil.getInst().dip2px(14)), index, strSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		return strSpan;
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		mPriceLl.removeAllViews();
	}

	private IWXAPI api;
	private void pay(){
		if(mRewardNumList.size() == 0){
			mBtnPay.setEnabled(false);
			return;
		}
		RequestParams params = new RequestParams();
		params.put("aid", mArticleTid);
//		params.put("money", "0.01");
		params.put("money", mRewardNumList.get(mCurrentIndex).getPrice());
		UIHelper.addPD(getActivity(), getResources().getString(R.string.handle_hint));
		HttpUtil.getClient().post(getActivity(), JUrl.SITE + JUrl.URL_GET_REWARD_ORDER, params, new MyStatusResponseHandler() {
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.removePD();
				mBtnPay.setEnabled(true);
				
	        	JSONObject json;
				try {
					startActivity(new Intent(getActivity(), WXPayEntryActivity.class));
					json = new JSONObject(data);
					PayReq req = new PayReq();
					req.appId			= json.getString("appid");
					req.partnerId		= json.getString("partnerid");
					req.prepayId		= json.getString("prepayid");
					req.nonceStr		= json.getString("noncestr");
					req.timeStamp		= json.getString("timestamp");
					req.packageValue	= json.getString("package");
					req.sign			= json.getString("sign");
					req.extData			= "app data"; // optional
					// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
					api.sendReq(req);
				} catch (JSONException e) {
					e.printStackTrace();
					UIHelper.showToast(getActivity(), getResources().getString(R.string.notice_format_error));
				} 
				
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.removePD();
				mBtnPay.setEnabled(true);
				UIHelper.showToast(getActivity(), message);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				UIHelper.removePD();
				mBtnPay.setEnabled(true);
				Common.handleHttpFailure(getActivity(), throwable);
			}
		});

	}

	public String getmArticleTid() {
		return mArticleTid;
	}

	public void setmArticleTid(String mArticleTid) {
		this.mArticleTid = mArticleTid;
	}

	public List<RewardNumBean> getmRewardNumList() {
		return mRewardNumList;
	}

	public void setmRewardNumList(List<RewardNumBean> mRewardNumList) {
		this.mRewardNumList = mRewardNumList;
	}
	
}
