import java.io.IOException;
import java.net.ServerSocket;

public class CalcProtocol {

    public static void main(String[] args) {

        Thread server = new Thread(new Server(26994));
        server.start();

        Client client = new Client(26994);
        client.contactServer();
    }
}
