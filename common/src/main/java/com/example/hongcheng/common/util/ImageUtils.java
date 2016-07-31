package com.example.hongcheng.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;

/**
 * Created by hongcheng on 16/4/2.
 */
public class ImageUtils {

    private ImageUtils() {
        throw new AssertionError();
    }

    /**
     * convert Bitmap to byte array
     *
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     *
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * scale image
     *
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     *
     * @param org
     * @param scaleWidth  sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    public static Bitmap scalePicture(Context context, String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 如果置为true,仅仅返回图片的分辨率,不会图片对象,并且把图片的高赋值给opts.outHeigth,把宽赋值给opts.outWidth,这样就不会抛出OOM的异常
        opts.inJustDecodeBounds = true;
        // 根据路径加载图片,不得到图片对象,只得到图片的分辨率
        BitmapFactory.decodeFile(filePath, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;

        // 2.得到设备的分辨率;
        // 得到系统提供的窗口管理器
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 得到系统默认的分辨率
        Display defaultDisplay = wm.getDefaultDisplay();
        // 得到设备的分辨率
        int screenHeight = defaultDisplay.getHeight();
        int screenWidth = defaultDisplay.getWidth();

        // 3.通过比较得到合适的比例值;
        // 屏幕的 宽320 高 480 ,图片的宽3000 ,高是2262  3000/320=9  2262/480=5,,使用大的比例值
        int scale = 1;
        int sx = srcWidth / screenWidth;
        int sy = srcHeight / screenHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }

        // 4.根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的图片
        Bitmap bm = BitmapFactory.decodeFile(filePath, opts);
        return bm;
    }

    public static Bitmap scalePicture(Context context, String filePath, int ivWidth, int ivHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 如果置为true,仅仅返回图片的分辨率,不会图片对象,并且把图片的高赋值给opts.outHeigth,把宽赋值给opts.outWidth,这样就不会抛出OOM的异常
        opts.inJustDecodeBounds = true;
        // 根据路径加载图片,不得到图片对象,只得到图片的分辨率
        BitmapFactory.decodeFile(filePath, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;

        int scale = 1;
        int sx = srcWidth / ivWidth;
        int sy = srcHeight / ivHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }

        // 4.根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的图片
        Bitmap bm = BitmapFactory.decodeFile(filePath, opts);
        return bm;
    }

    public static Bitmap scalePicture(Context context, int resId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 如果置为true,仅仅返回图片的分辨率,不会图片对象,并且把图片的高赋值给opts.outHeigth,把宽赋值给opts.outWidth,这样就不会抛出OOM的异常
        opts.inJustDecodeBounds = true;
        // 根据路径加载图片,不得到图片对象,只得到图片的分辨率
        BitmapFactory.decodeResource(context.getResources(), resId, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;

        // 2.得到设备的分辨率;
        // 得到系统提供的窗口管理器
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 得到系统默认的分辨率
        Display defaultDisplay = wm.getDefaultDisplay();
        // 得到设备的分辨率
        int screenHeight = defaultDisplay.getHeight();
        int screenWidth = defaultDisplay.getWidth();

        // 3.通过比较得到合适的比例值;
        // 屏幕的 宽320 高 480 ,图片的宽3000 ,高是2262  3000/320=9  2262/480=5,,使用大的比例值
        int scale = 1;
        int sx = srcWidth / screenWidth;
        int sy = srcHeight / screenHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }

        // 4.根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的图片
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId, opts);
        return bm;
    }

    public static Bitmap scalePicture(Context context, int resId, int ivWidth, int ivHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();

        // 如果置为true,仅仅返回图片的分辨率,不会图片对象,并且把图片的高赋值给opts.outHeigth,把宽赋值给opts.outWidth,这样就不会抛出OOM的异常
        opts.inJustDecodeBounds = true;
        // 根据路径加载图片,不得到图片对象,只得到图片的分辨率
        BitmapFactory.decodeResource(context.getResources(), resId, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;

        int scale = 1;
        int sx = srcWidth / ivWidth;
        int sy = srcHeight / ivHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }

        // 4.根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的图片
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId, opts);
        return bm;
    }

}
