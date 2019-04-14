package ch.heigvd.res.smtp;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.Mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.res.smtp.Protocole.*;

public class SmtpClient implements MailClient {

    private Config config;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


    public SmtpClient(Config config){
        this.config = config;
        try {
            this.socket = new Socket(config.serverAddress, config.serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    public void sendMails(LinkedList<Mail> mails) throws IOException {


        serverGreetings();

        for(Mail m : mails){
            try {
                sendMail(m);
                break;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        serverGoodBye();
    }


    @Override
    public void sendMail(Mail mail) throws IOException {
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
        data += SUBJECT + mail.getConent().getSubject()+EOL;
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

                    // check if server wait for instruction protocle : <digit><digit><digit><space>
                    if(Character.isDigit(serverResponse.charAt(0))
                        && Character.isDigit(serverResponse.charAt(1))
                        && Character.isDigit(serverResponse.charAt(2))
                        && serverResponse.charAt(3) == ' '){
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
        if(config.authlogin == false){
            return;
        }


        sendCommand(AUTH_LOGIN, "");
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());

        sendCommand("", config.usernameB64);
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());
        sendCommand("", config.passwordB64);
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());


    }


    private void serverGreetings() throws IOException {

        // server welcome message at first
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());

        sendCommand(EHLO,config.serverAddress);
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());
        authentificationManager();
    }

    private void serverGoodBye() throws IOException {
        sendCommand(QUIT,"");
        Logger.getLogger(SmtpClient.class.getName()).log(Level.INFO,read());
        socket.close();
    }
}
