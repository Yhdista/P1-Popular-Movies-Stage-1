package com.yhdista.nanodegree.p1.utils;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Activity Utility
 */
public class UtilsActivity {


    /**
     * Set Toolbar as support ActionBar
     *
     * @param activity AppCompatActivity
     * @param toolbar compat Toolbar
     * @param resId layout of Toolbar
     */
    public static void setToolbarAsSupportActionBar(AppCompatActivity activity, Toolbar toolbar, int resId) {
        toolbar = (Toolbar) activity.findViewById(resId);
        activity.setSupportActionBar(toolbar);
    }


    /**
     * Set the home button in ActionBar for returning to parent Activity
     *
     * @param activity
     */
    public static void setSupportActionBarHomeButton(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


}
