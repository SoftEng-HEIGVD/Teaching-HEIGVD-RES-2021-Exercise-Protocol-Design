package HEIG;

public class App 
{

    public static void main( String[] args )
    {
        Client client = new Client();
        client.Connect("127.0.1",3301);
    }
}
