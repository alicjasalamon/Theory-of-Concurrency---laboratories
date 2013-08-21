package zad431;


public class Main {

public static void main(String[] args) throws InterruptedException{		
		
		int iloscProcesowA = 3;
		int iloscProcesowB = 3;
		int iloscProcesowAB = 3;
		
		Thread procesyA[] = new Thread[iloscProcesowA];
		Thread procesyB[] = new Thread[iloscProcesowB];
		Thread procesyAB[] = new Thread[iloscProcesowAB];
		
		Zasoby zasoby = new Zasoby();

		for(int i=0; i<iloscProcesowA; ++i){
			procesyA[i] = new ProcesA(i, zasoby);
		}

		for(int i=0; i<iloscProcesowB; ++i){
			procesyB[i] = new ProcesB(i, zasoby);
		}
		
		for(int i=0; i<iloscProcesowAB; ++i){
			procesyAB[i] = new ProcesAB(i, zasoby);
		}

		for(int i=0; i<iloscProcesowA; ++i){
			procesyA[i].start();
		}
		
		for(int i=0; i<iloscProcesowB; ++i){
			procesyB[i].start();
		}
		
		for(int i=0; i<iloscProcesowAB; ++i){
			procesyAB[i].start();
		}
		
		for(int i=0; i<iloscProcesowA; ++i){
			procesyA[i].join();
		}
		
		for(int i=0; i<iloscProcesowB; ++i){
			procesyB[i].join();
		}
		
		for(int i=0; i<iloscProcesowAB; ++i){
			procesyAB[i].join();
		}
		

		
	}
}
