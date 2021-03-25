package nicrausaz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private final int lstPort;
    private ServerSocket srvSocket;

    public Server(int lstPort)
    {
        this.lstPort = lstPort;
    }

    public void start()
    {
        new Thread(new MainWorker()).start();
    }

    private class MainWorker implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                srvSocket = new ServerSocket(lstPort);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            while (true)
            {
                try
                {
                    Socket client = srvSocket.accept();

                    new Thread(new Worker(client)).start();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }
}
