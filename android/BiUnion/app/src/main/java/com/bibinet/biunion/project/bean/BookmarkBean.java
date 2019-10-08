package com.bibinet.biunion.project.bean;

import com.bibinet.biunion.project.models.MagazineChildModel;

import java.io.Serializable;

/**
 * Created by bibinet on 2017-9-7.
 */

public class BookmarkBean implements Serializable {
    private MagazineChildModel magazineChildModel;

    private long date;
    private int pageNum;

    public MagazineChildModel getMagazineChildModel() {
        return magazineChildModel;
    }

    public void setMagazineChildModel(MagazineChildModel magazineChildModel) {
        this.magazineChildModel = magazineChildModel;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookmarkBean that = (BookmarkBean) o;

        if (pageNum != that.pageNum) return false;
        return magazineChildModel != null ? magazineChildModel.equals(that.magazineChildModel) : that.magazineChildModel == null;
    }

    @Override
    public int hashCode() {
        int result = magazineChildModel != null ? magazineChildModel.hashCode() : 0;
        result = 31 * result + pageNum;
        return result;
    }
}
