package zad4410;

import java.util.Random;


public class Proces1 extends Thread{
	
	Random rand = new Random();
	Zasoby zas;
	
	public Proces1(Zasoby z) {
		zas=z;
	}	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(rand.nextInt(1000)+3000);
				zas.przydziel1();
				Thread.sleep(rand.nextInt(1000)+3000);
				zas.zwolnij1();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

}
