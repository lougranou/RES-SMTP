package ch.heigvd.res.model.mail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    private static final String SEPARATOR="=";
    String serverAdress;
    int serverPort;
    int nbGroup;

    public String getServerAdress() {
        return serverAdress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getNbGroup() {
        return nbGroup;
    }


    public Config(String path){
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line;
            while ((line = in.readLine()) != null) {

                String[] splittedLine = line.split(SEPARATOR);

                switch (splittedLine[0]){
                    case "serverAdress":
                        this.serverAdress = splittedLine[1];
                        break;
                    case "serverPort":
                        this.serverPort = Integer.parseInt(splittedLine[1]);
                        break;
                    case "nbGroup":
                        this.nbGroup = Integer.parseInt(splittedLine[1]);
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
