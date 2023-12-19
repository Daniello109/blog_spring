package com.crudblog.demo.article.application;

import com.crudblog.demo.article.domain.entity.Article;
import com.crudblog.demo.article.domain.entity.Comment;
import com.crudblog.demo.article.domain.service.ArticleService;
import com.crudblog.demo.article.infrastructure.exception.ArticleNotFoundException;
import com.crudblog.demo.article.infrastructure.repository.ArticleRepository;
import com.crudblog.demo.article.infrastructure.repository.CommentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    List<Article> readAll() {
        return articleService.getAllArticles();
    }

    @GetMapping("/articles/{id}")
    Article readOne(@PathVariable Long id) {
        return  articleService.getOneArticle(id);
    }

    @PostMapping("/articles")
    Article create(@RequestBody Article newArticle) {
        return articleService.createArticle(newArticle);
    }

    @PutMapping("/articles/{id}")
    Article edit(@RequestBody Article newArticle, @PathVariable Long id) {
        return articleService.updateArticle(newArticle, id);
    }

    @DeleteMapping("/articles/{id}")
    void delete(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }
}
