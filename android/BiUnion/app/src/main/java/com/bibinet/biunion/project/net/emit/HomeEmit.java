package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.models.BannerModel;
import com.bibinet.biunion.project.models.InformationItemsModel;
import com.bibinet.biunion.project.models.InformationModel;

import java.util.List;

/**
 * Created by bibinet on 2017-9-18.
 */

public interface HomeEmit {
    void onLoadBannerSuccess(List<BannerModel.ItemBean> bannerInfo);
    void onLoadBannerFail(String error);

    void onLoadItemDataSuccess(List<InformationItemsModel> homeProjcetInfos, int type);
    void onLoadItemDataFail(String msg);
}
