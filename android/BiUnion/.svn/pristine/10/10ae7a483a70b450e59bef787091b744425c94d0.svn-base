package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.AdviceSubmitEmit;
import com.bibinet.biunion.project.net.ready.SettingReady;

/**
 * Created by bibinet on 2017-6-26.
 */

public class SettingFiring extends BaseFiring {
    private SettingReady settingReady;
    private AdviceSubmitEmit adviceSubmitEmit;

    public SettingFiring(AdviceSubmitEmit adviceSubmitEmit) {
        super(adviceSubmitEmit);
        this.adviceSubmitEmit = adviceSubmitEmit;
        this.settingReady = new SettingReady();
    }

    public void submitAdvice(String content, String phone) {
        settingReady.submitAdvice(content, phone, new MyRetrofitResponseCallback<BaseModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                adviceSubmitEmit.onSubmitFailed(resMessage);
            }

            @Override
            protected void onRequestSuccess(BaseModel model) {
                adviceSubmitEmit.onSubmitSucess(model);
            }
        });
    }
}
