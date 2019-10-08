package com.chengning.common.base;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class MyOkhttpCallback implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        if (!call.isCanceled()) {
            call.cancel();
        }
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        if (response != null && response.isSuccessful()) {
            handleResponse(response);
        } else {
            onFailure(call,null);
        }
    }

    private void handleResponse(Response response) throws IOException {
        ResponseBody body = response.body();
        String resultStr = body.string();
        if (!TextUtils.isEmpty(resultStr)) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(resultStr);
                String mod = obj.optString("mod");
                String msg = obj.optString("message");
                int status = obj.optInt("status");
                String data = obj.optString("data");
                switch (status) {
                    case 0:
                        onDataSuccess(status, mod, msg, data, response);
                        break;
                    default:
                        onDataFailure(status, mod, msg, data, response);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                onDataFailure(1,"",e.getMessage(),resultStr, response);
            }

        } else {
            onDataFailure(1,"",response.message(),resultStr, response);
        }
    }

    public abstract void onDataSuccess(int status, String mod, String message, String data, Response response);
    public abstract void onDataFailure(int status, String mod, String message, String data, Response response);

}
