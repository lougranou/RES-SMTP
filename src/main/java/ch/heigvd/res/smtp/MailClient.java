package ch.heigvd.res.smtp;

import ch.heigvd.res.model.mail.Mail;

import java.io.IOException;

public interface MailClient {
    void sendMail(Mail mail) throws IOException;
}
