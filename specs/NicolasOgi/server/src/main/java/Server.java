package main.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static String WELCOME_MESSAGE = "WELCOME \r\n" +
            "- AVAILABLE OPERATIONS \r\n" +
            "- ADD <N1> <N2> \r\n" +
            "- MULT <N1> <N2> \r\n" +
            "- DIV <N1> <N2> \r\n" +
            "- END OPERATIONS \r\n";

    public static void main(String[] args) throws IOException {

        Server server = new Server();
        while (true) {
            server.waitForIncomingClient();
        }

    }

    public void waitForIncomingClient() throws IOException {

        // Le serveur attend qu'un client se connecte sur le port 3101
        ServerSocket receptionistSocket = new ServerSocket(3101);
        Socket clientSocket = receptionistSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        // Envoi du message de bienvenue au client
        out.println(WELCOME_MESSAGE);
        out.flush();

        String clientMessage = in.readLine();

        String[] tokens = clientMessage.split(" ");

        try {

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
        catch(Exception e) { // Catch toutes les exceptions pour l'instant...
            out.println("ERROR : " + e.getMessage() + "\r\n");
            out.flush();
        }
        finally {
            in.close();
            out.close();
            clientSocket.close();
            receptionistSocket.close();
        }
    }
}