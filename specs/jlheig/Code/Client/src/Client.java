import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Scanner scan = new Scanner(System.in);

        if(args.length == 0){
            System.out.println("Error, we need 1 argument to serve as the server adress");
            return;
        }

        String server = args[0];
        client.connect(server, PORT);
        boolean stayConnected = true;
        String input;
        do{
            input = scan.nextLine();

            if(input.compareTo(QUIT) == 0){
                stayConnected = false;
            }

            client.writer.println(input);
            client.writer.flush();

        }while(stayConnected);

        client.disconnect();
    }

    private static String QUIT = "QUIT";
    private static int PORT = 3334;

    private Logger logger = Logger.getLogger(Client.class.getName());

    Socket clientSocket;
    PrintWriter writer;
    BufferedReader reader;
    boolean isConnected = false;

    public void connect(String server, int port) throws IOException{
        try{
            clientSocket = new Socket(server, port);
            writer = new PrintWriter(clientSocket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            isConnected = true;
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "Impossible to connect to the server", e.getMessage());
        }

        new Thread(new listener()).start();
    }

    public void disconnect() throws IOException {
        try {
            if (isConnected) {
                logger.log(Level.INFO, "Client wants to quit");
                isConnected = false;
                writer.close();
                reader.close();
                clientSocket.close();
            }
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "Error while disconnecting", e.getMessage());
        }
    }

    class listener implements Runnable {
        public void run(){
            String resp;
            try{
                while((isConnected && (resp = reader.readLine()) != null)){
                    System.out.println(resp);
                }
            }
            catch (IOException e){
                logger.log(Level.SEVERE, "Error while listening", e.getMessage());
                isConnected = false;
            }
        }
    }
}
