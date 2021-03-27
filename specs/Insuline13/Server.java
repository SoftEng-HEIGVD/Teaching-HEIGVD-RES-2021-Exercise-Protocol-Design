import java.io.*;
import java.net.*;

public class Server {
    public void waitForIncomingClient() throws IOException {
        ServerSocket receptionistSocket = new ServerSocket(2205);
        Socket workerSocket = receptionistSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(workerSocket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(workerSocket.getOutputStream()));
        out.println("Hello, you can ADD, MULT, SUB and DIV two numbers. For example: ADD 2 3");
        out.flush();
        String message = in.readLine();
        while (message != null) {
            String[] tokens = message.split(" ");
            if (tokens[0].equals("QUIT")) {
                out.println("Bye");
                out.flush();
                break;
            }
            if (tokens.length != 3) {
                out.println("Error, give 3 arguments");
                out.flush();
                message = in.readLine();
                continue;
            }


            String operation = tokens[0];
            int operand1;
            int operand2;
            try {
                operand1 = Integer.parseInt(tokens[1]);
                operand2 = Integer.parseInt(tokens[2]);
            } catch (NumberFormatException e) {
                out.println("Error :Wrong operation, please give numbers");
                out.flush();
                message = in.readLine();
                continue;
            }

            if (operation.equals("ADD")) {
                out.println("OK " + (operand1 + operand2));
            } else if (operation.equals("MULT")) {
                out.println("OK " + (operand1 * operand2));
            } else if (operation.equals("SUB")) {
                out.println("OK " + (operand1 - operand2));
            } else if (operation.equals("DIV")) {
                try {
                    int result = operand1 / operand2;
                    out.println("OK " + result);
                } catch (Exception e) {
                    out.println("You can't divide by zero");
                }
            } else {
                out.println("Error : Wrong operation, please do it again or QUIT");
            }
            out.flush();
            message = in.readLine();
        }

        in.close();
        out.close();
        workerSocket.close();
        receptionistSocket.close();
    }


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        while (true) {
            server.waitForIncomingClient();
        }
    }
}
