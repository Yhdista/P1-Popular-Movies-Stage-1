package com.yhdista.nanodegree.p1.interfaces;

import java.util.List;

/**
 * MainFragment callbacks
 */
public interface DatasetCallbacks<E> {

    void setData(List<E> elements);

    void sortBy(int which);

}
