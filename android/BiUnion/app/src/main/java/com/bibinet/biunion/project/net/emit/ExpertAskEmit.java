package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.bean.AskExpertsBean;
import com.bibinet.biunion.project.bean.ExpertsDataBean;

import java.util.List;

/**
 * Created by bibinet on 2017-6-28.
 */

public interface ExpertAskEmit {
    void onSetExpertSuccess(AskExpertsBean projectInfo);
    void onSetExpertFail(String msg);
    void onGetExpertSuccess(List<ExpertsDataBean.ItemsBean> itemsBeanList);
    void onGetExpertFail(String msg);
}
