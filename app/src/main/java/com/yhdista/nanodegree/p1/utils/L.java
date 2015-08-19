package com.yhdista.nanodegree.p1.utils;

import android.util.Log;
import android.widget.Toast;

import com.yhdista.nanodegree.p1.constants.C;



/**
 * Loging helper class
 */
public class L {


    /**
     * Create debug log into LogCat if debugging mod (Lifecycles)
     *
     * @param message
     */
    public static void lifeCycle(String message)
    {
        if (C.DEBUG) Log.d("LIFE", message);
    }

    /**
     * Create debug log into LogCat if debugging mod.
     *
     * @param message
     */
    public static void m(String message)
    {
        if (C.DEBUG) Log.d("HELP", message);
    }

    /**
     * Create Toast message
     *
     * @param message
     */
    public static void t(String message)
    {
        Toast.makeText(U.getCTX(), message, Toast.LENGTH_LONG).show();
    }

}