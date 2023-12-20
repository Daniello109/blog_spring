package com.crudblog.demo.auth.domain.service;

import com.crudblog.demo.auth.domain.dto.UserDTO;
import com.crudblog.demo.auth.domain.entity.User;
import com.crudblog.demo.auth.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    private UserMapper userMapper;

    public UserRegistrationService(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public UserDTO UserRegistration(User userData) {
        userData.setPassword(GenerateHashedPassword(userData.getPassword()));
        try {
            return userMapper.transformUserEntityInUserDto(userRepository.save(userData), true);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String GenerateHashedPassword(String password) {
        return bcryptPasswordEncoder.encode(password);
    }
}
