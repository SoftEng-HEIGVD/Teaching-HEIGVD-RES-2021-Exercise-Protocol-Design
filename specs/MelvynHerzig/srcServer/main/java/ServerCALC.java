import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerCALC
{
    public static void main(String[] args) {
        ServerCALC server = new ServerCALC();
        server.serveClients();
    }

    private final String WELCOME_START = "WELCOME";
    private final String WELCOME_END = "END";

    private final String ADD  = "ADD";
    private final String SUB  = "SUB";
    private final String MULT = "MULT";

    private final String RESULT = "RESULT";
    private final String QUIT = "QUIT";

    private final String ERROR = "BAD_REQUEST";

    private final int UNKNOWN_OPERATION = 1;
    private final int MISSING_NUMBER    = 2;
    private final int OTHER_ERROR       = 3;

    final static Logger LOG = Logger.getLogger(ServerCALC.class.getName());

    /*
     * Port à utiliser.
     */
    private final int listenPort = 2001;


    public void serveClients()
    {
        LOG.info("Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionnistCALC()).start();
    }

    /**
     * Serveur. Récepsionniste
     */
    private class ReceptionnistCALC implements Runnable
    {

        @Override
        public void run()
        {
            ServerSocket serverSocket;

            try
            {
                serverSocket = new ServerSocket(listenPort);
            }
            catch (IOException e)
            {
                LOG.log(Level.SEVERE, null, e);
                return;
            }
            while(true)
            {
                LOG.log(Level.INFO, "Waiting for a new client");

                try
                {
                    Socket clientSocket = serverSocket.accept();
                    LOG.info("A new client has arrived. Starting a new thread and delegating work to a new servant...");
                    new Thread(new WorkerCALC(clientSocket)).start();
                } catch (IOException ex)
                {
                    Logger.getLogger(ServerCALC.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }

        private class WorkerCALC implements Runnable
        {
            Socket clientSocket;
            BufferedReader in = null;
            PrintWriter out = null;

            public WorkerCALC(Socket clientSocket)
            {
                try {
                    this.clientSocket = clientSocket;
                    in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream());
                } catch (IOException ex)
                {
                    Logger.getLogger(ServerCALC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


            private boolean validOperand(String left, String right)
            {
                try
                {
                    double o1 = Double.parseDouble(left);
                    double o2 = Double.parseDouble(right);
                }
                catch(Exception e)
                {
                    out.println(ERROR + " " + MISSING_NUMBER);
                    out.flush();
                    return false;
                }

                return true;
            }

            private void addOp(String left, String right)
            {
                if(validOperand(left, right))
                {
                    out.println(RESULT + " " + (Double.parseDouble(left) + Double.parseDouble(right)));
                    out.flush();
                }
            }

            private void subOp(String left, String right)
            {
                if(validOperand(left, right))
                {
                    out.println(RESULT + " " + (Double.parseDouble(left) - Double.parseDouble(right)));
                    out.flush();
                }
            }

            private void multOp(String left, String right)
            {
                if(validOperand(left, right))
                {
                    out.println(RESULT + " " + (Double.parseDouble(left) * Double.parseDouble(right)));
                    out.flush();
                }
            }

            @Override
            public void run()
            {
                String line;
                boolean shouldRun = true;

                out.println(WELCOME_START);
                out.println(ADD);
                out.println(MULT);
                out.println(SUB);
                out.println(WELCOME_END);
                out.flush();

                try
                {
                    LOG.info("READING REQUEST");
                    while ((shouldRun) && (line = in.readLine()) != null)
                    {
                        String[] tokens = line.split(" ");

                        if(tokens.length == 0 || tokens.length > 3)
                        {
                            out.println(ERROR + " " + OTHER_ERROR);
                            out.flush();
                        }
                        else
                        {
                            String left, right;
                            if(tokens.length != 3)
                            {
                                left = null;
                                right = null;
                            }
                            else
                            {
                                left = tokens[1];
                                right = tokens[2];
                            }

                            switch(tokens[0])
                            {
                                case QUIT:
                                    shouldRun = false;
                                    break;
                                case ADD :
                                    addOp(left, right);
                                    break;
                                case SUB :
                                    subOp(left, right);
                                    break;
                                case MULT:
                                    multOp(left, right);
                                    break;
                                default:
                                    out.println(ERROR + " " + UNKNOWN_OPERATION);
                                    out.flush();
                                    break;

                            }
                        }
                    }

                    LOG.info("Cleaning up resources...");
                    clientSocket.close();
                    in.close();
                    out.close();

                }
                catch (IOException ex)
                {
                    if (in != null)
                    {
                        try
                        {
                            in.close();
                        } catch (IOException ex1)
                        {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    if (out != null)
                    {
                        out.close();
                    }
                    if (clientSocket != null)
                    {
                        try
                        {
                            clientSocket.close();
                        } catch (IOException ex1)
                        {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
    }
}

