package com.example.hongcheng.thelearn.presenter;

/**
 * Created by hongcheng on 16/4/3.
 */
public interface WelcomePresenter extends BasePresenter {
    void checkFirstInstall();
    void cacheLogin();
    void checkVersionUpdate();
}
