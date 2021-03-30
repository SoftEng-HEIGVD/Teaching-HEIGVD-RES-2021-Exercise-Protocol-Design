package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Server {

    private int port;

    public Server(int p) {
        port = p;
    }

    public void serveClients() {
        new Thread(new ReceptionistWorker()).start();
    }

    private class ReceptionistWorker implements Runnable {

        @Override
        public void run() {
            System.out.println("Run");
            ServerSocket serverSocket;

            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                return;
            }

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connecté...");
                    new Thread(new ServantWorker(clientSocket)).start();
                } catch (IOException ex) {
                }
            }

        }

        private class ServantWorker implements Runnable {

            Socket clientSocket;
            BufferedReader in = null;
            PrintWriter out = null;
            OutputStream os = null;

            public ServantWorker(Socket clientSocket) {
                try {
                    this.clientSocket = clientSocket;
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    os = clientSocket.getOutputStream();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);

                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }

            @Override
            public void run() {
                String line;
                boolean shouldRun = true;

                try {
                    while ((shouldRun) && (line = in.readLine()) != null) {
                        String res = "";
                        int op1 = 0, op2 = 0;
                        String[] s = line.split("\\s+");
                        boolean error = false;
                        if(s.length != 3){
                            res = "ERR";
                            error = true;
                        }else{
                            op1 = Integer.valueOf(s[1]);
                            op2 = Integer.valueOf(s[2]);

                        }

                        line = line.toLowerCase(Locale.ROOT);
                        
                        if (!error && s[0].equalsIgnoreCase("ciao")) {
                            shouldRun = false;
                        }else if(!error && s[0].equalsIgnoreCase("add")){
                            res = String.valueOf(op1 + op2);


                        }else if(!error && s[0].equalsIgnoreCase("sub")){
                            res = String.valueOf(op1 - op2);

                        }else if(!error && s[0].equalsIgnoreCase("mult")){
                            res = String.valueOf(op1 * op2);

                        }else{
                            res = "ERR";
                        }
                        System.out.println("Message reçu    :" + line);
                        System.out.println("Message renvoyé : " + res);
                        out.println("RES " + res);
                        out.flush();
                    }

                    in.close();
                    out.close();

                    clientSocket.close();

                } catch (IOException ex) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex1) {
                            System.out.println(ex1);
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException ex1) {
                            System.out.println(ex1);
                        }
                    }
                }
            }
        }
    }

}
