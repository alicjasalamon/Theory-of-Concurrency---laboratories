package zad439;


public class Main {

	public static void main(String[] args) throws InterruptedException{		
		
		int iloscPar = 5;
		
		Thread kobiety[] = new Thread[iloscPar];
		Thread mezczyzni[] = new Thread[iloscPar];		
		
		Kelner k = new Kelner(iloscPar);

		for(int i=0; i<iloscPar; ++i){
			kobiety[i] = new Kobieta(i, k);
			mezczyzni[i] = new Mezczyzna(i, k);
		}
		
		for(int i=0; i<iloscPar; ++i){
			kobiety[i].start();
			mezczyzni[i].start();
		}
		
		for(int i=0; i<iloscPar; ++i){
			kobiety[i].join();
			mezczyzni[i].join();
		}	
	}
}
