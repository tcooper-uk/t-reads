package com.cooper.article.service;

import com.cooper.article.setup.Properties;
import com.cooper.article.data.feed.ArticleDao;
import com.cooper.article.exception.ArticleRetrieveException;
import com.cooper.article.model.Article;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {

    private static final String ARTICLE_TAG = "java";

    private ArticleDao articleFeedMock;
    private Properties propsProvider;
    private ArticleService articleService;

    @Before
    public void setUp(){
        articleFeedMock = mock(ArticleDao.class);
        propsProvider = mock(Properties.class);
        articleService = new ArticleService(articleFeedMock, propsProvider);
    }

    @Test
    public void articleCanBeRetrieved() throws Exception {

        // Arrange
        var article = getTestArticles().get(0);
        when(propsProvider.getProperty("tags")).thenReturn(Optional.of(ARTICLE_TAG));
        when(articleFeedMock.getTopArticles(Optional.of(ARTICLE_TAG))).thenReturn(List.of(article));

        // Act
        var a = articleService.getTodaysArticle();

        // Assert
        assertThat(a.getTitle()).isEqualTo("test");
        assertThat(a.getDescription()).isEqualTo("test description");
        verify(propsProvider, times(1)).getProperty("tags");
    }

    @Test
    public void articleServiceThrowWhenNoArticle() {

        // Arrange
        when(propsProvider.getProperty("tags")).thenReturn(Optional.of(ARTICLE_TAG));
        when(articleFeedMock.getTopArticles(Optional.of(ARTICLE_TAG))).thenReturn(new ArrayList<Article>());

        // Act
        assertThatThrownBy(() -> {
            var a = articleService.getTodaysArticle();
        })
                // Assert
                .isInstanceOf(ArticleRetrieveException.class)
                .hasMessage("There was an error retrieving today's article.");
        verify(propsProvider, times(1)).getProperty("tags");
    }

    @Test
    public void articleServicePicksArticle() throws ArticleRetrieveException {

        // Arrange
        var testData = getTestArticles();
        when(propsProvider.getProperty("tags")).thenReturn(Optional.of(ARTICLE_TAG));
        when(articleFeedMock.getTopArticles(Optional.of("java"))).thenReturn(testData);

        // Act
        var a = articleService.getTodaysArticle();

        // Assert
        verify(propsProvider, times(1)).getProperty("tags");
        assertThat(a.getTitle()).isIn(testData.stream().map(td -> td.getTitle()).toArray());
        assertThat(a.getDescription()).isIn(testData.stream().map(td -> td.getDescription()).toArray());
    }


    // get some dummy data
    private List<Article> getTestArticles(){
        return List.of(
                new Article("test", "test description"),
                new Article("test2", "test2 description")
        );
    }
}
