package com.bibinet.biunion.project.bean.cityselectbean;

/**
 * Created by 吴昊 on 2017-6-28.
 */

public class Province {
    private String no;
    private String name;

    public Province(String name, String no) {
        this.name = name;
        this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Province{" +
                "no='" + no + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
