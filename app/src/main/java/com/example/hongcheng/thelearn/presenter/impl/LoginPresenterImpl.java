package com.example.hongcheng.thelearn.presenter.impl;

import com.example.hongcheng.common.constant.BaseConstants;
import com.example.hongcheng.common.retrofitHelper.ExampleRetrofit;
import com.example.hongcheng.common.retrofitHelper.RetrofitManager;
import com.example.hongcheng.common.retrofitHelper.response.UserInfoResponse;
import com.example.hongcheng.common.util.SPUtils;
import com.example.hongcheng.thelearn.BaseApplication;
import com.example.hongcheng.thelearn.presenter.LoginPresenter;
import com.example.hongcheng.thelearn.ui.uiInterface.LoginUI;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by hongcheng on 16/4/4.
 */
public class LoginPresenterImpl extends BasePresenterImpl implements LoginPresenter {
    private LoginUI loginUI;

    public LoginPresenterImpl(LoginUI loginUI) {
        this.loginUI = loginUI;
    }

    @Override
    public void login(final String email, final String password) {
        mSubscriptions.add(RetrofitManager.createRetrofit(BaseApplication.getInstance(), ExampleRetrofit.class)
                .login(email, password)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loginUI.showLoading(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO 暂时打桩测试
                        loginUI.showLoading(false);
                        SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.IS_FROM_LOGIN, true);
                        loginUI.toWelcome();
                    }

                    @Override
                    public void onNext(UserInfoResponse userInfoResponse) {
                        loginUI.showLoading(false);
                        int errCode = userInfoResponse.error;
                        if (BaseConstants.ERROR_OK == errCode) {
                            SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.IS_FROM_LOGIN, true);
                            SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.USERNAME, email);
                            SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.PASSWORD, password);
                            SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.TOKEN, userInfoResponse.token);
                            SPUtils.putValueToSP(BaseApplication.getInstance(), BaseConstants.ACCOUNT_ID, userInfoResponse.accountId);
                            loginUI.toWelcome();
                        } else {
                            //TODO 根据错误码提示用户
                        }
                    }
                }));
    }
}
