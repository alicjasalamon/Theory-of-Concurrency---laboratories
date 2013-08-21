package lab6;

import java.util.Random;

public class Pisarz extends Thread {
	
	int id;
	Random rand;
	Czytelnia czytelnia;
	Biblioteka biblioteka;
	
	public Pisarz(int i, Czytelnia czytelnia, Biblioteka b) {
		this.id = i;
		rand = new Random();
		this.czytelnia = czytelnia;
		biblioteka=b;
	}
	
	public void run(){
		while(true){
			try {
				czytelnia.poczatekPisania();
				biblioteka.biblioteka++;
				//System.out.println("pisarz " + id + " zaczyna zapisywac " + biblioteka.biblioteka);
				Thread.sleep(rand.nextInt(1000)+1000);
				//System.out.println("pisarz " + id + " konczy zapisywac " + biblioteka.biblioteka);
				czytelnia.koniecPisania();
				//Thread.sleep(rand.nextInt(1000)+1000);				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

}
