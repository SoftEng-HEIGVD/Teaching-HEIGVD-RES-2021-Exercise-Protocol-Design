package HEIG;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class Server implements Runnable
{
    public Socket socket;


    boolean running = false;
    private void Run()
    {
        try(ServerSocket serverSocket  = new ServerSocket(3301))
        {
            System.out.println("Server is listening on port 3301");
            running  = true;

            while(running)
            {
                Socket socket =  serverSocket.accept();

                //a client has connected
                System.out.println("New client connected");
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output);

                String usrInput;
                String result;
                //write welcome block
                writer.println(Utils.WELCOME);
                writer.println(Utils.ADD);
                writer.println(Utils.MULT);
                writer.println(Utils.DIV);
                writer.println(Utils.END);
                writer.flush();

                do{

                    //read input from client
                    usrInput = reader.readLine();


                    //parse

                    result = DoOperation(usrInput);
                    if(result.equals("END"))
                        break;
                    //do operation
                    //send result to client
                    writer.println(result);
                    writer.flush();
                }while(true);
                writer.println("CLOSING CONNECTION");
                writer.flush();
                socket.close();
                System.out.println("Listening to other connections on port 33101");
            }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
    }

    public String DoOperation(String usrInput)
    {
        if(usrInput.equals(Utils.END))
            return "END";
        if(usrInput.indexOf(" ") < 0)
            return "ERROR";
        String[] operands = usrInput.substring(usrInput.indexOf(" ")).split(" ", 3);

        System.out.println(Arrays.toString(operands));
        usrInput = usrInput.substring(0,usrInput.indexOf(" "));
        String result = "";
        double op1;
        double op2;
        if(operands.length > 2)
        {
            try
            {
                op1 = Double.parseDouble(operands[1]);
                op2 = Double.parseDouble(operands[2]);
            }
            catch(Exception e)
            {
                return "ERROR : PARSING ERROR";
            }

            switch(usrInput)
            {
                case Utils.ADD:
                result = "RESULT " + String.valueOf(op1 + op2);
                break;
                case Utils.MULT:
                result = "RESULT " +String.valueOf(op1 * op2);
                break;
                case Utils.DIV:
                result = "RESULT " +String.valueOf(op1 / op2);
                break;
                case Utils.END:
                result = "RESULT " +"CLOSE CONNECTION";
                running = false;
                break;
                default:
                System.out.println( usrInput);
                result = "ERROR : UKNOWN OPERATION";
                break;
            }
        }
        else
        {
            result = "ERROR : PARSING ERROR";
        }
        return result;

    }

    @Override
    public void run() {
        Run();        
    }
}   