package ch.heigvd.res.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

public class Config {
    private static final String SEPARATOR = "=";
    public String serverAddress;
    public int serverPort;
    public int nbGroup;
    public int minVictimsInGroup;
    public boolean authlogin = false;
    public String usernameB64;
    public String passwordB64;

    //TODO use properties
    public Config(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            while ((line = in.readLine()) != null) {

                String[] splittedLine = line.split(SEPARATOR);

                switch (splittedLine[0]) {
                    case "serverAddress":
                        this.serverAddress = splittedLine[1];
                        break;
                    case "serverPort":
                        this.serverPort = Integer.parseInt(splittedLine[1]);
                        break;
                    case "nbGroup":
                        this.nbGroup = Integer.parseInt(splittedLine[1]);
                        break;
                    case "authlogin":
                        this.authlogin = splittedLine[1].equals("true");
                        break;
                    case "username":
                        /**
                         * need to encode usernameB64 in base 64 for mailtrap
                         */
                        this.usernameB64 =
                                new String(Base64.getEncoder().encode(splittedLine[1].getBytes()));
                        break;
                    case "password":
                        /**
                         * need to encode passwordB64 in base 64  for mailtrap
                         */
                        this.passwordB64 =
                                new String(Base64.getEncoder().encode(splittedLine[1].getBytes()));
                        break;
                    default:
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
