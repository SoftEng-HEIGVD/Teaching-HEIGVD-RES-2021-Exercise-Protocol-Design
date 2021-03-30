package com.mycompany.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements a single-threaded TCP server. It is able to interact
 * with only one client at the time. If a client tries to connect when
 * the server is busy with another one, it will have to wait.
 *
 * @author Olivier Liechti
 */
public class SingleThreadedServer {

    final static Logger LOG = Logger.getLogger(SingleThreadedServer.class.getName());

    int port;

    /**
     * Constructor
     * @param port the port to listen on
     */
    public SingleThreadedServer(int port) {
        this.port = port;
    }

    /**
     * This method initiates the process. The server creates a socket and binds
     * it to the previously specified port. It then waits for clients in a infinite
     * loop. When a client arrives, the server will read its input line by line
     * and send back the data converted to uppercase. This will continue until
     * the client sends the "BYE" command.
     */
    public void serveClients() {
        ServerSocket serverSocket;
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        while (true) {
            try {

                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
                clientSocket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream());
                String line;
                boolean shouldRun = true;

                out.println("Welcome to the Single-Threaded Server.\nSend me text lines and conclude with the QUIT command.");
                out.flush();
                LOG.info("Reading until client sends quit or closes the connection...");
                while ( (shouldRun) && (line = in.readLine()) != null ) {
                    int nbArgs = line.split(" ").length;
                    if (line.equalsIgnoreCase("quit")) {
                        shouldRun = false;
                    } else if (nbArgs == 4 && line.split(" ")[0].equalsIgnoreCase("compute")){
                        boolean numOk = true;
                        double num1 =0, num2=0;
                        try {
                            num1 = Double.parseDouble(line.split(" ")[2]);
                            num2 = Double.parseDouble(line.split(" ")[3]);
                        } catch (NumberFormatException nfe) {
                            out.println("Error 400. Syntax error\r\n");
                            out.println("Must be number\n");
                            numOk = false;
                        }
                        if (numOk) {
                            double result;
                            switch (line.split(" ")[1]) {
                                case "ADD":
                                case "add":
                                    result = num1 + num2;
                                    out.println(num1 + "+" + num2 + "=" + result +"\r\n");
                                    break;
                                case "MULT":
                                case "mult":
                                    result = num1 * num2;
                                    out.println(num1 + "*" + num2 + "=" + result +"\r\n");
                                    break;
                            }
                        }
                    } else {
                        out.println("Error 400. Syntax error\r\n");
                    }
                    out.println("> " + line.toUpperCase());
                    out.flush();
                }

                LOG.info("Cleaning up resources...");
                clientSocket.close();
                in.close();
                out.close();

            } catch (IOException ex) {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ex1) {
                        LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                    }
                }
                if (out != null) {
                    out.close();
                }
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException ex1) {
                        LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                    }
                }
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}
