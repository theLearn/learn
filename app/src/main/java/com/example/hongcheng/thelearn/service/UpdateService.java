package com.example.hongcheng.thelearn.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.hongcheng.common.constant.BaseConstants;
import com.example.hongcheng.common.retrofitHelper.ExampleRetrofit;
import com.example.hongcheng.common.retrofitHelper.RetrofitManager;
import com.example.hongcheng.common.util.FileUtils;
import com.example.hongcheng.common.util.IOUtils;
import com.example.hongcheng.common.util.LoggerUtils;
import com.example.hongcheng.common.util.RxUtils;
import com.example.hongcheng.common.util.StringUtils;
import com.example.hongcheng.thelearn.BaseApplication;
import com.example.hongcheng.thelearn.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class UpdateService extends Service {
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();
    private NotificationManager manager;
    private File file;

    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        //TODO 下载新版本并通过Notification显示下载进度
        final String url = intent.getStringExtra("fileUrl");

        if (StringUtils.isEmpty(url)) {
            return super.onStartCommand(intent, flags, startId);
        }

        createNotification(0, false);

        mSubscriptions = RxUtils.getCompositeSubscription(mSubscriptions);
        mSubscriptions.add(Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Response response = RetrofitManager.createRetrofit(BaseApplication.getInstance(), ExampleRetrofit.class).downloadNewVersion("theLearn.apk");
                if (response.isSuccessful()) {
                    long sumSize = response.raw().body().contentLength();
                    long downLoadSize = 0L;
                    InputStream in = response.raw().body().byteStream();

                    file = new File(FileUtils.getApkFilePath(), FileUtils.getFileName(url));
                    OutputStream o = null;
                    try {
                        FileUtils.makeDirs(file.getAbsolutePath());
                        o = new FileOutputStream(file, false);
                        byte data[] = new byte[1024];
                        int length = -1;
                        while ((length = in.read(data)) != -1) {
                            o.write(data, 0, length);
                            downLoadSize += length;
                            subscriber.onNext((int) (downLoadSize / sumSize) * 100);
                        }
                        o.flush();
                    } catch (FileNotFoundException e) {
                        createNotification(-1, true);
                        LoggerUtils.error("FileNotFoundException", e.getMessage());
                    } catch (IOException e) {
                        createNotification(-1, true);
                        LoggerUtils.error("IOException", e.getMessage());
                    } finally {
                        IOUtils.close(o);
                        IOUtils.close(in);
                        subscriber.onNext(-1);
                    }
                } else {
                    subscriber.onNext(-1);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        stopSelf();
                    }

                    @Override
                    public void onError(Throwable e) {
                        createNotification(-1, true);
                        stopSelf();
                    }

                    @Override
                    public void onNext(Integer i) {
                        createNotification(i, false);
                    }
                }));
        return super.onStartCommand(intent, flags, startId);

    }

    private void createNotification(int i, boolean cancel) {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Notification myNotify = new Notification();
        myNotify.icon = R.mipmap.main_icon;
        myNotify.when = System.currentTimeMillis();
        myNotify.flags = cancel ? Notification.FLAG_NO_CLEAR : Notification.FLAG_AUTO_CANCEL;

        RemoteViews rv = new RemoteViews(getPackageName(),
                R.layout.download_notification);

        Intent intent = new Intent(Intent.ACTION_MAIN);

        if (0 <= i && i < 100){
            rv.setProgressBar(R.id.pb_download, 100, i, false);
            rv.setTextViewText(R.id.tv_download_tip, getString(R.string.downloading));
            rv.setTextViewText(R.id.tv_download_process, i+"%");
        }else if(i < 0){
            rv.setTextViewText(R.id.tv_download_tip, getString(R.string.download_fail));
        }else{
            rv.setTextViewText(R.id.tv_download_tip, getString(R.string.download_success));
            if(file != null && file.exists()){
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
        }

        myNotify.contentView = rv;


        PendingIntent contentIntent = PendingIntent.getActivity(BaseApplication.getInstance(), 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        myNotify.contentIntent = contentIntent;

        manager.notify(BaseConstants.NOTIFICATION_FLAG, myNotify);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribe(mSubscriptions);
    }
}
