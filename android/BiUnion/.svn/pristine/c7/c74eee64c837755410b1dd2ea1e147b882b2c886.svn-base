package com.bibinet.biunion.project.bean.cityselectbean;

/**
 * Created by 吴昊 on 2017-6-12.
 */

public class County extends City{
    private String parentCity;
    private String parentNo;

    public County(String no, String name, String parentNo, String parentCity) {
        super(no, name);
        this.parentCity = parentCity;
        this.parentNo = parentNo;
    }

    @Override
    public String toString() {
        return "County{" +
                "parentCity='" + parentCity + '\'' +
                ", parentNo='" + parentNo + '\'' +
                "no='" + no + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getParentCity() {
        return parentCity;
    }

    public void setParentCity(String parentCity) {
        this.parentCity = parentCity;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }
}
