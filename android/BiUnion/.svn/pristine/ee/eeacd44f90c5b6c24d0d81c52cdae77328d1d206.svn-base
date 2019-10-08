package com.bibinet.biunion.project.net;

import android.util.Log;

import com.bibinet.biunion.project.models.PageModel;

public abstract class RetrofitPageCallBack<T extends PageModel> extends MyRetrofitResponseCallback<T> {

    @Override
    protected void onRequestFail(String resMessage) {
        onPageRequestFail(resMessage);
    }

    protected abstract void onPageRequestFail(String resMessage);

    @Override
    protected void onRequestSuccess(T model) {
        Log.e("onRequestSuccess=", "onRequestSuccess=");
        PageModel.PageChildModel pageChildModel = model.getPage();
        onPageRequestSuccess(model, pageChildModel);
    }

    protected abstract void onPageRequestSuccess(T model, PageModel.PageChildModel pageChildModel);
}
