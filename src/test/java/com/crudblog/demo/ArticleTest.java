package com.crudblog.demo;

import com.crudblog.demo.article.domain.entity.Article;
import com.crudblog.demo.article.domain.entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticleTest {
    private Article article;

    @BeforeEach
    public void setUp() {
        article = new Article();
    }

    @Test
    public void testArticleId() {
        Long id = 1L;
        article.setId(id);
        assertEquals(id, article.getId());
    }

    @Test
    public void testArticleTitle() {
        String title = "Test Title";
        article.setTitle(title);
        assertEquals(title, article.getTitle());
    }

    @Test
    public void testArticleDescription() {
        String description = "Test Description";
        article.setDescription(description);
        assertEquals(description, article.getDescription());
    }

    @Test
    public void testArticleCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.now();
        assertEquals(createdAt.getYear(), article.getCreatedAt().getYear());
        assertEquals(createdAt.getMonth(), article.getCreatedAt().getMonth());
        assertEquals(createdAt.getDayOfMonth(), article.getCreatedAt().getDayOfMonth());
    }

    @Test
    public void testArticleComments() {
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comments.add(comment1);
        comments.add(comment2);

        article.setComments(comments);
        assertNotNull(article.getComments());
        assertEquals(2, article.getComments().size());
    }
}
