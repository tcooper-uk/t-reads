package com.cooper.article.data.http;

import com.cooper.article.model.Article;

import java.util.List;
import java.util.Optional;

@Deprecated
public interface ArticleDao {

    List<Article> getTopArticles(Optional<String> param);
}
