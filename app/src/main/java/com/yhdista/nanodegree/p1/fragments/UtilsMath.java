package com.yhdista.nanodegree.p1.fragments;

/**
 * Math Utility
 */
public class UtilsMath {

    /**
     * Compaer two doubles
     * @param d1
     * @param d2
     * @return
     */
    public static int compareDouble(double d1, double d2) {
        if (d1 < d2) return -1;
        if (d1 > d2) return 1;
        return 0;
    }

}


