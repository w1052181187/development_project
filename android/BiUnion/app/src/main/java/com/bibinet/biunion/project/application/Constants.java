package com.bibinet.biunion.project.application;

import com.bibinet.biunion.project.bean.LoginResultBean;
/**
 * Created by bibinet on 2017-5-18.
 */

public class Constants {
//    public final static int NET_TYPE_INSIDE_ZHIYUN = 0;  //内网接口 app1913 志云
//    public final static int NET_TYPE_INSIDE_YANGZHI = 1;  //内网接口 app1913 杨志
//    public final static int NET_TYPE_INSIDE_ZHIYUAN = 2;  //内网接口 app1913 zhiyuan
//    public final static int NET_TYPE_OUTSIDE = 3; //映射接口 流量
//    public final static int NET_TYPE_TEST = 4;  //测试服务接口
//    public final static int NET_TYPE_TEST_OUTSIDE = 5;  //测试服务 外网
    public final static int NET_TYPE_FORMAL = 6;  //正式接口 全能


    public static LoginResultBean loginresultInfo = null;
    public static int NET_INTERFACE_TYPE = 6;

    static final String DB_NAME = "bibinet_biunion.db";

    public static final String MEDIA_LIVE_DETAIL_DATA_TYPE = "live_type";
    public static final String MEDIA_RECORD_DETAIL_DATA_RECORD_PBJECTID = "record_objectid";
    public static final String MEDIA_RECORD_DETAIL_DATA_INFO= "record_info";
    public static final String MEDIA_RECORD_DETAIL_DATA_NAME= "record_name";
    public static final String MEDIA_LIVE_DETAIL_DATA_CONFIG = "live_config";

    public enum LiveType {
        TYPE_SPECTATOR, //观众
        TYPE_ANCHOT //主持
    }

    public static final int FILE_TYPE_BUSINESS = 1;
    public static final int FILE_TYPE_LEGAL_PERSON_FRONT = 6;
    public static final int FILE_TYPE_LEGAL_PERSON_REVERSE = 7;
    public static final int FILE_TYPE_LICENCE_OPEN_ACCOUNT = 4;
}
