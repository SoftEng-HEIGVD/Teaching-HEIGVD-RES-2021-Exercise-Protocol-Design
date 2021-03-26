import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String message;
            String[] tokens;
            String op;
            final String ERROR = "ERREUR : Une erreur est survenue ->";
            int val1, val2;

            out.println("Bienvenu sur CALC. " +
                    "Les operations supportees sont l'addition (+) et la multiplication (*). " +
                    "Pour quitter taper \"Exit\".");
            out.flush();

            message = in.readLine();
            while (message != null) {
                tokens = message.split(" ");

                try {
                    if (tokens[0].equals("Exit")) {
                        out.println("Merci d'avoir visite CALC. A bientot !");
                        out.flush();
                        break;
                    }

                    if (tokens.length != 3) {
                        throw new Exception();
                    }

                    op = tokens[1];
                    val1 = Integer.parseInt(tokens[0]);
                    val2 = Integer.parseInt(tokens[2]);

                    if (op.equals("+")) {
                        out.println(message + " = " + (val1 + val2));
                    } else if (op.equals("*")) {
                        out.println(message + " = " + (val1 * val2));
                    } else {
                        out.println(ERROR + "l'operateur (" + op + ") n'est pas supporte.");
                    }
                } catch (Exception e) {
                    out.println(ERROR + "le format est invalide. Le format a utilise est : <Valeur> <Operateur> <Valeur>");
                }
                out.flush();
                message = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
