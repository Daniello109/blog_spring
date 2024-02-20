package com.crudblog.demo.files.infrastructure.repository;

import com.crudblog.demo.files.domain.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> { }
