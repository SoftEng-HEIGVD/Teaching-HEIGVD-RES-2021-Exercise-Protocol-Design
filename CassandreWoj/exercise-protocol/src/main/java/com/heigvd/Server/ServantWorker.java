package com.heigvd.Server;
import com.heigvd.Protocol.*;

import com.heigvd.Protocol.CalculatorProtocol;
import jdk.jfr.FlightRecorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServantWorker implements Runnable{
    Socket clientSocket;
    CalculatorServer server;
    final static Logger LOG = Logger.getLogger(ServantWorker.class.getName());

    /*
     * A flag that indicates whether the server should continue to run (or whether
     * a shutdown is in progress)
     */
    private boolean shouldRun = false;

    public ServantWorker( Socket clientSocket, CalculatorServer server ) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new PrintWriter(clientSocket.getOutputStream());
        shouldRun = true;
    }

    BufferedReader reader = null;
    PrintWriter writer = null;


    public String calculate(String operation, int op1, int op2) throws IOException {
        String result = CalculatorProtocol.CMD_RESULT + " " ;
        switch(operation){
            case CalculatorProtocol.CMD_MULT:
                result += Integer.toString(op1 * op2);
                break;
            case CalculatorProtocol.CMD_ADD:
                result += Integer.toString(op1 + op2);
                break;
            default:
                result = CalculatorProtocol.CMD_ERROR_BAD;
        }
        return result;
    }






    public void notifyWorkerShutdown() {
        try {
            reader.close();
            writer.close();

        } catch (IOException ex) {
            LOG.log(Level.INFO, "Exception while closing input or output stream on the server: {0}", ex.getMessage());
        }

        try {
            clientSocket.close();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Exception while closing socket on the server: {0}", ex.getMessage());
        }
        server.notifyWorkerDone(this);
    }

    @Override
    public void run() {
        String blabla = null;
        String[] command = null;
        writer.println(CalculatorProtocol.RESPONSE_START);
        writer.flush();
        writer.println(CalculatorProtocol.RESPONSE_START2);
        writer.flush();
        try {
            while(shouldRun){
                blabla = reader.readLine();
                command = blabla.split(" ");
                if(command[0].equals(CalculatorProtocol.CMD_CALC))

                if(command[0].equals(CalculatorProtocol.))

                if(blabla.equals(CalculatorProtocol.CMD_QUIT)){
                    shouldRun = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        notifyWorkerShutdown();
        server.notifyWorkerDone(this);

    }
}
