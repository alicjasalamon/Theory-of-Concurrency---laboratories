package zad437;

import java.util.Random;


public class Proces extends Thread {

	Random rand = new Random();
	int id;
	Obsluga obs;
	int drukarka=-1;
	
	public Proces( int i, Obsluga o) {
		id=i;
		obs=o;
	}	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(rand.nextInt(1000)+1000);
				drukarka = obs.zajmij(id);
				Thread.sleep(rand.nextInt(1000)+1000);
				obs.zwolnij(drukarka);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
