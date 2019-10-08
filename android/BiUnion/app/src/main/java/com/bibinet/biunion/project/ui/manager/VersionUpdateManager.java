package com.bibinet.biunion.project.ui.manager;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.RetrofitRequestInterface.ApiInterface;
import com.bibinet.biunion.project.application.BiUnionApplication;
import com.bibinet.biunion.project.application.PathConfig;
import com.bibinet.biunion.project.models.VersionModel;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.UrlConfig;
import com.bibinet.biunion.project.utils.FileUtils;
import com.bibinet.biunion.project.utils.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.bibinet.biunion.project.net.UrlConfig.TYPE_PIS;
import static com.bibinet.biunion.project.net.UrlConfig.getBaseUrl;


/**
 * Created by Administrator on 2017/7/1.
 */

public class VersionUpdateManager implements View.OnClickListener {
    private Context context;

    public VersionUpdateManager(Context context) {
        this.context = context;
    }

    private final String re = "\\\\n";

    private final String url = getBaseUrl(TYPE_PIS)+"appVersion/download.json";

    private final String localPath = PathConfig.cachePathUpdate;

    private VersionModel model;
    public void versionUpdateCheck(){
        Observable<JsonElement> observable = BiUnionApplication.getInstance().getApiInterface()
                .get(url);
        RetrofitManager.subcribe(observable, new BaseRetrofitCallBack(){
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                Gson gson=new Gson();
                VersionModel model = gson.fromJson(s, VersionModel.class);
                VersionUpdateManager.this.model = model;
                versionCheck();
            }

            @Override
            protected void onRequestFail(String resMessage) {

            }
        });
    }

    private void versionCheck(){
        int tar = getVersionCode();
        int src = model.getVersionCode();
        if(tar<src){
            showDialog();
        }
    }
    private Dialog dialog;
    private void showDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_version_update, null, false);
        TextView name = (TextView) view.findViewById(R.id.dia_version_update_name);
        LinearLayout main = (LinearLayout) view.findViewById(R.id.dia_version_update_main);
        view.findViewById(R.id.dia_version_update_close).setOnClickListener(this);
        view.findViewById(R.id.dia_version_update_confirm).setOnClickListener(this);

        String message = model.getDescription();
        List<String> messageList = stringToList(message);
        for(int i=0 ; i<messageList.size() ; i++){
            //添加列表数据
            TextView tv = new TextView(context);
            tv.setTextSize(14);
            tv.setTextColor(Color.parseColor("#999999"));
            tv.setText(messageList.get(i));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setMinHeight(dip2px(context, 25));
//            tv.setHeight(dip2px(context, 25));
            main.addView(tv);
        }
        name.setText(model.getVersionName());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dia_version_update_close:
                dialog.dismiss();
                break;
            case R.id.dia_version_update_confirm:
                //下载
                String url = model.getUrl();
                download(url);
                dialog.dismiss();
                break;
        }
    }

    private void download(String url) {

        //自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
        final String name = new File(model.getUrl()).getName();
        final String l = localPath + "/" +name;
        if(new File(l).exists()){
            //直接调用安装
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(l)), "application/vnd.android.package-archive");
            ((Activity)context).startActivity(intent);
            return;
        }

        final RxjavaDownloadListener listener = new RxjavaDownloadListener() {
            @Override
            public void onStartDownload() {

            }

            @Override
            public void onProgress(long total, long progress) {
                //当前进度和文件总大小
                sendNotification(total, progress);
            }

            @Override
            public void onFinishDownload() {
                clearNotification();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(l)), "application/vnd.android.package-archive");
                ((Activity)context).startActivity(intent);
            }

            @Override
            public void onFail(String errorInfo) {
                clearNotification();
            }
        };
        JsDownloadInterceptor mInterceptor = new JsDownloadInterceptor(listener);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlConfig.getBaseUrl(UrlConfig.TYPE_IIP))
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        listener.onStartDownload();
        Observable<ResponseBody> observable = retrofit.create(ApiInterface.class)
                .downloadMusicFile(url);

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {

                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }

                })
                .observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(InputStream inputStream) throws Exception {
                        if (FileUtils.writeFile(inputStream, localPath, name)){
                            listener.onFinishDownload();
                        } else {
                            listener.onFail("下载文件失败");
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        listener.onFail(throwable.getMessage());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    private void clearNotification() {
        NotificationManager  m = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        m.cancel(id);
    }

    private void sendNotification(Long... values) {
        long max = values[0];
        long current = values[1];
        send(max, current);
    }
    private final int id = 11000;
    private void send(long max, long current){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("开始下载");
        builder.setSmallIcon(R.mipmap.img_notification_icon);
        builder.setContentText("下载中");
        builder.setAutoCancel(true);
        builder.setProgress((int)max, (int)current, false);
        //计算下载进度
        int p = (int) (current*100/max);
        builder.setContentTitle("下载进度："+p+"%");
        builder.setDefaults(Notification.DEFAULT_SOUND);
        Notification timerNotification = builder.build();
        timerNotification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager  m = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        m.notify(id, timerNotification);

    }

    public int getVersionCode(){
        PackageManager manager = context.getPackageManager();//获取包管理器
        try {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);//获取包对象信息
            return  info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<String> stringToList(String stringList) {
        if (stringList == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        String [] s = stringList.split(re);
        for(int i=0 ; i<s.length ; i++){
            list.add(s[i]);
        }
        return list;
    }

    public int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }
}
