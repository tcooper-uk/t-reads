package com.cooper.article.service;

import com.cooper.article.data.feed.ArticleRssFeed;
import com.cooper.article.data.repo.FeedSourceDao;
import com.cooper.article.exception.ArticleRetrieveException;
import com.cooper.article.model.Article;
import com.cooper.article.model.FeedSource;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    private static final Logger logger = LogManager.getLogger(ArticleService.class);

    private ArticleRssFeed feedReader;
    private FeedSourceDao feedSourceDao;

    @Inject
    public ArticleService(ArticleRssFeed feedReader, FeedSourceDao feedSourceDao) {
        this.feedReader = feedReader;
        this.feedSourceDao = feedSourceDao;
    }

    public List<Article> getArticles() throws ArticleRetrieveException {
        var sources = getSources();

        List<Article> results = new ArrayList<>();

        if(!sources.isEmpty()) {

            for (var s : sources){
                var articles = feedReader.getFeedEntries(s.getUrl());
                results.addAll(articles);
            }
        }

        if(results.isEmpty()){
            throw new ArticleRetrieveException("There was an error getting article from the feed(s).");
        }

        return results;
    }

    private List<FeedSource> getSources(){
        var sources = feedSourceDao.getSources();

        if(sources.isEmpty()){
            logger.info("There are no RSS feeds available.");
        }

        return sources;
    }
}
