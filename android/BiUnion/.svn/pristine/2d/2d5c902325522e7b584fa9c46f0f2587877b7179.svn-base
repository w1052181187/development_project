package com.bibinet.biunion.project.ui.manager;

import com.bibinet.biunion.project.models.MagazineChildModel;
import com.bibinet.biunion.project.utils.SharedPresUtils;

public class MagazineDetailManager {
    public void setBrightness(int progress){
        SharedPresUtils.getsSharedPresUtils(SharedPresUtils.USER_CONFIG).putInt(SharedPresUtils.MAGAZINE_DETAIL_LIGHT, progress);
    }

    public int getBrightness(){
        return SharedPresUtils.getsSharedPresUtils(SharedPresUtils.USER_CONFIG).getInt(SharedPresUtils.MAGAZINE_DETAIL_LIGHT, 40);
    }
}
