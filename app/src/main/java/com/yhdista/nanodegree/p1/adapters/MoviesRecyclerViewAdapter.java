/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yhdista.nanodegree.p1.R;
import com.yhdista.nanodegree.p1.interfaces.OnItemClickListener;
import com.yhdista.nanodegree.p1.oodesign.Movie;
import com.yhdista.nanodegree.p1.utils.U;

import java.util.List;

/**
 * Created by Yhdista on 18.8.2015.
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>  {

    private List<Movie> mDataset;
    private OnItemClickListener mListener;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements Target {


        // each data item is just a string in this case
        private final ImageView mImageView;
        private final FrameLayout mPicassoView;
        private final ProgressBar mProgressBar;
        private final View mHighLightView;

        public ViewHolder(View v) {
            super(v);
            mPicassoView = (FrameLayout) v;
            mImageView = (ImageView) v.findViewById(R.id.image_view);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
            mHighLightView = v.findViewById(R.id.highlight_view);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mImageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            setViewInvisible(mProgressBar);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mImageView.setBackgroundDrawable(errorDrawable);
            setViewInvisible(mProgressBar);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            mImageView.setBackgroundDrawable(placeHolderDrawable);
            setViewVisible(mProgressBar);
        }



        private void setViewVisible(View view) {
            view.setVisibility(View.VISIBLE);
        }


        private void setViewInvisible(View view) {
            view.setVisibility(View.INVISIBLE);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MoviesRecyclerViewAdapter(List<Movie> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MoviesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picasso_frame_layout, parent, false);

        // for default highlight view is invisible
        v.findViewById(R.id.highlight_view).setVisibility(View.INVISIBLE);

        //ImageView iv = (ImageView) v.findViewById(R.id.image_view);
        //ProgressBar pb = (ProgressBar) v.findViewById(R.id.progress_bar);

        int width;
        int height;

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = ((WindowManager) U.getCTX().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); // getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            WindowManager wm = (WindowManager) U.getCTX().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            width = display.getWidth();
            height = display.getHeight();
        }


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width / 2, (int) (1.5 * width / 2));

        v.setLayoutParams(params);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

/*
        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = holder.mImageView.getWidth();

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (1.5 * targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
*/

        Picasso.with(U.getCTX())
                .load("http://image.tmdb.org/t/p/w185/" + mDataset.get(position)
                        .getPosterPath())
                        //            .fit()
                        //          .placeholder(R.drawable.__leak_canary_notification)
                        //        .error(R.drawable.__leak_canary_toast_background)
                        //       .transform(transformation)
                .into(holder);

        //holder.mImageView.setText(mDataset[position]);

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}