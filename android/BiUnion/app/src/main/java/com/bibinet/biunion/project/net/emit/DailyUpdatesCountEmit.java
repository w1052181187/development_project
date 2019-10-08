package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.models.DailyUpdatesModel;

/**
 * Created by bibinet on 2017-6-1.
 */

public interface DailyUpdatesCountEmit{
   void onGetCountSuccess(DailyUpdatesModel model);
   void onGetCountFail(String error);
}
