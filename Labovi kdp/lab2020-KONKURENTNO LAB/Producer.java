package lab2020;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Producer extends Thread {
	
	private String fileName;
	private Buffer<String> buffer;
	private int consumerCnt;

	public Producer(String fileName, Buffer<String> buffer, int consumerCnt) {
		super("Producer");
		this.fileName = fileName;
		this.buffer = buffer;
		this.consumerCnt = consumerCnt;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " started producing!");

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			br.readLine();
			
			while(true) {
				String line = br.readLine();
				
				if (line == null) {
					for (int i = 0; i < consumerCnt; i++) {
						buffer.put(null);
					}
					br.close();
					System.out.println(this.getName() + " finished producing!");
					break;
				}
				
				buffer.put(line);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
