import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ABImpl implements AB {

	Socket server;
	ObjectOutputStream out;
	ObjectInputStream in;
	@Override
	public boolean init(String host, int port) {
		try {
			server  = new Socket(host, port);
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean close() {
		try {
			server.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public void putGoods(String name, Goods goods) {
		try {
			out.writeObject("put");
			out.writeObject(name);
			out.writeObject(goods);
			//out.writeObject(goods);
			in.readObject(); // ack
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Goods getGoods(String name) {
		
		Goods good = null;
		try {
			out.writeObject("get");
			out.writeObject(name);
			good = (Goods) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Got " + good);
		return good;
	}

}
