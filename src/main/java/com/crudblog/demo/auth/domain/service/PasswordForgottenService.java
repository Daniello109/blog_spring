package com.crudblog.demo.auth.domain.service;

import com.crudblog.demo.auth.domain.entity.PasswordToken;
import com.crudblog.demo.auth.domain.entity.User;
import com.crudblog.demo.auth.domain.service.interfaces.EmailServiceInterface;
import com.crudblog.demo.auth.domain.service.interfaces.PasswordForgottenServiceInterface;
import com.crudblog.demo.auth.infrastructure.exception.PasswordNotSimilarException;
import com.crudblog.demo.auth.infrastructure.exception.PasswordTokenException;
import com.crudblog.demo.auth.infrastructure.repository.PasswordTokenRepository;
import com.crudblog.demo.auth.infrastructure.repository.UserRepository;
import com.mailjet.client.errors.MailjetException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

@Service
public class PasswordForgottenService implements PasswordForgottenServiceInterface {
    private UserRepository userRepository;
    private PasswordTokenRepository passwordTokenRepository;

    private EmailServiceInterface emailService;

    private UserRegistrationService userRegistrationService;

    public PasswordForgottenService(UserRepository userRepository, PasswordTokenRepository passwordTokenRepository, EmailServiceInterface emailService, UserRegistrationService userRegistrationService) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    public String tokenGenerator(String email) throws MailjetException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        LocalDateTime dateTime = LocalDateTime.now();
        String token = tokenProvider();
        PasswordToken passwordToken = new PasswordToken();
        passwordToken.setToken(token);
        passwordToken.setUser(user);
        passwordToken.setDateOfExpiration(dateTime.plusHours(24));

        passwordTokenRepository.save(passwordToken);
        userRepository.save(user);

        mailSender(user, token);
        return token;
    }

    @Override
    public void mailSender(User user, String token) throws MailjetException {
        emailService.SendMail(user.getEmail(), "Pour créer un nouveau mot de passe, veuillez accéder à cette adresse http://localhost:4200/password-forgotten/" + token);
    }

    @Override
    public String tokenProvider() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[100 * 3/4];
        random.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Override
    public void checkTokenValidityAndCreateNewPassword(String token, User user) {
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token);

        if (passwordToken == null || LocalDateTime.now().isAfter(passwordToken.getDateOfExpiration()) || passwordToken.isChanged()) {
            throw new PasswordTokenException();
        }

        User userToPersist = passwordToken.getUser();

        if (!Objects.equals(user.getPlainPassword(), user.getPlainPasswordVerification())) {
            throw new PasswordNotSimilarException();
        }

        String newHashedPasswordUser = userRegistrationService.GenerateHashedPassword(user.getPlainPassword());
        userToPersist.setPassword(newHashedPasswordUser);
        passwordToken.setChanged(true);
        passwordTokenRepository.save(passwordToken);
        userRepository.save(userToPersist);

    }
}
