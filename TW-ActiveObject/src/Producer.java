import java.util.Random;

public class Producer extends Thread {

	Proxy proxy;
	Random rand = new Random();
	int max;
	
	int howLong;
	long timeA, timeB, timeC;
	Double ile;
	
	public Producer(Proxy proxy, int max) {
		this.proxy = proxy;
		this.max = max;
	}
	
	public synchronized void loguj()
	{
		ile++;
		Main.wynikiA[Main.watki][Main.czasy]+=( (timeC-timeA)-(Main.wynikiA[Main.watki][Main.czasy]))/ile;
		Main.wynikiB[Main.watki][Main.czasy]+=( (timeA-timeB)-(Main.wynikiB[Main.watki][Main.czasy]))/ile;
	}

	public void run() {
		try {
			ile=0.0;
			while(true) {

				int ileProdukowac = rand.nextInt(10)+1;
				timeB = System.nanoTime();
				Future result = proxy.produce(ileProdukowac);
				timeA = System.nanoTime();
				result.get();
				timeC = System.nanoTime();
			}
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
}
