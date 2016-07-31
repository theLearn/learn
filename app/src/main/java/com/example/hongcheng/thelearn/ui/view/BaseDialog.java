package com.example.hongcheng.thelearn.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hongcheng.common.util.StringUtils;
import com.example.hongcheng.thelearn.R;

/**
 * Created by hongcheng on 16/4/2.
 */
public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        this(context, R.style.Dialog);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static BaseDialog createLoading(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        BaseDialog dialog = new BaseDialog(context);

        View layout = inflater.inflate(R.layout.loading_dialog, null);
        dialog.addContentView(layout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        return dialog;
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;

        private String left;
        private String center;
        private String right;

        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener centerButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int resId) {
            this.title = context.getResources().getString(resId);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int resId) {
            this.message = context.getResources().getString(resId);
            return this;
        }

        public Builder setPositiveButton(int resId,
                                         DialogInterface.OnClickListener listener) {
            this.right = context.getResources().getString(resId);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String rightText,
                                         DialogInterface.OnClickListener listener) {
            this.right = rightText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int resId,
                                         DialogInterface.OnClickListener listener) {
            this.left = context.getResources().getString(resId);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String leftText,
                                         DialogInterface.OnClickListener listener) {
            this.left = leftText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setCenterButton(int resId,
                                       DialogInterface.OnClickListener listener) {
            this.center = context.getResources().getString(resId);
            this.centerButtonClickListener = listener;
            return this;
        }

        public Builder setCenterButton(String centerText,
                                       DialogInterface.OnClickListener listener) {
            this.center = centerText;
            this.centerButtonClickListener = listener;
            return this;
        }

        public BaseDialog create() {
            LayoutInflater inflater = LayoutInflater.from(context);
            final BaseDialog dialog = new BaseDialog(context);

            View layout = inflater.inflate(R.layout.base_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView dialogTitle = (TextView) layout.findViewById(R.id.tv_dialog_title);
            TextView dialogMessage = (TextView) layout.findViewById(R.id.tv_dialog_message);
            TextView dialogLeft = (TextView) layout.findViewById(R.id.tv_dialog_left);
            TextView dialogCenter = (TextView) layout.findViewById(R.id.tv_dialog_center);
            TextView dialogRight = (TextView) layout.findViewById(R.id.tv_dialog_right);

            View lineFirst = layout.findViewById(R.id.line_dialog_first);
            View secondFirst = layout.findViewById(R.id.line_dialog_second);

            if (title != null) {
                if (title.isEmpty()) {
                    dialogTitle.setVisibility(View.INVISIBLE);
                } else {
                    dialogTitle.setText(title);
                }
            }

            if (StringUtils.isEmpty(message)) {
                dialogMessage.setVisibility(View.INVISIBLE);
            } else {
                dialogMessage.setText(message);
            }

            if (StringUtils.isEmpty(left)) {
                secondFirst.setVisibility(View.GONE);
                dialogLeft.setVisibility(View.GONE);
            } else {
                dialogLeft.setText(left);
                dialogLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (negativeButtonClickListener == null) {
                            dialog.dismiss();
                        } else {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
                        }
                    }
                });
            }

            if (StringUtils.isEmpty(center)) {
                secondFirst.setVisibility(View.GONE);
                dialogCenter.setVisibility(View.GONE);
            } else {
                dialogCenter.setText(center);
                dialogCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (centerButtonClickListener == null) {
                            dialog.dismiss();
                        } else {
                            centerButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    }
                });
            }

            if (StringUtils.isEmpty(right)) {
                lineFirst.setVisibility(View.GONE);
                dialogRight.setVisibility(View.GONE);
            } else {
                dialogRight.setText(right);
                dialogRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (positiveButtonClickListener == null) {
                            dialog.dismiss();
                        } else {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    }
                });
            }
            dialog.setContentView(layout);
            dialog.setCancelable(false);
            return dialog;
        }
    }
}
