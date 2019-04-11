package ch.heigvd.res.smtp;

import ch.heigvd.res.model.mail.Mail;

import java.io.IOException;

public class SmtpClient implements MailClient{
    private String serverAddress;
    private int port;

    public SmtpClient(String serverAddress, int port){

    }

    @Override
    public void sendMessage(Mail mail) throws IOException {

    }
}
