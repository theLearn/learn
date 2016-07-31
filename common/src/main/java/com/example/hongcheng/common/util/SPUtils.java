package com.example.hongcheng.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.List;

public class SPUtils {

    private SPUtils() {

    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static String getStringFromSP(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, "");
    }

    public static String getStringFromSP(Context context, String key, String def) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, def);
    }

    public static int getIntFromSP(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(key, 0);
    }

    public static int getIntFromSP(Context context, String key, int def) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(key, def);
    }

    public static boolean getBooleanFromSP(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

    public static boolean getBooleanFromSP(Context context, String key, boolean def) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, def);
    }

    public static Float getFloatFromSP(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getFloat(key, 0.0f);
    }

    public static Float getFloatFromSP(Context context, String key, float def) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getFloat(key, def);
    }

    public static Long getLongFromSP(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getLong(key, 0L);
    }

    public static Long getLongFromSP(Context context, String key, long def) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getLong(key, def);
    }

    public static void putValueToSP(Context context, String key, Object obj) {
        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();
        if (obj instanceof String) {
            editor.putString(key, (String) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        }
        editor.commit();
    }

    public static void writeSp(Context context, String key,
                               List<Integer> ints) {
        SharedPreferences sp = getSharedPreferences(context);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ints.size(); i++) {
            sb.append(ints.get(i) + ":");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sp.edit().putString(key, sb.toString()).commit();
    }

    public static List<Integer> readSp(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        List<Integer> result = new ArrayList<Integer>();
        String str = sp.getString(key, "");
        if (str.length() > 0) {
            String[] split = str.split(":");

            for (String string : split) {
                result.add(Integer.valueOf(string));
            }
        }
        return result;
    }
}
