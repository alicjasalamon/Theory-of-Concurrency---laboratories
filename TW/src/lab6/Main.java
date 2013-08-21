package lab6;


public class Main {
	
	public final static int iloscCzytelnikow =3, iloscPisarzy=70;

	
	public static Thread czytelnicy[];
	public static Thread pisarze[];
	
	public static Czytelnia czytelnia = new Czytelnia();
	
	static Biblioteka biblioteka = new Biblioteka(0);
	
	public static void main(String[] args) throws InterruptedException{		
		
		czytelnicy = new Thread[iloscCzytelnikow];
		pisarze = new Thread[iloscPisarzy];
		
		for(int i=0; i<iloscCzytelnikow; ++i){
			czytelnicy[i] = new Czytelnik(i, czytelnia, biblioteka);
		}

		for(int i=0; i<iloscPisarzy; ++i){
			pisarze[i] = new Pisarz(i, czytelnia, biblioteka);
		}
		
		for(int i=0; i<iloscCzytelnikow; ++i){
			czytelnicy[i].start();
		}

		for(int i=0; i<iloscPisarzy; ++i){
			pisarze[i].start();
		}
		
		for(int i=0; i<iloscCzytelnikow; ++i){
			czytelnicy[i].join();
		}

		for(int i=0; i<iloscPisarzy; ++i){
			pisarze[i].join();
		}
		
	}
	
}
