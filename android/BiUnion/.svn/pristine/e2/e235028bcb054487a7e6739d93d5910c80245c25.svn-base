package com.bibinet.biunion.project.ui.manager;

import com.bibinet.biunion.project.models.MagazineChildModel;
import com.bibinet.biunion.project.models.MagazineModel;
import com.bibinet.biunion.project.utils.SharedPresUtils;

public class MagazineManager {
    public void saveReading(MagazineChildModel magazineChildModel){
        SharedPresUtils.getsSharedPresUtils(SharedPresUtils.USER_CONFIG).saveReading(magazineChildModel);
    }

    public MagazineChildModel loadReading(){
        MagazineChildModel model = SharedPresUtils.getsSharedPresUtils(SharedPresUtils.USER_CONFIG).loadReading();
        if(model.getObjectId() == null){
            return null;
        }
        model.setReading(true);
        return model;
    }
}
