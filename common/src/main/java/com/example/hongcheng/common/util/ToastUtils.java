package com.example.hongcheng.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hongcheng on 16/4/3.
 */
public class ToastUtils {
    private static Toast toast;

    private ToastUtils(){

    }

    public static void show(Context context, String text){
        if(toast == null){
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void show(Context context, int resId){
        if(toast == null){
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }else {
            toast.setText(resId);
        }
        toast.show();
    }
}
