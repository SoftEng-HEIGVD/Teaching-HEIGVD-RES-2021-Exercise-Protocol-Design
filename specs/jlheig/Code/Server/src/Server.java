import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        while (true){
            server.waitForClients();
        }
    }

    public static String HELP = "HELP";
    public static String QUIT = "QUIT";
    public static int PORT = 3334;

    public static String ADD = "ADD";
    public static String SUB = "SUB";
    public static String MULT = "MULT";
    public static String DIV = "DIV";

    public void waitForClients() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        Socket calculator = server.accept();

        SocketAddress address = calculator.getRemoteSocketAddress();

        if(address instanceof InetSocketAddress){
            InetAddress addressInet = ((InetSocketAddress)address).getAddress();
            if(addressInet instanceof Inet4Address){
                System.out.println("Listening in IPV4 at : " + addressInet + " Port : " + PORT);
            }
            else if(addressInet instanceof Inet6Address){
                System.out.println("Listening in IPV6 at : " + addressInet + " Port : " + PORT);
            }
            else{
                System.err.println("Wrong internet protocol socket");
            }
        }

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(calculator.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(calculator.getInputStream()));

        writer.println("Hello, please enter the calculation you want to do : ");

        String input = reader.readLine();
        while(input != null){
            String[] inputSplitted = input.split(" ");

            if(inputSplitted.length == 1){
                String command = inputSplitted[0];
                if(command.equals(HELP)){
                    writer.println("Supported operations : ADD, SUB, MULT, DIV");
                    writer.println("All operations require only 2 arguments");
                    writer.println("If you want to quit use command QUIT");
                }
                else if(command.equals(QUIT)){
                    writer.println("Closing the connection...");
                    reader.close();
                    writer.close();
                    calculator.close();
                    server.close();
                    return;
                }
                else{
                    writer.println("Error, unsupported command, please use HELP");
                }
            }
            else if(inputSplitted.length == 3){
                String operation = inputSplitted[0];
                int n1 = Integer.parseInt(inputSplitted[1]);
                int n2 = Integer.parseInt(inputSplitted[2]);

                if(operation.equals(ADD)){
                    writer.println("Result : " + (n1 + n2));
                }
                else if(operation.equals(SUB)){
                    writer.println("Result : " + (n1 - n2));
                }
                else if(operation.equals(MULT)){
                    writer.println("Result : " + (n1 * n2));
                }
                else if(operation.equals(DIV)){
                    writer.println("Result : " + (n1 / n2));
                }
                else{
                    writer.println("Error, unsupported operation, please use HELP");
                }
            }
            else{
                writer.println("Error, wrong number of arguments, please use HELP");
            }
            writer.flush();
            input = reader.readLine();
        }

        reader.close();
        writer.close();
        calculator.close();
        server.close();

    }
}
