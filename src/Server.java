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

            writer.println("WELCOME \r\n" +
                    "- OPERATIONS \r\n" +
                    "- ADD 2 \r\n" +
                    "- MULT 2 \r\n" +
                    "- DIV 2 \r\n" +
                    "- ENDOFOPERATIONS \r\n");
            writer.flush();
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
    }
}
