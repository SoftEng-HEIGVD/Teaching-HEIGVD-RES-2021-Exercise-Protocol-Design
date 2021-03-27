import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    static final Logger LOG = Logger.getLogger(Client.class.getName());
    static final String host = "localhost";
    static final int port = 2424;

    public static void main(String[] args) {
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader userInput = null;

        try {
            clientSocket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            userInput = new BufferedReader(new InputStreamReader(System.in));

            String input = "";
            while(!input.equals("END")){
                input = userInput.readLine();
                out.println(input);
                out.flush();
                LOG.log(Level.INFO, "Response :" + in.readLine());
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                assert out != null;
                out.close();
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                assert userInput != null;
                userInput.close();
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                clientSocket.close();
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
