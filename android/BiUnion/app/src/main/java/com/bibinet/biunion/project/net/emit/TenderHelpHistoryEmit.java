package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.bean.DeleteHistoryBean;
import com.bibinet.biunion.project.bean.HelpTenderHistoryReusltBean;
import com.bibinet.biunion.project.net.expand.PageEmit;

import java.util.List;

/**
 * Created by bibinet on 2017-6-29.
 */

public interface TenderHelpHistoryEmit extends PageEmit<HelpTenderHistoryReusltBean.ItemBean> {
    void onDeleteTenderHelpSuccess(DeleteHistoryBean bean);
    void onDeleteTenderHelpFail(String s);
}
