import client.Client;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args){
//        String ip = "6.tcp.ngrok.io";

        Server server = new Server(1234);
        server.serveClients();

        int port = 10512;
        String ip = "6.tcp.ngrok.io";

        Client client = new Client("localhost", 1234);
        client.run();
        
    }
}