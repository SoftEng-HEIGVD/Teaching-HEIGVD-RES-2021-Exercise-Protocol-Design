import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    static final Logger LOG = Logger.getLogger(Client.class.getName());
    private final int PORT;
    private final static int BUFFER_SIZE = 1024;

    public Client(int port) {
        this.PORT = port;
    }

    private void run() {
        LOG.info("Starting Client side");
        Socket clientSocket = null;
        PrintWriter os = null;
        InputStream is = null;
        try {
            clientSocket = new Socket("localhost", PORT);
            os = new PrintWriter(clientSocket.getOutputStream());
            is = clientSocket.getInputStream();

            ByteArrayOutputStream response = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];

            int newBytes;
            String test = "";
            while(!test.equals("BYE") && (newBytes = is.read(buffer)) != -1) {
                response.write(buffer, 0, newBytes);
                System.out.print(response);
                response.reset();
                test = new Scanner(System.in).nextLine();
                os.println(test);
                os.flush();
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                if(clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
            }
            if (os != null) {
                os.close();
            }

            try {
                if(is != null) {
                    is.close();
                }
            } catch (IOException e) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    public static void main(String[] args) {
        Client client = new Client(9999);
        client.run();
    }
}
