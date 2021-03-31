package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceptionistWorker implements Runnable {

    final static Logger LOG = Logger.getLogger(ReceptionistWorker.class.getName());
    private int port;

    public ReceptionistWorker(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        while (true) {
            LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
            try {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(60*1000); // 60 sec
                LOG.info("A new client has arrived. Starting a new thread and delegating work to a new servant...");
                new Thread(new ServantWorker(clientSocket)).start();
            } catch (IOException ex) {
                Logger.getLogger(ReceptionistWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
