package com.crudblog.demo.article.infrastructure.repository;

import com.crudblog.demo.article.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
