package src;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A very simple example of TCP server. When the server starts, it binds a
 * server socket on any of the available network interfaces and on port 2205. It
 * then waits until one (only one!) client makes a connection request. When the
 * client arrives, the server does not even check if the client sends data. It
 * simply writes the current time, every second, during 15 seconds.
 *
 * To test the server, simply open a terminal, do a "telnet localhost 2205" and
 * see what you get back. Use Wireshark to have a look at the transmitted TCP
 * segments.
 *
 * @author Olivier Liechti
 */
public class  ServerThread {

    static final Logger LOG = Logger.getLogger(StreamingTimeServer.class.getName());

    private final int TEST_DURATION = 5000;
    private final int PAUSE_DURATION = 1000;
    private final int NUMBER_OF_ITERATIONS = TEST_DURATION / PAUSE_DURATION;
    private final int LISTEN_PORT = 3101;
    private String[] availableOperations = {"ADD", "MULT", "DIV", "END OPERATIONS"};
    private int[] nbreOfOperands = {2,2,2};

    public void serveClients() {
        LOG.info("Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionistWorker()).start();
    }



    private class ReceptionistWorker implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket;
            Socket clientSocket = null;
            BufferedReader reader = null;
            PrintWriter writer = null;
            LOG.log(Level.INFO, "Creating a server socket and binding it on any of the available network interfaces and on port {0}", new Object[]{Integer.toString(LISTEN_PORT)});

            try {
                serverSocket = new ServerSocket(LISTEN_PORT);
                logServerSocketAddress(serverSocket);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }

            while (true) {

                LOG.log(Level.INFO, "Waiting (blocking) for a connection request on {0} : {1}", new Object[]{serverSocket.getInetAddress(), Integer.toString(serverSocket.getLocalPort())});
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LOG.log(Level.INFO, "A client has arrived. We now have a client socket with following attributes:");
                logSocketAddress(clientSocket);

                LOG.log(Level.INFO, "Getting a Reader and a Writer connected to the client socket...");
                try {
                    try {
                        writer = new PrintWriter(clientSocket.getOutputStream());
                        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String str = "WELCOME \n";
                        for (int i = 0; i < availableOperations.length; ++i) {
                            String nbreOperands = "";
                            if (i < nbreOfOperands.length) {
                                nbreOperands = Integer.toString(nbreOfOperands[i]) + ' ';
                            }

                            str += "- " + availableOperations[i] + ' ' + nbreOperands + '\n';
                        }
                        writer.println(str + "FIN");
                        writer.flush();
                        //Thread.sleep(PAUSE_DURATION);

                        //LOG.log(Level.INFO, "Starting my job... sending current time to the client for {0} ms", TEST_DURATION);
                        String msg = "Client : ";
                        System.out.println(msg);
                        while (true) {
                            msg = reader.readLine();
                            while (msg.isEmpty()) {
                                msg = reader.readLine();

                            }


                            System.out.println(msg);
                            String arg[] = msg.split(" ");
                            boolean error = false;
                            if (arg[0].equals("COMPUTE")) {
                                int calcul = 0;
                                if (arg[1].equals("ADD")) {
                                    calcul = Integer.parseInt(arg[2]) + Integer.parseInt(arg[3]);
                                } else if (arg[1].equals("SUB")) {
                                    calcul = Integer.parseInt(arg[2]) - Integer.parseInt(arg[3]);
                                } else if (arg[1].equals("DIV")) {
                                    calcul = Integer.parseInt(arg[2]) / Integer.parseInt(arg[3]);
                                } else if (arg[1].equals("MUL")) {
                                    calcul = Integer.parseInt(arg[2]) * Integer.parseInt(arg[3]);
                                } else {
                                    writer.println("ERROR \n");
                                    writer.flush();
                                    error = true;
                                }
                                if (!error) {
                                    writer.println("RESULT " + calcul + " \n");
                                }
                                writer.flush();

                            } else if (arg[0].equals("QUIT")) {
                                reader.close();
                                writer.close();
                                clientSocket.close();
                                LOG.log(Level.INFO, "Fin de la connexion avec le client", TEST_DURATION);
                                break;
                            } else {
                                writer.println("ERROR \n");
                                writer.flush();
                            }
                        }

                    } finally {
                        reader.close();
                        writer.close();
                        clientSocket.close();
                    }
}catch (IOException ex) {
    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
}


            }

        }
    }

    /**
     * This method does the entire processing.
     */
    private void start() throws Exception {
        LOG.info("Starting server...");
        ServerSocket serverSocket;
        Socket clientSocket;
        BufferedReader reader = null;
        PrintWriter writer = null;

        LOG.log(Level.INFO, "Creating a server socket and binding it on any of the available network interfaces and on port {0}", new Object[]{Integer.toString(LISTEN_PORT)});
        serverSocket = new ServerSocket(LISTEN_PORT);
        logServerSocketAddress(serverSocket);
        while (true) {

            LOG.log(Level.INFO, "Waiting (blocking) for a connection request on {0} : {1}", new Object[]{serverSocket.getInetAddress(), Integer.toString(serverSocket.getLocalPort())});
            clientSocket = serverSocket.accept();

            LOG.log(Level.INFO, "A client has arrived. We now have a client socket with following attributes:");
            logSocketAddress(clientSocket);

            LOG.log(Level.INFO, "Getting a Reader and a Writer connected to the client socket...");

            try{
                writer = new PrintWriter(clientSocket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String str = "WELCOME \n";
                for(int i = 0; i < availableOperations.length; ++i){
                    String nbreOperands = "";
                    if(i < nbreOfOperands.length){
                        nbreOperands = Integer.toString(nbreOfOperands[i]) + ' ';
                    }

                    str += "- " + availableOperations[i] + ' ' + nbreOperands + '\n';
                }
                writer.println(str + "FIN");
                writer.flush();
                //Thread.sleep(PAUSE_DURATION);

                //LOG.log(Level.INFO, "Starting my job... sending current time to the client for {0} ms", TEST_DURATION);
                String msg = "Client : ";
                System.out.println(msg);
                while(true){
                    msg = reader.readLine();
                    while(msg.isEmpty()){
                        msg = reader.readLine();

                    }
                    System.out.println(msg);
                    String arg[] = msg.split(" ");
                    boolean error = false;
                    if(arg[0].equals("COMPUTE")){
                        int calcul = 0;
                        if(arg[1].equals("ADD")){
                            calcul = Integer.parseInt(arg[2]) + Integer.parseInt(arg[3]);
                        } else if(arg[1].equals("SUB")){
                            calcul = Integer.parseInt(arg[2]) - Integer.parseInt(arg[3]);
                        }else if(arg[1].equals("DIV")){
                            calcul = Integer.parseInt(arg[2]) / Integer.parseInt(arg[3]);
                        }else if(arg[1].equals("MUL")){
                            calcul = Integer.parseInt(arg[2]) * Integer.parseInt(arg[3]);
                        }else{
                            writer.println("ERROR \n");
                            writer.flush();
                            error = true;
                        }
                        if(!error){
                            writer.println("RESULT " + calcul + " \n");
                        }
                        writer.flush();

                    }else if(arg[0].equals("QUIT")){
                        reader.close();
                        writer.close();
                        clientSocket.close();
                        LOG.log(Level.INFO, "Fin de la connexion avec le client", TEST_DURATION);
                        break;
                    }else{
                        writer.println("ERROR \n");
                        writer.flush();
                    }
                }

            }finally{
                reader.close();
                writer.close();
                clientSocket.close();
            }

        }
    }

    /**
     * A utility method to print server socket information
     *
     * @param serverSocket the socket that we want to log
     */
    private void logServerSocketAddress(ServerSocket serverSocket) {
        LOG.log(Level.INFO, "       Local IP address: {0}", new Object[]{serverSocket.getLocalSocketAddress()});
        LOG.log(Level.INFO, "             Local port: {0}", new Object[]{Integer.toString(serverSocket.getLocalPort())});
        LOG.log(Level.INFO, "               is bound: {0}", new Object[]{serverSocket.isBound()});
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

        ServerThread server = new  ServerThread();
        server.serveClients();
    }

}
