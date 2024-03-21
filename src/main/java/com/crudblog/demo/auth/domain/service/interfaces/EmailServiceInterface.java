package com.crudblog.demo.auth.domain.service.interfaces;

import com.mailjet.client.errors.MailjetException;

public interface EmailServiceInterface {
    public void SendMail(String toEmail, String body) throws MailjetException;
}
