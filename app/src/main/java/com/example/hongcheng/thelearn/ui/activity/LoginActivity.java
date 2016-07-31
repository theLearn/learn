package com.example.hongcheng.thelearn.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.hongcheng.common.util.StringUtils;
import com.example.hongcheng.thelearn.R;
import com.example.hongcheng.thelearn.presenter.impl.LoginPresenterImpl;
import com.example.hongcheng.thelearn.ui.uiInterface.LoginUI;
import com.example.hongcheng.thelearn.ui.view.BaseDialog;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginUI {

    private EditText mEmailView;
    private EditText mPasswordView;
    private Dialog mProgressView;
    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, true);

        mEmailView = (EditText) findViewById(R.id.et_email);
        mPasswordView = (EditText) findViewById(R.id.et_password);

        Button btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();
    }

    private void login() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (StringUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }else if(!StringUtils.checkPassword(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (StringUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!StringUtils.isEmail(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            loginPresenter.login(email, password);
        }
    }

    @Override
    public void toWelcome() {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    @Override
    public void showLoading(boolean show) {
        if (mProgressView == null) {
            mProgressView = BaseDialog.createLoading(this);
        }

        if (show) {
            mProgressView.show();
        } else {
            mProgressView.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }
}

