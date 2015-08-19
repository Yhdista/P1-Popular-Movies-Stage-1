package com.yhdista.nanodegree.p1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Internet Utility
 */
public class UtilsNet {

    /**
     * This is MAIN METHOD for DETECTING INTERNET!!!!
     * check all internet sources
     * <p/>
     * * @return true if at least on of internet sources is passed, false if none of internet sources passed
     */
    public static boolean isConnectingToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager) U.getCTX().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}
