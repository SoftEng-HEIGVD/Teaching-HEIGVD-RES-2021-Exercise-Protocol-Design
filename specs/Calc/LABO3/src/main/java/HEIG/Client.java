package HEIG;

import java.io.*;
import java.net.Socket;

public class Client
{
    public Socket socket ;
    public boolean connected = false;
    
    public void Connect(String server, int port)
    {
        try
        {
            socket = new Socket(server,3301);
            connected = true;
        
            Run();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
    }
    public void Close()
    {
        if(connected)
        {
            //colse connection to server

            connected = false;
        }
    }
    public void Run()
    {
        try
        {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output,true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Console console =  System.console();

            for(int i = 0; i < 5; i++)
            {
                String t = reader.readLine();
                System.out.println(t);
            }
            while(connected)
            {
                String t = console.readLine();
                writer.println(t);
                if(t.equals("END"))
                {
                    connected = false;
                    continue;
                }
                String res = reader.readLine();
                System.out.println(res);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}