package ch.heigvd.res;

import ch.heigvd.res.model.mail.Group;
import ch.heigvd.res.model.mail.Person;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MailBot {
    public static void main(String[] args) {
        final int NB_GROUP = 2;
        List<Person> victims = new LinkedList<>();

        String pathToFile = "./src/main/java/ch/heigvd/res/config/emails.txt";
        try {
            BufferedReader in = new BufferedReader(new FileReader(pathToFile));
            String line;
            while((line = in.readLine()) != null){
                victims.add(new Person(line));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error in parseMessage");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Group [] groups = new Group[NB_GROUP];
        for(int i = 0; i < groups.length ; ++i){
            groups[i] = new Group();
        }

        for(int i = 0; i < victims.size() ; ++i){
            groups[i%NB_GROUP].addPerson(victims.get(i));
        }



        pathToFile = "./src/main/java/ch/heigvd/res/config/prankMessage.txt";

        for(int i = 0; i < groups.length ; ++i) {

            try {
                BufferedReader in = new BufferedReader(new FileReader(pathToFile));
                String line;
                while ((line = in.readLine()) != null) {

                }
            } catch (FileNotFoundException e) {
                System.err.println("Error in parseMessage");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
