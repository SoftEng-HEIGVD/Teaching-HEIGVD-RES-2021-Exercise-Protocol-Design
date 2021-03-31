package server;

import java.util.logging.Logger;

public class TcpServer {
    final static Logger LOG = Logger.getLogger(TcpServer.class.getName());

    public void serveClients(int port) {
        LOG.info("Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionistWorker(port)).start();
    }
}
