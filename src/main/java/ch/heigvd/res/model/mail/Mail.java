package ch.heigvd.res.model.mail;

import java.util.ArrayList;
import java.util.LinkedList;

public class Mail {
    private String from;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    Message conent;
    public Mail(String from, String[] to, String[] cc, String[] bcc, Message content) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.conent = content;
    }

    public static LinkedList<Mail> createMails(LinkedList<Group> groups) {
        LinkedList<Mail> mails = new LinkedList<>();

        for(Group g : groups){
            mails.add(new Mail(g.getSenderVictimAddress(),g.getReciversVictimAddress(),null, null,g.getPrankMessage()));
        }

        return mails;
    }

    public String getFrom() {
        return from;
    }

    public String[] getTo() {
        return to;
    }

    public String[] getCc() {
        return cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public Message getConent() {
        return conent;
    }
}
