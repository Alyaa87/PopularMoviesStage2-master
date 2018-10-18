package com.example.android.popularmoviesstage2.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieData implements Parcelable {
    private String poster_path;
    private String original_title;
    private long voteAverage;
    private String overview;
    private String releaseDate;

    // write the values we want to save to the `Parcel`.
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(voteAverage);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
        parcel.writeString(poster_path);
        parcel.writeString(original_title);
    }


    private MovieData(Parcel in) {
        voteAverage = in.readLong();
        releaseDate = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        original_title = in.readString();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public MovieData() {
    }

    //getter and setter methods.
    public long getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(long votAverage) {
        this.voteAverage = votAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_image() {
        return poster_path;
    }

    public void setPoster_image(String poster_image) {
        this.poster_path = poster_image;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}