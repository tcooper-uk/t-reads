package com.cooper.article.service;

import com.cooper.article.data.http.ArticleDao;
import com.cooper.article.exception.ArticleRetrieveException;
import com.cooper.article.model.Article;
import com.cooper.article.setup.Properties;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Random;

/**
 * First pass implementation at a service to get some articles.
 */
@Deprecated
public class ArticleHttpService {


    private final String TAG_PROP_NAME = "tags";
    private final String NO_ARTICLE_ERR_MSG = "There was an error retrieving today's article.";

    private final ArticleDao feed;
    private final Properties properties;

    @Inject
    public ArticleHttpService(@Named("ArticleFeed") ArticleDao feed, Properties props) {
        this.feed = feed;
        this.properties = props;
    }

    public Article getTodaysArticle() throws ArticleRetrieveException {

        var articles = feed.getTopArticles(properties.getProperty(TAG_PROP_NAME));

        if (articles.isEmpty())
            throw new ArticleRetrieveException(NO_ARTICLE_ERR_MSG);

        if(articles.size() > 1) {
            // get a single article
            var ran = new Random();
            return articles.get(ran.nextInt(articles.size() - 1));
        }
        else {
            return articles.get(0);
        }
    }
}
