package zad4410;

import java.util.Random;

public class Proces3 extends Thread{

	Random rand = new Random();
	Zasoby zas;
	
	char obecnyZasob;
	public Proces3(Zasoby z) {
		zas=z;
	}	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(rand.nextInt(1000)+3000);
				obecnyZasob = zas.przydziel3();
				Thread.sleep(rand.nextInt(1000)+3000);
				zas.zwolnij3(obecnyZasob);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
