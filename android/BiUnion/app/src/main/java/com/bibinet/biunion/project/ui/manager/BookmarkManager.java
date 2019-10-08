package com.bibinet.biunion.project.ui.manager;

import android.text.TextUtils;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.application.PathConfig;
import com.bibinet.biunion.project.bean.BookmarkBean;
import com.bibinet.biunion.project.models.MagazineChildModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bibinet on 2017-5-17.
 */

public class BookmarkManager {

    public static final int FLAG_NO_ADD = -1;

    public void addBookmark(int pageNum, MagazineChildModel magazineChildModel) {
        int pos = isAddBookmark(magazineChildModel);
        List<BookmarkBean> dL = readData();
        BookmarkBean bookmarkBean = new BookmarkBean();
        bookmarkBean.setDate(System.currentTimeMillis());
        bookmarkBean.setPageNum(pageNum);
        bookmarkBean.setMagazineChildModel(magazineChildModel);
        if (pos == FLAG_NO_ADD) {
            dL.add(0, bookmarkBean);
        } else {
            dL.set(pos, bookmarkBean);
        }
        writeData(dL);
    }

    public int getBookPage(MagazineChildModel magazineChildModel) {
        if (magazineChildModel == null) {
            return 0;
        }
        List<BookmarkBean> dL = readData();
        for (BookmarkBean bean : dL) {
            if (TextUtils.equals(bean.getMagazineChildModel().getObjectId(),magazineChildModel.getObjectId())) {
                return bean.getPageNum();
            }
        }
        return 0;
    }

    public int isAddBookmark(MagazineChildModel magazineChildModel) {
        List<BookmarkBean> dL = readData();

        for (BookmarkBean bean : dL) {
            if (TextUtils.equals(bean.getMagazineChildModel().getObjectId(),magazineChildModel.getObjectId())) {
                return dL.indexOf(bean);
            }
        }
        return FLAG_NO_ADD;
    }

    public boolean removeBookmark(MagazineChildModel magazineChildModel) {
        int pos = isAddBookmark(magazineChildModel);
        if (pos != FLAG_NO_ADD) {
            List<BookmarkBean> dL = readData();
            dL.remove(pos);
            writeData(dL);
            return true;
        }
        return false;
    }

    public List<BookmarkBean> getAllBookMark() {
        return readData();
    }

    private String getBookmarkFileNameById() {
        if (Constants.loginresultInfo != null && Constants.loginresultInfo.getUser() != null && Constants.loginresultInfo.getUser().getUserId() != null && !Constants.loginresultInfo.getUser().getUserId().equals("")) {
            return Constants.loginresultInfo.getUser().getUserId();
        }
        return "defail";
    }

    private void writeData(List<BookmarkBean> bookmarkBeans) {
        String path = PathConfig.cachePathPdfBookmark + "/" + getBookmarkFileNameById() + ".bookmark";
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
            oos.writeObject(bookmarkBeans);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<BookmarkBean> readData() {
        if(!new File(PathConfig.cachePathPdfBookmark).exists()){
            new File(PathConfig.cachePathPdfBookmark).mkdirs();
        }
        String path = PathConfig.cachePathPdfBookmark + "/" + getBookmarkFileNameById() + ".bookmark";
        if (!new File(path).exists()) {
            try {
                new File(path).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(path)));
            List<BookmarkBean> dL = (List<BookmarkBean>) ois.readObject();
            return dL;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<BookmarkBean>();
    }

}
