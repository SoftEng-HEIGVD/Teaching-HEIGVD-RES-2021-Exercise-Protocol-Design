package main.java;

import java.io.*;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.*;

public class Client {
    public final int port = 3101;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean connected;

    private ArrayList<Operation> operations;

    /**
     * Connect to the given server and setup the operations availables
     * @param ip the ip to connect to. (Connect to port 3101)
     * @throws IOException
     *
     * This commands verify if the server obey the specification
     */
    public void connect(String ip) throws IOException {

        socket = new Socket(InetAddress.getByName(ip), port);
        in = new BufferedReader((new InputStreamReader(socket.getInputStream())));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        if (!in.readLine().equals("WELCOME ")) {
            throw new RuntimeException("Server does not respect the specification for the protocol. ");
        }

        String res = in.readLine();
        if (!res.equals("- AVAILABLE OPERATIONS ")) {
            throw new RuntimeException("Server does not respect the specification for the protocol. ");
        }


        operations = new ArrayList<>();
        boolean hasMult = false;
        boolean hasAdd = false;

        String message = in.readLine();
        while (!message.equals("- END OPERATIONS ")) {
            String[] tokens = message.split(" ");
            if (tokens.length != 3) {
                throw new RuntimeException("Invalid operations specifications");
            }

            int nbArg = Integer.parseInt(tokens[2]);
            if (tokens[1].equals("MULT") && nbArg == 2) {
                hasMult = true;
            }
            if (tokens[1].equals("ADD") && nbArg == 2) {
                hasAdd = true;
            }

            operations.add(new Operation(tokens[1], nbArg)); // Add operations to list of supported operations

            message = in.readLine();
        }
        if (!hasAdd || !hasMult) {
            throw new RuntimeException("Server does not respect the specification for the protocol. " +
                    "Missing basic operations");
        }
        connected = true;
    }

    /**
     * Display availables operations to use
     * @return a formated string to display the information
     */
    public String displayAvailableOperations() {
        if (!connected || operations == null) {
            return "No operations availables";
        } else {
            StringBuilder out = new StringBuilder("Operations availables: \n");
            for (Operation o : operations) {
                out.append(o.name).append(" ").append(o.operand).append("\n");
            }
            out.append("End of Operation list");
            return out.toString();
        }
    }

    /**
     * Send a QUIT message to the server and close the socket.
     * @throws IOException
     */
    public void close() throws IOException {
        out.println("QUIT\r\n");
        out.flush();
        socket.close();
        in = null;
        out = null;
        connected = false;
    }


    /**
     * Send a computation request to the server
     * @param op the operation to compute
     * @param numbers the value to use in the computaion
     * @return the result of the computation
     * @throws IOException
     */
    public int computes(String op, ArrayList<Integer> numbers) throws IOException {
        if (!connected){
            throw new RuntimeException("Client must be connected");
        }
        StringBuilder message = new StringBuilder("COMPUTE ");

        if (operations.contains(new Operation(op, numbers.size()))) {

            message.append(op).append(" ");
            for (Integer i : numbers) {
                message.append(i).append(" ");
            }
            message.append("\r\n");
        } else {
            throw new RuntimeException("Operation " + op + " not supported");
        }
        out.println(message.toString());
        out.flush();

        String res = "";
        // Sometimes we get empty string or nothing at all so we discard them
        while (res == null || res.isEmpty()) {
            res = in.readLine();
        }

        if (res.contains("ERROR")) {
            throw new ArithmeticException(res);
        } else {
            String[] tokens = res.split(" ");
            return Integer.parseInt(tokens[1]);
        }


    }

    public boolean isConnected() {
        return connected;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        String ip = "127.0.0.1";

        System.out.println("Welcome to the online calculator");
        System.out.println("You are connecting to " + ip);

        try{
            client.connect(ip);
        }catch (IOException e){
            System.err.println("Could not connect to server");
            return;
        }

        System.out.println(client.displayAvailableOperations());

        System.out.println("Please enter a computation in Polish notation (OP N1 N2 ... )");
        Scanner in = new Scanner(System.in);

        String name = in.nextLine();
        while (!name.contains("QUIT")) {

            String[] tokens = name.split(" ");
            if (tokens.length < 2) {
                System.out.println("Please enter at least a number and an operation");
            } else {
                try {
                    Integer.parseInt(tokens[0]);
                    System.out.println("Error: Please Use the polish notation");
                    name = in.nextLine();
                    continue;
                } catch (NumberFormatException ignored) {

                }
                ArrayList<Integer> numbers = new ArrayList<>(tokens.length - 1);
                try {
                    for (int i = 1; i < tokens.length; i++) {
                        numbers.add(Integer.parseInt(tokens[i]));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please use the polish notation and use number in numerical format");
                    name = in.nextLine();
                    continue;
                }

                try {
                    System.out.format("Result is : %d\n",client.computes(tokens[0], numbers));

                } catch (RuntimeException | ServerException e) {
                    System.err.println("Compute "+ e.getMessage());
                }
            }
            name = in.nextLine();
        }
        client.close();
    }
}

class Operation {
    String name;
    int operand;

    public Operation(String name, int operand) {
        this.name = name;
        this.operand = operand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return operand == operation.operand && Objects.equals(name, operation.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, operand);
    }
}