package com.crudblog.demo.auth.infrastructure.repository;

import com.crudblog.demo.auth.domain.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    PasswordToken findByToken(String token);
}
