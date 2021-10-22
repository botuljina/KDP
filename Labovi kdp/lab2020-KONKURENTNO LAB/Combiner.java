package lab2020;

import java.util.ArrayList;
import java.util.List;

public class Combiner extends Thread {

	private Buffer<String> bufferFromConsumer;
	private int consumerCnt;
	private List<String> actors;
	private int cntToStop = 0;
	private Buffer<String> bufferToPrinter;
	private Printer printer;
	
	public Combiner(Buffer<String> bufferFromConsumer, int consumerCnt, Buffer<String> bufferToPrinter, Printer printer) {
		super("Combiner");
		this.bufferFromConsumer = bufferFromConsumer;
		this.consumerCnt = consumerCnt;
		actors = new ArrayList<>();
		this.bufferToPrinter = bufferToPrinter;
		this.printer = printer;
	}
	

	@Override
	public void run() {
		
		System.out.println(this.getName() + " started combining!");
		
		while (true) {
			
			String actor = bufferFromConsumer.get();
			//System.out.println(actor);
			if (actor == null) {
				if (++cntToStop == consumerCnt) {
					printer.setCombinerFinished(true);
					for (String s : actors) {
						bufferToPrinter.put(s);
					}
					bufferToPrinter.put(null);
					break;
				}
			} else {
				actors.add(actor);
			}
		}
	}

}
