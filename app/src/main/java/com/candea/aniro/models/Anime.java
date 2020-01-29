package com.candea.aniro.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.candea.aniro.AnilistFullMediaQuery;

import java.util.ArrayList;
import java.util.List;

public class Anime implements Parcelable {

    private String title;
    private String description;
    private String cover_url;
    private String banner_url;
    private String listName;
    private String type;
    private String format;
    private String status;
    private List<String> genres;
    private List<AnilistFullMediaQuery.StreamingEpisode> episodeList = new ArrayList<>();
    private List<AnilistFullMediaQuery.Edge> cast = new ArrayList<>();
    private AnilistFullMediaQuery.Staff staff;
    private int id;
    private float rating;
    private int nrEpisodes;

    public Anime() {
    }

    public Anime(int id, String title, String cover_url) {
        this.id = id;
        this.title = title;
        this.cover_url = cover_url;
    }

    protected Anime(Parcel in) {
        title = in.readString();
        cover_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(cover_url);
    }

    public static final Creator<Anime> CREATOR = new Creator<Anime>() {
        @Override
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNrEpisodes() {
        return nrEpisodes;
    }

    public void setNrEpisodes(int nrEpisodes) {
        this.nrEpisodes = nrEpisodes;
    }

    public List<AnilistFullMediaQuery.StreamingEpisode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<AnilistFullMediaQuery.StreamingEpisode> list) {
        episodeList.addAll(list);
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<AnilistFullMediaQuery.Edge> getCast() {
        return cast;
    }

    public void setCast(List<AnilistFullMediaQuery.Edge> list) {
        cast.addAll(list);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public AnilistFullMediaQuery.Staff getStaff() {
        return staff;
    }

    public void setStaff(AnilistFullMediaQuery.Staff staff) {
        this.staff = staff;
    }
}
