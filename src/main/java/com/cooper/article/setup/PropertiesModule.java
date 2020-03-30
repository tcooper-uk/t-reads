package com.cooper.article.setup;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class PropertiesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Properties.class).in(Scopes.SINGLETON);
    }
}
