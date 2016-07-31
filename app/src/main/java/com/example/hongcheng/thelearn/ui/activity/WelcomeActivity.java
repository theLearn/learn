package com.example.hongcheng.thelearn.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.hongcheng.thelearn.R;
import com.example.hongcheng.thelearn.presenter.impl.WelcomePresenterImpl;
import com.example.hongcheng.thelearn.service.UpdateService;
import com.example.hongcheng.thelearn.ui.uiInterface.WelcomeUI;
import com.example.hongcheng.thelearn.ui.view.BaseDialog;

public class WelcomeActivity extends BaseActivity implements WelcomeUI {

    private WelcomePresenterImpl welcomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome, true);

        welcomePresenter = new WelcomePresenterImpl(this);
        welcomePresenter.onCreate();
    }

    @Override
    public void toGuide() {

    }

    @Override
    public void showUpdateDialog(String tip, final String url) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        builder.setMessage(tip);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                toMain();
            }
        });
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startDownNewVersion(url);
            }
        });
        builder.create().show();
    }

    private void startDownNewVersion(String url) {
        Intent intent = new Intent(this.getApplicationContext(), UpdateService.class);
        intent.putExtra("fileUrl", url);
        startService(intent);
        toMain();
    }

    @Override
    public void toLogin() {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void toMain() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        welcomePresenter.onDestroy();
    }
}
