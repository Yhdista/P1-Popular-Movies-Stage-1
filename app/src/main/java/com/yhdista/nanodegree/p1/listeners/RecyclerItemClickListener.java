/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Yhdista on 18.8.2015.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onLongItemClick(View view, int position);

        void onDown(View view, int position);

        void onUp(View view, int position);


    }


    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {

        mListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                // I am interested in
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {


            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // I am interested in
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        });

        // There is no problem with long press which is not important in this case
        mGestureDetector.setIsLongpressEnabled(false);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {


            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mListener.onDown(childView, view.getChildAdapterPosition(childView));
                    break;
                case MotionEvent.ACTION_UP:
                    mListener.onUp(childView, view.getChildAdapterPosition(childView));
                    break;
                default:
                    mListener.onUp(childView, view.getChildAdapterPosition(childView));
                    //mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                    break;
            }


            return true;

        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}