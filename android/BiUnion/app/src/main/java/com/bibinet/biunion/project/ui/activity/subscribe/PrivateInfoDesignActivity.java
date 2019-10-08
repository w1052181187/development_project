package com.bibinet.biunion.project.ui.activity.subscribe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.adapter.PrivateAeraAdapter;
import com.bibinet.biunion.project.adapter.PrivateInfoDesignMultiselectAdapter;
import com.bibinet.biunion.project.adapter.PrivateInfoDesignTxtAdapter;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.subscribe.PrivateInfoDesignBean;
import com.bibinet.biunion.project.bean.subscribe.ProjectInfoDesignBean;
import com.bibinet.biunion.project.bean.subscribe.PurchaseInfoDesignBean;
import com.bibinet.biunion.project.bean.subscribe.TenderInfoDesignBean;
import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.models.ProjectInfoDesignModel;
import com.bibinet.biunion.project.models.PurchaseInfoDesignModel;
import com.bibinet.biunion.project.models.TenderInfoDesignModel;
import com.bibinet.biunion.project.net.emit.PrivateInfoDesignEmit;
import com.bibinet.biunion.project.net.firing.PrivateInfoFiring;
import com.bibinet.biunion.project.ui.base.BaseActivity;
import com.bibinet.biunion.project.ui.custom.PrivateInfoDesignRadioLayout;
import com.bibinet.biunion.project.ui.expand.TitleActivity;
import com.bibinet.biunion.project.utils.ToastUtils;
import static com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoShowActivity.*;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-1.
 */

public class PrivateInfoDesignActivity extends TitleActivity implements PrivateInfoDesignEmit {
    //定义几种订阅布局的实体类 公共静态
    public static final int LAYOUT_TYPE_TXT = 0; //文字选择
    public static final int LAYOUT_TYPE_IMG = 1; //图片选择
    public static final int LAYOUT_TYPE_RADIO = 2; //弹窗选择 单选
    public static final int LAYOUT_TYPE_MULTISELECT = 3; //弹窗选择 多选
    public static final int LAYOUT_TYPE_MULTIPLE_TXT = 4; //文字选择 多个
    private static final String TAG_SUBSCRIBE_TYPE = "TAG_SUBSCRIBE_TYPE";
    public static int REQUEST_CODE = 2001;
    public static int RESULT_CODE = 2002;
    @BindView(R.id.act_private_info_design_main_view)
    View mainGroupV;
    @BindView(R.id.act_private_net_error)
    View errorV;
    @BindView(R.id.act_private_info_design_main)
    LinearLayout mainV;
    @BindView(R.id.act_private_info_design_confirm)
    View confirmV;
    private int startType = -1;
    private PrivateInfoFiring privateInfoFiring;
    private ProjectInfoDesignBean projectInfoDesignBean;
    private TenderInfoDesignBean tenderInfoDesignBean;
    private PurchaseInfoDesignBean purchaseInfoDesignBean;
    private PrivateInfoDesignBean bean;

    private String userId;

    public static void start(PrivateInfoShowActivity activity, int startType) {
        Intent intent = new Intent(activity, PrivateInfoDesignActivity.class);
        intent.putExtra(TAG_SUBSCRIBE_TYPE, startType);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected int getTitleLayoutId() {
        return R.layout.activity_private_info_design;
    }


    @Override
    protected void onTitleCreate(Bundle savedInstanceState) {
        privateInfoFiring = new PrivateInfoFiring(this);
        startType = getIntent().getIntExtra(TAG_SUBSCRIBE_TYPE, -1);
        userId = Constants.loginresultInfo.getUser().getUserId();

        initTitle();
        //请求设计
        privateInfoFiring.getPrivateInfoDesignData(userId, startType);
//        initData(null);
    }

    @Override
    protected int getTitleString() {
        return 0;
    }

    @Override
    public void onSetDesignSuccess(BaseModel bean) {
        if(bean.getResCode().equals("0000")){
            ToastUtils.showShort("订阅成功");
            setResult(RESULT_CODE);
            finish();
        }else{
            ToastUtils.showShort(bean.getResMessage());
        }
    }

    @Override
    public void onSetDesignFail(String s) {
        ToastUtils.showShort("网络异常");
    }

    @Override
    public void onGetDesignSuccess(BaseModel bean) {
        mainGroupV.setVisibility(View.VISIBLE);
        errorV.setVisibility(View.GONE);
        initData(bean);
    }

    @Override
    public void onGetDesignFail(String s) {
        errorV.setVisibility(View.VISIBLE);
        mainGroupV.setVisibility(View.GONE);
        ToastUtils.showShort("网络异常");
    }

    private void initData(BaseModel model) {
        switch (startType) {
            case TYPE_PROJECT:
                //填充项目订阅布局
                projectInfoDesignBean = new ProjectInfoDesignBean((ProjectInfoDesignModel) model);
                initView(projectInfoDesignBean.getPidLayoutList());
                bean = projectInfoDesignBean;
                break;
            case TYPE_TENDER:
                //填充招标订阅布局
                tenderInfoDesignBean = new TenderInfoDesignBean((TenderInfoDesignModel) model);
                initView(tenderInfoDesignBean.getPidLayoutList());
                bean = tenderInfoDesignBean;
                break;
            case TYPE_PURCHASE:
                purchaseInfoDesignBean = new PurchaseInfoDesignBean((PurchaseInfoDesignModel) model);
                initView(purchaseInfoDesignBean.getPidLayoutList());
                bean = purchaseInfoDesignBean;
                break;
            default:
                ToastUtils.showShort("启动异常！");
                finish();
                break;
        }
        onDataChange();
    }

    @OnClick(R.id.act_private_info_design_confirm)
    void confirm(){
        //根据类型做检测 检查数据集合
        switch (startType) {
            case TYPE_PROJECT:
                //打印结果
                Log.e("typeselect", projectInfoDesignBean.getTypeSelect()+"-");
                Log.e("projecttype", projectInfoDesignBean.getProjectType()+"-");
                Log.e("getIndustry", projectInfoDesignBean.getIndustry()+"-");
                Log.e("getPubdate", projectInfoDesignBean.getPubdate()+"-");
                Log.e("getArea", projectInfoDesignBean.getArea()+"-");
                Log.e("getProjectStage", projectInfoDesignBean.getProjectStage()+"-");
                bean = projectInfoDesignBean;
                break;
            case TYPE_TENDER:
                //打印结果
                Log.e("typeselect", tenderInfoDesignBean.getTypeSelect()+"-");
                Log.e("projecttype", tenderInfoDesignBean.getProjectType()+"-");
                Log.e("getIndustry", tenderInfoDesignBean.getIndustry()+"-");
                Log.e("getPubdate", tenderInfoDesignBean.getPubdate()+"-");
                Log.e("getArea", tenderInfoDesignBean.getArea()+"-");
                bean = tenderInfoDesignBean;
                break;
            case TYPE_PURCHASE:
                //打印结果
                Log.e("typeselect", purchaseInfoDesignBean.getTypeSelect()+"-");
                Log.e("projecttype", purchaseInfoDesignBean.getProjectType()+"-");
                Log.e("getIndustry", purchaseInfoDesignBean.getIndustry()+"-");
                Log.e("getPubdate", purchaseInfoDesignBean.getPubdate()+"-");
                Log.e("getArea", purchaseInfoDesignBean.getArea()+"-");
                bean = purchaseInfoDesignBean;
                break;
            default:
                ToastUtils.showShort("数据异常！");
                finish();
                break;
        }
        privateInfoFiring.setPrivateInfoDesignData(userId, bean);
    }

    private void initTitle() {
        switch (startType) {
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
                ToastUtils.showShort("启动异常！");
                finish();
                break;
        }
    }

    private void initView(final List<PrivateInfoDesignActivity.PIDLayout> pidLayoutList) {
        for (int i = 0; i < pidLayoutList.size(); i++) {
            final PIDLayout pidLayout = pidLayoutList.get(i);
            View view = null;
            switch (pidLayout.type) {
                case LAYOUT_TYPE_TXT:
                    view = addTxtType(pidLayout);
                    break;
                case LAYOUT_TYPE_IMG:
                    view = addImgType(pidLayout);
                    break;
                case LAYOUT_TYPE_MULTIPLE_TXT:
                    view = addMultipleType(pidLayout);
                    break;
                case LAYOUT_TYPE_RADIO:
                    view = addRadioType(pidLayout);
                    break;
                case LAYOUT_TYPE_MULTISELECT:
                    view = addMultiselect(pidLayout);
                    break;
            }
            mainV.addView(view, i);
        }

    }

    private void addSelectData(int pos, final PIDLayout pidLayout) {
        if(pos == -1){
            pidLayout.selectTargetList.clear();
        }else {
            pidLayout.selectTargetList.clear();
            pidLayout.selectTargetList.add(pidLayout.dataTagList.get(pos));
        }
    }

    //使用适配器显示选择项 一行2个
    private View addTxtType(final PIDLayout pidLayout) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_private_info_design_txt, null, false);
        ((TextView) view.findViewById(R.id.v_private_info_design_txt_main)).setText(pidLayout.main);
        RecyclerView radioLayout = (RecyclerView) view.findViewById(R.id.v_private_info_design_txt_radio);
        //单选按钮 初始化一个
        final PrivateInfoDesignTxtAdapter adapter = new PrivateInfoDesignTxtAdapter(radioLayout, this, pidLayout.dataTagList, pidLayout.selectTargetList);
        radioLayout.setAdapter(adapter);
        return view;
    }

    private View addImgType(final PIDLayout pidLayout) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_private_info_design_img, null, false);
        ((TextView) view.findViewById(R.id.v_private_info_design_img_main)).setText(pidLayout.main);
        PrivateInfoDesignRadioLayout radioLayout = (PrivateInfoDesignRadioLayout) view.findViewById(R.id.v_private_info_design_img_radio);
        for (int j = 0; j < pidLayout.dataTagList.size(); j++) {
            View tag = LayoutInflater.from(this).inflate(R.layout.view_private_info_design_img_tag, null, false);
            int main = ((PrivateInfoDesignBean.BaseData) pidLayout.dataTagList.get(j)).getImg();
            ((ImageView) tag.findViewById(R.id.v_private_info_design_img_tag_main)).setImageResource(main);
            tag.setTag(pidLayout.dataTagList.get(j));
            radioLayout.addView(tag);
        }
        radioLayout.setInitValue(pidLayout.selectTargetList);
        radioLayout.setOnRadioLayoutOnClickListener(new PrivateInfoDesignRadioLayout.OnRadioLayoutOnClickListener() {
            @Override
            public void onClickPos(int pos) {
                int index = pidLayout.selectTargetList.indexOf(pidLayout.dataTagList.get(pos));
                if(index == -1){
                    //没有
                    pidLayout.selectTargetList.add(pidLayout.dataTagList.get(pos));
                }else{
                    pidLayout.selectTargetList.remove(pidLayout.dataTagList.get(pos));
                }
                onDataChange();
            }
        });
        return view;
    }

    private View addMultipleType(final PIDLayout pidLayout) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_private_info_design_multiple_txt, null, false);
        ((TextView) view.findViewById(R.id.v_private_info_design_multiple_txt_main)).setText(pidLayout.main);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.v_private_info_design_multiple_txt_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(gridLayoutManager);
        PrivateAeraAdapter aeraAdapter = new PrivateAeraAdapter(this, pidLayout.dataTagList, pidLayout.selectTargetList);
        recyclerView.setAdapter(aeraAdapter);
        recyclerView.setFocusable(false);
       return view;
    }

    private View addRadioType(final PIDLayout pidLayout) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_private_info_design_popup, null, false);
        ((TextView) view.findViewById(R.id.v_private_info_design_popup_main)).setText(pidLayout.main);
        view.findViewById(R.id.v_private_info_design_popup_operation).setVisibility(View.GONE);
        RecyclerView mainWindow = (RecyclerView) view.findViewById(R.id.v_private_info_design_popup_window);
        //单选按钮 初始化一个
        PrivateInfoDesignMultiselectAdapter adapter = new PrivateInfoDesignMultiselectAdapter(mainWindow, this, pidLayout.dataTagList, pidLayout.selectTargetList);
        adapter.setListener(new PrivateInfoDesignMultiselectAdapter.OnSelectListener() {
            @Override
            public void onSelectPos(int pos) {
                addSelectData(pos, pidLayout);
                onDataChange();
            }
        });
        mainWindow.setAdapter(adapter);
        return view;
    }

    private View addMultiselect(final PIDLayout pidLayout) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_private_info_design_popup, null, false);
        ((TextView) view.findViewById(R.id.v_private_info_design_popup_main)).setText(pidLayout.main);
        RecyclerView mainWindow = (RecyclerView) view.findViewById(R.id.v_private_info_design_popup_window);
        //单选按钮 初始化一个
        final PrivateInfoDesignMultiselectAdapter adapter = new PrivateInfoDesignMultiselectAdapter(mainWindow, this, pidLayout.dataTagList, pidLayout.selectTargetList, pidLayout.maxSelectCount);
        mainWindow.setAdapter(adapter);
        //添加 删除
        view.findViewById(R.id.v_private_info_design_popup_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItem();
            }
        });
        final Button deleteB = (Button) view.findViewById(R.id.v_private_info_design_popup_delete);
        //需要关联操作
        adapter.setDeleteBtn(deleteB);
        view.findViewById(R.id.v_private_info_design_popup_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.deleteItem(deleteB);
            }
        });
        return view;
    }


    public void onDataChange() {
        if(bean == null){
            return;
        }
        //检测数据变化
        boolean isNot = bean.isNotData();
        boolean isUpdate = bean.isUpdateData();
        if(isNot || isUpdate){
            confirmV.setSelected(false);
            confirmV.setEnabled(false);
        }else{
            confirmV.setSelected(true);
            confirmV.setEnabled(true);
        }
        Log.e("--", "--"+isNot+"--"+isUpdate);
    }



    public static class PIDLayout<T extends PrivateInfoDesignBean.BaseData> {
        private int type;
        private String main;
        private List<T> dataTagList;
        private List<PrivateInfoDesignBean.BaseData> selectTargetList;
        private int maxSelectCount;

        //利用构造函数区分多选单选
        public PIDLayout(int type, String main, List<T> dataTagList, List<PrivateInfoDesignBean.BaseData> selectTargetList, int maxSelectCount) {
            this.type = type;
            this.main = main;
            this.dataTagList = dataTagList;
            this.selectTargetList = selectTargetList;
            this.maxSelectCount = maxSelectCount;
        }

    }



}
