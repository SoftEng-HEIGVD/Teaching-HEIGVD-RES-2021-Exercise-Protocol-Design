import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TCP_Client based on K-do's Protocol. By implementing it, we see some lack of information
 * in the Protocol definition. The serv can send multiple lines belonging to a same response,
 * but the protocol doesn't define a stop TAG, so the client doesn't know when to stop reading.
 * <p>
 * Please, remember this to design a Protocol:
 * - begin_token / end_token when serv sends multiple lines
 * - define an end of line syntax (\n, \r\n, \r, CRLF)
 *
 * @author Do Vale Lopes Miguel
 */
public class TCP_Client {

    /**
     * Connect client to a TCP server
     * @param servHostName hostname of the server to connect
     * @param servPort port of the server to connect
     */
    public void connect(String servHostName, int servPort) {

        boolean shouldRun;
        Socket clientSocket;
        try {
            clientSocket = new Socket(servHostName, servPort);
            shouldRun = true;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        BufferedReader in = null;
        PrintWriter out = null;
        BufferedReader stdin = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            stdin = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Successfully connected to " + servHostName + ":" + servPort);
            String line;

            // Read welcome message
            while ((line = in.readLine()) != null) {
                System.out.println("> " + line);
                if (line.equals("Welcome_end")) {
                    break;
                }
            }

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
                        do {
                            line = in.readLine();
                            System.out.println("> " + line);

                        } while (!line.equals("Error_end"));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                out.close();
            }

            if (stdin != null) {
                try {
                    stdin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * main TCP client
     * @param args <servHostname> <servPort>
     */
    public static void main(String[] args) {
        final String SYNTAX = "TCP_client <servHostname> <servPort>";

        if (args.length != 2) {
            System.err.println("Number of arguments is wrong\nCorrect syntax is: " + SYNTAX);
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        TCP_Client client = new TCP_Client();
        client.connect(args[0], port);
    }

}
