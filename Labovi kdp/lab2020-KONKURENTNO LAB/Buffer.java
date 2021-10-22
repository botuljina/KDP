package lab2020;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer<T> {
	
	
	int cap;
	int size;
	int head = 0;
	int tail = 0;
	T[] buffer;
	
	Lock lock;
	Condition itemAv;
	Condition spaceAv;
	
	public Buffer(int cap) {
		this.cap = cap;
		size = 0;
		buffer = (T[]) new Object[cap];
		
		lock = new ReentrantLock(true);
		itemAv = lock.newCondition();
		spaceAv = lock.newCondition();
	}
	
	public void put(T item) {
		lock.lock();
		try {
			while (size == cap) {
				spaceAv.awaitUninterruptibly();
			}
			size++;
			buffer[head] = item;
			head = (head + 1) % cap;
			itemAv.signal();
			
		} finally {
			lock.unlock();
		}
	}
	
	public T get() {
		lock.lock();
		try {
			while (size == 0) {
				itemAv.awaitUninterruptibly();
			}
			size--;
			T item = buffer[tail];
			tail = (tail + 1) % cap;
			spaceAv.signal();
			
			return item;
		} finally {
			lock.unlock();
		}
	}
}
