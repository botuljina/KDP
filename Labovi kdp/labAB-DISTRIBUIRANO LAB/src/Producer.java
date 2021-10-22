

public class Producer {

	public static void main(String[] args) throws Exception {
		String host = "localhost"; //args[0];
		int port = 9999;//Integer.parseInt(args[1]);

		AB ab = new ABImpl(); // TODO zameniti null stvaranjem konkretnog objekta, npr. new PCClass(...)

		if (!ab.init(host, port))
			return;

		args = new String[5];
		for (int i = 2; i < args.length; i++) {
			String name = "goodie"+(i % myServer.capacity);//args[i];

			Goods goods = new GoodsImpl(); // TODO zameniti null stvaranjem konkretnog objekta, npr. new GoodsClass(name)
			goods.setName("Goodie");
			int size = (int) (Math.random() * 4 + 1);
			for (int j = 0; j < size; j++) {
				String data = "" + (Math.random() * 1234567) + "\n";
				goods.printLine(data);
				System.out.println(data);
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}

			//goods.save(name);
			ab.putGoods(name, goods);

		}
		ab.putGoods("end", null);
		ab.close();
		System.out.println("Producer done");
	}
}
