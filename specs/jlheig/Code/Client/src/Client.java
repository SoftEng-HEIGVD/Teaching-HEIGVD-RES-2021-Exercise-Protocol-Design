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
        String input;
        do{
            input = scan.nextLine();

            if(input.compareTo(QUIT) == 0){
                client.isConnected = false;
            }

            client.writer.println(input);

        }while(!client.isConnected);

        client.disconnect();
    }

    public static String QUIT = "QUIT";
    public static int PORT = 3334;

    private Logger logger = Logger.getLogger(Client.class.getName());

    Socket clientSocket;
    PrintWriter writer;
    BufferedReader reader;
    boolean isConnected;

    public void connect(String server, int port) throws IOException{
        try{
            clientSocket = new Socket(server, port);
            writer = new PrintWriter(clientSocket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            isConnected = true;
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "Impossible to connect to the server, server not found", e.getMessage());
        }

        listener();
    }

    public void disconnect() throws IOException{
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

    public void listener(){
        try{
            String resp;
            resp = reader.readLine();
            while(isConnected && (resp != null)){
                System.out.println(resp);
            }
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "Error while listening", e.getMessage());
        }
    }

}
