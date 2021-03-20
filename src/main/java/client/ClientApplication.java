/**
 * Authors : Dylan Canton, Alessandro Parrino
 * Date 20.03.2021
 * Exercice Protocol-Design
 * File : ClientApplication.java
 */

package client;

import protocol.Protocol;
import java.util.Scanner;

/**
 * Main function for client side
 */
public class ClientApplication {
    public static void main(String[] args) {

        //Need server address as argument
        if(args.length < 1){
            System.out.println("Error : need 1 argument (server address)");
            return;
        }

        String serverAddress = args[0];
        Client client = new Client();

        //Scan for console input
        Scanner scan = new Scanner(System.in);
        //Message from the client to server
        String request = "";
        //Determine when client wants to be disconnected
        Boolean quitConnection = false;

        //Connection to server
        client.connect(serverAddress, Protocol.PORT);

        //Client can send commands to server
        do{
            //Console input from client
            request = scan.nextLine();

            //If client wants to be disconnected
            if(request.compareTo(Protocol.QUIT) == 0){
                quitConnection = true;
            }

            //Send command to server and flush the writer for next command
            client.writer.println(request);
            client.writer.flush();
        }
        while(!quitConnection);

        //Disconnect client
        client.disconnect();
    }
}