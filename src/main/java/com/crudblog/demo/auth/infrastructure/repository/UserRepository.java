package com.crudblog.demo.auth.infrastructure.repository;

import com.crudblog.demo.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
