package nicrausaz;

public class App
{
    public static void main(String[] args)
    {
        final int PORT = 8083;
        System.out.println("Running server on port " + PORT);

        new Server(PORT).start();
    }
}
