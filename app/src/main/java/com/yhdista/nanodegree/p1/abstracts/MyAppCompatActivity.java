/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.abstracts;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


/**
 * Custom template for general compat Activity
 */
public abstract class MyAppCompatActivity extends AppCompatActivity {

    protected FragmentManager mFragmentManager;

    protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Strict Mode
        //setStrictMode();

        super.onCreate(savedInstanceState);

    }

    // turn on full StrictMode
    private static void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDialog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
                .penaltyLog()
                .build());
    }


    @Override
    protected void onDestroy() {

        // ensure there is no memory leak deals with StrictMode:InstanceCountViolation
        // TODO unbindDrawables(findViewById(R.id.frame_container));
        System.gc();
        super.onDestroy();


    }

    // ensure there is no memory leak deals with StrictMode:InstanceCountViolation
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }


}
