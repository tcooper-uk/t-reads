package com.cooper.article;

import com.cooper.article.exception.ApplicationPropertiesException;
import com.cooper.article.exception.ArticleRetrieveException;
import com.cooper.article.service.ArticleHttpService;
import com.cooper.article.service.ArticleService;
import com.cooper.article.setup.*;
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
        Injector inject = Container.Setup();

        // get article service from Guice
        var articleService = inject.getInstance(ArticleService.class);

        // get article
        try {
            var articles = articleService.getArticles();

            for(var article : articles) {

                // write article
                logger.info("Title: " + article.getTitle());
                logger.info("Url: " + article.getUrl());
            }

        } catch (ArticleRetrieveException e) {
            logger.error(e);
        }
    }
}
