package ch.heigvd.res;

import ch.heigvd.res.model.mail.Group;
import ch.heigvd.res.model.mail.Mail;
import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.mail.Person;
import ch.heigvd.res.smtp.SmtpClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MailBot {
    public static void main(String[] args) throws IOException {

        //TODO : Read config file
        SmtpClient client = new SmtpClient("localhost",25);
        final int NB_GROUP = 2;


        String pathToFile = "./src/main/java/ch/heigvd/res/config/emails.txt";
        LinkedList<Person> victims = Person.parseFile(pathToFile);

        ArrayList<Group> groups = new ArrayList<>();
        for (int i =0;i< NB_GROUP;i++)
            groups.add(new Group());
        for(int i = 0; i< victims.size();i++)
            groups.get(i % NB_GROUP).addPerson(victims.get(1));

        pathToFile = "./src/main/java/ch/heigvd/res/config/prankMessage.txt";
        LinkedList<Message> messages = Message.parseFile(pathToFile);
        LinkedList<Mail> mails = new LinkedList<>();
        for (Group g : groups) {
            mails.add(new Mail(g.getSenderVictimAddress(), g.getReciversVictimAddress(), null, null, messages.get(0)));
        }
        for(Mail m : mails){
            client.sendMessage(m);
        }
    }
}
