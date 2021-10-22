package lab2020;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args) {

		int consumersNumber = 5;
		int N = 100;
		String fileName = "data.tsv";
		int givenYear = 1950;
		
		Buffer<String> producerConsumerBuffer = new Buffer<>(10 * consumersNumber);
		Buffer<String> consumerCombinerBuffer = new Buffer<>(10 * consumersNumber);
		Buffer<String> combinerPrinterBuffer = new Buffer<>(10 * consumersNumber);
		AtomicInteger[] cntParsed = new AtomicInteger[consumersNumber];

		
		Producer producer = new Producer(fileName, producerConsumerBuffer, consumersNumber);
		producer.start();

		for (int i = 0; i < consumersNumber; i++) {
			cntParsed[i] = new AtomicInteger(0);
			Consumer consumer = new Consumer(i, producerConsumerBuffer, givenYear, N, consumerCombinerBuffer, cntParsed[i]);
			consumer.start();
		}

		Printer printer = new Printer(2000, cntParsed, consumersNumber, combinerPrinterBuffer);
		
		Combiner combiner = new Combiner(consumerCombinerBuffer, consumersNumber, combinerPrinterBuffer, printer);
		combiner.start();


		printer.start();

		try {
			printer.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
