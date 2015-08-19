package com.yhdista.nanodegree.p1.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yhdista.nanodegree.p1.abstracts.MyBasicDialogFragment;
import com.yhdista.nanodegree.p1.application.MyApplication;
import com.yhdista.nanodegree.p1.constants.C;
import com.yhdista.nanodegree.p1.interfaces.StartTaskInterface;
import com.yhdista.nanodegree.p1.oodesign.Movie;
import com.yhdista.nanodegree.p1.interfaces.AsyncTaskCallbacks;
import com.yhdista.nanodegree.p1.interfaces.DataListCallbacks;
import com.yhdista.nanodegree.p1.utils.L;
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
 * Non-UI task fragment
 */
public class AsyncTaskFragment extends MyBasicDialogFragment implements AsyncTaskCallbacks<Movie>, StartTaskInterface<JSONObject> {

    private static final String DOWNLOAD_URL =
            "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=86c8739ce30448eae621740714048022";
    //"http://api.themoviedb.org/3/discover/movie?with_genres=18&primary_release_year=2014&api_key=86c8739ce30448eae621740714048022";

    private JsonObjectRequest mVolleyRequest;

    private WeakReference<MyAsyncTask> mTask;


    private DataListCallbacks mCallback;
    private String mUrl = DOWNLOAD_URL;

    public static AsyncTaskFragment newRetainedInstance() {

        Bundle bundleArgs = new Bundle();
        setRetain(bundleArgs, true);
        setMyCancelable(bundleArgs, false);
        setMessage(bundleArgs, "Loading data...");

        AsyncTaskFragment fragment = new AsyncTaskFragment();
        fragment.setArguments(bundleArgs);

        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallback = (DataListCallbacks) mFragmentManager.findFragmentByTag(C.TAG_FRAGMENT_MAIN_ACTIVITY);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // Volley's json object request object
        mVolleyRequest = new JsonObjectRequest(mUrl, new MyVolleyResponseListener(this),
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

        private final WeakReference<StartTaskInterface> callback;

        MyVolleyResponseListener(StartTaskInterface callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        public void onResponse(JSONObject response) {
            this.callback.get().startTask(response);
        }

    }

    private static class MyVolleyResponseErrorListener implements Response.ErrorListener {

        // TODO nema tam byt MyBasicDialogFragment, nevim jestli resit ERROR handling tu ne
        // nebo ve vyssim fragmentu

        WeakReference<MyBasicDialogFragment> callback;

        MyVolleyResponseErrorListener(AsyncTaskFragment asyncTaskFragment) {

        }

        @Override
        public void onErrorResponse(VolleyError error) {

            L.t(error.getMessage());

        }
    }


    @Override
    public void onPreExecute() {
        // start ProgressFragment at time of downloading xml file and parsing
        /*mProgressDialogFragment = ProgressDialogFragment.newInstance(RETAINED);
        mProgressDialogFragment.setCancelable(true);
        mProgressDialogFragment.show(mFragmentManager, C.TAG_FRAGMENT_PROGRESS_DIALOG);*/
    }


    @Override
    public void onPostExecute(List<Movie> movies) {
     /*   if (mProgressDialogFragment != null) {
            mProgressDialogFragment.dismissAllowingStateLoss();
        }*/

        mCallback.setData(movies);
    }


    @Override
    public void onProgressUpdate(C.ErrorTag flag) {

        switch (flag) {
            case UNKNOWN_HOST_EXCEPTION:
                L.t("Není internet");
                break;
            case XML_PARSE_EXCEPTION:
                L.t("Nastala chyba (internetové spojení nefunguje správně)");
                break;
            case GENERIC_EXCEPTION:
                L.t("Nastala chyba: kontaktujte autora:-)");
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


        final WeakReference<AsyncTaskCallbacks> mCallback;


        MyAsyncTask(AsyncTaskCallbacks callback) {
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

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }





/*
            try {

  //              while (jsonObject.) {
//                }

                // Thread.sleep(MILLIS_FOR_SLEEP);


                movie = new Movie.Builder(title, thumbnail)
                        .setOverview("")
                        .setUserRating(0.0f)
                        .setReleaseDate(null)
                        .build();
                movies.add(movie);

                if (isCancelled()) {
  //                  break;
                }


                return movies;

            } catch (UnknownHostException e) {
                publishProgress(C.ErrorTag.UNKNOWN_HOST_EXCEPTION);
                e.printStackTrace();
            } catch (SAXParseException e) {
                publishProgress(C.ErrorTag.XML_PARSE_EXCEPTION);
            } catch (Exception e) {
                e.printStackTrace();
                publishProgress(C.ErrorTag.GENERIC_EXCEPTION);
            }
*/
            return Collections.EMPTY_LIST;


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