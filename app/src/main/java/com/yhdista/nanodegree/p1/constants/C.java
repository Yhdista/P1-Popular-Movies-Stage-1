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

    // exceptions
    public enum ErrorTag {XML_PARSE_EXCEPTION, GENERIC_EXCEPTION, UNKNOWN_HOST_EXCEPTION}


    public static final String TAG_BUNDLE_MOVIES = "movies";
    public static final String TAG_BUNDLE_MOVIE = "movie";

}
