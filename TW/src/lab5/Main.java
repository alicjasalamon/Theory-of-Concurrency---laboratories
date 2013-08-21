package lab5;

public class Main {
	
	public final static int rozmiarBufora = 10;
	public final static int iloscProducentow =3, iloscKonsumetow=3;

	
	public static Thread producenci[];
	public static Thread konsumenci[];
	public static Thread wypisz= new Wypisywanie();
	
	public static int bufor[]=new int[rozmiarBufora];
	
	public static void main(String[] args) throws InterruptedException{		
		
		//zainiacjalizuj bufor
		for(int i=0; i<rozmiarBufora; i++)
			bufor[i]=i;
		
		producenci = new Thread[iloscProducentow];
		konsumenci = new Thread[iloscKonsumetow];
		
		for(int i=0; i<iloscProducentow; ++i){
			producenci[i] = new Producent(i);
		}

		for(int i=0; i<iloscKonsumetow; ++i){
			konsumenci[i] = new Konsument(i);
		}
		
		wypisz.start();
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
