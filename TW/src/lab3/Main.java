package lab3;

public class Main {
	
	public final static int rozmiarBufora = 1;
	public final static int iloscProducentow =3, iloscKonsumetow=3;
	public static Bufor bufor;
	public static Thread producenci[];
	public static Thread konsumenci[];
	
	public static void main(String[] args) throws InterruptedException{		
		
		bufor = new Bufor();
		
		producenci = new Thread[iloscProducentow];
		konsumenci = new Thread[iloscKonsumetow];
		
		for(int i=0; i<iloscProducentow; ++i){
			producenci[i] = new Producent(bufor, i);
		}

		for(int i=0; i<iloscKonsumetow; ++i){
			konsumenci[i] = new Konsument(bufor, i);
		}
		
		for(int i=0; i<iloscProducentow; ++i){
			producenci[i].start();
		}

		for(int i=0; i<iloscKonsumetow; ++i){
			konsumenci[i].start();
		}
		
		for(int i=0; i<iloscProducentow; ++i){
			producenci[i].join();
		}

		for(int i=0; i<iloscKonsumetow; ++i){
			konsumenci[i].join();
		}
		
	}
	
}
