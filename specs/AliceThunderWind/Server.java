import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    static final Logger LOG = Logger.getLogger(Client.class.getName());
    private final int PORT;

    public Server(int port) {
        this.PORT = port;
    }

    private void run() {
        new Thread(new ReceptionistWorker()).start();
    }

    private class ReceptionistWorker implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket;

            try {
                serverSocket = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new ServantWorker(clientSocket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private class ServantWorker implements Runnable{
            Socket clientSocket;
            BufferedReader in = null;
            PrintWriter out = null;

            public ServantWorker(Socket clientSocket) {
                try {
                    this.clientSocket = clientSocket;
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream());
                    LOG.info("Starting new connexion with client");
                } catch (IOException e) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                }
            }

            @Override
            public void run() {
                String line;
                boolean shouldRun = true;

                out.println("WELCOME");
                out.flush();

                try {
                    while((shouldRun) && (line = in.readLine()) != null) {
                        LOG.info("Getting request from client : \"" + line + "\"");
                        String[] argument = line.split(" ");
                        String message;
                        if (argument.length == 1 && argument[0].equals("BYE")) {
                            shouldRun = false;
                            continue;
                        }
                        if((argument.length != 3)) {
                            out.println("ERROR 400 SYNTAX ERROR");
                            out.flush();
                            continue;
                        }

                        switch(argument[0]) {
                            case "ADD":
                                message = "RESULT " + (Integer.parseInt(argument[1]) + Integer.parseInt(argument[2]));
                                break;
                            case "SUB":
                                message = "RESULT " + (Integer.parseInt(argument[1]) - Integer.parseInt(argument[2]));
                                break;
                            case "MUL":
                                message = "RESULT " + (Integer.parseInt(argument[1]) * Integer.parseInt(argument[2]));
                                break;
                            case "DIV":
                                message = (Integer.parseInt(argument[2]) == 0) ? "ERROR 301 DIV BY ZERO" :
                                        "RESULT " + (Integer.parseInt(argument[1]) / Integer.parseInt(argument[2]));
                                break;
                            default:
                                message = "ERROR 300 UNKNOWN OPERATION";
                                break;
                        }
                        out.println(message);
                        out.flush();
                    }

                } catch (Exception e) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                } finally {
                    LOG.info("Closing connexion with client");
                    try {
                        if(clientSocket != null) {
                            clientSocket.close();
                        }
                    } catch (IOException e) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                    }
                    if (out != null) {
                        out.close();
                    }

                    try {
                        if(in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(9999);
        server.run();
    }
}
