package ch.heigvd.res;

import ch.heigvd.res.model.mail.*;
import ch.heigvd.res.smtp.SmtpClient;

import java.util.ArrayList;
import java.util.LinkedList;

import static ch.heigvd.res.model.mail.Group.createGroups;

public class MailBot {

    static private final String CONFIG_FILE_PATH = "./src/main/java/ch/heigvd/res/config/";

    static private SmtpClient client;
    static private LinkedList<Group> groups;
    static public Config config;
    static private ArrayList<Person> victims;
    static private LinkedList<Message> messages;
    static private LinkedList<Mail> mails;

    public static void main(String[] args) {
        try {

            initialisation();

            groups= createGroups(victims,messages);
            mails = Mail.createMails(groups);

            client.sendMails(mails);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    private static void initialisation() {
        config = new Config(CONFIG_FILE_PATH+"config.txt");
        victims = Person.parseFile( CONFIG_FILE_PATH+"emails.txt");
        messages = Message.parseFile(CONFIG_FILE_PATH+"prankMessage.txt");

        client = new SmtpClient(config.serverAdress,config.serverPort);
    }
}
