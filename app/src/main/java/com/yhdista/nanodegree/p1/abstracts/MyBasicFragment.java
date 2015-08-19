package com.yhdista.nanodegree.p1.abstracts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yhdista.nanodegree.p1.utils.L;


/**
 * Custom template for general Fragment
 */
public abstract class MyBasicFragment extends Fragment {

    public static final boolean RETAINED = true;

    private static final String TAG_IS_RETAINED = "isRetained";

    protected FragmentManager mFragmentManager;
    protected AppCompatActivity mActivity;

    // Root View of the fragment to be inflated.
    protected View mRootView;


    // Protected method for setting retained fragment by putting value into fragment args.
    protected static void setRetain(Bundle bundleArgs, boolean isRetained) {
        if (bundleArgs != null) {
            bundleArgs.putBoolean(TAG_IS_RETAINED, isRetained);
            //setArguments(mBundleArg);
        }
    }

    /**
     * Convention Fragment constructor.
     *
     * @return new instance MyBasicFragment
     */


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = (AppCompatActivity) activity;
        mFragmentManager = mActivity.getSupportFragmentManager();

        L.lifeCycle(this.toString() + " ATTACHED");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If fragment args has TAG_IS_RETAINED value true, the fragment would become retained.
        if (getArguments() != null) {
            setRetainInstance(getArguments().getBoolean(TAG_IS_RETAINED, false));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        mActivity = null;
        mFragmentManager = null;

        L.lifeCycle(this.toString() + " DETACHED");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        L.lifeCycle(this.toString() + " DESTROYED");

    }

    @Override
    public void onDestroyView() {

        // all reference to Views must be destroyed
        mRootView = null;

        super.onDestroyView();

    }
}




