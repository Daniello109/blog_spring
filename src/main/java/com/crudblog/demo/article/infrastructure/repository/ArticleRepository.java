package com.crudblog.demo.article.infrastructure.repository;

import com.crudblog.demo.article.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
