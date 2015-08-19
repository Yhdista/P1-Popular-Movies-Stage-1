package com.yhdista.nanodegree.p1.views;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Custom Dialog with modified onBackPressed method.
 */
public class MyDialog extends AlertDialog {

    final WeakReference<AppCompatActivity> mActivity;

    public MyDialog(Context context) {
        super(context);
        mActivity = new WeakReference<>((AppCompatActivity) context);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
        mActivity = new WeakReference<>((AppCompatActivity) context);
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mActivity = new WeakReference<>((AppCompatActivity) context);
    }

    /**
     * Difference with Dialog consist in enabling standard onBackPress behavior,
     * it means after pressed back button, the application is terminated (Activity is finished).
     */
    @Override
    public void onBackPressed() {
        mActivity.get().onBackPressed();
        super.onBackPressed();
    }


}
