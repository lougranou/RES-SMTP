package ch.heigvd.res.model.mail;

import ch.heigvd.res.MailBot;

import java.util.*;

public class Group {
    private Person sender;
    private LinkedList<Person> recievers;
    private Message prankMessage;


    public Group(List<Person> victims,Message prankMessage){
        recievers = new LinkedList<>(victims);
        sender = recievers.pop();
        this.prankMessage=prankMessage;
    }


    private void addReciever(Person p) {
        recievers.add(p);
    }

    public Message getPrankMessage() {
        return prankMessage;
    }

    public LinkedList<String> getAddresses(){
        LinkedList<String> addresses = new LinkedList<>();
        for(Person p : recievers){
            addresses.add(p.getMail());
        }
        return addresses;

    }

    public String getSenderVictimAddress(){
        return sender.getMail();
    }
    public String[] getReciversVictimAddress(){
        LinkedList<String> tmp = getAddresses();
        tmp.remove(getSenderVictimAddress());
        return tmp.toArray(new String[0]);
    }


    public static LinkedList<Group> createGroups(ArrayList<Person> victims, LinkedList<Message> messages) throws Exception {
        if(victims.size()<MailBot.config.minVictimsInGroup)
            throw new Exception("Not enouh victims to create a group");
        if(MailBot.config.nbGroup > messages.size())
            throw new Exception("Not enouh prank messages to have one different for each group");

        Collections.shuffle(victims);
        Collections.shuffle(messages);

        int nbPersonIngroup = victims.size()/MailBot.config.nbGroup;

        LinkedList<Group> groups = new LinkedList<>();
        for(int i=0;i<MailBot.config.nbGroup; i++){
            groups.add(new Group(victims.subList(i*nbPersonIngroup,(i*nbPersonIngroup)+nbPersonIngroup),messages.get(i)));
        }
        for(int i =victims.size();i<victims.size()%nbPersonIngroup;i--){
            groups.get(i%MailBot.config.nbGroup).addReciever(victims.get(i));
        }

        return groups;
    }

}
