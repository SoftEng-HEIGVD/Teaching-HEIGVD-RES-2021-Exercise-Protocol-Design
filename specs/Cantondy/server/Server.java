/**
 * Authors : Dylan Canton, Alessandro Parrino
 * Date 22.03.2021
 * Exercice Protocol-Design
 * File : Server.java
 */

package server;

import java.io.*;
import java.net.*;

import protocol.Protocol;

public class Server {
    public void waitForIncomingClient() throws IOException {

        // Initialize Server socket and waiting for client
        ServerSocket receptionistSocket = new ServerSocket(Protocol.PORT);
        Socket workerSocket = receptionistSocket.accept();

        // Return current address IP
        SocketAddress socketAddress = workerSocket.getRemoteSocketAddress();

        // Check that it is an internet protocol socket.
        if (socketAddress instanceof InetSocketAddress) {
            InetAddress inetAddress = ((InetSocketAddress)socketAddress).getAddress();
            if (inetAddress instanceof Inet4Address) //Check that the address is IPv4
                System.out.println("Im listening at IPv4: " + inetAddress + " Port: " + Protocol.PORT);
            else if (inetAddress instanceof Inet6Address) //Check that the address is IPv6
                System.out.println("Im listening at IPv6: " + inetAddress + " Port: " + Protocol.PORT);
            else
                System.err.println("Not an IP address.");
        } else {
            System.err.println("Not an internet protocol socket.");
        }

        // Initiliaze input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(workerSocket.getInputStream()));
        PrintWriter    writer = new PrintWriter(new OutputStreamWriter(workerSocket.getOutputStream()));

        //Write welcome message
        writer.println("Hello, enter your operation :");
        // Free the writing flow
        writer.flush();

        //Wait for a message from the client
        String message = reader.readLine();

        while (message != null) {

            // Split string in substring,
            // the delimiter used is blankspace
            String[] tokens = message.split(" ");

            // Check if the command is "QUIT"
            // "QUIT" command stops the server
            if(tokens.length == 1 && tokens[0].equals(Protocol.QUIT)){
                // Close reader and writer
                reader.close();
                writer.close();
                // Close ServerSocket
                workerSocket.close();
                receptionistSocket.close();
                return;
            }
            else if(tokens.length == 3) {// Check that the command is formed by  3 elements

                //Get each element
                String operation = tokens[0];

                //Try to convert string to int and catch exception
                try{
                    int operand1 = Integer.parseInt(tokens[1]);
                    int operand2 = Integer.parseInt(tokens[2]);

                    // Perform the chosen operation and write the result
                    // If the operation is invalid, an error message returns
                    switch (operation) {
                        case Protocol.ADD : writer.println("Result : " + (operand1 + operand2));break;
                        case Protocol.SUB : writer.println("Result : " + (operand1 - operand2));break;
                        case Protocol.MULT: writer.println("Result : " + (operand1 * operand2));break;
                        default: writer.println("ERROR : Invalid command, try again!");
                    }
                }
                catch(NumberFormatException e){
                    writer.println("ERROR : Invalid command, try again!");
                }
            }else {
                writer.println("ERROR : Invalid command, try again!");
            }
            writer.flush();// Free the writing flow
            message = reader.readLine();// Wait for a new message
        }
        // Close reader and writer
        reader.close();
        writer.close();
        // Close ServerSocket
        workerSocket.close();
        receptionistSocket.close();
    }

    public static void main( String[] args ) throws IOException
    {   // Start server
        Server server = new Server();
        while (true) {
            server.waitForIncomingClient();
        }
    }
}