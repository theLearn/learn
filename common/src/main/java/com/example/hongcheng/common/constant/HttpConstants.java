package com.example.hongcheng.common.constant;

import java.io.File;

/**
 * @Project CommonProject
 * @Packate com.micky.commonlib.utils
 * @Description
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2015-12-30 17:43
 * @Version 1.0
 */
public class HttpConstants {

    //网络日志大小
    public static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    //超时时间
    public static final long CONNECT_TIMEOUT = 5000L;

    //设缓存有效期为两个星期
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 14;

    //查询缓存的Cache-Control设置
    public static final String CACHE_CONTROL_CACHE = "public, only-if-cached, max-stale=" + CACHE_STALE_SEC;

    //查询网络的Cache-Control设置
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    //基础路径
    public static final String BASE_URL = "http://192.168.239.23/";

    //基础路径
    public static final String HOST_NAME = "8484";

    //登陆接口
    public static final String LOGIN_URL = "login?";

    //查询apk版本接口
    public static final String QUERY_VERSION_URL = "queryVersion?";
}
