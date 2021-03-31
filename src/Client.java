import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static String endmsg = "- ENDOFOPERATIONS"; // "END_OP"; //

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Socket clientSocket = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            clientSocket = new Socket("localhost", 8827);

            is = clientSocket.getInputStream();
            os = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String entry = "";
            while (!entry.equals(endmsg)) {
                entry = reader.readLine();
                System.out.println(entry);
            }
            writer.flush();

            String request;
            do {
                request = in.nextLine();
                writer.println(request);
                writer.flush();
                System.out.println(reader.readLine());
            } while (!request.equals("QUIT"));
        }catch(IOException io){

        }finally {
            try {
                assert is != null;
                is.close();
            }catch(IOException io){
                System.out.println(io.getMessage());
            }
            try{
                assert os != null;
                os.close();
            }catch (IOException io){
                System.out.println(io.getMessage());
            }
            try {
                clientSocket.close();
            }catch(IOException io){
                System.out.println(io.getMessage());
            }
        }


    }
}
