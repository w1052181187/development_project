package com.chengning.yiqikantoutiao.widget;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.activity.LoginActivity;

public class NoLoginReadArticleHintDialog extends BaseDialogFragment {

	private final static String READ_NUMBER_FORMATE = "您已经阅读了%d篇文章，产生了%d金币的收益。登录后获取的金币转换为收益可提现到微信哦";

	private View mCloseBtn;


	private Button mRegBtn;
	private TextView mMoneyTv;

	private View mView;
	private View mEmpty;

	private int mMoney;
	private int mNum;


	public void setData(int num, int money){
		Bundle bundle = new Bundle();
		bundle.putInt("num",num);
		bundle.putInt("money",money);
		setArguments(bundle);
	}

	@Override
	public void initData() {
		mNum = getArguments().getInt("num");
		mMoney = getArguments().getInt("money");
		mMoneyTv.setText(String.format(READ_NUMBER_FORMATE, mNum, mMoney));

	}

	@Override
	public void initView() {
		mEmpty = mView.findViewById(R.id.dialog_point_hint_empty);
		mCloseBtn = mView.findViewById(R.id.dialog_close_rl);
		mRegBtn = (Button) mView.findViewById(R.id.dialog_reg_btn);
		mMoneyTv = (TextView) mView.findViewById(R.id.dialog_reg_money_tv);

	}


	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_nologin_read_article_hint, null);
		return mView;
	}

	@Override
	public void initListener() {

		mEmpty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				dismissAllowingStateLoss();
			}
		});

		mCloseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});

		mRegBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoginActivity.launch(getActivity());
				dismissAllowingStateLoss();
			}

		});
	}
	
}
