package ch.heigvd.res.smtp;

import ch.heigvd.res.model.mail.Mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SmtpClient implements MailClient{
    private String serverAddress;
    private int port;
    static PrintWriter out;
    static BufferedReader in;

    public SmtpClient(String serverAddress, int port){
        this.serverAddress=serverAddress;
        this.port = port;
    }

    @Override
    public void sendMessage(Mail mail) throws IOException {
        Socket socket = new Socket(serverAddress, port);
        String line;
        out = new PrintWriter(socket.getOutputStream());
        out.flush();


        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        line = in.readLine();System.out.println(line);
        out.write("ehlo " +serverAddress+"\r\n");
        out.flush();
        line = in.readLine();System.out.println(line);
        line = in.readLine();System.out.println(line);
        line = in.readLine();System.out.println(line);

        out.write("mail from: "+mail.getFrom()+"\r\n");
        out.flush();

        line = in.readLine();System.out.println(line);

        String to = new String();
        for (String m : mail.getTo()){
            to += m +", ";
        }
        out.write("rcpt to: "+to+"\r\n");
        out.flush();

        line = in.readLine();System.out.println(line);


        out.write("data"+"\r\n");
        out.flush();

        line = in.readLine();System.out.println(line);
        out.write("Subject: "+ mail.getConent().getSubject()+"\r\n");
        out.flush();

        out.write(mail.getConent().getBody()+"\r\n");
        out.flush();

        out.write("."+"\r\n");
        out.flush();
        line = in.readLine();System.out.println(line);
    }
}
