package zad437;

public class Main {

public static void main(String[] args) throws InterruptedException{		
		
		int iloscProcesow = 9;
		
		Thread procesy[] = new Thread[iloscProcesow];
		
		Obsluga o = new Obsluga();

		Thread stan = new Stan(o);
		
		for(int i=0; i<iloscProcesow; ++i){
			procesy[i] = new Proces(i+1, o);
		}
		
		stan.start();

		for(int i=0; i<iloscProcesow; ++i){
			procesy[i].start();
		}
		
		for(int i=0; i<iloscProcesow; ++i){
			procesy[i].join();
		}		
	}
}
