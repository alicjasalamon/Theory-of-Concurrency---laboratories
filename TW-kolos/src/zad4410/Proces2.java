package zad4410;

import java.util.Random;

public class Proces2 extends Thread {


	Random rand = new Random();
	Zasoby zas;
	
	char obecnyZasob;
	public Proces2(Zasoby z) {
		zas=z;
	}	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(rand.nextInt(1000)+3000);
				obecnyZasob = zas.przydziel2();
				Thread.sleep(rand.nextInt(1000)+3000);
				zas.zwolnij2(obecnyZasob);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
