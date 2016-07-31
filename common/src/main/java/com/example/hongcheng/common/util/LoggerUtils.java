package com.example.hongcheng.common.util;

import org.apache.log4j.Logger;

/**
 * Created by hongcheng on 16/4/2.
 */
public class LoggerUtils {

    private LoggerUtils(){

    }
    public static void debug(String tag, Object message){
        Logger.getLogger(tag).debug(message);
    }

    public static void debug(String tag, Object message, Throwable t){
        Logger.getLogger(tag).debug(message, t);
    }

    public static void info(String tag, Object message){
        Logger.getLogger(tag).info(message);
    }

    public static void info(String tag, Object message, Throwable t){
        Logger.getLogger(tag).info(message, t);
    }

    public static void warn(String tag, Object message){
        Logger.getLogger(tag).warn(message);
    }

    public static void warn(String tag, Object message, Throwable t){
        Logger.getLogger(tag).warn(message, t);
    }

    public static void error(String tag, Object message){
        Logger.getLogger(tag).error(message);
    }

    public static void error(String tag, Object message, Throwable t){
        Logger.getLogger(tag).error(message, t);
    }
}
