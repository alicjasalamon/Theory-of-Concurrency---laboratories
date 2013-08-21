package zad431;

import java.util.Random;

public class ProcesB extends Thread{

	Random rand = new Random();
	int id;
	Zasoby zas;
	
	public ProcesB( int i, Zasoby z) {
		id=i;
		zas=z;
	}	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(rand.nextInt(1000)+3000);
				
				zas.wezZasobB();
				//System.out.println("proces " + id + " korzysta z B zasoby: " + zas.ileA + " " + zas.ileB);
				Thread.sleep(rand.nextInt(1000)+3000);
				zas.zwolnijZasobB();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
