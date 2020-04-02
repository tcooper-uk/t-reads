package com.cooper.article;

import com.cooper.article.exception.ApplicationPropertiesException;
import com.cooper.article.exception.ArticleRetrieveException;
import com.cooper.article.service.ArticleService;
import com.cooper.article.setup.DataModule;
import com.cooper.article.setup.Properties;
import com.cooper.article.setup.PropertiesModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        if(!Properties.isLoaded()){
            // we are going to need these, so, runtime exception!
            throw new ApplicationPropertiesException();
        }

        // setup DI
        Injector inject = Guice.createInjector(new PropertiesModule(), new DataModule());

        // get article service from Guice
        var articleService = inject.getInstance(ArticleService.class);

        // get article
        try {
            var todaysArticle = articleService.getTodaysArticle();

            // write article
            logger.trace("Today's article is:");
            logger.info("Title: " + todaysArticle.getTitle());
            logger.info("Description: " + todaysArticle.getDescription());
            logger.info("Url: " + todaysArticle.getUrl());

        } catch (ArticleRetrieveException e) {
            logger.error(e);
        }
    }
}
