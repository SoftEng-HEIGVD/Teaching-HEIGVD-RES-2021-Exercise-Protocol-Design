package com.heigvd.Client;

import java.io.IOException;
import java.util.List;

public interface ICalculatorClient {
    /**
     * Establishes a connection with the server, given its IP address or DNS name
     * and its port.
     *
     * @param server the IP address or DNS name of the servr
     * @param port the TCP port on which the server is listening
     * @throws java.io.IOException
     */
    public void connect(String server, int port) throws IOException;

    /**
     * Disconnects from the server by issuing the 'BYE' command.
     *
     * @throws IOException
     */
    public void disconnect() throws IOException;

    /**
     * Checks if the client is connected with the server
     *
     * @return true if the client is connected with the server
     */
    public boolean isConnected();

    /**
     *
     *
     * @return
     * @throws IOException
     */
    public void calculate(String calcul) throws IOException;
}
