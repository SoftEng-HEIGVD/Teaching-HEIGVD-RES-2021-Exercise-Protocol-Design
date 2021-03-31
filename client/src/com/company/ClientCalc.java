package com.company;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientCalc {
    private Socket clientSocket;
    private PrintWriter outputWriter;
    private BufferedReader inputReader;

    public ClientCalc(String host, int port) {
        if (port <= 0)
            throw new IllegalArgumentException("Port must be > 0");

        //responseBuffer = new ByteArrayOutputStream();
        initConnection(host, port);
    }


    public void sendCalcRequest(String request) {
        try {
            outputWriter.println(request); // Envoie de la requête au serveur

            String response = inputReader.readLine(); // Lecture de la réponse du serveur
            checkResponse(response.split(" "));

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
            outputWriter = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
            inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            //is = clientSocket.getInputStream();
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
