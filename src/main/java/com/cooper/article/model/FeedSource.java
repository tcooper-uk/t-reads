package com.cooper.article.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents a RSS feed source.
 */
public class FeedSource {

    private String name;
    private URL url;

    public FeedSource(String name, String url) throws MalformedURLException {
        this.name = name;
        this.url = new URL(url);
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }
}
