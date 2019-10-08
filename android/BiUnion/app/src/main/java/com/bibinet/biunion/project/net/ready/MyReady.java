package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.application.BiUnionApplication;
import com.bibinet.biunion.project.bean.CompanyInfoBean;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.NetConfig;
import com.bibinet.biunion.project.net.base.BaseReady;
import com.bibinet.biunion.project.utils.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by bibinet on 2017-6-24.
 */

public class MyReady extends BaseReady {
    public void setCollect(String userId, String projectCode, String type, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MY_SET_COLLECT, false, callBack,
                "userId", userId,
                "relatedCode", projectCode,
                "type", type);
    }

    public void isCollect(String customerId, String code, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MY_IS_COLLECT, false, callBack,
                "customerId", customerId,
                "code", code);
    }

    public void deleteCollect(String userId, String relatedCode, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MY_DELETE_COLLECT, false, callBack,
                "userId", userId,
                "relatedCode", relatedCode);
    }

    public void getCollect(String userId, String type, int index, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MY_GET_COLLECT, false, callBack,
                "userId", userId,
                "type", type,
                "pageSize", NetConfig.PAGE_SIZE_COLLECT,
                "pageNum", String.valueOf(index));
    }

    public void setUserIcon(String userId, String enterpriseId, String logofile, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MY_SET_USER_ICON, false, callBack,
                "userId", userId,
                "enterpriseId", enterpriseId,
                "logofile", logofile);
    }

    public void setCompanyInfo(String userId, String companyId, String operateType, CompanyInfoBean companyInfoBean,
                               BaseRetrofitCallBack callBack) {

        Gson gson = new Gson();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(gson.toJson(companyInfoBean.getCertificates()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("certificates",jsonArray);
            jsonObject.put("enterpriseCode", companyId);
            jsonObject.put("enterpriseName", companyInfoBean.getCompanyName());
            jsonObject.put("uscCode", companyInfoBean.getCode());
            jsonObject.put("businessLicenseName", companyInfoBean.getLegalPerson());
            jsonObject.put("businessLicenseCardNo", companyInfoBean.getLegalPersonNumber());
            jsonObject.put("industry", companyInfoBean.getIndustry().getSelectKey());
            jsonObject.put("region", companyInfoBean.getArea().getSelectKey());
            jsonObject.put("addr", companyInfoBean.getAddress());
            jsonObject.put("operateType", operateType);
            jsonObject.put("contactName", companyInfoBean.getUserName());
            jsonObject.put("contactCellphone", companyInfoBean.getPhone());
            jsonObject.put("userId", userId);
            HashMap<String, String> requestParams = new HashMap<>();
            requestParams.put("objectJson", jsonObject.toString());
            Observable<JsonElement> observable = BiUnionApplication.getInstance().getApiInterface().post(urlConfig.MY_SET_COMPANY_INFO,requestParams);
            RetrofitManager.subcribe(observable, callBack);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setBusinessLicense(File file, BaseRetrofitCallBack callBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Observable<JsonElement> observable = BiUnionApplication.getInstance().getApiInterface()
                .postBusinessLicense(urlConfig.MY_SET_COMPANY_INFO_BUSINESS_LICENSE,1, part);
        RetrofitManager.subcribe(observable, callBack);
    }
}
