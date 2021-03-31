package com.company;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientCalc {
    //private int port;
    private static int BUFFER_SIZE = 1000;
    //private static String[] OPERATIONS = new String[] {"ADD", "SOUS"};

    private Socket clientSocket;
    private OutputStream os;
    private InputStream is;
    private ByteArrayOutputStream responseBuffer;

    public ClientCalc(String host, int port) {
        if (port <= 0)
            throw new IllegalArgumentException("Port must be > 0");

        responseBuffer = new ByteArrayOutputStream();
        initConnection(host, port);
    }


    public void sendCalcRequest(String request) {
        try {
            os.write(request.getBytes());

            byte[] buffer = new byte[BUFFER_SIZE];
            /*int newBytes;
            while ((newBytes = is.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, newBytes);
            }*/

            responseBuffer.writeBytes(is.readAllBytes());

            checkResponse(responseBuffer.toString().split(" "));
            responseBuffer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initConnection(String host, int port) {
        try {
           clientSocket = new Socket(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            os = clientSocket.getOutputStream();
            is = clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkResponse(String[] response) {
        switch (response[0]) {
            case "ERROR":
                System.out.println("Erreur");
                break;
            case "RESULT":
                System.out.println("Résultat : " + response[1]);
                break;
            default:
                System.out.println("Réponse inconnue du serveur: ");
                break;
        }
    }
}
