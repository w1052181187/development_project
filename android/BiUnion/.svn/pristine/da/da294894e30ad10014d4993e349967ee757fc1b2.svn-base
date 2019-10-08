package com.bibinet.biunion.project.bean.subscribe;

import com.bibinet.biunion.project.models.PurchaseInfoDesignModel;
import com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoDesignActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wh on 2017-9-18.
 */

public class PurchaseInfoDesignBean extends PrivateInfoDesignBean{
    private List<PrivateInfoDesignActivity.PIDLayout> pidLayoutList;
    private PurchaseInfoDesignModel purchaseInfoDesignModel;
    public PurchaseInfoDesignBean(PurchaseInfoDesignModel purchaseInfoDesignModel) {
        //必须调用父类
        super();
        this.purchaseInfoDesignModel = purchaseInfoDesignModel;
        initData();

        //初始化数据
        if(purchaseInfoDesignModel!=null){
            selectTypeSelectList.addAll(getValue(purchaseInfoDesignModel.getInfoSubType(), typeSelectList, true));
            selectIndustryList.addAll(getValue(purchaseInfoDesignModel.getTradeFirst(), industryList, false));
            selectProjectTypeList.addAll(getValue(purchaseInfoDesignModel.getProjectType(), projectTypeList, false));
            selectPubdateList.addAll(getValue(purchaseInfoDesignModel.getDateRange(), pubdateList, true));
            selectAreaList.addAll(getValue(purchaseInfoDesignModel.getRegionCode(), areaList, false));
        }

        //填充布局项
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<TypeSelect>(PrivateInfoDesignActivity.LAYOUT_TYPE_TXT, "类型选择", typeSelectList, selectTypeSelectList, 1));
        //多选 采用多选目标
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<Industry>(PrivateInfoDesignActivity.LAYOUT_TYPE_MULTISELECT, "所属行业", industryList, selectIndustryList, 5));
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<ProjectType>(PrivateInfoDesignActivity.LAYOUT_TYPE_IMG, "项目类别", projectTypeList, selectProjectTypeList, 3));
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<Pubdate>(PrivateInfoDesignActivity.LAYOUT_TYPE_RADIO, "发布时间", pubdateList, selectPubdateList, 1));
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<Area>(PrivateInfoDesignActivity.LAYOUT_TYPE_MULTIPLE_TXT, "所属地区", areaList, selectAreaList, 5));
    }

    private void initData() {
        pidLayoutList = new ArrayList<PrivateInfoDesignActivity.PIDLayout>();

        //填充独有数据
        typeSelectList.add(new TypeSelect("政府采购", "1"));
        typeSelectList.add(new TypeSelect("企业采购", "2"));
    }

    @Override
    public String getCode() {
        return orderString(getTypeSelect()) + spec + orderString(getIndustry()) + spec+ orderString(getProjectType()) + spec + orderString(getPubdate())  + spec + orderString(getArea());
    }

    @Override
    public String getOldCode() {
        if(purchaseInfoDesignModel == null){
            return "";
        }
        String typeSelect = orderString(purchaseInfoDesignModel.getInfoSubType());
        String industry = orderString(purchaseInfoDesignModel.getTradeFirst());
        String projectType = orderString(purchaseInfoDesignModel.getProjectType());
        String pubdate = orderString(purchaseInfoDesignModel.getDateRange());
        String area = orderString(purchaseInfoDesignModel.getRegionCode());
        return typeSelect + spec + industry + spec + projectType + spec + pubdate + spec + area;
    }

    @Override
    public String getNotDataCode() {
        if(isSelectId){
            return defaultSelect + spec + industryList.get(0).getId() + spec + defaultSelect + spec + pubdateList.get(0).getId() + spec + defaultSelect;
        }else{
            return defaultSelect + spec + industryList.get(0).getName() + spec + defaultSelect + spec + pubdateList.get(0).getName() + spec + defaultSelect;
        }
    }


    public List<PrivateInfoDesignActivity.PIDLayout> getPidLayoutList() {
        return pidLayoutList;
    }
}
