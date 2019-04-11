package ch.heigvd.res.model.mail;

import java.io.*;
import java.util.List;

public class Mail extends Message {
    private String from;
    private String[] to;
    private String[] cc;
    private String[] bcc;

    public Mail(String from, String subject, String body, String[] to, String[] cc, String[] bcc) {
        super(subject, body);
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public static List<Mail> parseMessage(String pathToFile){
        try {
            BufferedReader in = new BufferedReader(new FileReader(pathToFile));
            String line;
            while((line = in.readLine()) != null){

            }
        } catch (FileNotFoundException e) {
            System.err.println("Error in parseMessage");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
