package com.bibinet.biunion.project.config;

import com.bibinet.biunion.project.bean.InformationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wh on 2017-11-16.
 */

public class InformationConfig {
    //项目信息
    public static final int ICT_PROJECT = 0;
    //拟在建项目
    public static final int ICT_PROJECT_NI = 1;
    //业主委托项目
    public static final int ICT_PROJECT_YE = 2;
    //PPP项目
    public static final int ICT_PROJECT_PP = 3;

    //招标信息
    public static final int ICT_TENDER = 4;
    //招标公告
    public static final int ICT_TENDER_ZA = 5;
    //中标候选人公示
    public static final int ICT_TENDER_ZR = 6;
    //中标公告
    public static final int ICT_TENDER_ZO = 7;
    //变更公告
    public static final int ICT_TENDER_BI = 8;
    //热门招标
    public static final int ICT_TENDER_RM = 9;

    //采购信息
    public static final int ICT_PRUCHASE = 10;
    //政府采购
    public static final int ICT_PRUCHASE_ZH = 11;
    //企业采购
    public static final int ICT_PRUCHASE_QI = 12;

    private List<InformationBean> dataList;



    public InformationConfig() {
        dataList = new ArrayList<InformationBean>();
        //项目信息
        dataList.add(new InformationBean(ICT_PROJECT, ICT_PROJECT, "1000", "3001,2001,2002", "项目信息", "", "本日更新项目信息"));
        //拟在建项目
        dataList.add(new InformationBean(ICT_PROJECT, ICT_PROJECT_NI, "1", "2002", "拟在建项目信息", "拟在建项目详情", "本日更新信息"));
        //业主委托项目
        dataList.add(new InformationBean(ICT_PROJECT, ICT_PROJECT_YE, "2", "2001", "业主委托项目信息", "业主委托项目详情", "本日更新信息"));
        //PPP项目
        dataList.add(new InformationBean(ICT_PROJECT_PP, ICT_PROJECT_PP, "", "3001", "PPP项目信息", "PPP项目详情", "本日更新信息"));

        //招标信息
        dataList.add(new InformationBean(ICT_TENDER, ICT_TENDER, "1000", "1001,1002,1003,1004,1005", "招标信息", "", "本日更新招标信息"));
        //招标公告
        dataList.add(new InformationBean(ICT_TENDER, ICT_TENDER_ZA, "1", "1001", "招标公告信息", "招标公告详情", "本日更新信息"));
        //中标候选人公示
        dataList.add(new InformationBean(ICT_TENDER, ICT_TENDER_ZR, "2", "1003", "中标候选人公示信息", "中标候选人公示详情", "本日更新信息"));
        //中标公告
        dataList.add(new InformationBean(ICT_TENDER, ICT_TENDER_ZO, "3", "1002", "中标公告信息", "中标公告详情", "本日更新信息"));
        //变更公告
        dataList.add(new InformationBean(ICT_TENDER, ICT_TENDER_BI, "4", "1004", "变更公告信息", "变更公告详情", "本日更新信息"));
        //热门招标
        dataList.add(new InformationBean(ICT_TENDER, ICT_TENDER_RM, "5", "", "热门招标信息", "热门招标详情", ""));

        //采购信息
        dataList.add(new InformationBean(ICT_PRUCHASE, ICT_PRUCHASE, "1000", "4001,4002", "采购信息", "", "本日更新采购信息"));
        //政府采购
        dataList.add(new InformationBean(ICT_PRUCHASE, ICT_PRUCHASE_ZH, "1", "4001", "政府采购信息", "政府采购详情", "本日更新信息"));
        //企业采购
        dataList.add(new InformationBean(ICT_PRUCHASE, ICT_PRUCHASE_QI, "2", "4002", "企业采购信息", "企业采购详情", "本日更新信息"));

    }


    public List<InformationBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<InformationBean> dataList) {
        this.dataList = dataList;
    }
}
