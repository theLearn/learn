package com.example.hongcheng.thelearn.presenter.impl;

import com.example.hongcheng.thelearn.presenter.MainPresenter;
import com.example.hongcheng.thelearn.ui.uiInterface.MainUI;

/**
 * Created by hongcheng on 16/3/30.
 */
public class MainPresenterImpl extends BasePresenterImpl implements MainPresenter {

    private MainUI mainUI;

    public MainPresenterImpl(MainUI ui) {
        this.mainUI = ui;
    }

}
