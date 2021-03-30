package client;

import java.io.*;
import java.net.Socket;

public class Client {
    private String serverIp;
    private int port;

    public Client(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    final static String END_LINE = "\n";

    private String add(int a, int b) {
        return String.format("ADD %d %d", a, b);
        //return String.format("ADD %d ", b);
    }

    private String sub(int a, int b) {
        return String.format("SUB %d %d", a, b);
    }

    private String multiply(int a, int b) {
        return String.format("MULT %d %d", a, b);
    }

    private String quit() {
        return String.format("CIAO %s", END_LINE);
    }

    private String formatLine(String line) {
        return String.format("%s%s", line, END_LINE);
    }

    private void send(BufferedWriter out, String toSend) throws IOException {
        System.out.println("sent line: " + toSend);
        out.write(formatLine(toSend));
        out.flush();
    }

    private String read(BufferedReader in) throws IOException {
        String response = in.readLine();
        System.out.println("Received line: " + response);
        return response;
    }

    public void run() {
        // Setter à null car autrement pas content car pas init
        Socket clientSocket = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try {

            clientSocket = new Socket(this.serverIp, this.port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));



            // Add
            send(out, add(1, 2));
            String result1 = read(in);


            // Sub
            send(out, sub(3, 2));
            String result2 = read(in);

            // Mult
            send(out, multiply(5, 2));
            String result3 = read(in);

            // Quit
            send(out, quit());

            clientSocket.close();
            in.close();
            out.close();

        } catch (IOException e) {
            System.out.println("Oops..." + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("Mamamia" + e.getMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Pas compris, répétez répondez" + e.getMessage());
                }
            }

        }
    }

    public static void main(String[] args) {

        // local
       int port = 2066;
        String ip = "127.0.0.1"; // "6.tcp.ngrok.io"

        // distant
/*        int port = 10512;
        String ip = "6.tcp.ngrok.io";*/

        Client client = new Client(ip, port);
        client.run();
    }

}
