package com.cooper.article.setup.modules;

import com.cooper.article.data.repo.FeedSourceDao;
import com.cooper.article.data.repo.FeedSourceRepo;
import com.google.inject.AbstractModule;

public class RepoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FeedSourceDao.class).to(FeedSourceRepo.class);
    }
}
