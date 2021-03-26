package com.heigvd.Server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.heigvd.Protocol.*;
import com.heigvd.Server.ServantWorker;

public class CalculatorServer {
    /*
     * The TCP port where client connection requests are accepted. -1 indicates that
     * we want to use an ephemeral port number, assigned by the OS
     */
    private int listenPort = -1;

    /*
     * The server socket, used to accept client connection requests
     */
    private ServerSocket serverSocket;

    /*
     * A flag that indicates whether the server should continue to run (or whether
     * a shutdown is in progress)
     */
    private boolean shouldRun = false;

    /*
     * The server maintains a list of client workers, so that they can be notified
     * when the server shuts down
     */
    List<ServantWorker> servantWorkers = new CopyOnWriteArrayList<>();

    final static Logger LOG = Logger.getLogger(CalculatorServer.class.getName());

    public CalculatorServer(int listenPort) {
        this.listenPort = listenPort;
    }

    public void startServer() throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(listenPort);
            shouldRun = true;
        }catch(IOException e) {
            LOG.log(Level.SEVERE, null, e);
            return;
        }

        while (shouldRun){
            LOG.log(Level.INFO, "Waiting for a new client");
            try{
                Socket clientSocket = serverSocket.accept();
                LOG.info("A new client has arrived...");
                ServantWorker worker = new ServantWorker(clientSocket, this);
                servantWorkers.add(worker);
                new Thread(worker).start();
            }catch (IOException e){
                LOG.log(Level.SEVERE, e.getMessage(), e);
                shouldRun = false;
            }
        }

    }

    /**
     * Requests a server shutdown. This will close the server socket and notify
     * all client workers.
     *
     * @throws IOException
     */
    public void stopServer() throws IOException {
        shouldRun = false;
        serverSocket.close();
        for (ServantWorker worker : servantWorkers) {
            worker.notifyWorkerShutdown();
        }
    }

    public void notifyWorkerDone(ServantWorker worker){
        servantWorkers.remove(worker);
    }
}
