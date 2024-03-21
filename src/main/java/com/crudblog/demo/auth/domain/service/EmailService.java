package com.crudblog.demo.auth.domain.service;

import com.crudblog.demo.auth.domain.service.interfaces.EmailServiceInterface;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailServiceInterface {
    @Value("${mailjet.apiKey")
    private String apiKey;

    @Value("${mailjet.apiSecret")
    private String apiSecret;

    @Override
    public void SendMail(String toEmail, String body) throws MailjetException {
        ClientOptions options = ClientOptions.builder()
                .apiKey("e9e361731558d8ae767495a73ebf5a9f")
                .apiSecretKey("35de473645b008cd126a0429adfb1ab9")
                .build();

        MailjetClient client = new MailjetClient(options);

        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact(toEmail, "stanislav"))
                .from(new SendContact("majid.beneddine@wildcodeschool.com", "Mailjet integration test"))
                .htmlPart("<h1>Mot de passe oublié</h1><p>" + body + "</p>")
                .subject("Mot de passe oublié")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1) // you can add up to 50 messages per request
                .build();

        // act
        SendEmailsResponse response = request.sendWith(client);
    }
}
