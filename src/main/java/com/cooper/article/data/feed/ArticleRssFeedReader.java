package com.cooper.article.data.feed;

import com.cooper.article.model.Article;
import com.sun.syndication.feed.synd.SyndEntry;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ArticleRssFeedReader extends RssFeedBase {

    @Override
    public List<Article> getFeedEntries(URL feedUrl) {

        // Let's get articles from the RSS Feed
        var feed = super.getFeed(feedUrl);

        if(feed.isPresent()){
            var results = new ArrayList<Article>();

            for (var e : feed.get().getEntries()) {
                final SyndEntry entry = (SyndEntry) e;
                var article = new Article(entry.getTitle(), entry.getDescription().getValue());
                article.setUrl(entry.getUri());
                results.add(article);
            }

            return results;
        }

        return new ArrayList<>();
    }
}
