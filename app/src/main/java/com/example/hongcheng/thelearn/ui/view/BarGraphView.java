package com.example.hongcheng.thelearn.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.hongcheng.common.util.LoggerUtils;

import java.util.List;

/**
 * Created by hongcheng on 16/4/13.
 */
public class BarGraphView extends View {

    private int lineMargin = 8;
    private int theMax = 1000;

    private Paint grayLinePaint;
    private Paint defaultLinePaint;
    private List<Float> data;
    private Paint grayTextPaint;
    private Paint defaultTextPaint;

    public BarGraphView(Context context) {
        this(context, null);
    }

    public BarGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        grayLinePaint = getLinePaint(Color.GRAY, 2);
        defaultLinePaint = getLinePaint(Color.RED, 2);

        grayTextPaint = getTextPaint(Color.GRAY, 16);
        defaultTextPaint = getTextPaint(Color.RED, 16);
    }

    private Paint getLinePaint(int color, int size) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        return paint;
    }

    private Paint getTextPaint(int color, int size) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(color);
        paint.setTextSize(size);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        LoggerUtils.error("width", width);
        LoggerUtils.error("height", height);

        canvas.drawColor(Color.WHITE);

        if (data != null && !data.isEmpty()) {
            int size = data.size();

            float defaultCell = lineMargin + defaultLinePaint.getStrokeWidth();
            float bottomWidth = size * defaultCell;
            float totalWidth = bottomWidth > width ? bottomWidth : width;

            LoggerUtils.error("defaultCell", defaultCell);
            LoggerUtils.error("bottomWidth", bottomWidth);
            LoggerUtils.error("totalWidth", totalWidth);

            canvas.drawLine(0, height / 5, totalWidth, height / 5, grayLinePaint);
            canvas.drawLine(0, 2 * height / 5, totalWidth, 2 * height / 5, grayLinePaint);
            canvas.drawLine(0, 3 * height / 5, totalWidth, 3 * height / 5, grayLinePaint);
            canvas.drawLine(0, 4 * height / 5, totalWidth, 4 * height / 5, grayLinePaint);
            canvas.drawLine(0, height - 5, bottomWidth, height - 5, defaultLinePaint);
            canvas.drawLine(bottomWidth, height - 5, totalWidth, height - 5, grayLinePaint);

            for (int i = 1; i <= size; i++) {
                float value = data.get(i - 1) / 1000 > 1 ? 1 : data.get(i - 1) / theMax;
                LoggerUtils.error("value", value);
                canvas.drawLine(i * defaultCell, height - 5, i * defaultCell, height - 5 - value * height, defaultLinePaint);
            }
        }
    }

    public void setData(List<Float> data) {
        this.data = data;
        invalidate();
    }
}
