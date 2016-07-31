package com.example.hongcheng.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hongcheng on 16/4/3.
 */
public class ToastUtils {
    private ToastUtils(){

    }

    public static void show(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_SHORT);
    }
}
