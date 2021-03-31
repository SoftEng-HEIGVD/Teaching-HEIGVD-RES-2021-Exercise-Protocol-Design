import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        boolean serverShutdownRequested = false;
        ServerSocket receptionistSocket = new ServerSocket(3232);
        String response = "";

        while (!serverShutdownRequested) {
            Socket clientSocket = receptionistSocket.accept(); // blocking call
            InputStream is = clientSocket.getInputStream();
            Reader reader = new BufferedReader( new InputStreamReader(is));
            OutputStream os = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter( new OutputStreamWriter(os));
            Scanner in = new Scanner(reader);

            System.out.println("Nouveau client.");
            writer.println("WELCOME \r\n" +
                    "- OPERATIONS \r\n" +
                    "- ADD 2 \r\n" +
                    "- SUB 2 \r\n" +
                    "- MULT 2 \r\n" +
                    "- ENDOFOPERATIONS");
            writer.flush();

            while(!response.equals("QUIT") && clientSocket.isConnected()){

                response = in.nextLine();

                if(response.equals("QUIT")) continue;
                String[] operation = response.split(" ");
                for(String s : operation) System.out.println(s);
                if(operation.length != 3) response = "ERROR 400 SYNTAX ERROR";
                else {
                    int op1 = Integer.parseInt(operation[1]);
                    int op2 = Integer.parseInt(operation[2]);
                    switch (operation[0]) {
                        case "ADD":
                            response = "RESULT " + (op1 + op2);
                            break;
                        case "SUB":
                            response = "RESULT " + (op1 - op2);
                            break;
                        case "MULT":
                            response = "RESULT " + (op1 * op2);
                            break;
                        default:
                            response = "ERROR 300 UNKNOWN OPERATION";
                    }
                }

                writer.println(response);
                writer.flush();
                System.out.println(response);
            }

            System.out.println("Client parti");
            //serverShutdownRequested = response.equals("QUIT");

            is.close();
            os.close();
            clientSocket.close();
        }

        receptionistSocket.close();
    }

    /*
    static final Logger LOG = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) throws IOException {
        // create socket
        ServerSocket socket = null;
        Socket clientSocket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            socket = new ServerSocket(3101);
            clientSocket = socket.accept();
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream());
            System.out.println("test");
            writer.println("WELCOME \r\n" +
                    "- OPERATIONS \r\n" +
                    "- ADD 2 \r\n" +
                    "- MULT 2 \r\n" +
                    "- DIV 2 \r\n" +
                    "- ENDOFOPERATIONS \r\n");
            writer.flush();
            System.out.println("envoyé");
            String entreeClient;

            do {
                entreeClient = reader.readLine();
                System.out.println(entreeClient);

            }while(!entreeClient.equals("QUIT \r\n"));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        } finally {
            assert reader != null;
            reader.close();
            assert writer != null;
            writer.close();
            clientSocket.close();
            socket.close();
            //close
        }
    }*/
}
