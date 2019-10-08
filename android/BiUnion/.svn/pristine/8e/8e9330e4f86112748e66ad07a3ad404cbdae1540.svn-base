package com.bibinet.biunion.project.bean;

import java.util.List;

public class KeyValueSelectBean {
    private String selectKey;
    private String selectValue;

    private List<KeyValueBean> keyValueList;

    public KeyValueSelectBean(String selectKey, String selectValue, List<KeyValueBean> keyValueList) {
        this.selectKey = selectKey;
        this.selectValue = selectValue;
        this.keyValueList = keyValueList;
    }

    public String getSelectKey() {
        return selectKey;
    }

    public void setSelectKey(String selectKey) {
        this.selectKey = selectKey;
    }

    public String getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }

    public List<KeyValueBean> getKeyValueList() {
        return keyValueList;
    }

    public void setKeyValueList(List<KeyValueBean> keyValueList) {
        this.keyValueList = keyValueList;
    }

    public String keyToValue(String key){
        for(KeyValueBean b : keyValueList){
            if(b.getKey().equals(key)){
                return b.getValue();
            }
        }
        return "";
    }

    public String valueToKey(String value){
        for(KeyValueBean b : keyValueList){
            if(b.getValue().equals(value)){
                return b.getKey();
            }
        }
        return "";
    }
}