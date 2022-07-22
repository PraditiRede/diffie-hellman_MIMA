import java.net.*;
import java.io.*;

public class Serverd
{
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
    private DataOutputStream out = null;

    public int p = 23;
    public int g = 5;

    private int a = 4;

    private int A = (int)Math.pow(g, a)%p;
    public int B;
    private int key1;

	public Serverd(int port)
	{
		try
		{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");
			System.out.println("");

			out = new DataOutputStream(socket.getOutputStream());

			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));

            String Ainstring = String.valueOf(A);
            out.writeUTF(Ainstring);

            B = Integer.parseInt(in.readUTF()); 

			System.out.println("Public key of server (A) = "+A);
			System.out.println("Public key received form client = "+B);

            key1 = (int)Math.pow(B,a)%p;
            System.out.println("Key = "+key1);

			System.out.println("");
			System.out.println("Closing connection");
			socket.close();
			in.close();
            out.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}
	public static void main(String args[])
	{
		Serverd serverd = new Serverd(7000);
	}
}