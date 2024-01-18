package com.crudblog.demo.auth.domain.service;

import com.crudblog.demo.auth.domain.entity.User;
import com.crudblog.demo.auth.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

    public UserLoginService(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    public User login(User user) {
        User userEntity = getUserEntityByUsername(user.getUsername());
        if (!verifyHashedPasswordDuringLogin(user.getPassword(), userEntity.getPassword())){
            throw new RuntimeException();
        }

        user.setRoles(userEntity.getRoles());

        return user;
    }

    public boolean verifyHashedPasswordDuringLogin(String password, String hashedPassword) {
        return bcryptPasswordEncoder.matches(password, hashedPassword);
    }

    public User getUserEntityByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
