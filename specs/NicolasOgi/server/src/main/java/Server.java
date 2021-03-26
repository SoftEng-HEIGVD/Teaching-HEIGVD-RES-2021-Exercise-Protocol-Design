package main.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// Class allowing to instantiate a server able to calculate the result with integers
public class Server {

    private final static String WELCOME_MESSAGE = "WELCOME \r\n" +
            "- AVAILABLE OPERATIONS \r\n" +
            "- ADD 2 \r\n" +
            "- MULT 2 \r\n" +
            "- DIV 2 \r\n" +
            "- END OPERATIONS \r\n";

    public static void main(String[] args) throws IOException {

        Server server = new Server();
        while (true) {
            server.waitForIncomingClient();
        }
    }

    /***
     * Method of waiting for a client to connect and sending it the result of the requested calculations
     * @throws IOException
     */
    public void waitForIncomingClient() throws IOException {

        // The server is waiting for a client to connect to port 3101
        ServerSocket receptionistSocket = new ServerSocket(3101);

        while(true) {
            Socket clientSocket = receptionistSocket.accept();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // Sending the welcome message to the client
            out.println(WELCOME_MESSAGE);
            out.flush();

            String clientMessage = "";
            BufferedReader in = null;

            // As long as the client does not send the "QUIT" message
            while (!clientMessage.contains("QUIT")) {

                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    clientMessage = in.readLine();
                    String[] tokens = clientMessage.split(" ");

                    String command = tokens[0];
                    String operation = tokens[1];
                    int operand1 = Integer.parseInt(tokens[2]);
                    int operand2 = Integer.parseInt(tokens[3]);
                    int result = 0;
                    boolean error = false;

                    if (tokens.length != 4)
                        throw new IllegalArgumentException("TOO MUCH ARGUMENTS");

                    if (command.equals("COMPUTE")) {

                        switch (operation) {
                            case "ADD":
                                result = operand1 + operand2;
                                break;
                            case "MULT":
                                result = operand1 * operand2;
                                break;
                            case "DIV":
                                result = operand1 / operand2;
                                break;
                            default:
                                out.println("ERROR 300 UNKNOWN OPERATION \r\n");
                                error = true;
                                break;
                        }

                        if (!error)
                            out.println("RESULT " + result + "\r\n");

                        out.flush();
                    }
                    else {
                        out.println("ERROR 900 UNKNOWN COMMAND \r\n");
                        out.flush();
                    }
                }
                catch(Exception e) { // Catch toutes les exceptions pour l'instant
                    out.println("ERROR : " + e.getMessage() + "\r\n");
                    out.flush();

                    in.close();
                    out.close();
                    clientSocket.close();
                }
            }
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}