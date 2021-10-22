import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class AtomicBroadcast {

	Goods[] buffer;
	int cnt[];
	int writeIndex;
	int readIndex[];
	int size;
	int cap;
	int N;
	
	Semaphore mutex[];
	Semaphore mutexP = new Semaphore(1);
	Semaphore empty;
	Semaphore notEmpty[];
	public AtomicBroadcast(int capacity, int N) {
		this.cap = capacity;
		this.N = N;
		cnt = new int[capacity];
		readIndex = new int[N];
		buffer = new Goods[cap];
		notEmpty = new Semaphore[N];
		mutex = new Semaphore[cap];
		size = 0;
		for(int i=0;i<N;i++) {
			readIndex[i] = 0;
			notEmpty[i] = new Semaphore(0);
		}
		for(int i=0;i<cap;i++) {
			cnt[i] = 0;
			mutex[i] = new Semaphore(1);
		}
		writeIndex = 0;
		empty = new Semaphore(cap);
	}
	

	public void putGoods(Goods goods) {
		empty.acquireUninterruptibly();
		mutexP.acquireUninterruptibly();
		buffer[writeIndex] = goods;
		cnt[writeIndex] = N;
		System.out.println("Put " + goods + " at " + writeIndex);
		writeIndex = (writeIndex+1) % cap;
		size++;
		for(int i=0;i<N;i++) {
			notEmpty[i].release();
		}
		mutexP.release();

	}

	public Goods getGoods(int id) {
		Goods ret = null;
		int index = readIndex[id];
		notEmpty[id].acquireUninterruptibly();
		mutex[index].acquireUninterruptibly();
		ret = buffer[index];
		System.out.println("Consumed " + ret + " at index " + index);
		cnt[index]--;
		if(cnt[index] == 0) {
			empty.release();
		}
		mutex[index].release();
		readIndex[id] = (index+1) % cap;
		return ret;
	}

}
