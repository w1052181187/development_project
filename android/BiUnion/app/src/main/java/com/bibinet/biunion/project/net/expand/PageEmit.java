package com.bibinet.biunion.project.net.expand;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface PageEmit<T>  {
    //读取更多 分页
    void loadMoreSuccess(List<T> historyInfo);
    void loadMoreFail(String s);
}
