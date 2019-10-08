/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import com.bibinet.biunion.project.ui.config.DateFormatConfig;
import com.bibinet.biunion.project.utils.DateFormatUtils;

import java.util.List;

public class MessageModel extends BaseModel{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private List<ItemsModel> knowledgeInfos;
        private Page page;

        public List<ItemsModel> getKnowledgeInfos() {
            return knowledgeInfos;
        }

        public void setKnowledgeInfos(List<ItemsModel> knowledgeInfos) {
            this.knowledgeInfos = knowledgeInfos;
        }

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }
    }

    public static class Page{
        private String totalPage;
        private String totalRows;
        private String pageNum;

        public String getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(String totalPage) {
            this.totalPage = totalPage;
        }

        public String getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(String totalRows) {
            this.totalRows = totalRows;
        }

        public String getPageNum() {
            return pageNum;
        }

        public void setPageNum(String pageNum) {
            this.pageNum = pageNum;
        }
    }

    public static class ItemsModel {

        private String summary;
        private String title;
        private String objectId;
        private String type;
        private String publishDate;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPublishDate() {
            return DateFormatUtils.format(publishDate, DateFormatConfig.INN_MESSAGE_LIST_DATE_FORMAT);
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }
    }
}