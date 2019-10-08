package com.bibinet.biunion.project.bean;

import android.text.TextUtils;

import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class WriteTenderBookBean {
    private WriteTenderBookSelect bookSelect;
    private WriteTenderBookSelect projectType;
    private WriteTenderBookSelect tenderStyle;
    private WriteTenderBookSelect bookType;
    private String name;
    private String phone;
    private String email;

    public WriteTenderBookBean() {
        List<KeyValueBean> bookSelectKeyValueList = new ArrayList<KeyValueBean>();
        bookSelectKeyValueList.add(new KeyValueBean("0", "招标文件"));
        bookSelectKeyValueList.add(new KeyValueBean("1", "投标文件"));

        List<KeyValueBean> projectTypeKeyValueList = new ArrayList<KeyValueBean>();
        projectTypeKeyValueList.add(new KeyValueBean("0", "政府采购"));
        projectTypeKeyValueList.add(new KeyValueBean("1", "非政府采购"));

        List<KeyValueBean> tenderStyleKeyValueList = new ArrayList<KeyValueBean>();
        tenderStyleKeyValueList.add(new KeyValueBean("0", "公开招标"));
        tenderStyleKeyValueList.add(new KeyValueBean("1", "依法招标"));
        tenderStyleKeyValueList.add(new KeyValueBean("2", "邀请招标"));

        List<KeyValueBean> bookTypeKeyValueList = new ArrayList<KeyValueBean>();
        bookTypeKeyValueList.add(new KeyValueBean("0", "工程"));
        bookTypeKeyValueList.add(new KeyValueBean("1", "货物"));
        bookTypeKeyValueList.add(new KeyValueBean("2", "服务"));

        bookSelect = new WriteTenderBookSelect(null, null, bookSelectKeyValueList);
        projectType = new WriteTenderBookSelect(null, null, projectTypeKeyValueList);
        tenderStyle = new WriteTenderBookSelect(null, null, tenderStyleKeyValueList);
        bookType = new WriteTenderBookSelect(null, null, bookTypeKeyValueList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WriteTenderBookSelect getBookSelect() {
        return bookSelect;
    }

    public void setBookSelect(WriteTenderBookSelect bookSelect) {
        this.bookSelect = bookSelect;
    }

    public WriteTenderBookSelect getProjectType() {
        return projectType;
    }

    public void setProjectType(WriteTenderBookSelect projectType) {
        this.projectType = projectType;
    }

    public WriteTenderBookSelect getTenderStyle() {
        return tenderStyle;
    }

    public void setTenderStyle(WriteTenderBookSelect tenderStyle) {
        this.tenderStyle = tenderStyle;
    }

    public WriteTenderBookSelect getBookType() {
        return bookType;
    }

    public void setBookType(WriteTenderBookSelect bookType) {
        this.bookType = bookType;
    }

    public boolean checkInput(){
        if (bookSelect.getId() == null) {
            ToastUtils.showShort("标书选择为空");
            return false;
        } else if (projectType.getId() == null) {
            ToastUtils.showShort("项目类型为空");
            return false;
        } else if (tenderStyle.getId() == null) {
            ToastUtils.showShort("招标方式为空");
            return false;
        } else if (bookType.getId() == null) {
            ToastUtils.showShort("标书类型为空");
            return false;
        } else if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("联系人不能为空");
            return false;
        } else if (!PhoneNumberUtils.isMobileNumber(phone)) {
            ToastUtils.showShort("手机号格式不正确");
            return false;
        } else if (!PhoneNumberUtils.isEmail(email)) {
            ToastUtils.showShort("邮箱格式不正确");
            return false;
        }
        return true;
    }

    public class WriteTenderBookSelect {
        List<KeyValueBean> keyValueBeanList;
        private String id;
        private String name;

        public WriteTenderBookSelect(String id, String name, List<KeyValueBean> keyValueBeanList) {
            this.id = id;
            this.name = name;
            this.keyValueBeanList = keyValueBeanList;
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

        public List<KeyValueBean> getKeyValueBeanList() {
            return keyValueBeanList;
        }
    }
}