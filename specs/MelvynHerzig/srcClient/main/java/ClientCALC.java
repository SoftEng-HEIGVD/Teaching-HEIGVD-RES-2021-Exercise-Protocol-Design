
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientCALC
{
    public static void main(String[] args)
    {
        ClientCALC client = new ClientCALC();
        Scanner sc = new Scanner(System.in);
        int cmd;
        try
        {
            client.connect("localhost", 2001);
            while (client.connected)
            {
                client.displayMenu();
                cmd = sc.nextInt();

                if(cmd > client.nbCmd() || cmd < 0)
                {
                    continue;
                }
                else if(cmd == client.nbCmd())
                {
                    client.disconnect();
                }
                else
                {
                    double left, right;
                    System.out.println("Entrez deux double avec un espace entre");
                    left = sc.nextDouble();
                    right = sc.nextDouble();

                    client.parseResponse(client.sendOperation(cmd, left, right));
                }

            }
        }
        catch(Exception e)
        {
            LOG.log(Level.SEVERE, "{0}", e.getMessage());
        }
    }

    private static final Logger LOG = Logger.getLogger(ClientCALC.class.getName());

    private final String WELCOME_END = "END";

    private final String RESULT = "RESULT";
    private final String QUIT = "QUIT";

    private final String ERROR = "BAD_REQUEST";

    private final int UNKNOWN_OPERATION = 1;
    private final int MISSING_NUMBER    = 2;
    private final int OTHER_ERROR       = 3;

    private boolean connected;

    private Socket clientSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    private LinkedList<String> cmds = new LinkedList<>();


    public void connect(String server, int port) throws IOException {
        try
        {
            clientSocket = new Socket(server, port);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            connected = true;
            readOperations();

        }
        catch(IOException e)
        {
            LOG.log(Level.SEVERE, "Unable to connect to server: {0}", e.getMessage());
            cleanup();
            return;
        }
    }

    public int nbCmd()
    {
        return cmds.size();
    }

    public void displayMenu()
    {
        System.out.println("Options , type a number to proceed");
        int i;
        for(i = 0; i < cmds.size(); ++i)
        {
            System.out.println(i + ") " + cmds.get(i));
        }
        System.out.println(i + ") " + QUIT);
    }

    public void disconnect() throws IOException
    {
        if(isConnected())
        {

            LOG.log(Level.INFO, "client has request to be disconnect.");
            sendToServer(QUIT);
            connected = false;
            cleanup();
        }
    }

    public boolean isConnected()
    {
        return connected;
    }


    private void cleanup()
    {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }

        if (out != null) {
            out.close();
        }

        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // Lis les commandes possibles
    private void readOperations()
    {
        try
        {   // WELCOME skipped
            skipMessageServer();

            String message;
            message = in.readLine();
            while(! message.equals(WELCOME_END))
            {
                cmds.add(message);
                message = in.readLine();
            }
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    protected void sendToServer(String s)
    {
        out.println(s);
        out.flush();
    }

    public String sendOperation(int i, double left, double right)
    {
        String response = null;
        try
        {
            out.println(cmds.get(i) + " " + left + " " + right);
            out.flush();
            response = in.readLine();
        }
        catch(Exception e)
        {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return response;
    }

    public void parseResponse(String response)
    {
        if(response == null) return;

        String[] tokens = response.split(" ");

        switch(tokens[0])
        {
            case RESULT:
                System.out.println("Réponse: " + tokens[1]);
                break;
            case ERROR:
                System.out.println("Erreur");
                break;
        }
    }

    protected void skipMessageServer() throws IOException
    {
        in.readLine();
    }

}
