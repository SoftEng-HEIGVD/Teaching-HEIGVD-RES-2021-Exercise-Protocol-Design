import java.io.*;
import java.net.Socket;

public class Client {

    private int port;

    public Client(int port) {
        this.port = port;
    }

    public void contactServer() {
        try (
                Socket clientSocket = new Socket("localhost", port);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            String[] toSend = new String[] {
                    "1 + 1", "1", "5 * 8", "2 - 1", "Error test 1 2", "Exit"
            };
            System.out.println(in.readLine());
            for (String s : toSend) {
                System.out.println(s);
                out.println(s);
                out.flush();
                System.out.println(in.readLine());
            }
        } catch (Exception e) {
            System.out.println("Echec de connexion.");
        }
    }
}
