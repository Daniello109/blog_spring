package com.crudblog.demo.article.infrastructure.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(Long id) {
        super("Could not find article with id " + id);
    }
}
