package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.bean.DeleteHistoryBean;
import com.bibinet.biunion.project.bean.WriteTenderBookHistoryBean;
import com.bibinet.biunion.project.net.expand.PageEmit;

import java.util.List;

/**
 * Created by bibinet on 2017-6-26.
 */

public interface WriteTenderHistoryEmit extends PageEmit<WriteTenderBookHistoryBean.ItemBean> {
    void onDeleteWriteTenderBookSuccess(DeleteHistoryBean bean);
    void onDeleteWriteTenderBookFail(String s);
}
