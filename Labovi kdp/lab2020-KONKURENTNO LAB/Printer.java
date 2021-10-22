package lab2020;

import java.util.concurrent.atomic.AtomicInteger;

public class Printer extends Thread {

	private int sleepTime = 10000;
	private AtomicInteger[] cntParsed;
	private int consumerCnt;
	private boolean combinerFinished = false;
	private volatile long totalCnt = 0;
	
	Buffer<String> bufferFromCombiner;
	
	public Printer(int sleepTime, AtomicInteger[] cntParsed, int consumerCnt, Buffer<String> bufferFromCombiner) {
		super("Printer");
		this.cntParsed = cntParsed;
		this.consumerCnt = consumerCnt;
		this.bufferFromCombiner = bufferFromCombiner;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " started printing!");
		
		while (true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (combinerFinished) {
				
				while(true) {
					String actor = bufferFromCombiner.get();
					if (actor == null) {
						System.out.println(totalCnt);
						break;
					} else {
						totalCnt++;
						System.out.println(actor);
					}
				}
				
				break;
				
			} else {
				for(int i = 0; i < consumerCnt; i++) {
					System.out.println("Consumer" + i + " parsed " + cntParsed[i].get() + " actors");
				}
			}
		}
	}
	
	public void setCombinerFinished(boolean finished) {
		this.combinerFinished = finished;
	}

}
