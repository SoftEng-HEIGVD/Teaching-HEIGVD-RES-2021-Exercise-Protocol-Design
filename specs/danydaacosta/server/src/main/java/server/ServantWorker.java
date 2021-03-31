package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServantWorker implements Runnable {
    final static Logger LOG = Logger.getLogger(ServantWorker.class.getName());

    Socket clientSocket;
    BufferedReader in = null;
    PrintWriter out = null;

    public ServantWorker(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)); // PrintWriter fait du buffering
        } catch (IOException ex) {
            Logger.getLogger(ServantWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String line;

        try {
            try {
                while ((line = in.readLine()) != null) {
                    if (line.equalsIgnoreCase("exit"))
                        break;

                    String[] input = line.split("\\s+");
                    try {
                        if(input.length < 3)
                            throw new Exception();

                        String operation = input[0].toLowerCase();
                        input[0] = "0";

                        double[] operands = Arrays.stream(input).mapToDouble(Double::parseDouble).toArray();
                        double res = 0;

                        switch (operation) {
                            case "add":
                                for(int i = 1; i < operands.length; i++)
                                    res += operands[i];
                                break;
                            case "sous":
                                res = operands[1] - operands[2];
                                for(int i = 3; i < operands.length; i++)
                                    res -= operands[i];
                                break;
                            default:
                                throw new Exception("401");
                        }

                        out.print("RESULT ");
                        if(res == (long) res)
                            out.println(String.format("%d", (long)res));
                        else
                            out.println(String.format(Locale.FRENCH, "%s",res));
                    } catch (Exception ex) {
                        String error = ex.getMessage();
                        if(error == null)
                            out.println("ERROR 400 Syntax error");
                        else {
                            switch (error) {
                                case "401" :
                                    out.println("ERROR 401 Unsupported operation");
                                    break;
                                default:
                                    out.println("ERROR 400 Syntax error");
                                    break;
                            }
                        }
                    }

                    out.flush();
                }

            } catch (SocketTimeoutException ste) {
                LOG.info("A client connection timed out");
                out.println("You have been idle for too long, disconnecting...");
                out.flush();
            } catch (SocketException se) {
                LOG.info(se.toString());
            }

            LOG.info("Cleaning up resources...");
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException ex) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            if (out != null) {
                out.close();
            }
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
