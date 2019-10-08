package com.appmf.net.appmf.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;

import com.appmf.net.appmf.bean.AdConfigBean;
import com.appmf.net.appmf.bean.DbConfigBean;
import com.appmf.net.appmf.core.DBHelper;
import com.appmf.net.appmf.core.http.api.GeeksApis;
import com.appmf.net.appmf.dao.DaoMaster;
import com.appmf.net.appmf.dao.DaoSession;
import com.appmf.net.appmf.di.component.AppComponent;
import com.appmf.net.appmf.di.component.DaggerAppComponent;
import com.appmf.net.appmf.di.module.AppModule;
import com.appmf.net.appmf.di.module.HttpModule;
import com.appmf.net.appmf.utils.CommonUtils;
import com.appmf.net.appmf.utils.RetrofitManager;
import com.appmf.net.appmf.utils.SPUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.umeng.commonsdk.UMConfigure;

import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.appmf.net.appmf.app.Constants.ASSERTS_FILE_CONFIG_SYS_NAME;
import static com.appmf.net.appmf.app.Constants.ASSERTS_FILE_SQL_INIT_NAME;
import static com.appmf.net.appmf.app.Constants.ASSERTS_FILE_SQL_INSTALL_NAME;
import static com.appmf.net.appmf.utils.SPUtils.APP_CONFIG;
import static com.appmf.net.appmf.utils.SPUtils.KEY_FIRST_INIT_DB;

/**
 * @author wyg
 * @date 2018/11/27
 */

public class MyApp extends Application {

    private static MyApp instance;
    private static AppComponent appComponent;
    public static boolean isFirstRun;
    private String domain;

    //static 代码段可以防止内存泄露
    {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    private DaoSession mDaoSession;
    private AdConfigBean adConfigBean;
    private String sysConfigData;
    private String appId;

    GeeksApis apiInterface;

    @Inject
    OkHttpClient.Builder mClientBuilder;
    @Inject
    Retrofit.Builder mRetrofitBuilder;

    public OkHttpClient.Builder getClientBuilder() {
        return mClientBuilder;
    }

    public static synchronized MyApp getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build().inject(this);
        instance = this;
//              refWatcher = LeakCanary.install(this);
        initGreenDao();
        initUmeng();
        initDb();
        // 程序创建的时候执行
        Log.e("appmf", "onCreate");
    }

    private void initDb() {
        CommonUtils.handleFileByRxjava(this, ASSERTS_FILE_CONFIG_SYS_NAME, new CommonUtils.RxjavaCallback() {
            @Override
            public void success(String data) {
                setSysConfigData(data);
                handleDbConfigFile(data);
            }
        });
    }

    private void handleDbConfigFile(String data) {
        final SPUtils spUtils = SPUtils.getInstance(this, APP_CONFIG);
        boolean isFirstDb = spUtils.getBoolean(KEY_FIRST_INIT_DB, false);
        if (isFirstDb) {
            return;
        }
        final Gson gson = new Gson();
        final JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        int sqlSetup = jsonObject.getAsJsonPrimitive("sqlSetup").getAsInt();
        if (sqlSetup == 1) {
            CommonUtils.handleFileByRxjava(this, ASSERTS_FILE_SQL_INSTALL_NAME, new CommonUtils.RxjavaCallback() {
                @Override
                public void success(String data) {
                    ArrayList<DbConfigBean> beans = gson.fromJson(data, new TypeToken<ArrayList<DbConfigBean>>(){}.getType());
                    DBHelper dbHelper = DBHelper.getInstance(instance);
                    int sqlInitData = jsonObject.getAsJsonPrimitive("sqlInitData").getAsInt();
                    if (dbHelper.creatTable(beans)) {
                        switch (sqlInitData) {
                            case 1:
                                handleDbInitTable(spUtils, gson, dbHelper);
                                break;
                            case 0:
                                spUtils.putBoolean(KEY_FIRST_INIT_DB, true);
                                break;
                        }
                    }
                }
            });
        }
    }

    private void handleDbInitTable(final SPUtils spUtils, final Gson gson, final DBHelper dbHelper) {
        CommonUtils.handleFileByRxjava(instance, ASSERTS_FILE_SQL_INIT_NAME, new CommonUtils.RxjavaCallback() {
            @Override
            public void success(String data) {
                ArrayList<String> datas = gson.fromJson(data, new TypeToken<ArrayList<String>>(){}.getType());
                dbHelper.initTableData(spUtils, datas);
            }
        });
    }

    private void initUmeng() {
        UMConfigure.init(this, Constants.UMENG_KEY, Constants.UMENG_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    public static synchronized AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

    public void setAdConfigBean(AdConfigBean bean) {
        this.adConfigBean = bean;
    }

    public AdConfigBean getAdConfigBean() {
        return adConfigBean;
    }

    public void setSysConfigData(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        this.sysConfigData = data;
        JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        this.appId = jsonObject.getAsJsonPrimitive("appId").getAsString();
        String host = jsonObject.getAsJsonPrimitive("apiDomain").getAsString();
        domain = jsonObject.getAsJsonPrimitive("domain").getAsString();
        initRetrofit(host);
    }

    private void initRetrofit(String host) {
        RetrofitManager.getInst().initRetrofit(getApplicationContext(), host);
        apiInterface = RetrofitManager.getInst().createService(GeeksApis.class);
//        mRetrofitBuilder.baseUrl(host);
//        apiInterface = mRetrofitBuilder.build().create(GeeksApis.class);
    }

    public String getSysConfigData() {
        return sysConfigData;
    }

    public String getAppId() {
        return appId;
    }

    public GeeksApis getApiInterface() {
        return apiInterface;
    }

    public String getDomain() {
        return domain;
    }
}
