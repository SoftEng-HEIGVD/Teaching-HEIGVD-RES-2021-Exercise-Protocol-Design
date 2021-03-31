import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        Socket clientSocket = new Socket("localhost", 3232);

        InputStream is = clientSocket.getInputStream();
        OutputStream os = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter( new OutputStreamWriter(os));
        BufferedReader reader = new BufferedReader( new InputStreamReader(is));

        String entry = "";
        while(!entry.equals("- ENDOFOPERATIONS")){
            entry = reader.readLine();
            System.out.println(entry);
        }
        writer.flush();

        String request = "";
        do {
            request = in.nextLine();
            writer.println(request);
            writer.flush();
            System.out.println(reader.readLine());
        }while(!request.equals("QUIT"));

        is.close();
        os.close();
        clientSocket.close();
    }

    /*
    static final Logger LOG = Logger.getLogger(Client.class.getName());

    final static int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        //1. create a socket
        Socket socket = null;
        OutputStream os = null;
        BufferedReader is = null;
        String request;
        Scanner in = new Scanner(System.in);

        try {
            //2. make a connection request on an IP adress / port
            //socket = new Socket("188.61.173.23", 8827);
            socket = new Socket("localhost", 3101);
            os = socket.getOutputStream();
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Start");

            //3. read and write bytes through this socket, communicating with the client
            do {
                System.out.println("doWhile");
                ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
                System.out.println("1");
                byte[] buffer = new byte[BUFFER_SIZE];
                int newBytes;
                while ((newBytes = is.read()) != -1) {
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
    }*/
}
