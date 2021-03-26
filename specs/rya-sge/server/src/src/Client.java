package src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    static final Logger LOG = Logger.getLogger(src.StreamingTimeServer.class.getName());

    private final int TEST_DURATION = 5000;
    private final int PAUSE_DURATION = 1000;
    private final int NUMBER_OF_ITERATIONS = TEST_DURATION / PAUSE_DURATION;
    private final int LISTEN_PORT = 2205;

    /**
     * This method does the entire processing.
     */
    private void start() throws Exception {
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter writer;
        /*
         * les informations du serveur ( port et adresse IP ou nom d'hote
         * 127.0.0.1 est l'adresse local de la machine
         */
        clientSocket = new Socket("0.0.0.0", 3101);
        LOG.log(Level.INFO, "Creating a client socket and binding it on any of the available network interfaces and on port {0}", new Object[]{Integer.toString(LISTEN_PORT)});
        logSocketAddress(clientSocket);
        writer = new PrintWriter(clientSocket.getOutputStream());

        //flux pour recevoir
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String msg = "Serveur : ";
        System.out.println(msg);
        while ((msg = in.readLine()) != null) {
            System.out.println(msg);
            if (msg.equals("FIN")) {
                break;
            }
        }
        //Compute
        writer.println("COMPUTE ADD 1 2 \n");
        writer.flush();
        msg = in.readLine();
        System.out.println(msg);

        //Error
        writer.println("LOL \n");
        writer.flush();

        msg = in.readLine();
        while(msg.isEmpty()){
            msg = in.readLine();
        }
        System.out.println(msg);
        //Quitter
        writer.println("QUIT \n");
        writer.flush();

        in.close();
        writer.close();
        clientSocket.close();
    }

    /**
     * A utility method to print socket information
     *
     * @param clientSocket the socket that we want to log
     */
    private void logSocketAddress(Socket clientSocket) {
        LOG.log(Level.INFO, "       Local IP address: {0}", new Object[]{clientSocket.getLocalAddress()});
        LOG.log(Level.INFO, "             Local port: {0}", new Object[]{Integer.toString(clientSocket.getLocalPort())});
        LOG.log(Level.INFO, "  Remote Socket address: {0}", new Object[]{clientSocket.getRemoteSocketAddress()});
        LOG.log(Level.INFO, "            Remote port: {0}", new Object[]{Integer.toString(clientSocket.getPort())});
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        Client client = new Client();
        client.start();
    }
}
