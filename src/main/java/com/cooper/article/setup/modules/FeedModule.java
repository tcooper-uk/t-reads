package com.cooper.article.setup.modules;

import com.cooper.article.data.feed.ArticleRssFeed;
import com.cooper.article.data.feed.ArticleRssFeedReader;
import com.google.inject.AbstractModule;

public class FeedModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ArticleRssFeed.class).to(ArticleRssFeedReader.class);
    }
}
