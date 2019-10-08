package com.appmf.net.appmf.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Message;

import com.appmf.net.appmf.R;
import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.base.BaseHandler;
import com.appmf.net.appmf.base.activity.BaseWebviewActivity;
import com.appmf.net.appmf.contract.MainContract;
import com.appmf.net.appmf.core.DBHelper;
import com.appmf.net.appmf.presenter.MainPresenter;
import com.appmf.net.appmf.utils.CommonUtils;
import com.just.agentweb.AgentWeb;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.appmf.net.appmf.app.Constants.ASSERTS_FILE_PAGE_MAIN_NAME;
import static com.appmf.net.appmf.app.Constants.FILE_PATH_BASE;

public class MainActivity extends BaseWebviewActivity<MainPresenter> implements MainContract.View {

    private final int MSG_PAGE_FINISHED = 1;
    private final int MSG_END = 2;
    private final String ASSETS_BASE_URL = "file:///android_asset/";
    private long lastTime = 0;
    private String mUrl = "";
    private AgentWeb mAgemtweb;
    private boolean isEnd = false;

    @Inject
    MainPresenter mainPresenter;

    public static void launch(Activity from) {
        Intent intent = new Intent(from, MainActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        showNormal();
        String mFileName = ASSERTS_FILE_PAGE_MAIN_NAME;
        CommonUtils.handleFileByRxjava(this, mFileName, new CommonUtils.RxjavaCallback() {
            @Override
            public void success(String data) {
                initLoadData(data);
            }
        });
//        String assertsStr = FileIOUtils.getFromAssets(this, "index2.html");
//        initLoadData(assertsStr);
//        initLoadUrl();
        String key = "wC:GnD";
        final String path = FILE_PATH_BASE + "/encodePut.txt";
//        DBHelper dbHelper = DBHelper.getInstance(getActivityComponent().getActivity());
//        dbHelper.testInsert();
//        getPermissions();
    }

    private void getPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            DBHelper dbHelper = DBHelper.getInstance(getActivityComponent().getActivity());
                            dbHelper.testInsert();
                        } else {
                            CommonUtils.showShortMessage(getActivityComponent().getActivity(), "权限申请失败，某些功能无法使用");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    BaseHandler mHandler = new BaseHandler(new BaseHandler.HandlerMsgCallback() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PAGE_FINISHED:
                    String data = MyApp.getInstance().getSysConfigData();
                    mAgemtweb.getJsAccessEntrace().quickCallJs("appmf.ready", data);
                    mHandler.obtainMessage(MSG_END).sendToTarget();
                    break;
                case MSG_END:
                    isEnd = true;
                    if (CommonUtils.isNetworkConnected(getActivityComponent().getActivity())) {
                        mainPresenter.saveToCache(getActivityComponent().getActivity());
                        mainPresenter.uploadStatistics();
                    }
                    break;
            }
        }
    });

    @Override
    public void handlePageFinished(AgentWeb agentWeb) {
        super.handlePageFinished(agentWeb);
        if (isEnd) {
            return;
        }
        if (agentWeb == null) {
            return;
        }
        this.mAgemtweb = agentWeb;
        mHandler.obtainMessage(MSG_PAGE_FINISHED).sendToTarget();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onBackPressedSupport() {
        if (!onBackPressedWebview()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime < 1500) {
                supportFinishAfterTransition();
            } else {
                lastTime = currentTime;
                CommonUtils.showShortMessage(this,"再按一次退出");
            }
        }
    }

    public void initLoadUrl() {
        initLoadUrl(ASSETS_BASE_URL + "index2.html");
    }

}
