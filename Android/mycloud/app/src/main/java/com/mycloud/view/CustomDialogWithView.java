package com.mycloud.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


/**
 * @author clarechen 2014-10-28 自定义对话框，传入view即可，不固定样式
 */
public class CustomDialogWithView extends Dialog {
    private View view;
    private boolean isCancelable;
    private boolean isCanceledOnTouchOutside;
    private int mWidth;
    private float mDensity;

    public CustomDialogWithView(Context context, View view, boolean isCancelable,
                                boolean isCanceledOnTouchOutside) {
        super(context, android.R.style.Theme_Light_NoTitleBar);
        this.view = view;
        this.isCancelable = isCancelable;
        this.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
        mWidth = context.getResources().getDisplayMetrics().widthPixels;
        mDensity = context.getResources().getDisplayMetrics().density;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (mWidth - 48 * mDensity);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        setCancelable(isCancelable);
        setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        setContentView(view);
    }

}
