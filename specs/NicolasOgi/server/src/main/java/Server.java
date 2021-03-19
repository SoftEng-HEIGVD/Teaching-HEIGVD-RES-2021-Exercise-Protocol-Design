package main.java;

import java.io.*;
import java.net.*;

public class Server {

    private final static String WELCOME_MESSAGE = "WELCOME \r\n" +
                                                  "- AVAILABLE OPERATIONS \r\n" +
                                                  "- ADD <N1> <N2> \r\n" +
                                                  "- MULT <N1> <N2> \r\n" +
                                                  "- DIV <N1> <N2> \r\n" +
                                                  "- END OPERATIONS \r\n";


    public void waitForIncomingClient() throws IOException {

        // Le serveur attend qu'un client se connecte sur le port 3101
        ServerSocket receptionistSocket = new ServerSocket(3101);
        Socket clientSocket = receptionistSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        // Envoie du message de bienvenue au client
        out.println(WELCOME_MESSAGE);
        out.flush();

        String clientMessage = in.readLine();
        while (clientMessage != null) {
            String[] tokens = clientMessage.split(" ");
            String command = tokens[0];
            String operation = tokens[1];
            int operand1 = Integer.parseInt(tokens[2]);
            int operand2 = Integer.parseInt(tokens[3]);
            int result;

            if (command.equals("COMPUTE")) {

                if (operation.equals("ADD")) {
                    result = operand1 + operand2;
                }
                else if (operation.equals("MULT")) {
                    result = operand1 * operand2;
                }
                else if (operation.equals("DIV")) {
                    result = operand1 / operand2;
                }
                else {
                    out.println("ERROR 300 UNKNOWN OPERATION \r\n");
                    break;
                }
                out.println("RESULT " + result + "\r\n");
                out.flush();
            }
        }
        in.close();
        out.close();
        clientSocket.close();
        receptionistSocket.close();

    }

    public static void main(String[] args) throws IOException {

        Server server = new Server();
        while (true) {
            server.waitForIncomingClient();
        }

    }
}
