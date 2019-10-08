package com.bibinet.biunion.project.ui.activity.subscribe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.bibinet.biunion.R;
import com.bibinet.biunion.project.adapter.PrivateInfoShowAdapter;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.models.InformationItemsModel;
import com.bibinet.biunion.project.net.emit.PrivateInfoShowEmit;
import com.bibinet.biunion.project.net.firing.PrivateInfoFiring;
import com.bibinet.biunion.project.ui.expand.PageActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.ToastUtils;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-7.
 */

public class PrivateInfoShowActivity extends PageActivity<InformationItemsModel> implements PrivateInfoShowEmit, View.OnClickListener {

    public static final int TYPE_PROJECT = 1;  //项目信息订阅
    public static final int TYPE_TENDER = 2;  //招标信息订阅
    public static final int TYPE_PURCHASE = 3;  //采购信息订阅

    private static final String TYPE = "TYPE";

    private PrivateInfoFiring privateInfoFiring;
    private int startType = -1;

    public static void start(Context context, int startType){
        Intent intent = new Intent(context, PrivateInfoShowActivity.class);
        intent.putExtra(TYPE, startType);
        context.startActivity(intent);
    }

    @Override
    protected int getNotDataViewId() {
        return R.id.act_private_info_show_not_data;
    }

    @Override
    protected PageActivityAdapter getPageAdapter(List<InformationItemsModel> dataList) {
        return new PrivateInfoShowAdapter(this, dataList);
    }

    @Override
    protected void initLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @Override
    protected void onPageCreate(Bundle savedInstanceState) {
        startType = getIntent().getIntExtra(TYPE, -1);
        setRightBtn(0, R.mipmap.bianji, this);
        initView();
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.act_private_info_show_recycler;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.act_private_info_show_swipe_refresh;
    }

    @Override
    protected int getPageLayoutId() {
        return R.layout.activity_private_info_show;
    }

    @Override
    protected void nextPageLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @Override
    protected void refreshLoad(int pageNumb) {
        loadData(pageNumb);
    }

    private void initView() {
        privateInfoFiring = new PrivateInfoFiring(this);
        switch (startType){
            case TYPE_PROJECT:
                setTitleString("项目信息订阅");
                break;
            case TYPE_TENDER:
                setTitleString("招标信息订阅");
                break;
            case TYPE_PURCHASE:
                setTitleString("采购信息订阅");
                break;
            default:
                ToastUtils.showShort("启动异常");
                finish();
                break;
        }
    }

    //取数据
    private void loadData(int pageNumb) {
        String cId = Constants.loginresultInfo.getUser().getUserId();
        privateInfoFiring.getPrivateInfoShowData(pageNumb, cId, startType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //订阅成功重新刷数据
        if(requestCode == PrivateInfoDesignActivity.REQUEST_CODE && resultCode == PrivateInfoDesignActivity.RESULT_CODE){
            initRefresh();
        }
    }

    @Override
    protected int getTitleString() {
        return 0;
    }

    //信息订阅
    @Override
    public void onClick(View view) {
        switch (startType){
            case TYPE_PROJECT:
                PrivateInfoDesignActivity.start(this, TYPE_PROJECT);
                break;
            case TYPE_TENDER:
                PrivateInfoDesignActivity.start(this, TYPE_TENDER);
                break;
            case TYPE_PURCHASE:
                PrivateInfoDesignActivity.start(this, TYPE_PURCHASE);
                break;
            default:
                ToastUtils.showShort("启动异常");
                finish();
                break;
        }
    }
}
