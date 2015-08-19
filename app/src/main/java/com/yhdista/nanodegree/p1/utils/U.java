package com.yhdista.nanodegree.p1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yhdista.nanodegree.p1.application.MyApplication;


/**
 * Utils class
 */
public class U {


    /**
     * This is MAIN METHOD for DETECTING INTERNET!!!!
     * check all internet sources
     *
     * @param context is usually Activity
     * @return true if at least on of internet sources is passed, false if none of internet sources passed
     */
    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

/*
    public static void toggleToolbarProgressBar(int visible) {
        if (U.getActivity() != null) {
            if (!U.getActivity().isFinishing()) {
                //ProgressBar tpb = (ProgressBar) U.getActivity().findViewById(R.id.toolbar_progress_bar);
                tpb.setVisibility((visible == View.VISIBLE) ? View.VISIBLE : View.GONE);
            }
        }
    }
*/
    /**
     * Special case for toggling ProgressBar in onPreExecute method when
     * ProgressBar is already visible from the previous task
     */ /*
    public static void toggleToolbarProgressBarOnlyIfWasGone() {
        if (U.getActivity() != null) {
            if (!U.getActivity().isFinishing()) {
                ProgressBar tpb = (ProgressBar) U.getActivity().findViewById(R.id.toolbar_progress_bar);
                if (tpb.getVisibility() == View.GONE) {
                    tpb.setVisibility((tpb.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                }
            }
        }
     }          */


    // dalagete of MyApplication instance (Application Cntext)
    public static Context getCTX() {
        return MyApplication.getContext();
    }

    /*
    // to prevent Memory Leak, delegate of getter MainActivity instance J-I-T
    public static AppCompatActivity getActivity() {
        return MainActivity.getActivity();
    }
    */


}
