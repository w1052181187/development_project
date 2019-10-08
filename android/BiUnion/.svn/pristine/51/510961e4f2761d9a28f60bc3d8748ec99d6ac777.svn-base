package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.bean.AskExpertsBean;
import com.bibinet.biunion.project.bean.ExpertsAskAnswerResultBean;
import com.bibinet.biunion.project.bean.ExpertsDataBean;
import com.bibinet.biunion.project.bean.PostRatingResultBean;
import com.bibinet.biunion.project.bean.RatingResultBean;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.ExpertAskEmit;
import com.bibinet.biunion.project.net.emit.ExpertAskHistoryEmit;
import com.bibinet.biunion.project.net.emit.ExpertAskResultEmit;
import com.bibinet.biunion.project.net.ready.ExpertReady;

/**
 * Created by bibinet on 2017-6-28.
 */

public class ExpertFiring extends BaseFiring {
    private ExpertReady expertReady;
    private ExpertAskEmit expertAskEmit;
    private ExpertAskHistoryEmit expertAskHistoryEmit;
    private ExpertAskResultEmit expertAskResultEmit;

    public ExpertFiring(ExpertAskEmit expertAskEmit) {
        super(expertAskEmit);
        this.expertAskEmit = expertAskEmit;
        this.expertReady = new ExpertReady();
    }

    public ExpertFiring(ExpertAskHistoryEmit expertAskHistoryEmit) {
        super(expertAskHistoryEmit);
        this.expertAskHistoryEmit = expertAskHistoryEmit;
        this.expertReady = new ExpertReady();
    }

    public ExpertFiring(ExpertAskResultEmit expertAskResultEmit) {
        super(expertAskResultEmit);
        this.expertAskResultEmit = expertAskResultEmit;
        this.expertReady = new ExpertReady();
    }

    public void setExpert(String userId, String CompanyId, String expertsCode, String question, String questionContent) {
        waitDialog.open();
        expertReady.setExpert(userId, CompanyId, expertsCode, question, questionContent,
                new MyRetrofitResponseCallback<AskExpertsBean>() {
                    @Override
                    protected void onRequestFail(String resMessage) {
                        expertAskEmit.onSetExpertFail(resMessage);
                        waitDialog.close();
                    }

                    @Override
                    protected void onRequestSuccess(AskExpertsBean model) {
                        expertAskEmit.onSetExpertSuccess(model);
                        waitDialog.close();
                    }

        });
    }

    public void getExpert() {
        waitDialog.open();
        expertReady.getExpert(new MyRetrofitResponseCallback<ExpertsDataBean>() {
            @Override
            protected void onRequestFail(String resMessage) {
                waitDialog.close();
                expertAskEmit.onGetExpertFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(ExpertsDataBean model) {
                waitDialog.close();
                expertAskEmit.onGetExpertSuccess(model.getItems());
            }

        });
    }

    public void setExpertResult(String questionCode, String sucessCode) {
        expertReady.setExpertResult(questionCode, sucessCode, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
            }

            @Override
            protected void onRequestFail(String resMessage) {

            }
        });
    }

    public void getExpertAsk(String userId, int pageNum) {
        expertReady.getExpertAsk(userId, pageNum, new MyRetrofitResponseCallback<ExpertsAskAnswerResultBean>() {
            @Override
            protected void onRequestFail(String resMessage) {
                expertAskHistoryEmit.loadMoreFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(ExpertsAskAnswerResultBean model) {
                expertAskHistoryEmit.loadMoreSuccess(model.getItems());
            }

        });
    }

    public void deleteExpertAsk(String objectId) {
        expertReady.deleteExpertAsk(objectId, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                expertAskHistoryEmit.onDeleteExpertAskSuccess();
            }

            @Override
            protected void onRequestFail(String resMessage) {
                expertAskHistoryEmit.onDeleteExpertAskFail(resMessage);
            }
        });
    }

    public void getRating(String objectId) {
        waitDialog.open();
        expertReady.getRating(objectId, new MyRetrofitResponseCallback<RatingResultBean>() {
            @Override
            protected void onRequestFail(String resMessage) {
                expertAskResultEmit.onGetRatingFail(resMessage);
                waitDialog.close();
            }

            @Override
            protected void onRequestSuccess(RatingResultBean model) {
                expertAskResultEmit.onGetRatingSuccess(model);
                waitDialog.close();
            }

        });
    }

    public void setRating(String answerCode, int commentLevel, String commentContent, String userId) {
        waitDialog.open();
        expertReady.setRating(answerCode, commentLevel, commentContent, userId,
                new MyRetrofitResponseCallback<PostRatingResultBean>() {
                    @Override
                    protected void onRequestFail(String resMessage) {

                        expertAskResultEmit.onSetRatingFail(resMessage);
                        waitDialog.close();
                    }

                    @Override
                    protected void onRequestSuccess(PostRatingResultBean model) {
                        expertAskResultEmit.onSetRatingSuccess(model);
                        waitDialog.close();
                    }

        });
    }
}
