package com.crudblog.demo.auth.domain.service.interfaces;

import com.crudblog.demo.auth.domain.entity.User;
import com.mailjet.client.errors.MailjetException;

public interface PasswordForgottenServiceInterface {
    public String tokenGenerator(String email) throws MailjetException;
    public String tokenProvider();

    public void mailSender(User user, String token) throws MailjetException;

    public void checkTokenValidityAndCreateNewPassword(String token, User user);
}
