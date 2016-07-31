package com.example.hongcheng.thelearn.presenter.impl;

import android.os.Handler;

import com.example.hongcheng.common.constant.BaseConstants;
import com.example.hongcheng.common.retrofitHelper.ExampleRetrofit;
import com.example.hongcheng.common.retrofitHelper.RetrofitManager;
import com.example.hongcheng.common.retrofitHelper.response.UserInfoResponse;
import com.example.hongcheng.common.retrofitHelper.response.VersionInfoResponse;
import com.example.hongcheng.common.util.AppUtils;
import com.example.hongcheng.common.util.DateUtils;
import com.example.hongcheng.common.util.NetUtils;
import com.example.hongcheng.common.util.SPUtils;
import com.example.hongcheng.common.util.StringUtils;
import com.example.hongcheng.thelearn.BaseApplication;
import com.example.hongcheng.thelearn.R;
import com.example.hongcheng.thelearn.presenter.WelcomePresenter;
import com.example.hongcheng.thelearn.ui.uiInterface.WelcomeUI;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hongcheng on 16/4/3.
 */
public class WelcomePresenterImpl extends BasePresenterImpl implements WelcomePresenter {

    private WelcomeUI welcomeUI;
    private final long startTime;
    private long token;
    private long accountId;

    public WelcomePresenterImpl(WelcomeUI welcomeUI) {
        this.welcomeUI = welcomeUI;
        startTime = DateUtils.getCurrentTimeInLong();

        token = SPUtils.getLongFromSP(BaseApplication.getInstance(), BaseConstants.TOKEN);
        accountId = SPUtils.getLongFromSP(BaseApplication.getInstance(), BaseConstants.ACCOUNT_ID);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        checkFirstInstall();
    }

    @Override
    public void checkFirstInstall() {
        boolean isFirstInstall = SPUtils.getBooleanFromSP(BaseApplication.getInstance(), BaseConstants.IS_FIRST_INSTALL, true);
        //TODO 暂时打桩
        if (!isFirstInstall) {
            welcomeUI.toGuide();
            SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.IS_FIRST_INSTALL, false);
        } else {
            cacheLogin();
        }
    }

    @Override
    public void cacheLogin() {
        boolean isFromLogin = SPUtils.getBooleanFromSP(BaseApplication.getInstance(), BaseConstants.IS_FROM_LOGIN);
        if (isFromLogin) {
            SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.IS_FROM_LOGIN, false);
            checkVersionUpdate();
        } else {
            if (0L == token || 0L == accountId) {
                delayJump(true);
                return;
            }
            mSubscriptions.add(RetrofitManager.createRetrofit(BaseApplication.getInstance(), ExampleRetrofit.class)
                    .login(token, accountId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserInfoResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            delayJump(true);
                        }

                        @Override
                        public void onNext(UserInfoResponse userInfoResponse) {
                            if (BaseConstants.ERROR_OK == userInfoResponse.error) {
                                token = userInfoResponse.token;
                                accountId = userInfoResponse.accountId;
                                SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.TOKEN, token);
                                SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.ACCOUNT_ID, accountId);
                                if (NetUtils.isWifi(BaseApplication.getInstance())) {
                                    checkVersionUpdate();
                                } else {
                                    delayJump(false);
                                }
                            } else {
                                delayJump(true);
                            }
                        }
                    }));
        }
    }

    @Override
    public void checkVersionUpdate() {
        mSubscriptions.add(RetrofitManager.createRetrofit(BaseApplication.getInstance(), ExampleRetrofit.class)
                .queryVersion(token, accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        delayJump(false);
                    }

                    @Override
                    public void onNext(VersionInfoResponse versionInfoResponse) {
                        if (BaseConstants.ERROR_OK == versionInfoResponse.error && versionInfoResponse.versionCode > AppUtils.getVersionCode(BaseApplication.getInstance())) {
                            welcomeUI.showUpdateDialog(StringUtils.replaceStr(BaseApplication.getInstance(), R.string.version_update_tip, versionInfoResponse.versionName), versionInfoResponse.fileUrl);
                        } else {
                            delayJump(false);
                        }
                    }
                }));
    }

    /**
     * true 延时跳转到login界面
     * false 延时跳转到main界面
     *
     * @param isToLogin
     */
    private void delayJump(final boolean isToLogin) {
        long leftTime = 3000 + startTime - DateUtils.getCurrentTimeInLong();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isToLogin) {
                    welcomeUI.toLogin();
                } else {
                    welcomeUI.toMain();
                }
            }
        }, leftTime);
    }
}
