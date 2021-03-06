/*
 * Copyright (C) 2013 The Android Open Source Project
 */

package com.yhdista.nanodegree.p1.oodesign;

import android.os.Parcel;
import android.os.Parcelable;

import com.yhdista.nanodegree.p1.utils.UtilsDate;

import java.util.Date;

/**
 * Design of Movie element
 * Object is Parcelable because of Intent transaction
 */
public class Movie implements Parcelable {

    public static final String RELEASE_DATE_FORMAT = "yyyy-mm-dd";

    public static final String TAG_ADULT = "adult";
    public static final String TAG_TITLE = "title";
    public static final String TAG_OVERVIEW = "overview";
    public static final String TAG_RELEASE_DATE = "release_date";
    public static final String TAG_POSTER_PATH = "poster_path";
    public static final String TAG_VOTE_AVERAGE = "vote_average";
    public static final String TAG_POPULARITY = "popularity";

    // TODO preparation for next project
    private boolean mAdult;             // adult	:	false
    private String mBackdropPath;       // backdrop_path	:	/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg
    // genre_ids		[3]
    // 0	:	28
    // 1	:	12
    // 2	:	878
    private long mId;                   // id	:	102899
    private String mOriginalLanguage;   // original_language	:	en
    private String mOriginalTitle;      // original_title	:	Ant-Man
    private String mOverview;           // overview	:	Armed with the astonishing ability to shrink in scale but increase in strength, con-man Scott Lang must embrace his inner-hero and help his mentor, Dr. Hank Pym, protect the secret behind his spectacular Ant-Man suit from a new generation of towering threats. Against seemingly insurmountable obstacles, Pym and Lang must plan and pull off a heist that will save the world.
    private Date mReleaseDate;          // release_date	:	2015-07-17
    private String mPosterPath;         // poster_path	:	/7SGGUiTE6oc2fh9MjIk5M00dsQd.jpg
    private double mPopularity;         // popularity	:	52.680889
    private String mTitle;              // title	:	Ant-Man
    boolean mVideo;             // video	:	false
    private double mVoteAverage;        // vote_average	:	7.1
    int mVoteCount;         // vote_count	:	826

    // Constructor is hidden
    private Movie() {
    }

    private Movie(Builder builder) {

        mTitle = builder.mTitle;
        mPosterPath = builder.mPosterPath;
        mOverview = builder.mOverview;
        mVoteAverage = builder.mUserRating;
        mReleaseDate = builder.mReleaseDate;
        mPopularity = builder.mPopularity;

    }

    private Movie(Parcel in) {
        mBackdropPath = in.readString();
        mId = in.readLong();
        mOriginalLanguage = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mPosterPath = in.readString();
        mTitle = in.readString();
        mVoteAverage = in.readDouble();
        mReleaseDate = UtilsDate.getDateFromMillis(in.readLong());
        mPopularity = in.readDouble();
        // TODO dont forget to write down all variables here later!
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBackdropPath);
        dest.writeLong(mId);
        dest.writeString(mOriginalLanguage);
        dest.writeString(mOriginalTitle);
        dest.writeString(mOverview);
        dest.writeString(mPosterPath);
        dest.writeString(mTitle);
        dest.writeDouble(mVoteAverage);
        dest.writeLong(getReleaseDateInMillis());
        dest.writeDouble(mPopularity);
        // TODO dont forget to write down all variables here later!
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getUserRating() {
        return mVoteAverage;
    }

    private long getReleaseDateInMillis() {
        return UtilsDate.getTimeInMillis(mReleaseDate);
    }

    public int getReleaseYear() {
        return UtilsDate.getTimeAsYear(mReleaseDate);
    }

    public double getPopularity() {
        return mPopularity;
    }

    /**
     * Movie Builder pattern
     */
    public static class Builder {


        private final String mTitle;
        private final String mPosterPath;
        private long mId;
        private String mOverview;
        private double mUserRating;
        private Date mReleaseDate;
        private double mPopularity;

        /**
         * Builder pattern for creating Movie element
         *
         * @param title
         * @param path
         */
        public Builder(String title, String path) {
            mTitle = title;
            mPosterPath = path;
        }

        public Builder setId(long id) {
            mId = id;
            return this;
        }

        public Builder setOverview(String overview) {
            mOverview = overview;
            return this;
        }

        public Builder setUserRating(double rating) {
            mUserRating = rating;
            return this;
        }

        public Builder setReleaseDate(Date date) {
            mReleaseDate = date;
            return this;
        }

        public Builder setPopularity(double popularity) {
            mPopularity = popularity;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }

    }

}
