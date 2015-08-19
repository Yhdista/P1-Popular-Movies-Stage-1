/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yhdista.nanodegree.p1.R;
import com.yhdista.nanodegree.p1.abstracts.MyBasicDialogFragment;
import com.yhdista.nanodegree.p1.abstracts.MyBasicFragment;
import com.yhdista.nanodegree.p1.activities.DetailActivity;
import com.yhdista.nanodegree.p1.adapters.MoviesGridViewAdapter;
import com.yhdista.nanodegree.p1.constants.C;
import com.yhdista.nanodegree.p1.interfaces.DatasetCallbacks;
import com.yhdista.nanodegree.p1.oodesign.Movie;
import com.yhdista.nanodegree.p1.oodesign.SortItems;
import com.yhdista.nanodegree.p1.utils.L;
import com.yhdista.nanodegree.p1.utils.UtilsNet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Main Fragment.
 */
public class MainFragment extends MyBasicFragment implements DatasetCallbacks<Movie> {

    private MyBasicDialogFragment mAsyncTaskFragment;

    private MoviesGridViewAdapter mAdapter;
    private GridView mGridView;
    private List<Movie> mMovies;

    public MainFragment() {

        Bundle bundleArgs = new Bundle();
        setRetain(bundleArgs, true);
        setArguments(bundleArgs);

    }

/*
    public static MainFragment newRetainedInstance() {
        Bundle bundleArgs = new Bundle();
        setRetain(bundleArgs, true);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundleArgs);

        return fragment;
    }

    public static MainFragment newNonRetainInstance() {
        return new MainFragment();
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) mRootView.findViewById(R.id.grid_view);
        mGridView.setNumColumns(2);

        initAdapter();

        setClickListener();

        return mRootView;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList(C.TAG_BUNDLE_MOVIES);
        }

        if (mMovies == null) {
            if (mAsyncTaskFragment == null) {
                if (UtilsNet.isConnectingToInternet()) {
                    mAsyncTaskFragment = AsyncTaskFragment.newRetainedInstance();
                    mAsyncTaskFragment.show(mActivity.getSupportFragmentManager(), C.TAG_FRAGMENT_ASYNC_TASK);
                } else {
                    L.t(mActivity.getString(R.string.error_unknown_host_exception));
                }

            }
        } else {
            changeAdapterDataset(mMovies);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMovies != null) {
            outState.putParcelableArrayList(C.TAG_BUNDLE_MOVIES, (ArrayList<? extends Parcelable>) mMovies);
        }
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new MoviesGridViewAdapter(Collections.<Movie>emptyList());
        }
        // adapter is set to GridView in any case :-)
        mGridView.setAdapter(mAdapter);
    }

    private void changeAdapterDataset(List<Movie> movies) {
        mAdapter.setDataset(movies);
        mAdapter.notifyDataSetChanged();
    }


    /**
     * Set Movies dataset
     *
     * @param movies
     */
    @Override
    public void setData(List<Movie> movies) {
        mAsyncTaskFragment.dismissAllowingStateLoss();
        mMovies = movies;
        changeAdapterDataset(mMovies);
    }

    /**
     * Sort movies
     *
     * @param which to compare
     */
    @Override
    public void sortBy(final int which) {

        Collections.sort(mMovies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {

                if (which == SortItems.TITLE.getPosition()) {
                    return lhs.getTitle().compareTo(rhs.getTitle());
                } else if (which == SortItems.POPULARITY.getPosition()) {
                    return UtilsMath.compareDouble(rhs.getPopularity(), lhs.getPopularity());
                } else if (which == SortItems.HIGHEST_RATED.getPosition()) {
                    return UtilsMath.compareDouble(rhs.getUserRating(), lhs.getUserRating());
                } else if (which == SortItems.LOWES_RATED.getPosition()) {
                    return UtilsMath.compareDouble(lhs.getUserRating(), rhs.getUserRating());
                }
                return 0;
            }
        });
        mAdapter.notifyDataSetChanged();

    }

    private void startDetailActivity(Movie movie) {
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(C.TAG_BUNDLE_MOVIE, movie);
        startActivity(intent);
    }

    private void setClickListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setItemSelected(position);
                mAdapter.notifyDataSetChanged();
                startDetailActivity(mMovies.get(position));
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSetting();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // show sorting dialog
    private void showSetting() {
        SettingDialog mSettingDialog = SettingDialog.newRetainedInstance();
        mSettingDialog.show(mFragmentManager, C.TAG_FRAGMENT_SETTING_DIALOG);
    }


}
