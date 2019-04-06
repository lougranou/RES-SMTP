package ch.heigvd.res.smtp;

import ch.heigvd.res.model.mail.Message;

public interface MailClient {
    void sendMessage(Message message);
}
