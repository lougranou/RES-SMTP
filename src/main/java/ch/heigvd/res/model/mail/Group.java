package ch.heigvd.res.model.mail;

import java.util.ArrayList;
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

    public LinkedList<String> getAddresses(){
        LinkedList<String> addresses = new LinkedList<>();
        for(Person p : people){
            addresses.add(p.getMail());
        }
        return addresses;

    }

    public List<Person> getPeople(){
        return this.people;
    }
    public String getSenderVictimAddress(){
        return getAddresses().get(0);
    }
    public String[] getReciversVictimAddress(){
        LinkedList<String> tmp = getAddresses();
        tmp.remove(getSenderVictimAddress());
        return tmp.toArray(new String[0]);
    }
}
