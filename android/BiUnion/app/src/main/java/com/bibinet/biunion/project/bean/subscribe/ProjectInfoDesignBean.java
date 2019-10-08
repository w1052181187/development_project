package com.bibinet.biunion.project.bean.subscribe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.models.ProjectInfoDesignModel;
import com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoDesignActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wh on 2017-9-18.
 */

public class ProjectInfoDesignBean extends PrivateInfoDesignBean {
    private List<PrivateInfoDesignActivity.PIDLayout> pidLayoutList;


    //    private String projectStage;
    private List<BaseData> selectProjectStageList;
    private List<ProjectStage> projectStageList;

    private ProjectInfoDesignModel projectInfoDesignModel;

    //请求来的数据 判断是否有初值
    public ProjectInfoDesignBean(ProjectInfoDesignModel projectInfoDesignModel) {
        //必须调用父类
        super();
        this.projectInfoDesignModel = projectInfoDesignModel;
        initData();

        //初始化的值
        if (projectInfoDesignModel != null) {
            selectTypeSelectList.addAll(getValue(projectInfoDesignModel.getInfoSubType(), typeSelectList, true));
            selectIndustryList.addAll(getValue(projectInfoDesignModel.getTradeFirst(), industryList, false));
            selectProjectTypeList.addAll(getValue(projectInfoDesignModel.getProjectType(), projectTypeList, false));
            selectPubdateList.addAll(getValue(projectInfoDesignModel.getDateRange(), pubdateList, true));
            selectProjectStageList.addAll(getValue(projectInfoDesignModel.getProjectStage(), projectStageList, false));
            selectAreaList.addAll(getValue(projectInfoDesignModel.getRegionCode(), areaList, false));
        }

        //填充布局项
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<TypeSelect>(PrivateInfoDesignActivity.LAYOUT_TYPE_TXT, "类型选择", typeSelectList, selectTypeSelectList, 1));
        //多选 采用多选目标
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<Industry>(PrivateInfoDesignActivity.LAYOUT_TYPE_MULTISELECT, "所属行业", industryList, selectIndustryList, 5));
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<ProjectType>(PrivateInfoDesignActivity.LAYOUT_TYPE_IMG, "项目类别", projectTypeList, selectProjectTypeList, 3));
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<Pubdate>(PrivateInfoDesignActivity.LAYOUT_TYPE_RADIO, "发布时间", pubdateList, selectPubdateList, 1));
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<ProjectStage>(PrivateInfoDesignActivity.LAYOUT_TYPE_MULTISELECT, "项目阶段", projectStageList, selectProjectStageList, 5));
        pidLayoutList.add(new PrivateInfoDesignActivity.PIDLayout<Area>(PrivateInfoDesignActivity.LAYOUT_TYPE_MULTIPLE_TXT, "所属地区", areaList, selectAreaList, 5));
    }

    private void initData() {
        pidLayoutList = new ArrayList<PrivateInfoDesignActivity.PIDLayout>();
        projectStageList = new ArrayList<ProjectStage>();
        selectProjectStageList = new ArrayList<BaseData>();

        //填充独有数据
        typeSelectList.add(new TypeSelect("拟在建项目", "1"));
        typeSelectList.add(new TypeSelect("业主委托项目", "2"));
        typeSelectList.add(new TypeSelect("PPP项目", "3"));

        //填充阶段数据
        projectStageList.add(new ProjectStage("", "全部"));
        projectStageList.add(new ProjectStage("1", "可行性研究"));
        projectStageList.add(new ProjectStage("2", "设计阶段"));
        projectStageList.add(new ProjectStage("3", "审批/核准/备案"));
        projectStageList.add(new ProjectStage("4", "项目立项"));
        projectStageList.add(new ProjectStage("5", "环境影响评价"));
        projectStageList.add(new ProjectStage("6", "施工准备"));
        projectStageList.add(new ProjectStage("7", "施工在建"));
        projectStageList.add(new ProjectStage("8", "项目竣工"));
    }

    public String getProjectStage() {
        return setValue(selectProjectStageList, false);
    }

    public List<PrivateInfoDesignActivity.PIDLayout> getPidLayoutList() {
        return pidLayoutList;
    }

    @Override
    public String getCode() {
        return orderString(getTypeSelect()) + spec + orderString(getIndustry()) + spec + orderString(getProjectType()) + spec + orderString(getPubdate()) + spec + orderString(getProjectStage()) + spec + orderString(getArea());
    }

    //请求到原来的数据拼接的码
    @Override
    public String getOldCode() {
        if(projectInfoDesignModel == null){
            return "";
        }
        String typeSelect = orderString(projectInfoDesignModel.getInfoSubType());
        String industry = orderString(projectInfoDesignModel.getTradeFirst());
        String projectType = orderString(projectInfoDesignModel.getProjectType());
        String pubdate = orderString(projectInfoDesignModel.getDateRange());
        String projectStage = orderString(projectInfoDesignModel.getProjectStage());
        String area = orderString(projectInfoDesignModel.getRegionCode());
        return typeSelect + spec + industry + spec + projectType + spec + pubdate + spec + projectStage + spec + area;
    }

    @Override
    public String getNotDataCode() {
        if (isSelectId) {
            return defaultSelect + spec + industryList.get(0).getId() + spec + defaultSelect + spec + pubdateList.get(0).getId() + spec + projectStageList.get(0).getId() + spec + defaultSelect;
        } else {
            return defaultSelect + spec + industryList.get(0).getName() + spec + defaultSelect + spec + pubdateList.get(0).getName() + spec + projectStageList.get(0).getName() + spec + defaultSelect;
        }
    }

    public class ProjectStage extends BaseData {
        public ProjectStage(String id, String name) {
            super(id, name, -1);
        }
    }

}
