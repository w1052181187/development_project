package com.bibinet.biunion.project.bean.statistics;
import java.util.List;

/**
 * Auto-generated: 2017-11-14 10:39:5
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ToLoginBean {

    private List<ToLoginChildBean> toLogin;

    public List<ToLoginChildBean> getToLogin() {
        return toLogin;
    }

    public void setToLogin(List<ToLoginChildBean> toLogin) {
        this.toLogin = toLogin;
    }

    @Override
    public String toString() {
        return "ToLoginBean{" +
                "toLogin=" + toLogin +
                '}';
    }

    public class ToLoginChildBean {

        private String id;
        private String describe;
        private String key;
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "ToLoginChildBean{" +
                    "id='" + id + '\'' +
                    ", describe='" + describe + '\'' +
                    ", key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}