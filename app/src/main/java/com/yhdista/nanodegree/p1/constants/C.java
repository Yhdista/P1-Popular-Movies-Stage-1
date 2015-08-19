/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.constants;

import com.yhdista.nanodegree.p1.BuildConfig;

/**
 * Class of constants.
 */
public class C {

    // if DEBUG
    public static final boolean DEBUG = BuildConfig.DEBUG;

    // fragments
    public static final String TAG_FRAGMENT_PROGRESS_DIALOG = "activityProgressDialog";
    public static final String TAG_FRAGMENT_ASYNC_TASK = "fragmentAsyncTask";
    public static final String TAG_FRAGMENT_MAIN_ACTIVITY = "fragmentMainActivity";
    public static final String TAG_FRAGMENT_SETTING_DIALOG = "fragmentSettingDialog" ;

    // exceptions
    public enum ErrorTag {XML_PARSE_EXCEPTION, GENERIC_EXCEPTION, UNKNOWN_HOST_EXCEPTION}

    // tags for bundle arguments
    public static final String TAG_BUNDLE_MOVIES = "movies";
    public static final String TAG_BUNDLE_MOVIE = "movie";

    // base URL for themoviedb.org API
    public static final String MOVIEDB_AUTHORITY_URL = "http://image.tmdb.org/t/p/";

    // accessible size of images
    public static final String MOVIEDB_W185 = "w185";
    public static final String MOVIEDB_W342 = "w342";

}
