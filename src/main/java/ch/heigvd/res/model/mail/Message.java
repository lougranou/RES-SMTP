package ch.heigvd.res.model.mail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Message {
    private String subject;
    private String body;

    private static final  String MESSAGE_SEPERATOR="===";
    private static final  String CONETENT_SEPERATOR=": ";

    public Message(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static LinkedList<Message> parseFile(String path){
        LinkedList<Message> messages = new LinkedList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            String subject ="";
            String content="";
            while ((line = in.readLine()) != null) {
                if(line.equals(MESSAGE_SEPERATOR)){
                    messages.add(new Message(subject,content));
                }else {
                    String[] tab = line.split(CONETENT_SEPERATOR);
                    switch (tab[0]) {
                        case "subject":
                            subject = tab[1];
                            break;
                        case "content":
                            content = tab[1];
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error in parseMessage");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
