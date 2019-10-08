package com.bibinet.biunion.project.net.expand;

import com.bibinet.biunion.project.models.PageModel;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface MultiplePageRefreshEmit<T>  {
    //读取更多 分页
    void loadMoreSuccess(List<T> dataList, PageModel.PageChildModel pageChildModel);
    void loadMoreFail(String s);
}
