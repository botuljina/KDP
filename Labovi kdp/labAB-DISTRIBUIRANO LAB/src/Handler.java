import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Handler extends Thread {

	static int id = 0;
	int myid;
	Socket client;
	AtomicBroadcast ab;
	ObjectOutputStream out;
	ObjectInputStream in;
	HashMap<String, AtomicBroadcast> buffers;
	public Handler(Socket client, ObjectOutputStream out, ObjectInputStream in, HashMap<String, AtomicBroadcast> buffers) {
		this.client = client;
		this.ab = ab;
		this.out = out;
		this.in = in;
		this.buffers = buffers;
	}
	public void run() {
		boolean firstGet = true;
		try {
			
			while(true) {
				String operation = (String) in.readObject();
				String name = (String) in.readObject();
				System.out.println(operation + " requested");
				if(operation.equals("put")) {
					Goods good = (Goods) in.readObject();
					
					if(good == null) {
						out.writeObject("ack");
						client.close();
						break;
					}
					else {
						AtomicBroadcast buff;
						if(buffers.containsKey(name)) {
							buff = buffers.get(name);
						}
						else {
							buff = new AtomicBroadcast(myServer.capacity, myServer.numC);
							buffers.put(name, buff);
						}
						buff.putGoods(good);
						out.writeObject("ack");
					}
				}
				else {
					if(firstGet) {
						myid = id++;
						firstGet = false;
					}
					if(name.equals("end")) {
						out.writeObject(null);
						client.close();
						break;
					}
					System.out.println(name);
					ab  = buffers.get(name);
					Goods good = ab.getGoods(myid);
					out.writeObject(good);
				}
				
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
