package com.example.hongcheng.common.constant;

import java.io.File;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseConstants {

    //debug开关
    public static final boolean DEBUG = true;

    //记录是否首次安装
    public static final String IS_FIRST_INSTALL = "IS_FIRST_INSTALL";

    //记录是否从登陆界面过来
    public static final String IS_FROM_LOGIN = "IS_FROM_LOGIN";

    //RecyclerView限制个数及开关
    public static final boolean IS_LIMIT = true;
    public static final int LIMIT_NUM = 30;

    //日志相关
    public static final String BASE_FILE_PATH = "LearnArchitecture";
    public static final String LOG_PATH = BASE_FILE_PATH + File.separator + "log";
    public static final String LOG_FILE = BASE_FILE_PATH + ".log";
    public static final String CRASH_PATH = BASE_FILE_PATH + File.separator + "crash";
    public static final String APK_PATH = BASE_FILE_PATH + File.separator + "apk";

    //屏幕相关
    public static final String SCREEN_WIDTH = "SCREEN_WIDTH";
    public static final String SCREEN_HEIGHT = "SCREEN_HEIGHT";

    // 登陆相关相关
    public static final int ERROR_OK = 0;
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String TOKEN = "TOKEN";
    public static final String ACCOUNT_ID = "ACCOUNT_ID";

    public static final int NOTIFICATION_FLAG = 1000;
}
