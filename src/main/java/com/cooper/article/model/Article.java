package com.cooper.article.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Article {

    private UUID articleId;

    @SerializedName("title")
    private String title;

    private String description;

    private String url;

    {
        articleId = UUID.randomUUID();
    }

    public Article(){}

    public Article(String title, String description) {
        validateTitle(title);

        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void validateTitle(String title) {
        if(title.isBlank() || title.isEmpty()){
            throw new IllegalArgumentException("The article name must have a value");
        }
    }

    public UUID getArticleId() {
        return articleId;
    }
}
