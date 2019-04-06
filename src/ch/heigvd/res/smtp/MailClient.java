package ch.heigvd.res.smtp;

import ch.heigvd.res.model.mail.Message;

import java.io.IOException;

public interface MailClient {
    void sendMessage(Message message) throws IOException;
}
