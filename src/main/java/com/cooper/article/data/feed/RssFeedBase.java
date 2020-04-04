package com.cooper.article.data.feed;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public abstract class RssFeedBase implements ArticleRssFeed {

    protected static final Logger logger = LogManager.getLogger(RssFeedBase.class);

    protected Optional<SyndFeed> getFeed(URL feedUrl){
        try {

            // open up a new feed
            SyndFeedInput input = new SyndFeedInput();
            return Optional.of(input.build(new XmlReader(feedUrl)));

        } catch (IOException | FeedException e) {
            logger.error(e);
            return Optional.empty();
        }
    }
}
