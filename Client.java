import java.net.*;
import java.io.*;

public class Client
{
	private Socket socket = null;
	private DataOutputStream out = null;
    private DataInputStream in = null;

    public int p = 23;
    public int g = 5;

    private int b = 3;

    private int B = (int)Math.pow(g, b)%p;
    public int A;
    private int key2;

	public Client(String address, int port)
	{
		try
		{
			socket = new Socket(address, port);
			System.out.println("Connected");
			System.out.println("");

			out = new DataOutputStream(socket.getOutputStream());

            in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));

            A = Integer.parseInt(in.readUTF()); 

            String Binstring = String.valueOf(B);
            out.writeUTF(Binstring);

			System.out.println("Public key of client (B) = "+B);
			System.out.println("Public key received by client = "+A);

            key2 = (int)Math.pow(A, b)%p;
            System.out.println("Key = "+key2);

			System.out.println("");
            System.out.println("Closing connection");
			out.close();
			socket.close();
            in.close();
		}
		catch(UnknownHostException u)
		{
			System.out.println(u);
		}
		catch(IOException i)
		{
			System.out.println(i);
		}  
	}
	public static void main(String args[])
	{
		Client client = new Client("127.0.0.1", 6000);
	}
}