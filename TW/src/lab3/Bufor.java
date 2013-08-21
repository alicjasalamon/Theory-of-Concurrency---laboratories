package lab3;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bufor {

	int rozmiarBufora = 10;
	int ile=-1;
	Random rand = new Random();
	int[] bufor;
	
	public Bufor() {
		bufor = new int[rozmiarBufora];
		for(int i=0; i<rozmiarBufora; i++)
			bufor[i]=-1;
	}
	
	public synchronized void konsumuj(int id) throws InterruptedException
	{
		while(ile<0) wait();
		System.out.println("konsument nr " + id + " sobie zjada porcje " + bufor[ile] + " z pozycji " + ile) ;
		ile--;
		Thread.sleep(1000);
		notifyAll();
	}
	
	public synchronized void produkuj(int id) throws InterruptedException
	{
		while(ile>=rozmiarBufora-1) wait();
		int n = rand.nextInt(20);
		ile++;
		bufor[ile]=n;
		System.out.println("producent nr " + id + " produkuje sobie porcje " + n + " na pozycji " + ile) ;
		Thread.sleep(1000);
		notifyAll();
	}
}
