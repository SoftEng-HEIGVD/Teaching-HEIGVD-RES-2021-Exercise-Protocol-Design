package com.heigvd;

import com.heigvd.Client.CalculatorClient;
import com.heigvd.Server.CalculatorServer;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );
        int port = 2222;
        String ip = "192.168.1.107";

        CalculatorServer server = new CalculatorServer(port);
        server.startServer();

    }
}
