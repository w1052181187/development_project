package com.appmf.net.appmf.base.activity;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.appmf.net.appmf.R;
import com.appmf.net.appmf.app.Constants;
import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.base.BaseHandler;
import com.appmf.net.appmf.base.BaseRetrofitCallBack;
import com.appmf.net.appmf.base.bean.BaseModel;
import com.appmf.net.appmf.base.presenter.BasePresenter;
import com.appmf.net.appmf.bean.PageBean;
import com.appmf.net.appmf.bean.file.FastFileParamsBean;
import com.appmf.net.appmf.bean.file.FileParamsBean;
import com.appmf.net.appmf.core.DBHelper;
import com.appmf.net.appmf.core.http.api.GeeksApis;
import com.appmf.net.appmf.utils.CommonUtils;
import com.appmf.net.appmf.utils.EncodeUtils;
import com.appmf.net.appmf.utils.FileIOUtils;
import com.appmf.net.appmf.utils.FileManagerUtils;
import com.appmf.net.appmf.utils.FileUtils;
import com.appmf.net.appmf.utils.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.just.agentweb.AgentWeb;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.appmf.net.appmf.utils.FileManagerUtils.getRealFastPath;
import static com.appmf.net.appmf.utils.FileManagerUtils.getRealPath;
import static com.appmf.net.appmf.utils.FileManagerUtils.handleCommonFile;
import static com.appmf.net.appmf.utils.FileManagerUtils.handleSysAllFile;

public abstract class BaseWebviewActivity<T extends BasePresenter> extends BaseActivity<T> {

    private static final String BASE_JS = "javascript:" + "";
    private static final String BASE_URL = "file:///android_asset/";
    private final int MSG_START_JS_CALLBACK = 1001;
    private static final int MSG_HANDLE_DB = 1002;
    private static final int MSG_HANDLE_HTTP = 1003;
    private static final int MSG_HANDLE_FILE = 1004;
    private static final int MSG_HANDLE_FAST_FILE = 1005;

    private AgentWeb mAgentWeb;
    private WebView mWebView;
    private String mCallbackIndex;
    private ArrayList<String> mCopyPaths = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_webview;
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        mCopyPaths.clear();
        super.onDestroy();
    }

    @Override
    public void showNormal() {
        super.showNormal();
        initWebview();
    }

    void initWebview() {
//        String baseJs = "javascript:" + getString(R.string.base_js);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((ViewGroup) getNormalView(), new LinearLayout.LayoutParams(-1, -1))
                .closeIndicator()
//                .useDefaultIndicator()
//                .setMainFrameErrorView(R.layout.webview_error_view, -1)
//                .addJavascriptInterface("jsInterface", new JavascriptInterface())
                .setWebViewClient(new MyWebViewClient())
                .createAgentWeb()
                .ready()
                .go(BASE_JS);

        mWebView = mAgentWeb.getWebCreator().getWebView();
        WebSettings mSettings = mWebView.getSettings();
//        if (mPresenter.getNoImageState()) {
//            mSettings.setBlockNetworkImage(true);
//        } else {
            mSettings.setBlockNetworkImage(false);
//        }
//        if (mPresenter.getAutoCacheState()) {
            mSettings.setDefaultTextEncodingName("UTF-8");
            mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            mSettings.setJavaScriptEnabled(true);
            mSettings.setAppCacheEnabled(true);
            mSettings.setDomStorageEnabled(true);
            mSettings.setDatabaseEnabled(true);
            if (CommonUtils.isNetworkConnected(this)) {
                mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
//        } else {
//            mSettings.setAppCacheEnabled(false);
//            mSettings.setDomStorageEnabled(false);
//            mSettings.setDatabaseEnabled(false);
//            mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        }

        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        mSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        mSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mAgentWeb.getJsInterfaceHolder().addJavaObject("jsInterface", new AndroidInterface(mAgentWeb,this));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public boolean onBackPressedWebview() {
        if (mWebView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
            mWebView.goBack();
            return true;
        }
        return false;
    }

    public void initLoadUrl(String url) {
        mWebView.loadUrl(url);
    }
    public void initLoadData(String data){
        mWebView.loadDataWithBaseURL(BASE_URL, data, "text/html; charset=UTF-8", null, null);
    }

    private class AndroidInterface {

        private final AgentWeb mAgentWeb;
        private final BaseWebviewActivity<T> activity;
        private String mFileName ="";

        public AndroidInterface(AgentWeb agentWeb, BaseWebviewActivity<T> tBaseWebviewActivity) {
            this.mAgentWeb = agentWeb;
            this.activity = tBaseWebviewActivity;
        }

        @JavascriptInterface
        public void callNative(final String methodName, String params, final String callbackIndex) {
            if (!TextUtils.isEmpty(methodName)) {
                mCallbackIndex = callbackIndex;
                final Gson gson = new Gson();
                switch (methodName) {
                    case "getPage":
                        PageBean pageBean = gson.fromJson(params, PageBean.class);
                        mFileName = generatePageFileName(pageBean.getId());
                        handlePageFileToJs(activity, mFileName, callbackIndex);
                        break;
                    case "handleDb":
//                        String strDb = DBHelper.getInstance(getActivityComponent().getActivity()).handleDb(params);
//                        mHandler.obtainMessage(MSG_START_JS_CALLBACK, "db").sendToTarget();
                        mHandler.obtainMessage(MSG_HANDLE_DB, params).sendToTarget();
                        break;
                    case "http":
                        mHandler.obtainMessage(MSG_HANDLE_HTTP, params).sendToTarget();
                        break;
                    case "handleFile":
                        mHandler.obtainMessage(MSG_HANDLE_FILE, params).sendToTarget();
                        break;
                }

            }
        }
    }

    protected void handlePageFileToJs(final BaseWebviewActivity<T> activity, final String mFileName, final String callbackIndex) {

        CommonUtils.handleFileByRxjava(activity, mFileName, new CommonUtils.RxjavaCallback() {
            @Override
            public void success(String data) {
                try {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
                    ArrayList<ComponentBean> componentList = gson.fromJson(jsonObject.getAsJsonArray("mids").toString(),
                            new TypeToken<ArrayList<ComponentBean>>(){}.getType());
                    final JsonArray mods = new JsonArray();
                    final JsonArray cssList = new JsonArray();
                    for (ComponentBean bean : componentList) {
                        String encodeMod = FileIOUtils.getFromAssets(activity, generateModFileName(bean.getM(), bean.getV()));
                        String decodeMod = EncodeUtils.decodeBykey(encodeMod, Constants.key);
                        mods.add(gson.fromJson(decodeMod, JsonObject.class));
                        cssList.add(generateModCssFileName(bean.getM(), bean.getV()));
                    }
                    jsonObject.add("mods", mods);
                    jsonObject.add("css",cssList);
                    BaseModel bean = new BaseModel();
                    bean.setStatus(1);
                    JsonObject dataJson = new JsonObject();
                    dataJson.add("page", jsonObject);
                    bean.setData(dataJson);
                    String jsonBean = gson.toJson(bean);
                    mHandler.obtainMessage(MSG_START_JS_CALLBACK, jsonBean).sendToTarget();
                    mCallbackIndex = callbackIndex;
//                    if (mAgentWeb != null) {
//                        mAgentWeb.getJsAccessEntrace().quickCallJs("appmf.callbackJs", callbackIndex, jsonBean);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private BaseHandler mHandler = new BaseHandler(new BaseHandler.HandlerMsgCallback() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MSG_START_JS_CALLBACK:
                    String str = (String) msg.obj;
                    if (mAgentWeb != null) {
                        mAgentWeb.getJsAccessEntrace().quickCallJs("appmf.callbackJs", mCallbackIndex, str);
                    }
                    break;
                case MSG_HANDLE_DB:
                    Observable.create(new ObservableOnSubscribe<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    String params = (String) msg.obj;
                                    JsonObject strDb = DBHelper.getInstance(getActivityComponent().getActivity()).handleDb(params);
                                    Gson gson = new Gson();
                                    BaseModel bean = new BaseModel();
                                    bean.setStatus(1);
                                    bean.setData(strDb);
                                    String json = gson.toJson(bean);
                                    emitter.onNext(json);
                                    emitter.onComplete();
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    mHandler.obtainMessage(MSG_START_JS_CALLBACK, s).sendToTarget();
                                }
                            });
                    break;
                case MSG_HANDLE_HTTP:
                    Gson gsonHttp = new Gson();
                    HttpParamsBean paramBean = gsonHttp.fromJson((String) msg.obj, HttpParamsBean.class);
                    if (paramBean == null || TextUtils.isEmpty(paramBean.getUrl())) {
                        return;
                    }

                    HashMap<String, String> headerMap = paramBean.getHeader();
                    OkHttpClient.Builder clientBuilder = RetrofitManager.getInst().getClientBuilder();
                    if (headerMap != null && !headerMap.isEmpty()) {
                        clientBuilder.addInterceptor(new RetrofitManager.HeaderInterceptor(headerMap));
                    }
                    Map<String, String> httpParams = new HashMap<>();
                    JsonObject data = paramBean.getData();
                    if (data != null && !data.isJsonNull()) {
                        Set<String> keys = data.keySet();
                        for (String key : keys) {
                            httpParams.put(key, data.getAsJsonPrimitive(key).getAsString());
                        }
                    }
                    Observable<JsonElement> observer = null;
                    String url = MyApp.getInstance().getDomain() + paramBean.getUrl();
//                    String url = paramBean.getUrl();
                    Retrofit.Builder retrofitBuilder = RetrofitManager.getInst().getRetrofitBuilder();
                    retrofitBuilder = retrofitBuilder
                            .client(clientBuilder.cookieJar(new RetrofitManager.CookieManager(getActivityComponent().getActivity())).build());
                    RetrofitManager.getInst().setRetrofitBuilder(retrofitBuilder);
                    RetrofitManager.getInst().setRetrofit(retrofitBuilder.build());
                    GeeksApis geeksApis = RetrofitManager.getInst().createService(GeeksApis.class);
//                    GeeksApis geeksApis = MyApp.getInstance().getApiInterface();
                    String method = paramBean.getMethod();
                    if (TextUtils.isEmpty(method)) {
                        method = "get";
                    }
                    method = method.toLowerCase();
                    switch (method) {
                        case "get":
                            if (httpParams.isEmpty()) {
                                observer = geeksApis.get(url);
                            } else {
                                observer = geeksApis.get(url, httpParams);
                            }
                            break;
                        case "post":
                            if (httpParams.isEmpty()) {
                                observer = geeksApis.post(url);
                            } else {
                                observer = geeksApis.post(url, httpParams);
                            }
                            break;
                        case "put":
                            if (httpParams.isEmpty()) {
                                observer = geeksApis.put(url);
                            } else {
                                observer = geeksApis.put(url, httpParams);
                            }
                            break;
                        case "delete":
                            if (httpParams.isEmpty()) {
                                observer = geeksApis.delete(url);
                            } else {
                                observer = geeksApis.delete(url, httpParams);
                            }
                            break;
                        case "head":
                            if (httpParams.isEmpty()) {
                                observer = geeksApis.head(url);
                            } else {
                                observer = geeksApis.head(url, httpParams);
                            }
                            break;
                        case "options":
                            if (httpParams.isEmpty()) {
                                observer = geeksApis.options(url);
                            } else {
                                observer = geeksApis.options(url, httpParams);
                            }
                            break;
                    }

                    if (observer == null) {
                        return;
                    }
                    RetrofitManager.subcribe(observer, new BaseRetrofitCallBack() {

                        @Override
                        public void onNext(JsonElement response) {
                            mHandler.obtainMessage(MSG_START_JS_CALLBACK, response.toString()).sendToTarget();
                        }

                        @Override
                        protected void onRequestSuccess(String data) {
                        }

                        @Override
                        protected void onRequestFail(String resMessage) {
                        }

                    });
                break;
                case MSG_HANDLE_FILE:
                    Observable
                            .create(new ObservableOnSubscribe<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    emitter.onNext(handleFile(msg));
                                    emitter.onComplete();
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    mHandler.obtainMessage(MSG_START_JS_CALLBACK, s).sendToTarget();
                                }
                            });
                break;
                case MSG_HANDLE_FAST_FILE:
                    Observable
                            .create(new ObservableOnSubscribe<String>() {
                                @Override
                                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                    emitter.onNext(handleFastFile(msg));
                                    emitter.onComplete();
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    mHandler.obtainMessage(MSG_START_JS_CALLBACK, s).sendToTarget();
                                }
                            });
                    break;
            }
        }
    });

    /**
     * js操作本地文件
     * @param msg
     * @return
     */
    private String handleFile(Message msg) {
        Gson gsonFile = new Gson();
        FileParamsBean paramFile = gsonFile.fromJson((String) msg.obj, FileParamsBean.class);
        BaseModel fileBean = new BaseModel();
        fileBean.setStatus(1);
        JsonObject jsonObject = new JsonObject();
        if (paramFile == null || TextUtils.isEmpty(paramFile.getMethod())) {
            fileBean.setStatus(0);
            fileBean.setMsg("method为空");
            jsonObject.addProperty("file", false);
            fileBean.setData(jsonObject);
            return gsonFile.toJson(fileBean);
        }
        String fileMethod = paramFile.getMethod();
        switch (fileMethod) {
            case "getDirFile":
                jsonObject = (JsonObject) handleCommonFile(paramFile);
                break;
            case "getSysFile":
                jsonObject = (JsonObject) handleSysAllFile(getActivityComponent().getActivity(), paramFile);
                break;
            case "delFile":
                ArrayList<String> paths = new ArrayList<>();
                if (paramFile.getPath().isJsonPrimitive()) {
                    paths.add(paramFile.getPath().getAsJsonPrimitive().getAsString());
                } else {
                    paths.addAll(gsonFile.fromJson(paramFile.getPath(), ArrayList.class));
                }
                JsonArray array = new JsonArray();
                for (String path : paths) {
                    array.add(FileUtils.delete(getRealPath(path)));
                }
                if (array.size() == 0) {
                    fileBean.setStatus(0);
                    fileBean.setMsg("路径为空");
                } else {
                    jsonObject.add("file", array);
                }
                break;
            case "copyFile":
                mCopyPaths.clear();
                if (paramFile.getPath().isJsonPrimitive()) {
                    mCopyPaths.add(paramFile.getPath().getAsJsonPrimitive().getAsString());
                } else {
                    mCopyPaths.addAll(gsonFile.fromJson(paramFile.getPath(), ArrayList.class));
                }

                if (mCopyPaths.size() == 0) {
                    fileBean.setStatus(0);
                    fileBean.setMsg("路径为空");
                } else {
                    jsonObject.addProperty("file", true);
                }
                break;
            case "pasteFile":
                String pastePath = "";
                if (paramFile.getPath().isJsonPrimitive()) {
                    pastePath = getRealPath(paramFile.getPath().getAsJsonPrimitive().getAsString());
                }
                if (mCopyPaths.size() == 0) {
                    fileBean.setStatus(0);
                    fileBean.setMsg("路径为空");
                } else {
                    JsonArray pasteArray = new JsonArray();
                    String tempPath;
                    for (String path : mCopyPaths) {
                        tempPath = getRealPath(path);
                        pasteArray.add(FileUtils.isDir(tempPath) ? FileUtils.copyDir(path, pastePath) : FileUtils.copyFile(path, pastePath));
                    }
                    jsonObject.add("file", pasteArray);
                }
                break;
            case "renFile":
                String renPath = "";
                String renName = paramFile.getName();
                if (paramFile.getPath().isJsonPrimitive()) {
                    renPath = getRealPath(paramFile.getPath().getAsJsonPrimitive().getAsString());
                    if (TextUtils.isEmpty(renPath)) {
                        fileBean.setStatus(0);
                        fileBean.setMsg("路径为空");
                    } else if (TextUtils.isEmpty(renName)) {
                        fileBean.setStatus(0);
                        fileBean.setMsg("重命名名字为空");
                    } else {
                        jsonObject.addProperty("file", FileUtils.rename(renPath, renName));
                    }
                } else {
                    fileBean.setStatus(0);
                    fileBean.setMsg("重命名只可单次操作一个文件或文件夹");
                }


                break;
            case "getFileInfo":
                String infoPath = "";
                if (paramFile.getPath().isJsonPrimitive()) {
                    infoPath = getRealPath(paramFile.getPath().getAsJsonPrimitive().getAsString());
                }

                if (TextUtils.isEmpty(infoPath)) {
                    fileBean.setStatus(0);
                    fileBean.setMsg("路径为空");
                } else {
                    jsonObject.add("file", FileManagerUtils.generateFileInfo(new File(infoPath), paramFile.getUnit()));
                }
                break;

        }
        fileBean.setData(jsonObject);
        return gsonFile.toJson(fileBean);
    }

    private String handleFastFile(Message msg) {
        Gson gsonFile = new Gson();
        FastFileParamsBean paramFile = gsonFile.fromJson((String) msg.obj, FastFileParamsBean.class);
        BaseModel fileBean = new BaseModel();
        fileBean.setStatus(1);
        JsonObject jsonObject = new JsonObject();
        if (paramFile == null || TextUtils.isEmpty(paramFile.getMethod())) {
            fileBean.setStatus(0);
            fileBean.setMsg("method为空");
            jsonObject.addProperty("file", false);
            fileBean.setData(jsonObject);
            return gsonFile.toJson(fileBean);
        }
        String path = getRealFastPath(paramFile.getPath());
        String name = paramFile.getName();
        if (!FileManagerUtils.checkFileName(name)) {
            fileBean.setStatus(0);
            fileBean.setMsg("文件名必须是英文数字下划线");
            return gsonFile.toJson(fileBean);
        }
        String exension = paramFile.getExtension();
        if (TextUtils.isEmpty(exension)) {
            exension = "json";
        }
        path += new StringBuilder().append("/").append(name).append(".").append(exension);
        String fileMethod = paramFile.getMethod();
        switch (fileMethod) {
            case "setFast":
                JsonElement element = paramFile.getData();
                String data = "";
                if (element.isJsonPrimitive()) {
                    data = element.getAsJsonPrimitive().getAsString();
                } else if (element.isJsonObject()) {
                    data = element.getAsJsonObject().toString();
                }
                 if (TextUtils.isEmpty(data)) {
                    fileBean.setStatus(0);
                    fileBean.setMsg("写入的数据为空");
                } else {
                    try {
                        File file = new File(path);
                        file.setLastModified(CommonUtils.getCurTime10() + paramFile.getTime() * 1000);
                        jsonObject.addProperty("file", FileIOUtils.writeFileFromString(path, paramFile.getPas() == 1 ? EncodeUtils.encodeByKey(data, Constants.key) : data));
                    } catch (UnsupportedEncodingException e) {
                        jsonObject.addProperty("file", false);
                    }
                }

                break;
            case "getFast":
                File file = new File(path);
                if (file.exists()) {
                    if (FileUtils.getFileLastModified(file) < CommonUtils.getCurTime10()) {
                        FileUtils.delete(file);
                        fileBean.setMsg("文件已过期");
                        jsonObject.addProperty("file", false);
                    } else {
                        try {
                            String getData = FileIOUtils.readFile2String(file);
                            if (paramFile.getPas() == 1) {
                                getData = EncodeUtils.decodeBykey(getData, Constants.key);
                            }
                            jsonObject.addProperty("file", getData);
                        } catch (UnsupportedEncodingException e) {
                            jsonObject.addProperty("file", false);
                        }
                    }
                } else {
                    fileBean.setStatus(0);
                    fileBean.setMsg("文件不存在");
                }
                break;
            case "delFast":
                jsonObject.addProperty("file", FileUtils.delete(path));
                break;
            case "addStore":
                path = getRealFastPath(paramFile.getPath());
                exension = paramFile.getExtension();
                if (TextUtils.isEmpty(exension)) {
                   exension =  "txt";
                }
                path += new StringBuilder().append("/").append(name).append(".").append(exension);
                JsonElement storeElement = paramFile.getData();
                String addData = "";
                if (storeElement.isJsonPrimitive()) {
                    addData = storeElement.getAsJsonPrimitive().getAsString();
                } else if (storeElement.isJsonObject()) {
                    addData = storeElement.getAsJsonObject().toString();
                }
                if (TextUtils.isEmpty(addData)) {
                    fileBean.setStatus(0);
                    fileBean.setMsg("写入的数据为空");
                } else {
                    try {
                        jsonObject.addProperty("file",
                                FileIOUtils.writeFileFromString(path, paramFile.getPas() == 1 ? EncodeUtils.encodeByKey(addData, Constants.key) : addData));
                    } catch (UnsupportedEncodingException e) {
                        jsonObject.addProperty("file", false);
                    }
                }
                break;
            case "getStore":
                path = getRealFastPath(paramFile.getPath());
                exension = paramFile.getExtension();
                if (TextUtils.isEmpty(exension)) {
                    exension =  "txt";
                }
                path += new StringBuilder().append("/").append(name).append(".").append(exension);
                try {

                    String getData = "";
                    int page = paramFile.getPage();
                    if (page > 0) {
                        // 开启分页
                        int limit = paramFile.getLimit();
                        long pos = (page - 1) * limit;
//                        long endIndex = page * limit - 1;
                        RandomAccessFile accessFile = new RandomAccessFile(path, "r");
                        if (pos > accessFile.length()) {
                            fileBean.setStatus(0);
                            fileBean.setMsg("没有更多内容了");
                            return gsonFile.toJson(fileBean);
                        }
                        accessFile.seek(pos);
                        StringBuilder builder = new StringBuilder();
                        while (builder.length() < limit) {
                            builder.append(accessFile.readChar());
                        }
                    } else {
                        getData = FileIOUtils.readFile2String(path);
                    }

                    if (paramFile.getPas() == 1) {
                        getData = EncodeUtils.decodeBykey(getData, Constants.key);
                    }
                    jsonObject.addProperty("file", getData);


                } catch (Exception e) {
                    jsonObject.addProperty("file", false);
                }

                break;
            case "delStore":
                path = getRealFastPath(paramFile.getPath());
                exension = paramFile.getExtension();
                if (TextUtils.isEmpty(exension)) {
                    exension =  "txt";
                }
                path += new StringBuilder().append("/").append(name).append(".").append(exension);
                jsonObject.addProperty("file", FileUtils.delete(path));
                break;

        }
        fileBean.setData(jsonObject);
        return gsonFile.toJson(fileBean);
    }


    String generatePageFileName(String id) {
        return "page/" + EncodeUtils.md5(id) + ".json";
    }

    /**
     *
     * @param m 组件id
     * @param v 组件版本id
     * @return
     */
    String generateModFileName(String m, String v) {
        return "mod/" + m + "/" + v + "/095ba9ad09433bdc1517df844f82fb3e.json";
    }
    String generateModCssFileName(String m, String v) {
        return "mod/" + m + "/" + v + "/c7a628cba22e28eb17b5f5c6ae2a266a.css";
    }


    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            int progress = view.getProgress();
            if (progress == 100) {
                handlePageFinished(mAgentWeb);
            }
            super.onPageFinished(view, url);
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
////            return super.shouldOverrideUrlLoading(view, request);
//            return false;
//        }
//
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.e("appmf", "webview-err");
            super.onReceivedError(view, request, error);
        }
    }

    class MyChromeClient extends WebChromeClient {

    }

    public void handlePageFinished(AgentWeb agentWeb) {

    }
    class ComponentBean {
        String m;
        String v;

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }

    class HttpParamsBean implements Serializable {
        private String url;
        private String method;
        private JsonObject data;
        private HashMap<String, String> header;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public JsonObject getData() {
            return data;
        }

        public void setData(JsonObject data) {
            this.data = data;
        }

        public HashMap<String, String> getHeader() {
            return header;
        }

        public void setHeader(HashMap<String, String> header) {
            this.header = header;
        }
    }


}
