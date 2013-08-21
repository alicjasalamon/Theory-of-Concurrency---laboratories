package zad439;

import java.util.Random;

public class Kobieta extends Thread {

	Random rand = new Random();
	int id;
	Kelner kelner;
	
	public Kobieta(int i, Kelner k) {
		id=i;
		kelner=k;
	}	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(rand.nextInt(1000)+1000);
				kelner.chceStolik(id);
				Thread.sleep(rand.nextInt(1000)+1000);
				kelner.zwalaniam();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
