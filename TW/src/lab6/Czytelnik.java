package lab6;

import java.util.Random;

public class Czytelnik extends Thread {
	
	int id;
	Random rand;
	Czytelnia czytelnia;
	Biblioteka biblioteka;
	
	public Czytelnik(int i, Czytelnia czytelnia, Biblioteka b) {
		this.id = i;
		rand = new Random();
		this.czytelnia = czytelnia;
		biblioteka =b;
	}
	
	public void run(){
		while(true){
			try {
				czytelnia.poczatekCzytania();
				//System.out.println("czytelnik " + id + " zaczyna czytac " + biblioteka.biblioteka);
				Thread.sleep(rand.nextInt(1000)+1000);
				//System.out.println("czytelnik " + id + " konczy czytac " + biblioteka.biblioteka);
				czytelnia.koniecCzytania();
				//Thread.sleep(rand.nextInt(1000)+1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	
}
