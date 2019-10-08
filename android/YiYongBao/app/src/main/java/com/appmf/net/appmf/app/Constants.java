package com.appmf.net.appmf.app;


import android.os.Environment;

import com.appmf.net.appmf.utils.DeviceUtils;

/**
 * @author wyg
 * @date 2018/11/27
 */

public class Constants {

//    public static String HOST = "http://api.appmf.net";

    public final static String UMENG_KEY = "5c369a3ef1f556b0ad000b1e";
    public final static String UMENG_CHANNEL = "umeng_appmf";

    public final static String FILE_PATH_SD = Environment.getExternalStorageDirectory().
            getAbsolutePath();

    public final static String FILE_PATH_BASE = FILE_PATH_SD + "/" + Constants.class.getPackage().getName();


    public static String key = "wC:GnD";

    static final String DB_NAME = Constants.class.getPackage().getName() + ".db";

    public static final String KEY_INTENT_AD = "AD_CONTENT";
    public static final String KEY_INTENT_AD_CACHE = "AD_IS_CACHE";


    public final static String ASSERTS_FILE_CONFIG_AD_NAME = "config/312a18b44b6ddf6d9a2b06db05b23ffb.json";
    public final static String ASSERTS_FILE_CONFIG_SYS_NAME = "config/2245023265ae4cf87d02c8b6ba991139.json";
    public final static String ASSERTS_FILE_PAGE_MAIN_NAME = "page/6a992d5529f459a44fee58c733255e86.html";
    public final static String ASSERTS_FILE_SQL_INSTALL_NAME = "sql/88df4874e44770bcf41a7e8dfe1aef96.json";
    public final static String ASSERTS_FILE_SQL_INIT_NAME = "sql/7125153abbcb2e949a1b4f81cbe22af5.json";

    /***
     * 文件管理相关路径常量
     */
    public static final String FILE_MANAGER_DOWNLOAD_DIR = FILE_PATH_BASE + "/boxDir";
    public static final String FILE_MANAGER_CACHE_DIR = FILE_PATH_BASE + "/cacheDir";
    public static final String FILE_MANAGER_RESOURSE_DIR = FILE_PATH_BASE + "/fxDir";

    //缓存图片
    public final static String PATH_CACHE_AD_IMAGE = FILE_MANAGER_CACHE_DIR + "/adImage";
    //缓存的音乐文件
    public final static String PATH_CACHE_AD_VIDEO = FILE_MANAGER_CACHE_DIR + "/adVideo";
}
