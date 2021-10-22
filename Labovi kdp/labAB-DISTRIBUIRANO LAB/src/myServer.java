import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class myServer {

	static int numC = 3;
	static int capacity = 3;
	public static void main(String[] args) {
		int port = 9999;
		HashMap<String, AtomicBroadcast> buffers = new HashMap<String, AtomicBroadcast>();
		try(ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Server started..");
			while(true) {
				Socket client = serverSocket.accept();
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
				(new Handler(client, out, in, buffers)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
