package com.bibinet.biunion.project.models;

import java.io.Serializable;

/**
 * Created by Wh on 2017-12-4.
 */

public class PageModel extends BaseModel{
    private PageChildModel page;

    public PageChildModel getPage() {
        return page;
    }

    public void setPage(PageChildModel page) {
        this.page = page;
    }

    public class PageChildModel{
        private int pageNum;  //第几页
        private int pageSize; //一页几个
        private int totalPage; //总页面数
        private int totalRows; //总条数

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
        }
    }
}
