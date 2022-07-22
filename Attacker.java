import java.net.*;
import java.io.*;

public class Attacker 
{
    private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
    private DataOutputStream out = null;

    public int p = 23;
    public int g = 5;

    private int ea = 7;
    private int eb = 9;

    private int EA = (int)Math.pow(g, ea)%p;
    private int EB = (int)Math.pow(g, eb)%p;

    public int A;
    public int B;

    private int key_server;
    private int key_client;

    public Attacker(String address, int port)
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

            String EBinstring = String.valueOf(EB);
            out.writeUTF(EBinstring);

			System.out.println("Public key of attacker sent to server sender(EB) = "+EB);
			System.out.println("Public key received from server(A) = "+A);

            key_server = (int)Math.pow(A, eb)%p;
            System.out.println("Server Key = "+key_server);

			System.out.println("");
            System.out.println("Closing connection");
			System.out.println("");
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
    public Attacker(int port)
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

            String EAinstring = String.valueOf(EA);
            out.writeUTF(EAinstring);

            B = Integer.parseInt(in.readUTF()); 

			System.out.println("Public key of attacker sent to client sender(EA) = "+EA);
			System.out.println("Public key received from client(B) = "+B);

            key_client = (int)Math.pow(B,ea)%p;
            System.out.println("Client Key = "+key_client);

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
        Attacker aclient = new Attacker("127.0.0.1", 5000);
        Attacker aserver = new Attacker(6000);
	}
}