package com.cooper.article.data.feed;

import com.cooper.article.model.Article;

import java.net.URL;
import java.util.List;

public interface ArticleRssFeed {

    /**
     * Get the articles from the RSS feed.
     * @param feedUrl A valid URL to an RSS feed.
     * @return a list of Articles.
     */
    List<Article> getFeedEntries(URL feedUrl);
}
