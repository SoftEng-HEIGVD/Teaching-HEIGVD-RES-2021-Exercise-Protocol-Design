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

    final static Logger LOG = Logger.getLogger(CalculatorServer.class.getName());

    public CalculatorServer(int listenPort) {
        this.listenPort = listenPort;
    }

    public void startServer() throws IOException {}

    /**
     * Requests a server shutdown. This will close the server socket and notify
     * all client workers.
     *
     * @throws IOException
     */
    public void stopServer() throws IOException {

    }
}
