/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yhdista.nanodegree.p1.utils.L;


/**
 * Custom definition for Application singleton.
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();


    // Volley Request Queue
    private RequestQueue mRequestQueue;


    // instance of current Application (Singleton)
    private static MyApplication sInstance;

     private JsonObjectRequest mVolleyRequest;


    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    private LruCache<String, Bitmap> mMemoryCache;


    @Override
    public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);

        sInstance = this;

        L.m(sInstance.toString() + " singleton MY_APPLICATION CREATED");


        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                // The cache size will be measured in kilobytes rather than
                // number of items.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    return bitmap.getByteCount() / 1024;
                } else {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }

            }

        };

    }

    /**
     * Put bitmap into cache memory.
     *
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * Get bitmap from memory cache.
     *
     * @param key
     * @return bitmap
     */
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * Delete memory cache.
     */
    public void deleteBitmamCache() {
        mMemoryCache.evictAll();
    }

    /**
     * Easy way to get Application isntance
     *
     * @return instance of MyApplication
     */
    public static MyApplication getInstance() {
        return sInstance;
    }

    /**
     * Easy way to get ApplicationContext
     *
     * @return
     */
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }


    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public void cancelVolleyRequest() {

        if (mRequestQueue != null) {
            mRequestQueue.cancelAll("JSON");
        }
    }


}
