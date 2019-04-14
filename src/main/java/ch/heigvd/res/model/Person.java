package ch.heigvd.res.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Person {
    private String mail;

    public Person(String mail){
        this.mail = mail;
    }
    public String  getMail(){
        return this.mail;
    }

    public static ArrayList<Person> parseFile(String path){
        ArrayList<Person> persons = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            while ((line = in.readLine()) != null) {
                persons.add(new Person('<' + line + '>'));
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
