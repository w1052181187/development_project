package com.bibinet.biunion.project.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bibinet on 2017-6-7.
 */

public class TenderInfoDesignModel extends BaseModel {
    //日否有值 没提交过数据没有值（第一次） 这里是false 其他情况true
    private String messageFlag; //请求标志
    //数据
    private String infoType;  //主类型 项目 招标 采购
    private String infoSubType; //类型选择
    private String tradeFirst;  //行业
    private String projectType; //工程 货物 服务
    private String dateRange; //日期
    private String regionCode; //地区


    public String getMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(String messageFlag) {
        this.messageFlag = messageFlag;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfoSubType() {
        return infoSubType;
    }

    public void setInfoSubType(String infoSubType) {
        this.infoSubType = infoSubType;
    }

    public String getTradeFirst() {
        return tradeFirst;
    }

    public void setTradeFirst(String tradeFirst) {
        this.tradeFirst = tradeFirst;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
