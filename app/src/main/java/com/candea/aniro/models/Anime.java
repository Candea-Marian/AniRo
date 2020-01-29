package com.candea.aniro.models;

import java.util.ArrayList;

public class Anime {
    private String title;
    private String category;
    private String studio;
    private String description;
    private String image_url;
    private String rating;
    private int nrSeasons;
    private int nrEpisodes;
    private ArrayList<Episode> episodeList = new ArrayList<>();

    public Anime() {
    }

    public Anime(String title, String category, String studio, String description, String image_url, String rating, int nrSeasons, int nrEpisodes, ArrayList<Episode> episodeList) {
        this.title = title;
        this.category = category;
        this.studio = studio;
        this.description = description;
        this.image_url = image_url;
        this.rating = rating;
        this.nrSeasons = nrSeasons;
        this.nrEpisodes = nrEpisodes;
        this.episodeList = episodeList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getNrSeasons() {
        return nrSeasons;
    }

    public void setNrSeasons(int nrSeasons) {
        this.nrSeasons = nrSeasons;
    }

    public int getNrEpisodes() {
        return nrEpisodes;
    }

    public void setNrEpisodes(int nrEpisodes) {
        this.nrEpisodes = nrEpisodes;
    }

    public ArrayList<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(ArrayList<Episode> episodeList) {
        this.episodeList = episodeList;
    }
}
