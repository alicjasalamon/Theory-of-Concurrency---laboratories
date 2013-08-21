package lab5;

import java.util.Random;

public class Konsument extends Thread {

	int id;
	Random rand;
	
	public Konsument(int i) {
		this.id = i;
		rand = new Random();
	}
	
	public void run(){
		while(true){
			try {
				int i = Monitor.pobierz_poczatek();
				//System.out.println("konsument " + id +" je sobie element " + Main.bufor[i] );
				Thread.sleep(rand.nextInt(1000)+1000);
				//Monitor.wypiszStan();
				Monitor.pobierz_koniec(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

}
