package lab2020;

public class ConcurrentInteger {
	volatile int value;
	
	public ConcurrentInteger(int value) {
		this.value = value;
	}
	
	public synchronized int get() {
		return value;
	}
	
	public synchronized void set(int value) {
		this.value = value;
	}
	
	public synchronized void add(int value) {
		this.value += value;
	}
}
