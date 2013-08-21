import java.util.Random;

public class Consumer extends Thread {

	Proxy proxy;
	Random rand = new Random();
	Future result;
	int max;
	long timeA, timeB, timeC;
	
	Double ile;

	public Consumer(Proxy proxy, int max) {
		this.proxy = proxy;
		this.max=max;
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
				int howMany = rand.nextInt(10)+1;
				timeB = System.nanoTime();
				result = proxy.consume(howMany);
				timeA = System.nanoTime();
				result.get();
				timeC = System.nanoTime();
				//System.out.println("A " + (System.nanoTime()-timeA) + "\tB " + (timeA-timeB));
				loguj();
			}
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}	
	}
}
