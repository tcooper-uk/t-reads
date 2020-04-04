package com.cooper.article.service;

import com.cooper.article.data.feed.ArticleRssFeed;
import com.cooper.article.data.repo.FeedSourceDao;
import com.cooper.article.exception.ArticleRetrieveException;
import com.cooper.article.model.Article;
import com.cooper.article.model.FeedSource;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {

    private ArticleRssFeed articleRssFeedMock;
    private FeedSourceDao feedSourceDaoMock;
    private ArticleService service;

    private List<FeedSource> testSources;

    @Before
    public void setup(){
        articleRssFeedMock = mock(ArticleRssFeed.class);
        feedSourceDaoMock = mock(FeedSourceDao.class);

        service = new ArticleService(articleRssFeedMock, feedSourceDaoMock);

        testSources = getTestSources();
    }

    @Test
    public void articleSerivce_ShouldReturnArticles() throws ArticleRetrieveException {

        final int feedCount = testSources.size();

        var testArticles = getTestArticles();
        final int articleCount = testArticles.size();

        when(feedSourceDaoMock.getSources()).thenReturn(testSources);
        when(articleRssFeedMock.getFeedEntries(any(URL.class))).thenReturn(testArticles);

        var results = service.getArticles();

        // assert
        verify(articleRssFeedMock, times(feedCount)).getFeedEntries(any(URL.class));

        assertThat(results.size()).isEqualTo(articleCount * feedCount);

        var r1 = results.get(0);
        var r2 = results.get(1);

        assertThat(r1.getTitle()).isEqualTo("test");
        assertThat(r1.getDescription()).isEqualTo("test description");

        assertThat(r2.getTitle()).isEqualTo("test2");
        assertThat(r2.getDescription()).isEqualTo("test2 description");
    }


    @Test
    public void articleService_NoSources_ThrowsError() {
        when(feedSourceDaoMock.getSources()).thenReturn(new ArrayList<>());
        when(articleRssFeedMock.getFeedEntries(any(URL.class))).thenReturn(getTestArticles());

        assertThatThrownBy(() -> {
            var results = service.getArticles();
        })
                .hasMessage("There was an error getting article from the feed(s).")
                .isInstanceOf(ArticleRetrieveException.class);

    }

    @Test
    public void articleService_NoArticles_ThrowsError() {
        when(feedSourceDaoMock.getSources()).thenReturn(testSources);
        when(articleRssFeedMock.getFeedEntries(any(URL.class))).thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> {
            var results = service.getArticles();
        })
                .hasMessage("There was an error getting article from the feed(s).")
                .isInstanceOf(ArticleRetrieveException.class);

        verify(articleRssFeedMock, times(testSources.size())).getFeedEntries(any(URL.class));
    }

    @Test
    public void articleService_SomeArticles_DoesNotThrow() throws ArticleRetrieveException {
        var testArticles = getTestArticles();

        when(feedSourceDaoMock.getSources()).thenReturn(testSources);
        when(articleRssFeedMock.getFeedEntries(any(URL.class)))
                .thenReturn(testArticles)
                .thenReturn(new ArrayList<>());

        var results = service.getArticles();

        // assert
        verify(articleRssFeedMock, times(testSources.size())).getFeedEntries(any(URL.class));

        assertThat(results.size()).isEqualTo(testArticles.size());

        assertThat(results.get(0).getTitle()).isEqualTo("test");
        assertThat(results.get(0).getDescription()).isEqualTo("test description");
    }



    private List<FeedSource> getTestSources() {
        try {
            return List.of(
                    new FeedSource("test", "http://test.com/feed"),
                    new FeedSource("test2", "http://test.com/feed")
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // get some dummy data
    private List<Article> getTestArticles(){
        return List.of(
                new Article("test", "test description"),
                new Article("test2", "test2 description")
        );
    }
}
