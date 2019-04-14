package ch.heigvd.res.smtp;

import ch.heigvd.res.model.Mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.regex.*;


import static ch.heigvd.res.smtp.Protocole.*;

public class SmtpClient implements MailClient {

    private Properties config;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


    public SmtpClient(Properties config){
        this.config = config;
        try {
            this.socket = new Socket(config.getProperty("serverAddress"), Integer.parseInt(config.getProperty("serverPort")));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void sendMails(LinkedList<Mail> mails) throws IOException {


        serverGreetings();

        for(Mail m : mails) {
            sendMail(m);
        }

        serverGoodBye();
    }


    @Override
    public void sendMail(Mail mail) {
        String data;

        sendCommand(FROM,mail.getFrom());
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());

        /**
         * Multiple receiver
         */
        for (String m : mail.getTo()){
            sendCommand(TO,m);
            Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());
        }

        sendCommand(DATA,"");
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());

        /**
         * Construct DATA to send in one command
         */

        data = HEADING_FROM + mail.getFrom() + EOL;
        data += HEADING_TO;

        /**
         * Multiple receiver
         */
        for (String m : mail.getTo()){
            data += m + ',';
        }
        data += EOL;
        data += SUBJECT + mail.getConent().getSubject()+EOL+EOL; // need to add additional line between subject and body
        data += mail.getConent().getBody() + EOL;

        sendCommand("", data);
        sendCommand(END,"");
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());
    }
    private void sendCommand(String protocole, String value){
        out.write(protocole+ value + EOL);
        out.flush();
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,protocole+ value + EOL);
    }
    private String read(){
        String serverResponse = "";
        try {
            while(true){

                serverResponse =  in.readLine();

                if(serverResponse.length() > 3){

                    // check if server wait for instruction protocle : <digit><digit><digit><space><messgae>
                    Pattern p = Pattern.compile("[0-9]{3} [\\w]");
                    Matcher m = p.matcher(serverResponse);
                    if(m.find()){
                        System.out.println(serverResponse);
                        break;
                    }

                }

                //Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,serverResponse);

            }
        } catch (IOException e) {
            Logger.getLogger(SmtpClient.class.getName()).log(Level.SEVERE, null,e);
        }
        return serverResponse;
    }

    private void authentificationManager(){

        /**
         * no authentication required
         */
        if(config.getProperty("authlogin").equals("false")){
            return;
        }


        sendCommand(AUTH_LOGIN, "");
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());

        sendCommand("", Base64.getEncoder().encodeToString(config.getProperty("username").getBytes()));
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());
        sendCommand("",Base64.getEncoder().encodeToString(config.getProperty("password").getBytes()));
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());


    }


    private void serverGreetings() throws IOException {

        // server welcome message at first
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());

        sendCommand(EHLO,config.getProperty("serverAddress"));
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());
        authentificationManager();
    }

    private void serverGoodBye() throws IOException {
        sendCommand(QUIT,"");
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());
        socket.close();
    }
}
