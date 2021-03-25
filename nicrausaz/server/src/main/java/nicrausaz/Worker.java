package nicrausaz;

import java.io.*;
import java.net.Socket;

public class Worker implements Runnable
{
    private Socket client;
    private BufferedReader in = null;
    private PrintWriter out = null;

    Worker(Socket client)
    {
        try {
            this.client = client;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        out.println("You just connected to the server !");
        out.flush();

        /*
            There will be the process of the supported commands
         */

        out.println("Bye !");
        out.flush();

        try
        {
            client.close();
            out.close();
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
