import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public void connect(String host, int port) {
        boolean shouldRun;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdin = null;

        try {
            clientSocket = new Socket(host, port);
            shouldRun = true;

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            stdin = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Successfully connected to " + host + ":" + port);
            String line;

            // Read welcome message
            line = in.readLine();
            System.out.println("> " + line);

            String userInput;
            while (!clientSocket.isClosed() && shouldRun) {

                // Read userInput and write to serv
                if ((userInput = stdin.readLine()) != null) {
                    out.println(userInput);
                    out.flush();

                    // End comm
                    if (userInput.equals("QUIT")) {
                        shouldRun = false;
                    }
                }

                // Serv response
                if ((line = in.readLine()) != null) {
                    System.out.println("> " + line);

                    // Get full error response
                    if (line.equals("Error")) {

                        line = in.readLine();
                        System.out.println("> " + line);
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                stdin.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
            try {
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Give adresse IP + port");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        Client client = new Client();
        client.connect(args[0], port);

    }

}