package com.crudblog.demo.article.domain.service;

import com.crudblog.demo.article.domain.entity.Article;
import com.crudblog.demo.article.domain.entity.Comment;
import com.crudblog.demo.article.infrastructure.exception.ArticleNotFoundException;
import com.crudblog.demo.article.infrastructure.repository.ArticleRepository;
import com.crudblog.demo.article.infrastructure.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository repository;
    private final CommentRepository commentRepository;

    public ArticleService(ArticleRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

    public List<Article> getAllArticles() {
        return repository.findAll();
    }

    public Article getOneArticle(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id))
                ;
    }

    public Article createArticle(Article newArticle) {
        return repository.save(newArticle);
    }

    public Article updateArticle(Article newArticle, Long id) {
        return repository.findById(id)
                .map( article -> {
                    article.setTitle(newArticle.getTitle());
                    article.setDescription(newArticle.getDescription());

                    if (!newArticle.getComments().isEmpty()){
                        for (Comment comment: newArticle.getComments()) {
                            comment.setArticle(article);
                            commentRepository.save(comment);
                        }
                    }

                    return repository.save(article);
                })
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public void deleteArticle(Long id) {
        repository.deleteById(id);
    }
}
