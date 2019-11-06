package com.candea.aniro.models;

public class Anime {
    private String title;
    private String category;
    private String studio;
    private String description;
    private String image_url;
    private String rating;
    private int nrSeasons;
    private int nrEpisodes;


    public Anime() {
    }

    public Anime(String title, String category,String rating, String studio, String description, String image_url, int nrSeasons, int nrEpisodes) {
        this.title = title;
        this.category = category;
        this.studio = studio;
        this.description = description;
        this.image_url = image_url;
        this.nrSeasons = nrSeasons;
        this.nrEpisodes = nrEpisodes;
        this.rating = rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNrSeasons(int nrSeasons) {
        this.nrSeasons = nrSeasons;
    }

    public void setNrEpisodes(int nrEpisodes) {
        this.nrEpisodes = nrEpisodes;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getStudio() {
        return studio;
    }

    public String getDescription() {
        return description;
    }

    public int getNrSeasons() {
        return nrSeasons;
    }

    public int getNrEpisodes() {
        return nrEpisodes;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getRating() {
        return rating;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
