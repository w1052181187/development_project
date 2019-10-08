package com.bibinet.biunion.project.ui.config;

import com.bibinet.biunion.project.bean.KeyValueBean;
import com.bibinet.biunion.project.bean.TypeFilterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wh on 2017-12-4.
 */

public class SearchResultConfig {
    private List<TypeFilterBean> typeFilterBeanList;

    private List<KeyValueBean> industry;
    private List<KeyValueBean> area;
    private List<KeyValueBean> time;

    public SearchResultConfig(){
        typeFilterBeanList = new ArrayList<TypeFilterBean>();
        industry = new ArrayList<KeyValueBean>();
        area = new ArrayList<KeyValueBean>();
        time = new ArrayList<KeyValueBean>();

        industry.add(new KeyValueBean("", "全部"));
        industry.add(new KeyValueBean("1005", "最新招标"));
        industry.add(new KeyValueBean("1001", "招标公告"));
        industry.add(new KeyValueBean("1004", "变更公告"));
        industry.add(new KeyValueBean("1002", "中标候选人公示"));
        industry.add(new KeyValueBean("1003", "中标公告"));
        industry.add(new KeyValueBean("4001", "政府采购"));
        industry.add(new KeyValueBean("4002", "企业采购"));
        industry.add(new KeyValueBean("2002", "拟在建项目"));
        industry.add(new KeyValueBean("2001", "业主委托项目"));
        industry.add(new KeyValueBean("3001", "PPP项目"));

        area.add(new KeyValueBean("", "全部"));
        area.add(new KeyValueBean("110000", "北京市"));
        area.add(new KeyValueBean("120000", "天津市"));
        area.add(new KeyValueBean("130000", "河北省"));
        area.add(new KeyValueBean("140000", "山西省"));
        area.add(new KeyValueBean("150000", "内蒙古自治区"));
        area.add(new KeyValueBean("210000", "辽宁省"));
        area.add(new KeyValueBean("220000", "吉林省"));
        area.add(new KeyValueBean("230000", "黑龙江省"));
        area.add(new KeyValueBean("310000", "上海市"));
        area.add(new KeyValueBean("320000", "江苏省"));
        area.add(new KeyValueBean("330000", "浙江省"));
        area.add(new KeyValueBean("340000", "安徽省"));
        area.add(new KeyValueBean("350000", "福建省"));
        area.add(new KeyValueBean("360000", "江西省"));
        area.add(new KeyValueBean("370000", "山东省"));
        area.add(new KeyValueBean("410000", "河南省"));
        area.add(new KeyValueBean("420000", "湖北省"));
        area.add(new KeyValueBean("430000", "湖南省"));
        area.add(new KeyValueBean("440000", "广东省"));
        area.add(new KeyValueBean("450000", "广西壮族自治区"));
        area.add(new KeyValueBean("460000", "海南省"));
        area.add(new KeyValueBean("500000", "重庆市"));
        area.add(new KeyValueBean("510000", "四川省"));
        area.add(new KeyValueBean("520000", "贵州省"));
        area.add(new KeyValueBean("530000", "云南省"));
        area.add(new KeyValueBean("540000", "西藏自治区"));
        area.add(new KeyValueBean("610000", "陕西省"));
        area.add(new KeyValueBean("620000", "甘肃省"));
        area.add(new KeyValueBean("630000", "青海省"));
        area.add(new KeyValueBean("640000", "宁夏回族自治区"));
        area.add(new KeyValueBean("650000", "新疆维吾尔自治区"));
        area.add(new KeyValueBean("710000", "台湾省"));
        area.add(new KeyValueBean("810000", "香港特别行政区"));
        area.add(new KeyValueBean("820000", "澳门特别行政区"));

        time.add(new KeyValueBean("0", "全部"));
        time.add(new KeyValueBean("1", "近一周"));
        time.add(new KeyValueBean("2", "近一个月"));
        time.add(new KeyValueBean("3", "近三个月"));

        typeFilterBeanList.add(new TypeFilterBean("", "类型", industry));
        typeFilterBeanList.add(new TypeFilterBean("", "地区", area));
        typeFilterBeanList.add(new TypeFilterBean("0", "时间", time));
    }

    public List<TypeFilterBean> getTypeFilterBeanList() {
        return typeFilterBeanList;
    }

    public void setTypeFilterBeanList(List<TypeFilterBean> typeFilterBeanList) {
        this.typeFilterBeanList = typeFilterBeanList;
    }
}
