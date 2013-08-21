package zad431;

import java.util.Random;

public class ProcesA extends Thread{

	Random rand = new Random();
	int id;
	Zasoby zas;
	
	public ProcesA( int i, Zasoby z) {
		id=i;
		zas=z;
	}	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(rand.nextInt(1000)+3000);
				
				zas.wezZasobA();
				//System.out.println("proces " + id + " korzysta z A, zasoby: " + zas.ileA + " " + zas.ileB);				Thread.sleep(rand.nextInt(1000)+1000);
				Thread.sleep(rand.nextInt(1000)+3000);
				zas.zwolnijZasobA();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
