package com.example.hongcheng.thelearn.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.hongcheng.thelearn.R;
import com.example.hongcheng.thelearn.presenter.impl.MainPresenterImpl;
import com.example.hongcheng.thelearn.ui.uiInterface.MainUI;
import com.example.hongcheng.thelearn.ui.view.BarGraphView;
import com.example.hongcheng.thelearn.ui.view.BaseDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainUI {

    private MainPresenterImpl mainPresenterImpl;
    private DrawerLayout dlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenterImpl = new MainPresenterImpl(this);
        mainPresenterImpl.onCreate();
        initView();
    }

    private void initView() {
        Button logout = (Button) findViewById(R.id.bt_logout);
        logout.setOnClickListener(this);

        setIvBackResource(R.mipmap.menu);
        dlMain = (DrawerLayout) findViewById(R.id.dl_main);

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_main);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_brand:
                        break;
                    case R.id.rb_search:
                        break;
                    case R.id.rb_home:
                        break;
                    case R.id.rb_shopping:
                        break;
                    case R.id.rb_more:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void goBack() {

//        dlMain.openDrawer(GravityCompat.START);
        List<Float> list = new ArrayList<Float>();
        for(int i = 0 ; i < 200 ; i++)
        {
            list.add((float)(1+Math.random()*1000));
        }

        BarGraphView bg = (BarGraphView) findViewById(R.id.bg_view);

        bg.setData(list);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_logout:
                showLogoutDialog();
                break;
            default:
                break;
        }
    }

    private void showLogoutDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        builder.setMessage(R.string.logout_tip);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.create().show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenterImpl.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }
}
