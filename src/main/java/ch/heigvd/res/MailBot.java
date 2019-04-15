package ch.heigvd.res;

import ch.heigvd.res.model.Group;
import ch.heigvd.res.model.Mail;
import ch.heigvd.res.model.Message;
import ch.heigvd.res.model.Person;
import ch.heigvd.res.smtp.SmtpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

import static ch.heigvd.res.model.Group.createGroups;

public class MailBot {

    static private final String CONFIG_FILE_PATH = "./src/main/java/ch/heigvd/res/config/";

    static public Properties config;
    static private ArrayList<Person> victims;
    static private LinkedList<Message> messages;

    public static void main(String[] args) {
        try {

            loadFiles();

            LinkedList<Group> groups= createGroups(victims,messages);
            LinkedList<Mail> mails = Mail.createMails(groups);
            new SmtpClient(config).sendMails(mails);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    private static void loadFiles() throws IOException {
        InputStream input = new FileInputStream(CONFIG_FILE_PATH+"configMailTrap.properties");
        config = new Properties();
        config.load(input);
        victims = Person.parseFile( CONFIG_FILE_PATH+"emails.txt");
        messages = Message.parseFile(CONFIG_FILE_PATH+"prankMessage.txt");
    }
}
