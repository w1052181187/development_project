package com.bibinet.biunion.project.bean;

import android.text.TextUtils;

import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bibinet on 2017-6-26.
 */

public class CompanyInfoBean {
    private String companyName;
    private String code;
    private String legalPerson;
    private String legalPersonNumber;
    private String address;

    private KeyValueSelectBean industry;
    private KeyValueSelectBean area;

    private String userName;
    private String phone;
    private String email;

    private String businessLicense;
    private String legalPersonFrontURL;
    private String legalPersonReverseURL;
    private String licenceOpenAccountURL;

    //本地
    private boolean hasBusiness;
    private boolean hasLegalPersonFront;
    private boolean hasLegalPersonReverse;


    private ArrayList<LoginResultBean.UserBean.Certificate> certificates;

    public CompanyInfoBean() {
        List<KeyValueBean> industryKeyValueList = new ArrayList<KeyValueBean>();
        List<KeyValueBean> areaKeyValueList = new ArrayList<KeyValueBean>();

        industryKeyValueList.add(new KeyValueBean("A", "农、林、牧、渔业"));
        industryKeyValueList.add(new KeyValueBean("B", "采矿业"));
        industryKeyValueList.add(new KeyValueBean("C", "制造业"));
        industryKeyValueList.add(new KeyValueBean("D", "电力、热力、燃气及水生产和供应业"));
        industryKeyValueList.add(new KeyValueBean("E", "建筑业"));
        industryKeyValueList.add(new KeyValueBean("F", "批发和零售业"));
        industryKeyValueList.add(new KeyValueBean("G", "交通运输、仓储和邮政业"));
        industryKeyValueList.add(new KeyValueBean("H", "住宿和餐饮业"));
        industryKeyValueList.add(new KeyValueBean("I", "信息传输、软件和信息技术服务业"));
        industryKeyValueList.add(new KeyValueBean("J", "金融业"));
        industryKeyValueList.add(new KeyValueBean("K", "房地产业"));
        industryKeyValueList.add(new KeyValueBean("L", "租赁和商务服务业"));
        industryKeyValueList.add(new KeyValueBean("M", "科学研究和技术服务业"));
        industryKeyValueList.add(new KeyValueBean("N", "水利、环境和公共设施管理业"));
        industryKeyValueList.add(new KeyValueBean("O", "居民服务、修理和其他服务业"));
        industryKeyValueList.add(new KeyValueBean("P", "教育"));
        industryKeyValueList.add(new KeyValueBean("Q", "卫生和社会工作"));
        industryKeyValueList.add(new KeyValueBean("R", "文化、体育和娱乐业"));
        industryKeyValueList.add(new KeyValueBean("S", "公共管理、社会保障和社会组织"));
        industryKeyValueList.add(new KeyValueBean("T", "国际组织"));

        areaKeyValueList.add(new KeyValueBean("110000", "北京市"));
        areaKeyValueList.add(new KeyValueBean("120000", "天津市"));
        areaKeyValueList.add(new KeyValueBean("130000", "河北省"));
        areaKeyValueList.add(new KeyValueBean("140000", "山西省"));
        areaKeyValueList.add(new KeyValueBean("150000", "内蒙古自治区"));
        areaKeyValueList.add(new KeyValueBean("210000", "辽宁省"));
        areaKeyValueList.add(new KeyValueBean("220000", "吉林省"));
        areaKeyValueList.add(new KeyValueBean("230000", "黑龙江省"));
        areaKeyValueList.add(new KeyValueBean("310000", "上海市"));
        areaKeyValueList.add(new KeyValueBean("320000", "江苏省"));
        areaKeyValueList.add(new KeyValueBean("330000", "浙江省"));
        areaKeyValueList.add(new KeyValueBean("340000", "安徽省"));
        areaKeyValueList.add(new KeyValueBean("350000", "福建省"));
        areaKeyValueList.add(new KeyValueBean("360000", "江西省"));
        areaKeyValueList.add(new KeyValueBean("370000", "山东省"));
        areaKeyValueList.add(new KeyValueBean("410000", "河南省"));
        areaKeyValueList.add(new KeyValueBean("420000", "湖北省"));
        areaKeyValueList.add(new KeyValueBean("430000", "湖南省"));
        areaKeyValueList.add(new KeyValueBean("440000", "广东省"));
        areaKeyValueList.add(new KeyValueBean("450000", "广西壮族自治区"));
        areaKeyValueList.add(new KeyValueBean("460000", "海南省"));
        areaKeyValueList.add(new KeyValueBean("500000", "重庆市"));
        areaKeyValueList.add(new KeyValueBean("510000", "四川省"));
        areaKeyValueList.add(new KeyValueBean("520000", "贵州省"));
        areaKeyValueList.add(new KeyValueBean("530000", "云南省"));
        areaKeyValueList.add(new KeyValueBean("540000", "西藏自治区"));
        areaKeyValueList.add(new KeyValueBean("610000", "陕西省"));
        areaKeyValueList.add(new KeyValueBean("620000", "甘肃省"));
        areaKeyValueList.add(new KeyValueBean("630000", "青海省"));
        areaKeyValueList.add(new KeyValueBean("640000", "宁夏回族自治区"));
        areaKeyValueList.add(new KeyValueBean("650000", "新疆维吾尔自治区"));
        areaKeyValueList.add(new KeyValueBean("710000", "台湾省"));
        areaKeyValueList.add(new KeyValueBean("810000", "香港特别行政区"));
        areaKeyValueList.add(new KeyValueBean("820000", "澳门特别行政区"));

        industry = new KeyValueSelectBean("", "", industryKeyValueList);
        area = new KeyValueSelectBean("", "", areaKeyValueList);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPersonNumber() {
        return legalPersonNumber;
    }

    public void setLegalPersonNumber(String legalPersonNumber) {
        this.legalPersonNumber = legalPersonNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public KeyValueSelectBean getIndustry() {
        return industry;
    }

    public void setIndustry(KeyValueSelectBean industry) {
        this.industry = industry;
    }

    public KeyValueSelectBean getArea() {
        return area;
    }

    public void setArea(KeyValueSelectBean area) {
        this.area = area;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public boolean checkInput() {
        if (companyName.equals("")) {
            ToastUtils.showShort("请输入企业名称");
            return false;
        } else
            if (TextUtils.isEmpty(code) || code.length() < 10) {
            ToastUtils.showShort("请输入正确的信用代码");
            return false;
        }
        else if (legalPerson.equals("")) {
            ToastUtils.showShort("请输入法定代表人");
            return false;
        }
        else if (TextUtils.isEmpty(legalPersonNumber) || !PhoneNumberUtils.isLegalId(legalPersonNumber)) {
            ToastUtils.showShort("请输入正确的法人身份证号");
            return false;
        }
        else if (industry.getSelectValue().equals("")) {
            ToastUtils.showShort("请选择所属行业");
            return false;
        } else if (area.getSelectValue().equals("")) {
            ToastUtils.showShort("请选择所属地区");
            return false;
        } else if (address.equals("")) {
            ToastUtils.showShort("请输入详细地址");
            return false;
        } else if (userName.equals("")) {
            ToastUtils.showShort("请输入联系人");
            return false;
        } else if (!hasBusiness) {
            ToastUtils.showShort("您还未上传营业执照");
            return false;
        } else if (!hasLegalPersonFront) {
            ToastUtils.showShort("您还未上传身份证正面");
            return false;
        } else if (!hasLegalPersonReverse) {
            ToastUtils.showShort("您还未上传身份证反面");
            return false;
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<LoginResultBean.UserBean.Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(ArrayList<LoginResultBean.UserBean.Certificate> certificates) {
        this.certificates = certificates;
    }

    public String getLegalPersonFrontURL() {
        return legalPersonFrontURL;
    }

    public void setLegalPersonFrontURL(String legalPersonFrontURL) {
        this.legalPersonFrontURL = legalPersonFrontURL;
    }

    public String getLegalPersonReverseURL() {
        return legalPersonReverseURL;
    }

    public void setLegalPersonReverseURL(String legalPersonReverseURL) {
        this.legalPersonReverseURL = legalPersonReverseURL;
    }

    public String getLicenceOpenAccountURL() {
        return licenceOpenAccountURL;
    }

    public void setLicenceOpenAccountURL(String licenceOpenAccountURL) {
        this.licenceOpenAccountURL = licenceOpenAccountURL;
    }

    public boolean isHasBusiness() {
        return hasBusiness;
    }

    public void setHasBusiness(boolean hasBusiness) {
        this.hasBusiness = hasBusiness;
    }

    public boolean isHasLegalPersonFront() {
        return hasLegalPersonFront;
    }

    public void setHasLegalPersonFront(boolean hasLegalPersonFront) {
        this.hasLegalPersonFront = hasLegalPersonFront;
    }

    public boolean isHasLegalPersonReverse() {
        return hasLegalPersonReverse;
    }

    public void setHasLegalPersonReverse(boolean hasLegalPersonReverse) {
        this.hasLegalPersonReverse = hasLegalPersonReverse;
    }
}
