package com.bibinet.biunion.project.models;

import java.util.List;

/**
 * Created by Wh on 2017-12-8.
 */

public class SearchResultModel extends BaseModel{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        List<InformationItemsModel> items;
        Page page;

        public List<InformationItemsModel> getItems() {
            return items;
        }

        public void setItems(List<InformationItemsModel> items) {
            this.items = items;
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
}
