package com.bibinet.biunion.project.net.emit;


import com.bibinet.biunion.project.models.MediaVideoChildModel;
import com.bibinet.biunion.project.models.MediaVideoDetailTeacherModel;
import com.bibinet.biunion.project.net.expand.MultiplePageRefreshEmit;

import java.util.List;

/**
 * Created by bibinet on 2017-6-12.
 */

public interface MediaVideoDetailTeacherEmit {
    void onGetTeacherSuccess(List<MediaVideoDetailTeacherModel> dataList);

    void onGetTeacherFail(String error);
}
