package com.cooper.article.data.repo;

import com.cooper.article.model.FeedSource;

import java.util.List;

public interface FeedSourceDao {

    /**
     * Get the rss feed sources from the data store.
     * @return A list of URL's
     */
    List<FeedSource> getSources();
}
