package com.cooper.article.exception;

public class ArticleRetrieveException extends Exception {
    public ArticleRetrieveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleRetrieveException(String message) {
        super(message);
    }
}
