package HEIG;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        Server server = new Server();
        new Thread(server).start();
    }
}
