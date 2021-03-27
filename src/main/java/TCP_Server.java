import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Single threaded CALC server based on K-do's protocol
 *
 * @author do vale lopes miguel
 */
public class TCP_Server {

    final int port;

    /**
     * Operations available in the server
     */
    final static ArrayList<String> OPERATIONS = new ArrayList<>() {
        {
            add("ADD");
            add("SUB");
            add("MULT");
            add("DIV");
        }
    };

    /**
     * log output
     */
    final static Logger LOG = Logger.getLogger(TCP_Server.class.getName());

    /**
     * Syntax of an operation
     */
    final static String SYNTAX = "[OPERATION] <int_1> <int_2>";

    /**
     * Syntax of the quit command
     */
    final static String QUIT = "QUIT";

    /**
     * Constructor
     *
     * @param port the port to listen on
     */
    public TCP_Server(int port) {
        this.port = port;
    }

    /**
     * Start the server
     */
    public void start() {
        ServerSocket serverSocket;
        BufferedReader in = null;
        PrintWriter out = null;
        Socket clientSocket = null;

        // Create, bind, listen server socket
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        // Accept and treat each client
        while (true) {
            try {
                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);

                // Accept client and get his socket
                clientSocket = serverSocket.accept();

                // Create Reader and Writer
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream());
                String line;

                // Display welcome message with available operations
                out.println("Welcome to K-do's CALC system.\nOperations currently available are:");
                for (String op : OPERATIONS) {
                    out.println("- " + op);
                }
                out.println("Syntax is: " + SYNTAX);
                out.println("use QUIT to exit");
                out.println("Welcome_end");
                out.flush();

                // Reading client
                LOG.info("Reading until client sends QUIT or operation");
                while ((line = in.readLine()) != null) {

                    String[] parts = line.split(" ");
                    double number1;
                    double number2;

                    if (parts[0].equals(QUIT)) {
                        LOG.info("Client requested to end communication");
                        out.println("Goodbye!");
                        out.flush();
                        break;
                    }

                    // Check number of arguments
                    if (parts.length != 3) {
                        LOG.info("Error in client request: number of arguments incorrect");
                        out.println("Error\nNumber of arguments is wrong\nSyntax is: " + SYNTAX + "\nError_end");
                        out.flush();
                        continue;
                    }

                    // Check operation
                    if (!OPERATIONS.contains(parts[0])) {
                        LOG.info("Error in client request: operation not recognised");
                        out.println("Error\nOperation is not recognised, please provide one of the following:");
                        for (String op : OPERATIONS) {
                            out.println("- " + op);
                        }
                        out.println("Error_end");
                        out.flush();
                        continue;
                    }

                    // Check that floats or integer are provided
                    try {
                        number1 = Double.parseDouble(parts[1]);
                        number2 = Double.parseDouble(parts[2]);
                    } catch (NumberFormatException e) {
                        LOG.info("Error in client request: type argument incorrect");
                        out.println("Error\nType argument is not correct, only integers or float are accepted\n"
                                + SYNTAX + "\nError_end");
                        out.flush();
                        continue;
                    }

                    LOG.info("Calculating and sending result to client");
                    double res = calculate(parts[0], number1, number2);
                    out.format("Result: %.2f\n", res);
                    out.flush();
                }

                // Close buffers and client socket
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

    /**
     * Calculate an operation defined in OPERATIONS
     *
     * @param op the operation as a string
     * @param d1 the 1st number
     * @param d2 the 2nd number
     * @return the result of operation between the numbers
     */
    private double calculate(String op, double d1, double d2) {
        switch (op) {
            case "ADD":
                return d1 + d2;
            case "SUB":
                return d1 - d2;
            case "MULT":
                return d1 * d2;
            case "DIV":
                return d1 / d2;
            default:
                return 0;
        }
    }

    /**
     * main single threaded Server
     *
     * @param args none
     */
    public static void main(String... args) {
        TCP_Server server = new TCP_Server(2048);
        server.start();
    }

}
