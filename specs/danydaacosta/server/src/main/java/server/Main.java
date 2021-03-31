package server;

public class Main {
    public static void main(String[] args) {
        TcpServer server = new TcpServer();
        server.serveClients(2323);
    }
}
