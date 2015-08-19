/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.abstracts;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yhdista.nanodegree.p1.utils.L;


/**
 * Custom template for DialogFragment
 */
public abstract class MyBasicDialogFragment extends DialogFragment {

    public static final boolean RETAINED = true;

    protected static final String TAG_IS_RETAINED = "isRetained";
    protected static final String TAG_IS_CANCELLABLE = "isRetained";
    protected static final String TAG_DIALOG_TITLE = "dialogTitle";
    protected static final String TAG_DIALOG_MESSAGE = "dialogMessage";

    protected FragmentManager mFragmentManager;
    protected AppCompatActivity mActivity;

    // Root View of the fragment to be inflated.
    protected View mRootView;


    // Protected method for setting retained fragment by putting value into fragment args.
    protected static void setRetain(Bundle bundleArgs, boolean isRetained) {
        if (bundleArgs != null) {
            bundleArgs.putBoolean(TAG_IS_RETAINED, isRetained);
        }
    }

    // Protected method for setting title String for Dialog into fragment args.
    protected static void setTitle(Bundle bundleArgs, String message) {
        if (bundleArgs != null) {
            bundleArgs.putString(TAG_DIALOG_TITLE, message);
        }
    }


    // Protected method for setting message String for Dialog into fragment args.
    protected static void setMessage(Bundle bundleArgs, String message) {
        if (bundleArgs != null) {
            bundleArgs.putString(TAG_DIALOG_MESSAGE, message);
        }
    }

    // Protected method for setting cancellation of Dialog into fragment args.
    protected static void setMyCancelable(Bundle bundleArgs, boolean isCancellable) {
        if (bundleArgs != null) {
            bundleArgs.putBoolean(TAG_IS_CANCELLABLE, isCancellable);
        }
    }

    /*
     * Fragment factory method in concrete object!
     *
     * @return new instance MyBasicFragment
     */


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = (AppCompatActivity) activity;
        mFragmentManager = mActivity.getSupportFragmentManager();

        L.lifeCycle(this.toString() + " ATTACHED");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If fragment args has TAG_IS_RETAINED value true, the fragment would become retained.
        setRetainInstance(getArguments().getBoolean(TAG_IS_RETAINED, false));
        setCancelable(getArguments().getBoolean(TAG_IS_CANCELLABLE, true));

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity(), getTheme());
        dialog.setTitle(getArguments().getString(TAG_DIALOG_TITLE));
        dialog.setMessage(getArguments().getString(TAG_DIALOG_MESSAGE));
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return dialog;
    }


    @Override
    public void onDetach() {
        super.onDetach();

        mActivity = null;
        mFragmentManager = null;

        L.lifeCycle(this.toString() + " DETACHED");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        L.lifeCycle(this.toString() + " DESTROYED");

    }

    @Override
    public void onDestroyView() {

        // You may have to add this code to stop your dialog from being dismissed on rotation, due to a bug with the compatibility library:
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }

        // all reference to Views must be destroyed
        mRootView = null;

        super.onDestroyView();

    }
}




