package com.example.hongcheng.thelearn.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hongcheng.thelearn.BaseApplication;
import com.example.hongcheng.thelearn.R;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseActivity extends AppCompatActivity {
    protected TextView centerTitle;
    protected ImageView ivBack;
    private ProgressBar loading;

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, false);
    }

    public void setContentView(int layoutResID, boolean isHideBack) {
        super.setContentView(layoutResID);
        initTitle(isHideBack);
    }

    protected void initTitle(boolean isHideBack) {
        centerTitle = (TextView) findViewById(R.id.tv_title);
        loading = (ProgressBar) findViewById(R.id.pb_default);

        if(!isHideBack){
            ivBack = (ImageView) findViewById(R.id.iv_back);
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }
    }

    protected void setTitleText(String title){
        centerTitle.setText(title);
    }

    protected void setTitleText(int resId){
        centerTitle.setText(resId);
    }

    protected void setIvBackResource(int resId){
        if(ivBack != null){
            ivBack.setImageResource(resId);
        }
    }

    protected void showLoading(){
        if(loading != null){
            loading.setVisibility(View.VISIBLE);
        }
    }

    protected void hideLoading(){
        if(loading != null){
            loading.setVisibility(View.GONE);
        }
    }

    protected void goBack(){
        finish();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getRefWatcher().watch(this);
    }
}
