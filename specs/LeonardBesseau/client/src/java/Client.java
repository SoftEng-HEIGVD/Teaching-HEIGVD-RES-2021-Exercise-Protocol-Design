import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.ArrayList;

public class Client {
    private final int port = 3101;
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    ArrayList<Operation> operations;

    void connect(String ip) throws IOException {
        socket = new Socket(InetAddress.getByName(ip), 3101);
        in = new BufferedReader((new InputStreamReader(socket.getInputStream())));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        if (!in.readLine().equals("WELCOME ")) {
            throw new IOException();
        }
        operations = new ArrayList<>();
        if (!in.readLine().equals("AVAILABLE OPERATIONS ")) {
            throw new IOException();
        }
        String message = in.readLine();
        while (!message.equals("END OPERATIONS ")) {
            String[] tokens = message.split(" ");
            operations.add(new Operation(tokens[0], Integer.parseInt(tokens[1])));
            message = in.readLine();
        }
    }

    void close() throws IOException {
        socket.close();
        in = null;
    }


    boolean computes(String op, int... numbers) throws IOException {
        switch (op) {
            case "+":
                if (numbers.length != 2) {
                    throw new IllegalArgumentException();
                }
                out.println("ADD " + numbers[0] + " " + numbers[1]);
                break;
            case "*":
                if (numbers.length != 2) {
                    throw new IllegalArgumentException();
                }

                out.println("MULT " + numbers[0] + " " + numbers[1]);
                break;
            case "-":
                if (numbers.length != 2) {
                    throw new IllegalArgumentException();
                }
                out.println("SUB " + numbers[0] + " " + numbers[1]);
                break;

            case "/":
                if (numbers.length != 2) {
                    throw new IllegalArgumentException();
                }
                out.println("DIV " + numbers[0] + " " + numbers[1]);
                break;
            default:
                if (operations.contains(new Operation(op, numbers.length))){
                    out.println(op+" " + numbers[0] + " " + numbers[1]);
                }
                break;
        }
        String res = in.readLine();
        if (res.equals("ERROR ")){
            return false;
        }else{
            String[] tokens = res.split(" ");
            System.out.format("Result is : %d", Integer.parseInt(tokens[1]));
            return true;
        }
    }


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect("127.0.0.1");
    }
}

class Operation {
    String name;
    int operand;

    public Operation(String name, int operand) {
        this.name = name;
        this.operand = operand;
    }
}
