import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    static final Logger LOG = Logger.getLogger(Client.class.getName());

    final static int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        //1. create a socket
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        String request;
        Scanner in = new Scanner(System.in);

        try {
            //2. make a connection request on an IP adress / port
            socket = new Socket("localhost", 3101);
            os = socket.getOutputStream();
            is = socket.getInputStream();

            //3. read and write bytes through this socket, communicating with the client
            do {
                ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int newBytes;
                while ((newBytes = is.read(buffer)) != -1) {
                    responseBuffer.write(buffer, 0, newBytes);
                }

                LOG.log(Level.INFO, "Response sent by the server: ");
                LOG.log(Level.INFO, responseBuffer.toString());

                request = in.nextLine();
                System.out.print("You entered string " + request + "\r\n");
                os.write(request.getBytes());

            } while (!request.equals("QUIT \r\n"));

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            //4. close the client socket
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
