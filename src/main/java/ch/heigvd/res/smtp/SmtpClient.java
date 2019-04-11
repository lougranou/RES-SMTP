package ch.heigvd.res.smtp;

import ch.heigvd.res.model.mail.Mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.res.smtp.Protocole.*;

public class SmtpClient implements MailClient{

    private String serverAddress;
    private int serverPort;

    private PrintWriter out;
    private BufferedReader in;

    public SmtpClient(String serverAddress, int serverPort){
        this.serverAddress=serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public void sendMessage(Mail mail) throws IOException {

        Socket socket = new Socket(serverAddress, serverPort);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

        sendCommand(EHLO,serverAddress);
        read();
        read();
        read();

        sendCommand(FROM,mail.getFrom());
        read();

        // Are you sur about that ?
        String to = new String();
        for (String m : mail.getTo()){
            to += m +", ";
        }

        sendCommand(TO,to);
        read();

        // HAck : Make 2 function ?
        sendCommand(DATA,"");
        read();
        sendCommand("","Subject :" +mail.getConent().getSubject()+EOL);
        sendCommand("",mail.getConent().getBody());

        sendCommand(END,"");
        read();

        sendCommand(QUIT,"");
        read();
        socket.close();
    }
    private void sendCommand(String protocole, String value){
        out.write(protocole+ value + EOL);
        out.flush();
    }
    private String read(){
        String serverResponse = "";
        try {
            serverResponse=  in.readLine();
            Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,serverResponse);

        } catch (IOException e) {
            Logger.getLogger(SmtpClient.class.getName()).log(Level.SEVERE, null,e);
        }
        return serverResponse;
    }
}
