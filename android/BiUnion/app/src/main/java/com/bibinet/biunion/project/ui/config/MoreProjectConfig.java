package com.bibinet.biunion.project.ui.config;

import com.bibinet.biunion.project.bean.KeyValueBean;
import com.bibinet.biunion.project.bean.TypeFilterBean;
import com.bibinet.biunion.project.ui.manager.TypeFilterManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wh on 2017-12-4.
 */

public class MoreProjectConfig {
    private List<TypeFilterBean> typeFilterBeanList;

    private List<KeyValueBean> industry;
    private List<KeyValueBean> area;
    private List<KeyValueBean> time;

    public MoreProjectConfig(){
        typeFilterBeanList = new ArrayList<TypeFilterBean>();
        industry = new ArrayList<KeyValueBean>();
        area = new ArrayList<KeyValueBean>();
        time = new ArrayList<KeyValueBean>();

        industry.add(new KeyValueBean("z", "全部"));
        industry.add(new KeyValueBean("A", "农、林、牧、渔业"));
        industry.add(new KeyValueBean("B", "采矿业"));
        industry.add(new KeyValueBean("C", "制造业"));
        industry.add(new KeyValueBean("D", "电力、热力、燃气及水生产和供应业"));
        industry.add(new KeyValueBean("E", "建筑业"));
        industry.add(new KeyValueBean("F", "批发和零售业"));
        industry.add(new KeyValueBean("G", "交通运输、仓储和邮政业"));
        industry.add(new KeyValueBean("H", "住宿和餐饮业"));
        industry.add(new KeyValueBean("I", "信息传输、软件和信息技术服务业"));
        industry.add(new KeyValueBean("J", "金融业"));
        industry.add(new KeyValueBean("K", "房地产业"));
        industry.add(new KeyValueBean("L", "租赁和商务服务业"));
        industry.add(new KeyValueBean("M", "科学研究和技术服务业"));
        industry.add(new KeyValueBean("N", "水利、环境和公共设施管理业"));
        industry.add(new KeyValueBean("O", "居民服务、修理和其他服务业"));
        industry.add(new KeyValueBean("P", "教育"));
        industry.add(new KeyValueBean("Q", "卫生和社会工作"));
        industry.add(new KeyValueBean("R", "文化、体育和娱乐业"));
        industry.add(new KeyValueBean("S", "公共管理、社会保障和社会组织"));
        industry.add(new KeyValueBean("T", "国际组织"));

        area.add(new KeyValueBean("100000", "全部"));
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

        typeFilterBeanList.add(new TypeFilterBean("z", "行业", industry));
        typeFilterBeanList.add(new TypeFilterBean("100000", "地区", area));
        typeFilterBeanList.add(new TypeFilterBean("0", "时间", time));
    }

    public List<TypeFilterBean> getTypeFilterBeanList() {
        return typeFilterBeanList;
    }

    public void setTypeFilterBeanList(List<TypeFilterBean> typeFilterBeanList) {
        this.typeFilterBeanList = typeFilterBeanList;
    }
}
