package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-28.
 */

public class ExpertReady extends BaseReady {
    public void setExpert(String userId, String CompanyId, String expertsCode, String question,
                          String questionContent, MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.EXPERT_ASK_SET, false, callback,
                "userId", userId,
                "enterpriseId", CompanyId,
                "type", "3",
                "expertCodeStr", expertsCode,
                "title", question,
                "content", questionContent);
    }

    public void setExpertResult(String questionCode, String sucessCode, BaseRetrofitCallBack callback) {
        baseReady(urlConfig.EXPERT_ASK_SET_RESULT, false, callback,
                "questionCode", questionCode,
                "success", sucessCode);
    }

    public void getExpert(MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.EXPERT_GET, false, callback);
    }

    public void getExpertAsk(String userId, int pageNum, BaseRetrofitCallBack callback) {
        baseReady(urlConfig.EXPERT_ASK_GET, false, callback,
                "creator", userId,
                "pageNum", String.valueOf(pageNum));
    }

    public void deleteExpertAsk(String objectId, BaseRetrofitCallBack callback) {
        baseReady(urlConfig.EXPERT_ASK_DELETE, false, callback,
                "objectId", objectId);
    }

    public void getRating(String objectId, MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.EXPERT_ASK_RESULT_GET, true, callback,
                "objectId", objectId);
    }

    public void setRating(String answerCode, int commentLevel, String commentContent, String userId,
                          MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.EXPERT_ASK_RESULT_SET, false, callback,
                "answerCode", answerCode,
                "commentLevel", String.valueOf(commentLevel),
                "commentContent", commentContent,
                "creator", userId);
    }
}
