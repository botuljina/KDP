
public class Consumer {

	public static void main(String[] args) throws Exception {
		String host = "localhost";//args[0];
		int port = 9999;//Integer.parseInt(args[1]);
		AB ab = new ABImpl(); // TODO zameniti null stvaranjem konkretnog objekta, npr. new PCClass(...)

		if (!ab.init(host, port))
			return;

		args = new String[8];
		for (int i = 2; i < args.length; i++) {
			String name = "goodie"+(i%myServer.capacity);//args[i];

			Goods goods = ab.getGoods(name);
			
			int size = goods.getNumLines();
			for (int j = 0; j < size; j++) {
				System.out.println(goods.readLine());
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}
		}
		ab.getGoods("end");
		ab.close();
	}
}
