package com.cooper.article.setup;

import com.cooper.article.setup.modules.FeedModule;
import com.cooper.article.setup.modules.PropertiesModule;
import com.cooper.article.setup.modules.RepoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Container {

    public static Injector Setup() {
        return Guice.createInjector(
                new PropertiesModule(),
                new FeedModule(),
                new RepoModule());
    }
}
