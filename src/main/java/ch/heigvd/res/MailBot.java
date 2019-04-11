package ch.heigvd.res;

import ch.heigvd.res.model.mail.*;
import ch.heigvd.res.smtp.SmtpClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MailBot {
    public static void main(String[] args) throws IOException {

        Config config = new Config("./src/main/java/ch/heigvd/res/config/config.txt");
        SmtpClient client = new SmtpClient(config.getServerAdress(),config.getServerPort());


        String pathToFile = "./src/main/java/ch/heigvd/res/config/emails.txt";
        LinkedList<Person> victims = Person.parseFile(pathToFile);

        ArrayList<Group> groups = new ArrayList<>();
        for (int i =0;i< config.getNbGroup();i++)
            groups.add(new Group());
        for(int i = 0; i< victims.size();i++)
            groups.get(i % config.getNbGroup()).addPerson(victims.get(1));

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
