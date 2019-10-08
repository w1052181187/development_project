package com.cmstop.jstt.ad;

import android.content.Context;
import android.view.View;

import com.cmstop.jstt.Const;
import com.cmstop.jstt.ad.AdDataDummy.BaiduNativeNetworkDummyListener;
import com.cmstop.jstt.utils.Common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class AdDataBaiduDummy {

    private static boolean isBaiduAdJarExists = true;

    public static void getDataBySDKDummy(final Context context, final String adPlaceId, final BaiduNativeNetworkDummyListener listener) {
//		// old code begin
//		// 百度ad
//		BaiduNative baiduNative = new BaiduNative(context, adPlaceId, new BaiduNativeNetworkListener() {
//			
//			@Override
//			public void onNativeLoad(List<NativeResponse> list) {
//				ArrayList<AdDataDummy.NativeResponseDummy> dummyList = new ArrayList<AdDataDummy.NativeResponseDummy>();
//				if(!Common.isListEmpty(list)){
//					for(NativeResponse n : list){
//						NativeResponseDummy nd = new NativeResponseDummy(n);
//						dummyList.add(nd);
//					}
//				}
//				listener.onNativeLoad(dummyList);
//			}
//			
//			@Override
//			public void onNativeFail(NativeErrorCode arg0) {
//			}
//		});
//		RequestParameters requestParameters = new RequestParameters.Builder()
//			.confirmDownloading(true)
//			.build();
//		baiduNative.makeRequest(requestParameters);
//		// old code end
        if (!isBaiduAdJarExists) {
            return;
        }
        Class<?> bnClass = null;
        try {
            bnClass = Class.forName("com.baidu.mobad.feeds.BaiduNative");
        } catch (Exception e) {
            e.printStackTrace();
            isBaiduAdJarExists = false;
            return;
        }
        try {
            // baiduNative
            Class<?> bnnlClass = Class.forName("com.baidu.mobad.feeds.BaiduNative$BaiduNativeNetworkListener");
            BaiduNativeNetworkReflectListener bnnrListener = new BaiduNativeNetworkReflectListener(listener);
            Object bnnrlInst = Proxy.newProxyInstance(bnClass.getClassLoader(), new Class[]{bnnlClass}, bnnrListener);
            Object bnInst = bnClass.getDeclaredConstructors()[0]
                    .newInstance(context, adPlaceId, bnnrlInst);
            // requestParameters
            Class<?> rpBuilderClass = Class.forName("com.baidu.mobad.feeds.RequestParameters$Builder");
            Object rpBuilderInst = rpBuilderClass.getConstructor().newInstance();
            rpBuilderInst = rpBuilderClass.getMethod("confirmDownloading", boolean.class).invoke(rpBuilderInst, true);
            Object rpInst = rpBuilderClass.getMethod("build").invoke(rpBuilderInst);
            // makeRequest
            Method[] methods = bnClass.getMethods();
            for (Method m : methods) {
                if ("makeRequest".equals(m.getName())
                        && m.getParameterTypes() != null
                        && m.getParameterTypes().length == 1) {
                    m.invoke(bnInst, rpInst);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAppSid(Context context, String appid) {
        String sid = appid;
        if (sid == null || sid.isEmpty()) {
            sid = Const.BAIDU_AD_APPID_DEFAULT;
        }

        if (!isBaiduAdJarExists) {
            return;
        }
        Class<?> bnClass = null;
        try {
            bnClass = Class.forName("com.baidu.mobad.feeds.BaiduNative");
        } catch (Exception e) {
            e.printStackTrace();
            isBaiduAdJarExists = false;
            return;
        }
        try {
//			com.baidu.mobads.AdView.setAppSid(context, sid);
            Class<?> avClass = Class.forName("com.baidu.mobads.AdView");
            Method sasMethod = avClass.getMethod("setAppSid", new Class[]{Context.class, String.class});
            sasMethod.invoke(null, new Object[]{context, sid});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class BaiduNativeNetworkReflectListener implements InvocationHandler {
        private BaiduNativeNetworkDummyListener listener;

        BaiduNativeNetworkReflectListener(BaiduNativeNetworkDummyListener listener) {
            this.listener = listener;
        }

        @Override
        public Object invoke(Object obj, Method method, Object[] aobj) throws Throwable {
            if ("onNativeLoad".equals(method.getName())) {
                if (aobj != null && aobj[0] != null && List.class.isInstance(aobj[0])) {
                    List list = (List) aobj[0];
                    ArrayList<AdDataDummy.NativeResponseDummy> dummyList = new ArrayList<AdDataDummy.NativeResponseDummy>();
                    if (!Common.isListEmpty(list)) {
                        for (Object lo : list) {
                            NativeResponseDummy nd = new NativeResponseDummy(lo);
                            dummyList.add(nd);
                        }
                    }
                    listener.onNativeLoad(dummyList);
                }
            }
            return null;
        }

    }

    public static class NativeResponseDummy extends AdDataDummy.NativeResponseDummy {
        private Object adBean;

        public NativeResponseDummy(Object adBean) {
            super(adBean);
            this.adBean = adBean;
        }

        public void recordImpression(View view) {
//			adBean.recordImpression(view);
            try {
                adBean.getClass().getMethod("recordImpression", View.class).invoke(adBean, view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handleClick(View view) {
//			adBean.handleClick(view);
            try {
                adBean.getClass().getMethod("handleClick", View.class).invoke(adBean, view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//		public abstract void handleClick(View view, int i);

        public String getTitle() {
//			return adBean.getTitle();
            return invokeMethod(adBean, "getTitle", String.class);
        }

        public String getDesc() {
//			return adBean.getDesc();
            return invokeMethod(adBean, "getDesc", String.class);
        }

        public String getIconUrl() {
//			return adBean.getIconUrl();
            return invokeMethod(adBean, "getIconUrl", String.class);
        }

        public String getImageUrl() {
//			return adBean.getImageUrl();
            return invokeMethod(adBean, "getImageUrl", String.class);
        }

        //		public abstract int getMainPicWidth();
//
//		public abstract int getMainPicHeight();
//
//		public abstract String getBrandName();
//
//		public abstract boolean isDownloadApp();
//
//		public abstract boolean isAdAvailable(Context context);
//
//		public abstract long getAppSize();
//
//		public abstract String getAppPackage();
//
        public List<String> getMultiPicUrls() {
//			return adBean.getMultiPicUrls();
            return invokeMethod(adBean, "getMultiPicUrls", List.class);
        }

//		public abstract Map getExtras();
//
//		public abstract void onStart(Context context);
//
//		public abstract void onError(Context context, int i, int j);
//
//		public abstract void onComplete(Context context);
//
//		public abstract void onClose(Context context, int i);
//
//		public abstract void onClickAd(Context context);
//
//		public abstract void onFullScreen(Context context, int i);
//
//		public abstract String getVideoUrl();
//
//		public abstract int getDuration();
//
//		public abstract MaterialType getMaterialType();
//
//		public abstract String getHtmlSnippet();
//
//		public abstract WebView getWebView();
    }

    private static <T> T invokeMethod(Object obj, String methodName, Class<T> c) {
        try {
            Object ret = obj.getClass().getMethod(methodName).invoke(obj);
            if (ret != null && c.isInstance(ret)) {
                return (T) ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
