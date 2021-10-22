package lab2020;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer extends Thread {
	
	private int id;
	private Buffer<String> bufferFromProducer;
	private Buffer<String> bufferToCombiner;
	List<String> actors;
	
	private int givenYear;
	private int N;
	private int cntParsed = 0;
	private AtomicInteger cntParsedGlobal;

	public Consumer(int id, Buffer<String> bufferFromProducer, int givenYear, int N, Buffer<String> bufferToCombiner, AtomicInteger cntParsedGlobal) {
		super("Consumer" + id);
		this.id = id;
		this.bufferFromProducer = bufferFromProducer;
		actors = new ArrayList<>();
		this.givenYear = givenYear;
		this.N = N;
		this.bufferToCombiner = bufferToCombiner;
		this.cntParsedGlobal = cntParsedGlobal;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " started consuming!");
		
		while (true) {
			
			String line = bufferFromProducer.get();
			if (line == null) {
				for (String s : actors) {
					bufferToCombiner.put(s);
				}
				cntParsedGlobal.set(cntParsed);
				bufferToCombiner.put(null);
				System.out.println(this.getName() + " finished consuming!");
				break;
			}
			
			parseLine(line);
			
			if (++cntParsed == N) {
				for (String s : actors) {
					bufferToCombiner.put(s);
				}
				actors.clear();
				cntParsedGlobal.addAndGet(cntParsed);
				cntParsed = 0;
			}
		}
	}

	private void parseLine(String line) {
		String[] args = line.split("\t");
		
		String birthYear = args[2];
		String deathYear = args[3];
		String profession = args[4];
		
		if (!(profession.contains("actor") || profession.contains("actress"))) {
			return;
		}
		
		if (!deathYear.equals("\\N")) {
			return;
		}
	
		try {
			int birthYearNum = Integer.parseInt(birthYear);
			if (birthYearNum >= givenYear && birthYearNum < givenYear + 10) {
				actors.add(line);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}
