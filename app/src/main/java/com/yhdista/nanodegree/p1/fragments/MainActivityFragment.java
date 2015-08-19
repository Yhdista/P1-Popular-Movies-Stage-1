package com.yhdista.nanodegree.p1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
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
import com.yhdista.nanodegree.p1.interfaces.DataListCallbacks;
import com.yhdista.nanodegree.p1.oodesign.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends MyBasicFragment implements DataListCallbacks<Movie> {


    private MyBasicDialogFragment mAsyncTaskFragment;
    private MoviesGridViewAdapter mAdapter;
    private GridView mGridView;
    private List<Movie> mMovies;


    public MainActivityFragment() {

        Bundle bundleArgs = new Bundle();
        setRetain(bundleArgs, true);
        setArguments(bundleArgs);

    }

    /*
    public static MainActivityFragment newRetainedInstance() {

        Bundle bundleArgs = new Bundle();
        setRetain(bundleArgs, true);

        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(bundleArgs);

        return fragment;

    }


    public static MainActivityFragment newNonRetainInstance() {

        return new MainActivityFragment();

    }
*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mRootView = inflater.inflate(R.layout.fragment_main, container, false);


        mGridView = (GridView) mRootView.findViewById(R.id.grid_view);
        mGridView.setNumColumns(2);


        // specify an adapter (see also next example)
        if (mAdapter == null) {
            mAdapter = new MoviesGridViewAdapter(Collections.EMPTY_LIST);
        }

        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setItemSelected(position);
                mAdapter.notifyDataSetChanged();
                startDetailActivity(mMovies.get(position));
            }
        });


        return mRootView;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList(C.TAG_BUNDLE_MOVIES);
        }

        if (mMovies == null) {
            mAsyncTaskFragment = AsyncTaskFragment.newRetainedInstance();
            mAsyncTaskFragment.show(mActivity.getSupportFragmentManager(), C.TAG_FRAGMENT_ASYNC_TASK);
        } else {
            refreshAdapter();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMovies != null) {
            outState.putParcelableArrayList(C.TAG_BUNDLE_MOVIES, (ArrayList<? extends Parcelable>) mMovies);
        }
    }

    @Override
    public void setData(List<Movie> movies) {
        mAsyncTaskFragment.dismissAllowingStateLoss();
        mMovies = movies;
        refreshAdapter();
    }

    private void refreshAdapter() {
        mAdapter = new MoviesGridViewAdapter(mMovies);
        mGridView.setAdapter(mAdapter);
    }

    private void startDetailActivity(Movie movie) {
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(C.TAG_BUNDLE_MOVIE, movie);
        startActivity(intent);
    }

}
