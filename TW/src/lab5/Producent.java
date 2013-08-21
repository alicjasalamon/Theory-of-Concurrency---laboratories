package lab5;

import java.util.Random;

public class Producent extends Thread {

	int id;
	Random rand;
	
	public Producent(int i) {
		this.id = i;
		rand = new Random();
	}
	
	public void run(){
		while(true){
			try {
				int i = Monitor.wstaw_poczatek();
				//System.out.println("producent " + id +" produkuje sobie element " + Main.bufor[i] );
				Thread.sleep(rand.nextInt(1000)+1000);
			//	Monitor.wypiszStan();
				Monitor.wstaw_koniec(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

}
