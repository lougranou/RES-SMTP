package ch.heigvd.res.model.mail;

import java.util.LinkedList;
import java.util.List;

public class Group {
    private List<Person> people;

    public Group(){
        this.people = new LinkedList<>();
    }

    public void addPerson(Person person){
        this.people.add(person);
    }

    public List<Person> getPeople(){
        return this.people;
    }
}
