/**
 * Authors : Dylan Canton, Alessandro Parrino
 * Date 20.03.2021
 * Exercice Protocol-Design
 * File : Client.java
 */

package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    //Logger object for log indications
    final static Logger logger = Logger.getLogger(Client.class.getName());

    Socket clientSocket   = null;   //Client socket
    PrintWriter writer    = null;   //Writer
    BufferedReader reader = null;   //Reader

    //Used by responseListener to stop the thread
    boolean connected = false;

    //Constructor
    public Client(){}

    /**
     * This class provide a run method who can execute on a separated thread
     * for listening server response. This allows client to be always listening
     * for response. s
     */
    class responseListener implements Runnable {
        public void run() {
            String response;

            //Listen on the reader and show response when there is one
            try {
                while ((connected && (response = reader.readLine()) != null)) {
                    logger.log(Level.INFO, response);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Listening client error", e.getMessage());
                connected = false;
            } finally {
                closeConnection();
            }
        }
    }

    /**
     * Initialize client connection
     * @param serverAddress
     * @param serverPort
     */
    public void connect(String serverAddress, int serverPort){
        try{
            //Initialize connection to the server
            clientSocket = new Socket(serverAddress, serverPort);

            //Write data to the server
            writer = new PrintWriter(clientSocket.getOutputStream());

            //Read data from the server
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //Connecion is on (for responseListener class)
            connected = true;
        }
        catch(UnknownHostException e){
            logger.log(Level.SEVERE, "Unable to connect to server (server not found)", e.getMessage());
        }
        catch(IOException e){
            logger.log(Level.SEVERE, "Unable to connect to server (I/O error)", e.getMessage());
        }

        //Create a new thread for listening server response
        new Thread(new responseListener()).start();
    }

    /**
     * End connection by closing writer, reader and socket
     */
    public void closeConnection(){
        try{
            //Close writer and reader
            writer.close();
            reader.close();

            //Close connection with the server
            clientSocket.close();
        }
        catch(IOException e){
            logger.log(Level.SEVERE, "Unable to close connection", e.getMessage());
        }
    }

    /**
     *  Disconnect client by ending connection with closeConnection()
     */
    public void disconnect(){
        logger.log(Level.INFO, "Client has requested to be disconnected");

        //Used by the listening thread to end
        connected = false;

        //Close connection
        closeConnection();
    }
}