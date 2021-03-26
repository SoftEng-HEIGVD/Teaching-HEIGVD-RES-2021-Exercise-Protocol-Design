package com.heigvd.Client;
import com.heigvd.Protocol.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorClient implements ICalculatorClient{
    private static final Logger LOG = Logger.getLogger(CalculatorClient.class.getName());

    Socket socketClient = null;
    BufferedReader reader = null;
    PrintWriter writer = null;
    boolean clientConnected = false;

    @Override
    public void connect(String server, int port) throws IOException {
        try {
            this.socketClient = new Socket(server, port);
            this.reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            this.writer = new PrintWriter(socketClient.getOutputStream());
            if (!isConnected())
                throw new IOException("error");
            LOG.log(Level.INFO,  "connected to " + server + ':' + port + " ... ");
        }catch (IOException e){
            LOG.log(Level.SEVERE, "An error occured during connection to socket : {0}", e.getMessage());
            cleanConnection();
            throw e;
        }
    }

    protected void cleanConnection() throws IOException{
        try{
            socketClient.close();
            reader.close();
            writer.close();
            clientConnected = false;
        }catch (IOException e){
            throw e;
        }
    }


    @Override
    public void disconnect() throws IOException {
        try{
            writer.println(CalculatorProtocol.CMD_QUIT);
            writer.flush();
            LOG.log(Level.INFO, "disconnected ... ");
            cleanConnection();
        }catch (IOException e) {
            LOG.log(Level.INFO, "An error occured during disconnection : {0}", e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean isConnected() {
        try{
            //Recupère le Welcome
            reader.readLine();
            //ADD LOG
            clientConnected = true;
            return true;
        }catch (IOException e){
            return false;
        }
    }

    @Override
    public void calculate(String calcul) throws IOException {
        writer.println(calcul + CalculatorProtocol.CMD_END);
        writer.flush();
        reader.readLine();
        LOG.log(Level.INFO, "Calcul-... ");
    }
}
