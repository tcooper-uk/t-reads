package com.cooper.article;

import com.cooper.article.exception.ApplicationPropertiesException;
import com.cooper.article.exception.ArticleRetrieveException;
import com.cooper.article.service.ArticleService;
import com.cooper.article.setup.DataModule;
import com.cooper.article.setup.Properties;
import com.cooper.article.setup.PropertiesModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

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
            System.out.println("Today's article is:");
            System.out.println("Title: " + todaysArticle.getTitle());
            System.out.println("Description: " + todaysArticle.getDescription());
            System.out.println("Url: " + todaysArticle.getUrl());

        } catch (ArticleRetrieveException e) {
            System.out.println(e.getMessage());
        }
    }
}
