package com.example.hongcheng.thelearn;

import android.app.Application;

import com.example.hongcheng.common.CrashHandler;
import com.example.hongcheng.common.constant.BaseConstants;
import com.example.hongcheng.common.db.DBCore;
import com.example.hongcheng.common.util.FileUtils;
import com.example.hongcheng.common.util.StringUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);
        DBCore.init(this);
        mRefWatcher = BaseConstants.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
        initLog4j();
    }


    public static BaseApplication getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher(){
        return getInstance().mRefWatcher;
    }

    private void initLog4j() {
        final LogConfigurator logConfigurator = new LogConfigurator();

        String logPath = FileUtils.getLogFilePath();
        try {
            if (!StringUtils.isEmpty(logPath)) {
                File file = new File(logPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                logPath += File.separator + BaseConstants.LOG_FILE;
                file = new File(logPath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                logConfigurator.setFileName(logPath);
            }
            Level level = BaseConstants.DEBUG ? Level.ALL : Level.ERROR;
            logConfigurator.setRootLevel(level);
            logConfigurator.setLevel("org.apache", Level.ALL);
            logConfigurator.configure();
        } catch (Exception e) {
            android.util.Log.e("Application", e.getMessage(), e);
        }
    }
}
