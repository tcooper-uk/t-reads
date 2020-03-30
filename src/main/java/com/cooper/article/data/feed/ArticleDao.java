package com.cooper.article.data.feed;

import com.cooper.article.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {

    List<Article> getTopArticles(Optional<String> param);
}
