/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yhdista.nanodegree.p1.R;
import com.yhdista.nanodegree.p1.abstracts.MyBasicDialogFragment;
import com.yhdista.nanodegree.p1.application.MyApplication;
import com.yhdista.nanodegree.p1.constants.C;
import com.yhdista.nanodegree.p1.interfaces.AsyncTaskCallbacks;
import com.yhdista.nanodegree.p1.interfaces.DatasetCallbacks;
import com.yhdista.nanodegree.p1.interfaces.StartTaskInterface;
import com.yhdista.nanodegree.p1.oodesign.Movie;
import com.yhdista.nanodegree.p1.utils.L;
import com.yhdista.nanodegree.p1.utils.U;
import com.yhdista.nanodegree.p1.utils.UtilsDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Non-UI task fragment for Volley and AsyncTask processes
 */
public class AsyncTaskFragment extends MyBasicDialogFragment implements AsyncTaskCallbacks<Movie>, StartTaskInterface<JSONObject> {

    private static final String DOWNLOAD_URL =
            "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=86c8739ce30448eae621740714048022";
    //"http://api.themoviedb.org/3/discover/movie?with_genres=18&primary_release_year=2014&api_key=86c8739ce30448eae621740714048022";

    private WeakReference<MyAsyncTask> mTask;


    private DatasetCallbacks<Movie> mCallback;

    public static AsyncTaskFragment newRetainedInstance() {

        Bundle bundleArgs = new Bundle();
        setRetain(bundleArgs, true);
        setMyCancelable(bundleArgs, true);
        setMessage(bundleArgs, "Loading data...");

        AsyncTaskFragment fragment = new AsyncTaskFragment();
        fragment.setArguments(bundleArgs);

        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO is there any way to solve this warning withou supressing it?
        //noinspection unchecked
        mCallback = (DatasetCallbacks<Movie>) mFragmentManager.findFragmentByTag(C.TAG_FRAGMENT_MAIN);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // Volley's json object request object
        JsonObjectRequest mVolleyRequest = new JsonObjectRequest(DOWNLOAD_URL, new MyVolleyResponseListener(this),
                new MyVolleyResponseErrorListener(this));

        // adding request to request queue
        MyApplication.getInstance().addToRequestQueue(mVolleyRequest, "JSON");
        // JOHN's NOTE: this was the reason why it was leaking, because I wasn't able to cancel
        // request, so the memory leaks because of MainActivity instance as usual

    }

    @Override
    public void startTask(JSONObject jsonObject) {

        if (mTask == null) {
            mTask = new WeakReference<>(new MyAsyncTask(this));
            mTask.get().execute(jsonObject);
        }

    }


    private static class MyVolleyResponseListener implements Response.Listener<JSONObject> {

        private final WeakReference<StartTaskInterface<JSONObject>> callback;

        MyVolleyResponseListener(StartTaskInterface<JSONObject> callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        public void onResponse(JSONObject response) {
            this.callback.get().startTask(response);
        }

    }

    private static class MyVolleyResponseErrorListener implements Response.ErrorListener {

        // TODO probably more to make it clear

        WeakReference<MyBasicDialogFragment> callback;

        MyVolleyResponseErrorListener(AsyncTaskFragment asyncTaskFragment) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            L.t(U.getCTX().getString(R.string.error_unknown_host_exception));
            L.tdebug(error.getMessage());
        }
    }


    @Override
    public void onPreExecute() {
    }


    @Override
    public void onPostExecute(List<Movie> movies) {
        mCallback.setData(movies);
    }


    @Override
    public void onProgressUpdate(C.ErrorTag flag) {

        switch (flag) {
            case UNKNOWN_HOST_EXCEPTION:
                L.t(mActivity.getString(R.string.error_unknown_host_exception));
                break;
            case XML_PARSE_EXCEPTION:
                L.t(mActivity.getString(R.string.error_xml_parse_exception));
                break;
            case GENERIC_EXCEPTION:
                L.t(mActivity.getString(R.string.error_generic_exception));
                break;
        }


    }

    @Override
    public void onCancelled() {
    }

    /**
     * Custom AsyncTask for downloading xml file and parsing it.
     */
    private static class MyAsyncTask extends AsyncTask<JSONObject, C.ErrorTag, List<Movie>> {

        // sleeping millis for debuging purposes
        private static final long MILLIS_FOR_SLEEP = 0;

        final WeakReference<AsyncTaskCallbacks<Movie>> mCallback;

        MyAsyncTask(AsyncTaskCallbacks<Movie> callback) {
            mCallback = new WeakReference<>(callback);
        }


        @Override
        protected void onPreExecute() {
            if (mCallback.get() != null) {
                mCallback.get().onPreExecute();
            }

        }

        @Override
        protected List<Movie> doInBackground(JSONObject... params) {

            try {
                // just a trick to be sure to see progressbar for a moment
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Movie movie;
            List<Movie> movies = new ArrayList<>();

            JSONObject jsonObject = params[0];
            JSONArray jsonArray;
            try {
                jsonArray = jsonObject.getJSONArray("results");
                final int arrayLength = jsonArray.length();
                for (int i = 0; i < arrayLength; i++) {

                    JSONObject item = (JSONObject) jsonArray.get(i);

                    movie = new Movie.Builder(item.getString(Movie.TAG_TITLE),
                            item.getString(Movie.TAG_POSTER_PATH))
                            .setOverview(item.getString(Movie.TAG_OVERVIEW))
                            .setUserRating(item.getDouble(Movie.TAG_VOTE_AVERAGE))
                            .setReleaseDate(UtilsDate.parseDate_YYYY_MM_DD(item.getString(Movie.TAG_RELEASE_DATE)))
                            .build();
                    movies.add(movie);

                }

                return movies;

            } catch (ParseException | JSONException e) {
                publishProgress(C.ErrorTag.XML_PARSE_EXCEPTION);
                e.printStackTrace();
            }

            return Collections.emptyList();

        }

        @Override
        protected void onProgressUpdate(C.ErrorTag... values) {
            if (mCallback.get() != null) {
                mCallback.get().onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (mCallback.get() != null) {
                mCallback.get().onPostExecute(movies);
            }

        }

        @Override
        protected void onCancelled() {
            if (mCallback.get() != null) {
                mCallback.get().onCancelled();
            }
        }
    }

}
