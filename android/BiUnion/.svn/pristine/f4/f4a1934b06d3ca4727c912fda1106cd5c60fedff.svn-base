package com.bibinet.biunion.project.bean.subscribe;

import android.util.Log;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoDesignActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by Wh on 2017-9-18.
 */

public abstract class PrivateInfoDesignBean {
    //没有选择 默认给空字符串
    protected final String defaultSelect = "";
    protected final boolean isSelectId = true;

    protected final String spec = "=||=";

    //项目类型 多选
    protected List<BaseData> selectProjectTypeList;
    //    protected String projectType;
    protected List<ProjectType> projectTypeList;

    //发布时间 单选
    protected List<BaseData> selectPubdateList;
    //    protected String pubdate;
    protected List<Pubdate> pubdateList;

    //多选 地区
    protected List<BaseData> selectAreaList;
    //    protected String area;
    protected List<Area> areaList;

    //多选 给个选择列表
    protected List<BaseData> selectIndustryList;
    //    protected String industry;
    protected List<Industry> industryList;

    //单选 集合
    protected List<BaseData> selectTypeSelectList;
    //    protected String typeSelect;
    protected List<TypeSelect> typeSelectList;


    public PrivateInfoDesignBean() {
        this.projectTypeList = new ArrayList<ProjectType>();
        this.pubdateList = new ArrayList<Pubdate>();
        this.areaList = new ArrayList<Area>();
        this.industryList = new ArrayList<Industry>();
        //只创建 不填充数据
        this.typeSelectList = new ArrayList<TypeSelect>();

        //选择列表填充 多选 单选 公用
        selectIndustryList = new ArrayList<BaseData>();
        selectTypeSelectList = new ArrayList<BaseData>();
        selectAreaList = new ArrayList<BaseData>();
        selectProjectTypeList = new ArrayList<BaseData>();
        selectPubdateList = new ArrayList<BaseData>();

        //选择项目填充
        projectTypeList.add(new ProjectType("工程", "A", R.drawable.select_private_persion_design_project_img));
        projectTypeList.add(new ProjectType("货物", "B", R.drawable.select_private_persion_design_good_img));
        projectTypeList.add(new ProjectType("服务", "C", R.drawable.select_private_persion_design_service_img));

        pubdateList.add(new Pubdate("不限时间", "0"));
        pubdateList.add(new Pubdate("近一周", "1"));
        pubdateList.add(new Pubdate("近一个月", "2"));
        pubdateList.add(new Pubdate("近三个月", "3"));

        areaList.add(new Area("110000", "北京市"));
        areaList.add(new Area("120000", "天津市"));
        areaList.add(new Area("130000", "河北省"));
        areaList.add(new Area("140000", "山西省"));
        areaList.add(new Area("150000", "内蒙古"));
//        areaList.add(new Area("150000", "内蒙古自治区"));
        areaList.add(new Area("210000", "辽宁省"));
        areaList.add(new Area("220000", "吉林省"));
        areaList.add(new Area("230000", "黑龙江"));
//        areaList.add(new Area("230000", "黑龙江省"));
        areaList.add(new Area("310000", "上海市"));
        areaList.add(new Area("320000", "江苏省"));
        areaList.add(new Area("330000", "浙江省"));
        areaList.add(new Area("340000", "安徽省"));
        areaList.add(new Area("350000", "福建省"));
        areaList.add(new Area("360000", "江西省"));
        areaList.add(new Area("370000", "山东省"));
        areaList.add(new Area("410000", "河南省"));
        areaList.add(new Area("420000", "湖北省"));
        areaList.add(new Area("430000", "湖南省"));
        areaList.add(new Area("440000", "广东省"));
        areaList.add(new Area("450000", "广西"));
//        areaList.add(new Area("450000", "广西壮族自治区"));
        areaList.add(new Area("460000", "海南省"));
        areaList.add(new Area("500000", "重庆市"));
        areaList.add(new Area("510000", "四川省"));
        areaList.add(new Area("520000", "贵州省"));
        areaList.add(new Area("530000", "云南省"));
        areaList.add(new Area("540000", "西藏"));
//        areaList.add(new Area("540000", "西藏自治区"));
        areaList.add(new Area("610000", "陕西省"));
        areaList.add(new Area("620000", "甘肃省"));
        areaList.add(new Area("630000", "青海省"));
        areaList.add(new Area("640000", "宁夏"));
//        areaList.add(new Area("640000", "宁夏回族自治区"));
        areaList.add(new Area("650000", "新疆"));
//        areaList.add(new Area("650000", "新疆维吾尔自治区"));
        areaList.add(new Area("710000", "台湾"));
//        areaList.add(new Area("710000", "台湾省"));
        areaList.add(new Area("810000", "香港"));
//        areaList.add(new Area("810000", "香港特别行政区"));
        areaList.add(new Area("820000", "澳门"));
//        areaList.add(new Area("820000", "澳门特别行政区"));

        industryList.add(new Industry("", "全部"));
        industryList.add(new Industry("A", "农、林、牧、渔业"));
        industryList.add(new Industry("B", "采矿业"));
        industryList.add(new Industry("C", "制造业"));
        industryList.add(new Industry("D", "电力、热力、燃气及水生产和供应业"));
        industryList.add(new Industry("E", "建筑业"));
        industryList.add(new Industry("F", "批发和零售业"));
        industryList.add(new Industry("G", "交通运输、仓储和邮政业"));
        industryList.add(new Industry("H", "住宿和餐饮业"));
        industryList.add(new Industry("I", "信息传输、软件和信息技术服务业"));
        industryList.add(new Industry("J", "金融业"));
        industryList.add(new Industry("K", "房地产业"));
        industryList.add(new Industry("L", "租赁和商务服务业"));
        industryList.add(new Industry("M", "科学研究和技术服务业"));
        industryList.add(new Industry("N", "水利、环境和公共设施管理业"));
        industryList.add(new Industry("O", "居民服务、修理和其他服务业"));
        industryList.add(new Industry("P", "教育"));
        industryList.add(new Industry("Q", "卫生和社会工作"));
        industryList.add(new Industry("R", "文化、体育和娱乐业"));
        industryList.add(new Industry("S", "公共管理、社会保障和社会组织"));
        industryList.add(new Industry("T", "国际组织"));

    }

    protected String setValue(List<BaseData> listValue, boolean isSingle) {
        String value;
        if (isSingle) {
            if (listValue.size() == 0) {
                return defaultSelect;
            } else {
                if (isSelectId) {
                    value = listValue.get(0).getId();
                } else {
                    value = listValue.get(0).getName();
                }
                return value;
            }
        } else {
            if (listValue.size() == 0) {
                return defaultSelect;
            }
            if (isSelectId) {
                value = listValue.get(0).getId();
                for (int i = 1; i < listValue.size(); i++) {
                    value += "," + listValue.get(i).getId();
                }
                return value;
            } else {
                value = listValue.get(0).getName();
                for (int i = 1; i < listValue.size(); i++) {
                    value += "," + listValue.get(i).getName();
                }
                return value;
            }
        }
    }

    protected List<? extends BaseData> getValue(String stringValue, List<? extends BaseData> dataTagList, boolean isSingle) {
        List<BaseData> valueList = new ArrayList<BaseData>();
        if (isSingle) {
            if (stringValue.equals(defaultSelect)) {
                return valueList;
            } else {
                for (int i = 0; i < dataTagList.size(); i++) {
                    if(isSelectId){
                        if(stringValue.equals(dataTagList.get(i).getId())){
                            valueList.add(dataTagList.get(i));
                        }
                    }else{
                        if(stringValue.equals(dataTagList.get(i).getName())){
                            valueList.add(dataTagList.get(i));
                        }
                    }
                }
                return valueList;
            }
        } else {
            if (stringValue.equals(defaultSelect)) {
                return valueList;
            }else{
                //过滤掉空字符传
                String [] sd = stringValue.split(",");
                for (int i = 0; i < dataTagList.size(); i++) {
                    for(int j=0 ; j<sd.length ; j++){
                        if(isSelectId){
                            if(sd[j].equals(dataTagList.get(i).getId()) && !sd[j].equals("")){
                                valueList.add(dataTagList.get(i));
                            }
                        }else{
                            if(sd[j].equals(dataTagList.get(i).getName()) && !sd[j].equals("")){
                                valueList.add(dataTagList.get(i));
                            }
                        }
                    }
                }
                return valueList;
            }
        }
    }

    public String getProjectType() {
        return setValue(selectProjectTypeList, false);
    }


    public String getPubdate() {
        return setValue(selectPubdateList, true);
    }


    public String getArea() {
        return setValue(selectAreaList, false);
    }


    public String getIndustry() {
        return setValue(selectIndustryList, false);
    }


    public String getTypeSelect() {
        return setValue(selectTypeSelectList, true);
    }

    public boolean isNotData(){
//        Log.e("code", getCode());
//        Log.e("notdatac", getNotDataCode());
        if(getCode().equals(getNotDataCode())){
            return true;
        }else{
            return false;
        }
    }

    public abstract String getCode();

    public abstract String getOldCode();

    public abstract String getNotDataCode();

    public boolean isUpdateData(){
        if(getOldCode().equals(getCode())){
            return true;
        }else{
            return false;
        }
    }

    protected String orderString(String s1) {
        // TODO Auto-generated method stub
        String [] s = s1.split(",");
        TreeSet<String> ts = new TreeSet<String>();
        for(int i=0 ; i<s.length ; i++){
            ts.add(s[i]);
        }
        Iterator<String> it = ts.iterator();
        String res = "";
        while(it.hasNext()){
            res += it.next() + ",";
        }
        return res.substring(0, res.length()-1);
    }

    public static class BaseData {
        private String id;
        private String name;
        private int img;

        public BaseData(String id, String name, int img) {
            this.id = id;
            this.name = name;
            this.img = img;
        }

        /**
         * 方法描述：重写equals()方法
         * 输入参数：Object obj
         * 返回类型：boolean
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            BaseData baseData = (BaseData) obj;
            if (!id.equals(baseData.id)) {
                return false;
            }
            return true;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }
    }

    public class TypeSelect extends BaseData {
        public TypeSelect(String name, String id) {
            super(id, name, -1);
        }
    }

    public class ProjectType extends BaseData {
        public ProjectType(String name, String id, int img) {
            super(id, name, img);
        }
    }

    public class Pubdate extends BaseData {

        public Pubdate(String name, String id) {
            super(id, name, -1);
        }
    }

    public class Area extends BaseData {
        public Area(String id, String name) {
            super(id, name, -1);
        }
    }

    public class Industry extends BaseData {
        public Industry(String id, String name) {
            super(id, name, -1);
        }
    }

}
