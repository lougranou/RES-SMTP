package ch.heigvd.res.model.mail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Person {
    private String mail;

    public Person(String mail){
        this.mail = mail;
    }
    public String  getMail(){
        return this.mail;
    }

    public static LinkedList<Person> parseFile(String path){
        LinkedList<Person> persons = new LinkedList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            while ((line = in.readLine()) != null) {
                persons.add(new Person(line));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File "+path + "not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
