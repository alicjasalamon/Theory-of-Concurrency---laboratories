package zad431;

import java.util.Random;

public class ProcesAB extends Thread{

	Random rand = new Random();
	int id;
	Zasoby zas;
	
	public ProcesAB( int i, Zasoby z) {
		id=i;
		zas =z;
	}	
	
	public void run(){
		while(true){
			try {
				Thread.sleep(rand.nextInt(1000)+3000);
				
				zas.wezZasobAB();
				//System.out.println("proces " + id + " korzysta z A i B, zasoby: " + zas.ileA + " " + zas.ileB);
				Thread.sleep(rand.nextInt(1000)+3000);
				zas.zwolnijZasobAB();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
