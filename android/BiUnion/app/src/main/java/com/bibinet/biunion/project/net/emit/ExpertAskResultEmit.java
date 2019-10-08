package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.bean.PostRatingResultBean;
import com.bibinet.biunion.project.bean.RatingResultBean;

/**
 * Created by bibinet on 2017-7-27.
 */

public interface ExpertAskResultEmit {
    void onGetRatingSuccess(RatingResultBean ratingInfo);
    void onGetRatingFail(String msg);
    void onSetRatingSuccess(PostRatingResultBean postRatingResultBean);
    void onSetRatingFail(String msg);
}
